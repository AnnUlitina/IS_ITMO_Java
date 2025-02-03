package ru.itmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class WebOwnersClientImpl implements OwnerWebClient{

    private final WebClient ownersWebClient;

    @Autowired
    public WebOwnersClientImpl(@Qualifier(value = "owner") WebClient ownersWebClient) {
        this.ownersWebClient = ownersWebClient;
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ownersWebClient
                .get()
                .uri("admin/owners/")
                .retrieve()
                .bodyToFlux(OwnerDTO.class)
                .collectList()
                .block();
    }

    @Override
    public OwnerDTO getOwnerById(Long id) {
        return ownersWebClient
                .get()
                .uri("owners/%s".formatted(id))
                .retrieve()
                .bodyToMono(OwnerDTO.class)
                .block();
    }

    @Override
    public List<Long> findAllCats(Long id) {
        return ownersWebClient
                .get()
                .uri("owners/%s/cats".formatted(id))
                .retrieve()
                .bodyToFlux(Long.class)
                .collectList()
                .block();
    }
}
