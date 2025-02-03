package ru.itmo.cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.cats.service.exceptions.OwnerDoesntExistException;
import ru.itmo.dao.CatRepository;
import ru.itmo.dao.OwnerRepository;
import ru.itmo.model.Cat;
import ru.itmo.model.Owner;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {
    private OwnerRepository ownerRepository;

    private CatRepository catRepository;
    @Autowired
    public OwnerService(OwnerRepository ownerRepository, CatRepository catRepository) {
        this.ownerRepository = ownerRepository;
        this.catRepository = catRepository;
    }

    public OwnerDTO getOwner(int id) {
        return MappingUtils.mapToOwnerDto(ownerRepository.getById(id));
    }

    public void save(OwnerDTO owner) {
        ownerRepository.save(MappingUtils.mapToOwnerDao(owner));
    }

    public void update(OwnerDTO owner) throws OwnerDoesntExistException {
        Owner personFromRepo = ownerRepository.findById(owner.getId())
                .orElseThrow(() -> new OwnerDoesntExistException(owner.getId()));
        personFromRepo.setName(owner.getName());
        personFromRepo.setBirthday(owner.getBirthday());
        ownerRepository.save(personFromRepo);
    }

    public void delete(OwnerDTO owner) {
        ownerRepository.delete(MappingUtils.mapToOwnerDao(owner));
    }

    public List<OwnerDTO> getAll() {
        List<Owner> all = ownerRepository.findAll();
        List<OwnerDTO> allDTOs = new ArrayList<>();
        for (Owner owner : all) {
            allDTOs.add(MappingUtils.mapToOwnerDto(owner));
        }
        return allDTOs;
    }

    public List<CatDTO> getOwnerCatsById(int ownerId) {
        List<Cat> ownerCatsById = ownerRepository.getReferenceById(ownerId).getCats();
        List<CatDTO> ownerCatsDTOs = new ArrayList<>();
        for (Cat cat : ownerCatsById) {
            ownerCatsDTOs.add(MappingUtils.mapToCatDto(cat));
        }
        return ownerCatsDTOs;
    }

    public void deleteById(int ownerId) {
        Owner owner = ownerRepository.getById(ownerId);
        for (Cat cat : owner.getCats()) {
            cat.setOwner(null);
            catRepository.save(cat);
        }
        owner.getCats().clear();
        ownerRepository.save(owner);
    }

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setName(ownerDTO.getName());
        owner.setBirthday(ownerDTO.getBirthday());
        ownerRepository.save(owner);
        return MappingUtils.mapToOwnerDto(owner);
    }
}