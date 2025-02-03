package ru.itmo.service;

import itmo.QueueName;
import itmo.cats.Filter;
import itmo.cats.KotickColor;
import itmo.messaging.AddFriendMessage;
import itmo.messaging.AdminAddFriendMessage;
import itmo.messaging.CreateCatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.*;
import ru.itmo.dao.CatRepository;
import ru.itmo.dao.KittySpecifications;
import ru.itmo.model.Cat;
import ru.itmo.service.exceptions.CatDoesntExistsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatService {
    @Autowired
    private final CatRepository catRepository;
    public CatDTO getCat(Long id) {
        return MappingUtils.mapToCatDto(catRepository.getReferenceById(id));
    }

    @RabbitListener(queues = QueueName.CAT_CREATE)
    public void createCat(CreateCatMessage createCatMessage) {
        catRepository.save(
                Cat.builder()
                        .id(createCatMessage.getUuid())
                        .name(createCatMessage.getName())
                        .ownerId(createCatMessage.getCatOwnerUuid())
                        .birthday(createCatMessage.getBirthDay())
                        .breed(createCatMessage.getBreed())
                        .color(createCatMessage.getCatColor())
                        .build()
        );
    }

    @RabbitListener(queues = QueueName.ADD_FRIEND)
    public void addFriend(AddFriendMessage message) {
        Long catUuid = message.getCatId();
        Long friendUuid = message.getFriendId();
        Cat byUuidAndOwnerId = catRepository.findByIdAndOwnerId(catUuid, message.getOwnerId());
        Cat cat = catRepository.findById(friendUuid).orElseThrow(RuntimeException::new);

        byUuidAndOwnerId.addFriend(cat);
        catRepository.save(byUuidAndOwnerId);
        catRepository.save(cat);
    }

    @RabbitListener(queues = QueueName.ADD_FRIEND_ADMIN)
    public void addFriendAdmin(AdminAddFriendMessage message) {
        Long catUuid = message.getCatId();
        Long friendUuid = message.getFriendId();
        Cat byUuidAndOwnerId = catRepository.findById(catUuid).orElseThrow(RuntimeException::new);
        Cat cat = catRepository.findById(friendUuid).orElseThrow(RuntimeException::new);

        byUuidAndOwnerId.addFriend(cat);
        catRepository.save(byUuidAndOwnerId);
        catRepository.save(cat);
    }

    @Transactional
    @RabbitListener(queues = QueueName.DELETE_CAT)
    public void deleteCat(Long uuid) throws CatDoesntExistsException {
        Cat catRepo = catRepository.findById(uuid).orElseThrow(() -> new CatDoesntExistsException(String.valueOf(uuid)));
        catRepository.delete(catRepo);
    }

    @Transactional
    public void updateCat(CatDTO user) throws CatDoesntExistsException {
        Cat catFromRepo = catRepository.findById(user.getId()).orElseThrow(() -> new CatDoesntExistsException(String.valueOf(user.getId())));
        catFromRepo.setColor(user.getColor());
        catFromRepo.setName(user.getName());
        catFromRepo.setBreed(user.getBreed());
        catFromRepo.setBirthday(user.getBirthday());

        catRepository.save(catFromRepo);
    }

    public List<CatDTO> getAllCats() {
        List<Cat> all = catRepository.findAll();
        List<CatDTO> allDTOs = new ArrayList<>();
        for (Cat cat : all) {
            allDTOs.add(MappingUtils.mapToCatDto(cat));
        }
        return allDTOs;
    }
    public List<CatDTO> findKitties(Filter filter, String currentUsername) {
        return catRepository.findAll(KittySpecifications.byFilter(filter, currentUsername)).stream()
                .map(MappingUtils::mapToCatDto)
                .collect(Collectors.toList());
    }
    public List<CatDTO> getAllFriendsById(Long id) {
        List<Cat> allFriends = catRepository.getReferenceById(id).getCatsFriends();
        List<CatDTO> allFriendsDTOs = new ArrayList<>();
        for (Cat friend : allFriends) {
            allFriendsDTOs.add(MappingUtils.mapToCatDto(friend));
        }
        return allFriendsDTOs;
    }

    @Transactional
    public void dropFriends(Long catId1, Long catId2) {
        Cat cat1 = catRepository.getReferenceById(catId1);
        Cat cat2 = catRepository.getReferenceById(catId2);
        cat1.dropFriend(cat2);
        cat2.dropFriend(cat1);
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        catRepository.saveAll(cats);
    }

    public CatDTO adminReadCat(Long catUuid) {
        return catRepository.findById(catUuid)
                .map(MappingUtils::mapToCatDto)
                .orElseThrow(RuntimeException::new);
    }

    public List<CatDTO> adminGetByParams(List<String> name, List<Long> id, List<LocalDate> birthDay, List<KotickColor> color, List<String> breed) {
        Specification<Cat> spec = Specification
                .where(getSpec("name", name))
                .and(getSpec("id", id))
                .and(getSpec("birthDay", birthDay))
                .and(getSpec("catColor", color))
                .and(getSpec("breed", breed));

        return catRepository.findAll(spec)
                .stream()
                .map(MappingUtils::mapToCatDto)
                .collect(Collectors.toList());
    }

    private <T> Specification<Cat> getSpec(String field, List<T> values) {
        return (values != null) ?
                (root, query, criteriaBuilder) -> root.get(field).in(values) : null;
    }
}