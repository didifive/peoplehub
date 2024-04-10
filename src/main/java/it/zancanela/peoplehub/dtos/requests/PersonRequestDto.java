package it.zancanela.peoplehub.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import it.zancanela.peoplehub.entities.Person;
import jakarta.validation.constraints.NotBlank;

import static it.zancanela.peoplehub.utils.DateUtils.stringToLocalDate;
import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record PersonRequestDto(
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Schema(type = STRING, example = PERSON_NAME_EXAMPLE)
        String name,
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Schema(type = STRING, format = DATE, example = DATE_EXAMPLE)
        String birthDate
) {

    public static synchronized Person toEntity(PersonRequestDto dto) {
        Person person = new Person();
        person.setName(dto.name());
        person.setBirthDate(stringToLocalDate(dto.birthDate));
        return person;
    }

}
