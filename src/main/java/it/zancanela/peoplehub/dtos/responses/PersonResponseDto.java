package it.zancanela.peoplehub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.zancanela.peoplehub.utils.DateUtils.DATE_PATTERN;
import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record PersonResponseDto(
        @Schema(example = UUID_ID_EXAMPLE)
        String id,
        @Schema(type = STRING, example = PERSON_NAME_EXAMPLE)
        String name,
        @Schema(type = STRING, format = DATE, example = DATE_EXAMPLE)
        @JsonFormat(pattern = DATE_PATTERN)
        LocalDate birthDate,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<AddressResponseDto> adresses
) {

    public static synchronized PersonResponseDto toDto(Person person) {
        if (Objects.isNull(person.getAdresses()))
            person.setAdresses(new ArrayList<>());

        return new PersonResponseDto(
                person.getId(),
                person.getName(),
                person.getBirthDate(),
                AddressResponseDto.toDto(person.getAdresses())
        );
    }

    public static synchronized Page<PersonResponseDto> toDto(Page<Person> people) {
        return people.map(PersonResponseDto::toDto);
    }

}
