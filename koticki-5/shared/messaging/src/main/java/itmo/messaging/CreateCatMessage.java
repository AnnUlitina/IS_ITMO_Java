package itmo.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import itmo.cats.KotickColor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
public class CreateCatMessage {
    private Long uuid;
    private String name;
    private Long catOwnerUuid;
    private LocalDate birthDay;
    private String breed;
    private KotickColor catColor;
}
