package ru.itmo.cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.cats.service.exceptions.CatDoesntExistsException;
import ru.itmo.dao.CatRepository;
import ru.itmo.dao.KittySpecifications;
import ru.itmo.dao.OwnerRepository;
import ru.itmo.model.Cat;
import ru.itmo.model.Filter;
import ru.itmo.model.KotickColor;
import ru.itmo.model.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {
    private CatRepository catRepository;
    private OwnerRepository ownerRepository;
    @Autowired
    public CatService(CatRepository catRepository, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }
    public CatDTO getCat(int id) {
        return MappingUtils.mapToCatDto(catRepository.getReferenceById(id));
    }

    @Transactional
    public void deleteCat(int id) throws CatDoesntExistsException {
        Cat catRepo = catRepository.findById(id).orElseThrow(() -> new CatDoesntExistsException(id));
        catRepository.delete(catRepo);
    }

    @Transactional
    public void updateCat(CatDTO user) throws CatDoesntExistsException {
        Cat catFromRepo = catRepository.findById(user.getId()).orElseThrow(() -> new CatDoesntExistsException(user.getId()));
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
    public List<CatDTO> findKitties(Filter filter) {
        return catRepository.findAll(KittySpecifications.byFilter(filter)).stream()
                .map(MappingUtils::mapToCatDto)
                .collect(Collectors.toList());
    }
    public List<CatDTO> getAllFriendsById(int id) {
        List<Cat> allFriends = catRepository.getReferenceById(id).getCatsFriends();
        List<CatDTO> allFriendsDTOs = new ArrayList<>();
        for (Cat friend : allFriends) {
            allFriendsDTOs.add(MappingUtils.mapToCatDto(friend));
        }
        return allFriendsDTOs;
    }


    public OwnerDTO getOwnerByCatId(int id) {
        OwnerDTO ownerDTO = MappingUtils.mapToOwnerDto(catRepository.getReferenceById(id).getOwner());
        return ownerDTO;
    }

    @Transactional
    public void makeFriends(int catId1, int catId2) {
        Cat cat1 = catRepository.getReferenceById(catId1);
        Cat cat2 = catRepository.getReferenceById(catId2);
        if (cat1.getCatsFriends().contains(cat2))
            throw new IllegalArgumentException("They are already friends");

        cat1.addFriend(cat2);
        cat2.addFriend(cat1);
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        catRepository.saveAll(cats);
    }

    @Transactional
    public void dropFriends(int catId1, int catId2) {
        Cat cat1 = catRepository.getReferenceById(catId1);
        Cat cat2 = catRepository.getReferenceById(catId2);
        cat1.dropFriend(cat2);
        cat2.dropFriend(cat1);
        List<Cat> cats = new ArrayList<>();
        cats.add(cat1);
        cats.add(cat2);
        catRepository.saveAll(cats);
    }

    @Transactional
    public void changeOwner(int catId, int newOwnerId) {
        Owner newOwner = ownerRepository.getById(newOwnerId);
        Cat cat = catRepository.getReferenceById(catId);
        Owner oldOwner = cat.getOwner();

        newOwner.addCat(cat);
        cat.setOwner(newOwner);
        catRepository.save(cat);
        ownerRepository.save(newOwner);
    }

    @Transactional
    public List<CatDTO> getCatsByBreed(String breed) {
        List<CatDTO> kitties = new ArrayList<>();
        for (Cat kitty : catRepository.findCatByBreed(breed)) {
            kitties.add(MappingUtils.mapToCatDto(kitty));
        }
        return kitties;
    }

    @Transactional
    public List<CatDTO> getCatsByColor(KotickColor kotickColor) {
        KotickColor colour = KotickColor.valueOf(String.valueOf(kotickColor));
        List<CatDTO> kitties = new ArrayList<>();
        for (Cat kitty : catRepository.findAll()) {
            if (kitty.getColor().equals(colour)) {
                kitties.add(MappingUtils.mapToCatDto(kitty));
            }
        }
        return kitties;
    }

    @Transactional
    public List<CatDTO> getCatsByColorAndBreed(KotickColor kotickColor, String breed) {
        KotickColor colour = KotickColor.valueOf(String.valueOf(kotickColor));
        List<CatDTO> kitties = new ArrayList<>();
        for (Cat kitty : catRepository.findAll()) {
            if (kitty.getColor().equals(colour) && kitty.getBreed().equals(breed)) {
                kitties.add(MappingUtils.mapToCatDto(kitty));
            }
        }
        return kitties;
    }

    public CatDTO addCat(CatDTO catDto) {
        Cat dao = catRepository.save(MappingUtils.mapToCatDao(catDto));
        int generatedId = dao.getId();
        return new CatDTO(generatedId, catDto.getName(), catDto.getBirthday(), catDto.getBreed(), catDto.getColor());
    }
}