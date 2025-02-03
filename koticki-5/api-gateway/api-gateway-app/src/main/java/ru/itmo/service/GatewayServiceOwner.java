package ru.itmo.service;

import itmo.messaging.CreateOwnerMessage;
import itmo.messaging.DeleteOwnerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.OwnerDTO;
import ru.itmo.WebOwnersClientImpl;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatewayServiceOwner {
    private final WebOwnersClientImpl ownersClient;
    private final RabbitTemplate rabbitTemplate;

    public void createOwner(String name, LocalDate birthDay) {
        CreateOwnerMessage message = CreateOwnerMessage.builder().name(name).birthday(birthDay).build();
        rabbitTemplate.convertAndSend("CREATE_OWNER", message);
    }

    public OwnerDTO findOwnerById(Long id) {
        return ownersClient.getOwnerById(id);
    }

    public List<OwnerDTO> findAllOwners() {
        return ownersClient.findAllOwners();
    }
    public void removeOwner(Long ownerId) {
        DeleteOwnerMessage message = DeleteOwnerMessage.builder().uuid(ownerId).build();
        rabbitTemplate.convertAndSend("DELETE_OWNER", message);
    }
}
