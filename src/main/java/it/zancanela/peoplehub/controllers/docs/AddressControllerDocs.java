package it.zancanela.peoplehub.controllers.docs;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.zancanela.peoplehub.dtos.ApiErrorDTO;
import it.zancanela.peoplehub.dtos.requests.AddressRequestDto;
import it.zancanela.peoplehub.dtos.requests.AddressRequestListDto;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDetailsDto;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

@Tag(name = "Address Endpoints")
public interface AddressControllerDocs {

    @ApiResponse(responseCode = "201"
            , description = CREATE_201_DESCRIPTION)
    @ApiResponse(responseCode = "400"
            , description = CREATE_400_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<Void> addAddress(String personId,
                                    AddressRequestDto dto,
                                    BindingResult bindingResult);

    @ApiResponse(responseCode = "201"
            , description = CREATE_201_DESCRIPTION)
    @ApiResponse(responseCode = "400"
            , description = CREATE_400_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<Void> addAdresses(String personId,
                                     AddressRequestListDto dtos,
                                     BindingResult bindingResult);

    @Parameter(in = ParameterIn.PATH
            , schema = @Schema(type = STRING)
            , name = "personId"
            , description = FIND_BY_ID_PARAMETER_ID_DESCRIPTION
            , example = UUID_ID_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_ALL_200_DESCRIPTION)
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<List<AddressResponseDto>> findAllByPerson(String personId);

    @Parameter(in = ParameterIn.PATH
            , schema = @Schema(type = STRING)
            , name = "id"
            , description = FIND_BY_ID_PARAMETER_ID_DESCRIPTION
            , example = UUID_ID_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_BY_ID_200_DESCRIPTION)
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "409"
            , description = CREATE_409_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<AddressResponseDetailsDto> findById(String id);

    @Parameter(in = ParameterIn.PATH
            , schema = @Schema(type = STRING)
            , name = "id"
            , description = FIND_BY_ID_PARAMETER_ID_DESCRIPTION
            , example = UUID_ID_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_BY_ID_200_DESCRIPTION)
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "400"
            , description = CREATE_400_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "409"
            , description = CREATE_409_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<AddressResponseDetailsDto> update(String id,
                                                     AddressRequestDto dto,
                                                     BindingResult bindingResult);

    @Parameter(in = ParameterIn.PATH
            , schema = @Schema(type = STRING)
            , name = "id"
            , description = FIND_BY_ID_PARAMETER_ID_DESCRIPTION
            , example = UUID_ID_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_BY_ID_200_DESCRIPTION)
    @ApiResponse(responseCode = "404"
            , description = FIND_BY_ID_404_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "409"
            , description = CREATE_409_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<Void> makeMain(String id);

}
