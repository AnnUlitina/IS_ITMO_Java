package ru.itmo.service;

import itmo.QueueName;
import itmo.cats.KotickColor;
import itmo.messaging.AddFriendMessage;
import itmo.messaging.CreateCatMessage;
import itmo.messaging.DeleteCatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayServiceCat {
    private final RabbitTemplate rabbitTemplate;
    private final CatWebClient catWebClient;
    private final WebOwnersClientImpl ownerWebClient;

    @Autowired
    public GatewayServiceCat(RabbitTemplate rabbitTemplate,  CatWebClient catWebClient, WebOwnersClientImpl ownerWebClient) {
        this.rabbitTemplate = rabbitTemplate;
        this.catWebClient = catWebClient;
        this.ownerWebClient = ownerWebClient;
    }
    public OwnerDTO getOwnerById(Long id) {
        return ownerWebClient.getOwnerById(id);
    }

    public List<OwnerDTO> findAllOwners() {
        return ownerWebClient.findAllOwners();
    }
    public List<CatDTO> getByParams(List<String> name,
                             List<Long> id,
                             List<LocalDate> birthDay,
                             List<KotickColor> color,
                             List<String> breed,
                             List<Long> ownerId) {
        return catWebClient.getByParams(id, name, birthDay, color, breed, ownerId);
    }
    public CatDTO getCat(Long uuid, Long ownerUuid) {
        return catWebClient.getById(uuid, ownerUuid);
    }

    public CatDTO adminGetCatByID(Long id) {
        return catWebClient.adminGetById(id);
    }
    public void createCat(String name,
                          LocalDate birthDay,
                          String breed,
                          KotickColor color,
                          Long ownerId) {
        CreateCatMessage message = CreateCatMessage.builder()
                .name(name)
                .birthDay(birthDay)
                .breed(breed)
                .catColor(color)
                .catOwnerUuid(ownerId)
                .build();
        rabbitTemplate.convertAndSend(QueueName.CAT_CREATE, message);
    }
    public void makeFriends(Long catId, Long catFriendId) {
        AddFriendMessage message = AddFriendMessage.builder()
                .catId(catId)
                .friendId(catFriendId)
                .build();
        rabbitTemplate.convertAndSend(QueueName.ADD_FRIEND, message);
    }

    public List<CatDTO> adminGetByParams(List<String> name,
                                         List<Long> id,
                                         List<LocalDate> birthDay,
                                         List<KotickColor> color,
                                         List<String> breed) {
        return catWebClient.adminGetByParams(id, name, birthDay, color, breed);
    }

    public List<CatDTO> findAllFriends(Long id) {
        return catWebClient.adminGetByParams(ownerWebClient.findAllCats(id), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void removeCat(Long catId) {
        DeleteCatMessage message = DeleteCatMessage.builder().build();
        rabbitTemplate.convertAndSend(QueueName.DELETE_CAT, message);
    }
}
