package it.zancanela.peoplehub.controllers;

import it.zancanela.peoplehub.controllers.docs.AddressControllerDocs;
import it.zancanela.peoplehub.dtos.requests.*;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDetailsDto;
import it.zancanela.peoplehub.dtos.responses.AddressResponseDto;
import it.zancanela.peoplehub.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.zancanela.peoplehub.utils.BindingResultUtils.verifyBindingResult;

@RestController
@RequestMapping("api/v1/address")
public class AddressController implements AddressControllerDocs {

    private final AddressService service;

    public AddressController(AddressService addressService) {
        this.service = addressService;
    }

    @PostMapping("/add-to-person/{personId}")
    public ResponseEntity<Void> addAddress(@PathVariable String personId,
                                           @RequestBody @Valid AddressRequestDto dto,
                                           BindingResult bindingResult) {
        verifyBindingResult(bindingResult);

        service.addAddress(personId,
                AddressRequestDto.toEntity(dto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add-batch-to-person/{personId}")
    public ResponseEntity<Void> addAdresses(@PathVariable String personId,
                                            @RequestBody @Valid AddressRequestListDto dtos,
                                            BindingResult bindingResult) {

        verifyBindingResult(bindingResult);

        service.addAdresses(personId,
                AddressRequestListDto.toEntity(dtos));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find-by-person/{personId}")
    public ResponseEntity<List<AddressResponseDto>> findAllByPerson(@PathVariable String personId) {
        return ResponseEntity
                .ok(AddressResponseDto.toDto(service.findAllByPerson(personId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDetailsDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                AddressResponseDetailsDto.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDetailsDto> update(@PathVariable String id,
                                                            @RequestBody @Valid AddressRequestDto dto,
                                                            BindingResult bindingResult) {
        verifyBindingResult(bindingResult);

        return ResponseEntity.ok(
                AddressResponseDetailsDto.toDto(
                        service.update(id, AddressRequestDto.toEntity(dto))));
    }

    @PatchMapping("/make-main/{id}")
    public ResponseEntity<Void> makeMain(@PathVariable String id) {
        service.setMainAddress(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
