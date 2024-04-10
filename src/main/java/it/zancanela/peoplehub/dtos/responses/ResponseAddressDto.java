package it.zancanela.peoplehub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.util.List;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

public record ResponseAddressDto(
        @Schema(example = UUID_ID_EXAMPLE)
        String id,
        @Schema(example = ADDRESS_PUBLIC_PLACE_EXAMPLE)
        String publicPlace,
        @Min(0)
        @Max(Integer.MAX_VALUE)
        @Schema(example = ADDRESS_NUMBER_EXAMPLE)
        Integer number,
        @Schema(example = ADDRESS_CITY_EXAMPLE)
        String city,
        @Size(min = 1, max = 2)
        @Schema(example = ADDRESS_STATE_EXAMPLE)
        String state,
        @Size(min = 5, max = 9)
        @Schema(example = ADDRESS_ZIP_CODE_EXAMPLE)
        String zipCode,
        @Enumerated(EnumType.STRING)
        AddressType addressType,
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        boolean mainAddress
) {

    public static synchronized ResponseAddressDto toDto(Address entity) {
        return new ResponseAddressDto(
                entity.getId(),
                entity.getPublicPlace(),
                entity.getNumber(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getAddressType(),
                entity.isMain()
        );
    }

    public static synchronized List<ResponseAddressDto> toDto(List<Address> adresses) {
        return adresses.stream().map(ResponseAddressDto::toDto).toList();
    }

}
