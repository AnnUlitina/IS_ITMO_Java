package ru.itmo;

import itmo.cats.KotickColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WebCatsClientImpl implements CatWebClient {
    private final WebClient catWebClient;

    @Autowired
    public WebCatsClientImpl(@Qualifier(value = "cat") WebClient catWebClient) {
        this.catWebClient = catWebClient;
    }


    @Override
    public CatDTO getById(Long id, Long ownerId) {
        String uri = UriComponentsBuilder.fromPath("/cats/{id}")
                .buildAndExpand(id)
                .toUriString();

        return catWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CatDTO.class)
                .block();
    }

    @Override
    public CatDTO adminGetById(Long id) {
        return catWebClient
                .get()
                .uri("/admin/cats/%s".formatted(id))
                .retrieve()
                .bodyToMono(CatDTO.class)
                .block();
    }

    @Override
    public List<CatDTO> getByParams(List<Long> id, List<String> name, List<LocalDate> birthDay, List<KotickColor> color, List<String> breed, List<Long> ownerId) {
        String uri = UriComponentsBuilder.fromPath("/cats")
                .buildAndExpand(id)
                .toUriString();
//        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.put("name", name);
//        params.put("id", id.stream().map(Object::toString).toList());
//        params.put("birthDay", birthDay.stream().map(LocalDate::toString).toList());
//        params.put("color", color.stream().map(KotickColor::toString).toList());
//        params.put("breed", breed.stream().map(String::toString).toList());
//        params.put("owner", ownerId.stream().map(Object::toString).toList());

        return catWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(CatDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatDTO> adminGetByParams(List<Long> id, List<String> name, List<LocalDate> birthDay,
                                         List<KotickColor> color, List<String> breed) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", name);
        params.put("uuid", id.stream().map(Object::toString).toList());
        params.put("birthDay", birthDay.stream().map(LocalDate::toString).toList());
        params.put("color", color.stream().map(KotickColor::toString).toList());
        params.put("breed", breed.stream().map(String::toString).toList());

        return catWebClient
                .get()
                .uri(it -> it.path("/admin/cats").queryParams(params).build())
                .retrieve()
                .bodyToFlux(CatDTO.class)
                .collectList()
                .block();
    }
}
