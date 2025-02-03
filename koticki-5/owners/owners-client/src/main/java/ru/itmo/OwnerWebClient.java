package ru.itmo;

import java.util.List;
import java.util.UUID;

public interface OwnerWebClient {
    OwnerDTO getOwnerById(Long id);
    List<OwnerDTO> findAllOwners();
    List<Long> findAllCats(Long id);
}
