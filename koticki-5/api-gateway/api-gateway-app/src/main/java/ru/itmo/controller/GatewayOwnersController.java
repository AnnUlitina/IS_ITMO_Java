package ru.itmo.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.CatDTO;
import ru.itmo.OwnerDTO;
import ru.itmo.UserDTO;
import ru.itmo.service.GatewayServiceCat;
import ru.itmo.service.GatewayServiceOwner;
import ru.itmo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/owners/")
@AllArgsConstructor
public class GatewayOwnersController {
    private final GatewayServiceOwner gatewayServiceOwner;
    private final GatewayServiceCat gatewayServiceCat;
    private final UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createOwner(@RequestBody OwnerDTO ownerDto) {
        gatewayServiceOwner.createOwner(ownerDto.getName(), ownerDto.getBirthday());
    }
    @GetMapping()
    public List<OwnerDTO> getAll() {
        return gatewayServiceOwner.findAllOwners();
    }

    @GetMapping("/{id}")
    public OwnerDTO getOwner(@PathVariable Long id) {
        return gatewayServiceOwner.findOwnerById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/cats")
    public List<CatDTO> findAllCats() {
        UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Long> ownerIds =new ArrayList<>();
        ownerIds.add(user.getOwnerId());
        return gatewayServiceCat.getByParams(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), ownerIds);
    }
}
