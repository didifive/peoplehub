package it.zancanela.peoplehub.controllers;

import it.zancanela.peoplehub.controllers.docs.AddressControllerDocs;
import it.zancanela.peoplehub.dtos.requests.*;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDetailsDto;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDto;
import it.zancanela.peoplehub.dtos.responses.PersonResponseDto;
import it.zancanela.peoplehub.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.zancanela.peoplehub.utils.BindingResultUtils.verifyBindingResult;

@RestController
@RequestMapping("api/v1/address")
public class AddressController implements AddressControllerDocs {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add-to-person/{personId}")
    public ResponseEntity<Void> addAddress(@PathVariable String personId,
                                           @RequestBody @Valid AddressRequestDto dto,
                                           BindingResult bindingResult) {
        verifyBindingResult(bindingResult);

        addressService.addAddress(personId,
                AddressRequestDto.toEntity(dto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add-batch-to-person/{personId}")
    public ResponseEntity<Void> addAdresses(@PathVariable String personId,
                                            @RequestBody @Valid AddressRequestListDto dtos,
                                            BindingResult bindingResult) {

        verifyBindingResult(bindingResult);

        addressService.addAdresses(personId,
                AddressRequestListDto.toEntity(dtos));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("find-by-person/{personId}")
    public ResponseEntity<List<AddressResponseDto>> findAllByPerson(@PathVariable String personId) {
        return ResponseEntity
                .ok(AddressResponseDto.toDto(addressService.findAllByPerson(personId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDetailsDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                AddressResponseDetailsDto.toDto(addressService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDetailsDto> update(@PathVariable String id,
                                                            AddressRequestDto dto,
                                                            BindingResult bindingResult) {
        verifyBindingResult(bindingResult);

        return ResponseEntity.ok(
                AddressResponseDetailsDto.toDto(
                        addressService.update(id, AddressRequestDto.toEntity(dto))));
    }

}
