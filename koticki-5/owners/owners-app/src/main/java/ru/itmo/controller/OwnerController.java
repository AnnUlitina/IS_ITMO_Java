package ru.itmo.controller;

import itmo.messaging.CreateOwnerMessage;
import itmo.messaging.DeleteOwnerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.OwnerDTO;
import ru.itmo.service.OwnerService;

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
    public OwnerDTO getOwnerById(@PathVariable Long id) {
        return ownerService.getOwner(id);
    }
    @GetMapping
    public List<OwnerDTO> findAllOwners() {
        return ownerService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOwner(@RequestBody OwnerDTO ownerDto) {
        CreateOwnerMessage ownerMessage = new CreateOwnerMessage(ownerDto.getName(), ownerDto.getBirthday());
        ownerService.createOwner(ownerMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(new DeleteOwnerMessage(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
