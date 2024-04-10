package it.zancanela.peoplehub.controllers.docs;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.zancanela.peoplehub.dtos.ApiErrorDTO;
import it.zancanela.peoplehub.dtos.requests.PersonNameRequestDto;
import it.zancanela.peoplehub.dtos.requests.PersonRequestDto;
import it.zancanela.peoplehub.dtos.responses.PersonResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import static it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils.*;

@Tag(name = "Person Endpoints")
public interface PersonControllerDocs {

    @ApiResponse(responseCode = "201"
            , description = CREATE_201_DESCRIPTION)
    @ApiResponse(responseCode = "400"
            , description = CREATE_400_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "409"
            , description = CREATE_409_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<PersonResponseDto> create(PersonRequestDto dto,
                                             BindingResult bindingResult);

    @ApiResponse(responseCode = "201"
            , description = CREATE_201_DESCRIPTION)
    @ApiResponse(responseCode = "400"
            , description = CREATE_400_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    @ApiResponse(responseCode = "409"
            , description = CREATE_409_DESCRIPTION
            , content = @Content(schema = @Schema(implementation = ApiErrorDTO.class)))
    ResponseEntity<List<PersonResponseDto>> createBatch(List<PersonRequestDto> dtos,
                                                        BindingResult bindingResult);

    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = INTEGER)
            , name = "size"
            , description = PAGEABLE_PARAMETER_SIZE_DESCRIPTION
            , example = PAGEABLE_PARAMETER_SIZE_EXAMPLE)
    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = INTEGER)
            , name = "page"
            , description = PAGEABLE_PARAMETER_PAGE_DESCRIPTION
            , example = PAGEABLE_PARAMETER_PAGE_EXAMPLE)
    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = STRING)
            , name = "sort"
            , description = PAGEABLE_SORT_DESCRIPTION
            , example = PAGEABLE_SORT_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_ALL_200_DESCRIPTION)
    ResponseEntity<Page<PersonResponseDto>> findAll(@Parameter(hidden = true) Pageable pageable);

    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = INTEGER)
            , name = "size"
            , description = PAGEABLE_PARAMETER_SIZE_DESCRIPTION
            , example = PAGEABLE_PARAMETER_SIZE_EXAMPLE)
    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = INTEGER)
            , name = "page"
            , description = PAGEABLE_PARAMETER_PAGE_DESCRIPTION
            , example = PAGEABLE_PARAMETER_PAGE_EXAMPLE)
    @Parameter(in = ParameterIn.QUERY
            , schema = @Schema(type = STRING)
            , name = "sort"
            , description = PAGEABLE_SORT_DESCRIPTION
            , example = PAGEABLE_SORT_EXAMPLE)
    @ApiResponse(responseCode = "200"
            , description = FIND_ALL_200_DESCRIPTION)
    ResponseEntity<Page<PersonResponseDto>> findAllByName(PersonNameRequestDto dto,
                                                          BindingResult bindingResult,
                                                          @Parameter(hidden = true) Pageable pageable);

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
    ResponseEntity<PersonResponseDto> findById(String id);

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
    ResponseEntity<PersonResponseDto> update(String id,
                                             PersonRequestDto dto,
                                             BindingResult bindingResult);

}
