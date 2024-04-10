package it.zancanela.peoplehub.dtos.requests;

import it.zancanela.peoplehub.entities.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.NOT_BE_NULL_OR_EMPTY;

public record AddressRequestListDto(
        @NotEmpty(message = NOT_BE_NULL_OR_EMPTY)
        @Valid
        List<AddressRequestDto> data
) {
    public static synchronized List<Address> toEntity(AddressRequestListDto dto) {
        return dto.data().stream().map(AddressRequestDto::toEntity).toList();
    }
}
