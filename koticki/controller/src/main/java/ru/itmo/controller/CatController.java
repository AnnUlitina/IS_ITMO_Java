package ru.itmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.cats.service.CatDTO;
import ru.itmo.cats.service.CatService;
import ru.itmo.cats.service.OwnerDTO;
import ru.itmo.cats.service.exceptions.CatDoesntExistsException;
import ru.itmo.model.Filter;
import ru.itmo.model.KotickColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cats")
@Validated
public class CatController {
    @Autowired
    private CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping(value = "/{id}")
    public CatDTO getCatById(@PathVariable(name = "id") int id) {
        CatDTO cat = catService.getCat(id);
        return cat;
    }

    @PostMapping
    public ResponseEntity<?> createCat(@RequestBody CatDTO catDto) {
        catService.addCat(catDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCat(@PathVariable(name = "id") int id) throws CatDoesntExistsException {
        catService.deleteCat(id);
    }

    @GetMapping(value = "/friends/{id}")
    public List<CatDTO> getAllFriendsById(@PathVariable(name = "id") int id) {
        return catService.getAllFriendsById(id);
    }

    @GetMapping(value = "/owner/{id}")
    public OwnerDTO getOwnerByCatId(@PathVariable(name = "id") int id) {
        return catService.getOwnerByCatId(id);
    }
    @GetMapping
    public List<CatDTO> getKitties(@RequestParam Map<String, String> params) {

        Filter filter = new Filter(params.get("name"), params.get("breed"), params.get("color"));
        return catService.findKitties(filter);
    }
    @PutMapping(value = "/{catId1}/friend/{catId2}")
    public void makeFriends(@PathVariable(name = "catId1") int catId1, @PathVariable(name = "catId2") int catId2) {
        catService.makeFriends(catId1, catId2);
    }

    @DeleteMapping(value = "/{catId1}/friend/{catId2}")
    public void dropFriends(@PathVariable(name = "catId1") int catId1, @PathVariable(name = "catId2") int catId2) {
        catService.dropFriends(catId1, catId2);
    }

    @PutMapping(value = "{catId}/owner/{ownerId}")
    public void changeOwner(@PathVariable(name = "catId") int catId, @PathVariable(name = "ownerId") int ownerId) {
        catService.changeOwner(catId, ownerId);
    }
}
