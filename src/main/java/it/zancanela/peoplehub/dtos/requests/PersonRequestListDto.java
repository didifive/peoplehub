package it.zancanela.peoplehub.dtos.requests;

import it.zancanela.peoplehub.entities.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.NOT_BE_NULL_OR_EMPTY;

public record PersonRequestListDto(
        @NotEmpty(message = NOT_BE_NULL_OR_EMPTY)
        @Valid
        List<PersonRequestDto> data
) {
    public static synchronized List<Person> toEntity(PersonRequestListDto dto) {
        return dto.data().stream().map(PersonRequestDto::toEntity).toList();
    }
}
