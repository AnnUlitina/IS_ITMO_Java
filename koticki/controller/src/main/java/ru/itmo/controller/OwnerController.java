package ru.itmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.cats.service.CatDTO;
import ru.itmo.cats.service.OwnerDTO;
import ru.itmo.cats.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/owners")
@Validated
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public OwnerDTO getOwnerById(@PathVariable int id) {
        return ownerService.getOwner(id);
    }
    @GetMapping
    public List<OwnerDTO> findAllOwners() {
        return ownerService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOwner(@RequestBody OwnerDTO ownerDto) {
        ownerService.createOwner(ownerDto);
        ownerService.save(ownerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable int id) {
        ownerService.deleteById(id);
    }
}
