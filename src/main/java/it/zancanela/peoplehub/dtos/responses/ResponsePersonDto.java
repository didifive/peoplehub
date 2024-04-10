package it.zancanela.peoplehub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import it.zancanela.peoplehub.entities.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.zancanela.peoplehub.utils.DateUtils.DATE_PATTERN;
import static it.zancanela.peoplehub.utils.DateUtils.stringToLocalDate;
import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record ResponsePersonDto(
        @Schema(example = UUID_ID_EXAMPLE)
        String id,
        @Schema(type = STRING, example = PERSON_NAME_EXAMPLE)
        String name,
        @Schema(type = STRING, format = DATE, example = DATE_EXAMPLE)
        @JsonFormat(pattern = DATE_PATTERN)
        LocalDate birthDate,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ResponseAddressDto> adresses
) {

    public static synchronized ResponsePersonDto toDto(Person person) {
        if (Objects.isNull(person.getAdresses()))
            person.setAdresses(new ArrayList<>());

        return new ResponsePersonDto(
                person.getId(),
                person.getName(),
                person.getBirthDate(),
                ResponseAddressDto.toDto(person.getAdresses())
        );
    }

}
