package it.zancanela.peoplehub.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record PersonNameRequestDto(
        @NotNull(message = NOT_BE_NULL)
        @Schema(type = STRING, example = PERSON_NAME_EXAMPLE)
        String name
) {
}
