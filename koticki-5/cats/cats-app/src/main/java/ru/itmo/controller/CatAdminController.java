package ru.itmo.controller;

import itmo.cats.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.CatDTO;
import ru.itmo.service.CatService;

import java.util.List;
import java.util.Map;

@RequestMapping("/admin/cats")
@RestController
@RequiredArgsConstructor
public class CatAdminController {
    @Autowired
    private final CatService catService;

    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> readCat(@PathVariable("id") Long catUuid) {
        CatDTO catDTO = catService.adminReadCat(catUuid);
        return new ResponseEntity<>(catDTO, HttpStatus.OK);
    }

//    @GetMapping
//    public List<CatDTO> getFilteredCats(@RequestParam Map<String, String> params) {
//        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        Filter filter = new Filter(params.get("name"), params.get("breed"), params.get("color"));
//        return catService.findKitties(filter, currentUser);
//    }
}
