package ru.itmo.controller;

import itmo.cats.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.itmo.CatDTO;
import ru.itmo.service.CatService;
import ru.itmo.service.exceptions.CatDoesntExistsException;

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
    public ResponseEntity<CatDTO> getCatById(@PathVariable(name = "id") Long id) {
        CatDTO catDTO = catService.getCat(id);
        return new ResponseEntity<>(catDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCat(@PathVariable(name = "id") Long id) throws CatDoesntExistsException {
        catService.deleteCat(id);
    }

    @GetMapping(value = "/friends/{id}")
    public List<CatDTO> getAllFriendsById(@PathVariable(name = "id") Long id) {
        return catService.getAllFriendsById(id);
    }

//    @GetMapping
//    public List<CatDTO> getKitties(@RequestParam Map<String, String> params) {
//        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        Filter filter = new Filter(params.get("name"), params.get("breed"), params.get("color"));
//        return catService.findKitties(filter, currentUser);
//    }

    @DeleteMapping(value = "/{catId1}/friend/{catId2}")
    public void dropFriends(@PathVariable(name = "catId1") Long catId1, @PathVariable(name = "catId2") Long catId2) {
        catService.dropFriends(catId1, catId2);
    }
}
