package it.zancanela.peoplehub.controllers;

import it.zancanela.peoplehub.controllers.docs.PersonControllerDocs;
import it.zancanela.peoplehub.dtos.requests.PersonNameRequestDto;
import it.zancanela.peoplehub.dtos.requests.PersonRequestDto;
import it.zancanela.peoplehub.dtos.responses.PersonResponseDto;
import it.zancanela.peoplehub.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static it.zancanela.peoplehub.utils.BindingResultUtils.verifyBindingResult;

@RestController
@RequestMapping("api/v1/person")
public class PersonController implements PersonControllerDocs {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> create(
            @RequestBody @Valid PersonRequestDto dto,
            BindingResult bindingResult) {

        verifyBindingResult(bindingResult);

        PersonResponseDto savedPerson =
                PersonResponseDto.toDto(personService.save(PersonRequestDto.toEntity(dto)));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPerson.id()).toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PersonResponseDto>> createBatch(
            @RequestBody @Valid List<PersonRequestDto> dtos,
            BindingResult bindingResult) {

        verifyBindingResult(bindingResult);

        List<PersonResponseDto> savedPeople = personService.save(dtos.stream().map(PersonRequestDto::toEntity).toList())
                .stream().map(PersonResponseDto::toDto).toList();

        return new ResponseEntity<>(savedPeople, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PersonResponseDto>> findAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                PersonResponseDto.toDto(personService.findAll(pageable)));
    }

    @GetMapping
    public ResponseEntity<Page<PersonResponseDto>> findAllByName(
            @RequestBody @Valid PersonNameRequestDto dto,
            BindingResult bindingResult,
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        verifyBindingResult(bindingResult);

        return ResponseEntity.ok(
                PersonResponseDto.toDto(personService.findAll(dto.name(), pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                PersonResponseDto.toDto(personService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable String id,
                                                    @RequestBody @Valid PersonRequestDto dto,
                                                    BindingResult bindingResult) {
        verifyBindingResult(bindingResult);

        return ResponseEntity.ok(
                PersonResponseDto.toDto(
                        personService.update(id, PersonRequestDto.toEntity(dto))));
    }


}
