package ru.itmo.controller;

import itmo.cats.KotickColor;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.CatDTO;
import ru.itmo.Role;
import ru.itmo.UserDTO;
import ru.itmo.service.GatewayServiceCat;
import ru.itmo.service.MyUserDetails;
import ru.itmo.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cats/")
public class GatewayCatsController {
    private final GatewayServiceCat gatewayServiceCat;
    private final UserService userService;

    @Autowired
    public GatewayCatsController(GatewayServiceCat gatewayServiceCat, UserService userService) {
        this.gatewayServiceCat = gatewayServiceCat;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN')")
    @GetMapping
    public List<CatDTO> getFilteredCats(@RequestParam(defaultValue = "") List<Long> id,
                                        @RequestParam(defaultValue = "") List<String> name,
                                        @RequestParam(defaultValue = "") List<LocalDate> birthDay,
                                        @RequestParam(defaultValue = "") List<KotickColor> color,
                                        @RequestParam(defaultValue = "") List<String> breed) {
        MyUserDetails user = userService.getCurrentUser();
        List<Long> ownerIds = new ArrayList<>();
        ownerIds.add(user.getUserId());
        if(user.getAuthorities().contains(Role.ADMIN)) {
            return gatewayServiceCat.adminGetByParams(name, id, birthDay, color, breed);
        }
        return gatewayServiceCat.getByParams(name, id, birthDay, color, breed, ownerIds);
    }

    @GetMapping("/cats/{id}/{ownerid}")
    public CatDTO getCat(@PathVariable Long id,
                         @PathVariable Long ownerid) {
        return gatewayServiceCat.getCat(id, ownerid);
    }

    @GetMapping("/admin/cats/{id}")
    public CatDTO getCatAdmin(@PathVariable Long id) {
        return gatewayServiceCat.adminGetCatByID(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public CatDTO getCatById(@PathVariable Long id)
    {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return gatewayServiceCat.getCat(id, user.getUserId());
    }

    @PostMapping
    public void createCat(@RequestBody CatDTO catDto) {
        gatewayServiceCat.createCat(catDto.getName(), catDto.getBirthday(), catDto.getBreed(), catDto.getColor(), catDto.getOwnerId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}/friends")
    public void makeFriends(@PathVariable(value = "id") Long catID1, @RequestParam(value = "id") Long catId2) {
        gatewayServiceCat.makeFriends(catID1, catId2);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public void removeCat(@PathVariable Long id) {
        gatewayServiceCat.removeCat(id);
        throw new RuntimeException("Unauthorized attempt to delete cat");
    }
}
