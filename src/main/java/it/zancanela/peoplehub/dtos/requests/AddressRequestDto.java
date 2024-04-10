package it.zancanela.peoplehub.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record AddressRequestDto(
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Schema(example = ADDRESS_PUBLIC_PLACE_EXAMPLE)
        String publicPlace,
        @NotNull(message = NOT_BE_NULL)
        @Min(0)
        @Max(Integer.MAX_VALUE)
        @Schema(example = ADDRESS_NUMBER_EXAMPLE)
        Integer number,
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Schema(example = ADDRESS_CITY_EXAMPLE)
        String city,
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Size(min = 1, max = 2, message = MINIMO_1_MAXIMO_2)
        @Schema(example = ADDRESS_STATE_EXAMPLE)
        String state,
        @NotBlank(message = NOT_BE_NULL_EMPTY_OR_BLANK)
        @Size(min = 5, max = 9, message = MINIMO_2_MAXIMO_9)
        @Schema(example = ADDRESS_ZIP_CODE_EXAMPLE)
        String zipCode,
        @NotNull(message = NOT_BE_NULL)
        @Enumerated(EnumType.STRING)
        AddressType addressType
) {

    public static synchronized Address toEntity(AddressRequestDto dto) {
        Address address = new Address();
        address.setPublicPlace(dto.publicPlace());
        address.setNumber(dto.number());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setZipCode(dto.zipCode());
        address.setAddressType(dto.addressType());
        return address;
    }

}
