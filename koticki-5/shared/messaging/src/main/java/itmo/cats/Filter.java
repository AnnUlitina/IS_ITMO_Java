package itmo.cats;

import lombok.Data;

@Data
public class Filter {
    private String name;
    private String breed;
    private KotickColor color;

    public Filter(String name, String breed, String color) {
        this.name = name;
        this.breed = breed;
        if (color != null && !color.isBlank()) {
            this.color = KotickColor.valueOf(color.toUpperCase());
        }
    }
}
