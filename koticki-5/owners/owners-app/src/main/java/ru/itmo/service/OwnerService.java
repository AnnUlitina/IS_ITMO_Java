package ru.itmo.service;

import itmo.QueueName;
import itmo.messaging.CreateOwnerMessage;
import itmo.messaging.DeleteOwnerMessage;
import itmo.messaging.UpdateOwnerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.OwnerDTO;
import ru.itmo.dao.OwnerRepository;
import ru.itmo.exceptions.OwnerDoesntExistException;
import ru.itmo.mapping.MappingUtilsOwner;
import ru.itmo.model.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerService {
    @Autowired
    private final OwnerRepository ownerRepository;

    @RabbitListener(queues = QueueName.OWNER_CREATE)
    public void createOwner(CreateOwnerMessage createOwnerMessage) {
        Owner owner = new Owner();
        owner.setName(createOwnerMessage.getName());
        owner.setBirthday(createOwnerMessage.getBirthday());
        ownerRepository.save(owner);
    }

    @RabbitListener(queues = QueueName.OWNER_UPDATE)
    public void updateOwner(UpdateOwnerMessage updateOwnerMessage) throws OwnerDoesntExistException {
        Owner owner = ownerRepository.findById(updateOwnerMessage.getUuid())
                .orElseThrow(() -> new OwnerDoesntExistException(updateOwnerMessage.getUuid()));
        owner.setName(updateOwnerMessage.getName());
        owner.setBirthday(updateOwnerMessage.getBirthday());
        ownerRepository.save(owner);
    }

    @RabbitListener(queues = QueueName.DELETE_OWNER)
    public void deleteOwner(DeleteOwnerMessage deleteOwnerMessage) {
        Long ownerId = deleteOwnerMessage.getUuid();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(RuntimeException::new);
        ownerRepository.delete(owner);
    }
    public OwnerDTO getOwner(Long id) {
        return MappingUtilsOwner.mapToOwnerDto(ownerRepository.findById(id).orElseThrow());
    }

    public void save(OwnerDTO owner) {
        ownerRepository.save(MappingUtilsOwner.mapToOwnerDao(owner));
    }

    public List<OwnerDTO> getAll() {
        List<Owner> all = ownerRepository.findAll();
        List<OwnerDTO> allDTOs = new ArrayList<>();
        for (Owner owner : all) {
            allDTOs.add(MappingUtilsOwner.mapToOwnerDto(owner));
        }
        return allDTOs;
    }
}