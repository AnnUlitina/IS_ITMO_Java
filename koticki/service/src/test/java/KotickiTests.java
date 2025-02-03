import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.itmo.cats.service.*;
import ru.itmo.cats.service.CatDTO;
import ru.itmo.cats.service.OwnerDTO;
import ru.itmo.dao.CatRepository;
import ru.itmo.dao.OwnerRepository;
import ru.itmo.model.Cat;
import ru.itmo.model.KotickColor;
import ru.itmo.model.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KotickiTests {
    @Mock
    CatRepository catRepository;

    @Mock
    OwnerRepository ownerRepository;

    CatService catService;
    OwnerService ownerService;
    List<Cat> allCats;
    Owner owner;
    MockitoSession session;

    @BeforeEach
    public void beforeMethod() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
        catService = new CatService(catRepository, ownerRepository);
        ownerService = new OwnerService(ownerRepository, catRepository);
        owner = new Owner();
        owner.setName("Anna");
        owner.setBirthday(new Date(12346578));
        Cat cat1 = new Cat();
        cat1.setName("Busy");
        cat1.setBirthday(new Date(12346578));
        cat1.setBreed("No");
        cat1.setColor(KotickColor.BLACK);
        cat1.setOwner(owner);
        Cat cat2 = new Cat();
        cat1.setName("Sony");
        cat1.setBirthday(new Date(12346578));
        cat1.setBreed("Mainkun");
        cat1.setColor(KotickColor.GINGER);
        cat1.setOwner(owner);
        allCats = Stream.of(
                        cat1,
                        cat2)
                .toList();
    }

    @AfterEach
    public void afterMethod() {
        session.finishMocking();
    }

    @Test
    public void testGetAllCats() {
        CatService catService = new CatService(catRepository, ownerRepository);
        when(catRepository.findAll()).thenReturn(allCats);
        catService.getAllCats();
    }

    @Test
    public void testGetCat() {
        CatRepository catRepository = mock(CatRepository.class);
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        CatService catService = new CatService(catRepository, ownerRepository);
        Cat cat = new Cat();
        cat.setName("Fluffy");
        cat.setBreed("Persian");
        cat.setBirthday(new Date(2022-5-24));
        cat.setColor(KotickColor.WHITE);
        cat.setOwner(null);
        when(catRepository.getReferenceById(anyInt())).thenReturn(cat);
        CatDTO result = catService.getCat(cat.getId());
        Assertions.assertEquals(cat.getId(), result.getId());
    }

//    @Test
//    public void testGetAllFriendsById() {
//        CatRepository catRepository = mock(CatRepository.class);
//        OwnerRepository ownerRepository = mock(OwnerRepository.class);
//        CatService catService = new CatService(catRepository, ownerRepository);
//        Cat cat = new Cat();
//        cat.setName("Fluffy");
//        cat.setBreed("Persian");
//        cat.setBirthday(new Date(2010-5-24));
//        cat.setColor(KotickColor.GINGER);
//        cat.setOwner(null);
//        catService.getAllFriendsById(cat.getId());
//        verify(catRepository, times(1)).getReferenceById(cat.getId()).getCatsFriends();
//    }

//    @Test
//    public void testGetOwnerByCatId() {
//        CatRepository catRepository = mock(CatRepository.class);
//        OwnerRepository ownerRepository = mock(OwnerRepository.class);
//        CatService catService = new CatService(catRepository, ownerRepository);
//        Owner owner = new Owner();
//        owner.setName("Anna");
//        owner.setBirthday(new Date(12346578));
//        Cat cat = new Cat();
//        cat.setName("Fluffy");
//        cat.setBreed("Persian");
//        cat.setBirthday(new Date(2006-5-24));
//        cat.setColor(KotickColor.GINGER);
//        cat.setOwner(owner);
//        when(catRepository.getReferenceById(anyInt()).getOwner()).thenReturn(cat.getOwner());
//        OwnerDTO ownerDTO = catService.getOwnerByCatId(cat.getId());
//        verify(catRepository, times(1)).getReferenceById(ownerDTO.getId()).getOwner();
//    }


//    @Test
//    public void TestFindOwnerCatsById() {
//        Cat cat1 = new Cat();
//        cat1.setName("Fluffy");
//        cat1.setBirthday(new Date(2022 - 5 - 24));
//        cat1.setBreed("Persian");
//        cat1.setColor(KotickColor.WHITE);
//        cat1.setOwner(null);
//        Cat cat2 = new Cat();
//        cat1.setName("Sony");
//        cat1.setBirthday(new Date(2022 - 5 - 24));
//        cat1.setBreed("Mainkun");
//        cat1.setColor(KotickColor.GINGER);
//        cat1.setOwner(null);
//        List<Cat> ownerCats = Arrays.asList(
//                cat1,
//                cat2
//        );
//        Mockito.when(ownerRepository.getReferenceById(owner.getId()).getCats()).thenReturn(ownerCats);
//
//        List<CatDTO> expectedCatsDTO = ownerCats.stream()
//                .map(MappingUtils::mapToCatDto)
//                .collect(Collectors.toList());
//
//        List<CatDTO> actualCatsDTO = ownerService.getOwnerCatsById(owner.getId());
//        assertEquals(expectedCatsDTO, actualCatsDTO);
//    }

    @Test
    public void testGetOwner() {
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        CatRepository catRepository = mock(CatRepository.class);
        OwnerService ownerService = new OwnerService(ownerRepository, catRepository);
        Owner owner = new Owner();
        owner.setName("John");
        owner.setBirthday(new Date(12346578));
        when(ownerRepository.getById(anyInt())).thenReturn(owner);
        OwnerDTO result = ownerService.getOwner(owner.getId());
        Assertions.assertEquals(owner.getId(), result.getId());
    }

    @Test
    public void testSaveOwner() {
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        CatRepository catRepository = mock(CatRepository.class);
        OwnerService ownerService = new OwnerService(ownerRepository, catRepository);
        Owner owner = new Owner();
        owner.setName("John");
        owner.setBirthday(new Date(12346578));
        owner.setCats(new ArrayList<>());
        OwnerDTO ownerDTO = MappingUtils.mapToOwnerDto(owner);
        ownerService.save(ownerDTO);
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    public void testDeleteOwner() {
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        CatRepository catRepository = mock(CatRepository.class);
        OwnerService ownerService = new OwnerService(ownerRepository, catRepository);
        Owner owner = new Owner();
        owner.setName("John");
        owner.setBirthday(new Date(12346578));
        owner.setCats(new ArrayList<>());
        ownerService.delete(MappingUtils.mapToOwnerDto(owner));
        verify(ownerRepository, times(1)).delete(owner);
    }

    @Test
    public void testGetAllOwners() {
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        CatRepository catRepository = mock(CatRepository.class);
        OwnerService ownerService = new OwnerService(ownerRepository, catRepository);
        Owner owner1 = new Owner();
        owner1.setName("John");
        Owner owner2 = new Owner();
        owner2.setName("Alice");
        List<Owner> owners = Arrays.asList(
                owner1,
                owner2
        );
        Mockito.when(ownerRepository.findAll()).thenReturn(owners);
        List<OwnerDTO> ownersDTO = ownerService.getAll();
        List<OwnerDTO> expectedCatsDTO = owners.stream()
                .map(MappingUtils::mapToOwnerDto)
                .collect(Collectors.toList());
        assertEquals(expectedCatsDTO, ownersDTO);
    }
}