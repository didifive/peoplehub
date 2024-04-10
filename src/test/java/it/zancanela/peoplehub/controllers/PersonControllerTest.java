package it.zancanela.peoplehub.controllers;

import it.zancanela.peoplehub.dtos.requests.PersonNameRequestDto;
import it.zancanela.peoplehub.dtos.requests.PersonRequestDto;
import it.zancanela.peoplehub.dtos.requests.PersonRequestListDto;
import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import it.zancanela.peoplehub.exceptions.RestExceptionHandler;
import it.zancanela.peoplehub.services.PersonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDate;
import java.util.*;

import static it.zancanela.peoplehub.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Person Controller Tests")
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    public static final String PATH = "/api/v1/person";
    @Mock
    private PersonService service;
    @InjectMocks
    private PersonController controller;

    private Person person;
    private PersonRequestDto personRequestDto;
    private PersonRequestListDto personRequestListDto;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        personRequestDto = new PersonRequestDto(
                "Luis Zancanela",
                "1982-04-03"
        );
        personRequestListDto = new PersonRequestListDto(List.of(personRequestDto));

        person = PersonRequestDto.toEntity(personRequestDto);
        person.setId(UUID.randomUUID().toString());
    }

    @Test
    void create() throws Exception {
        when(service.save(any(Person.class))).thenReturn(person);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequestDto)))
                .andExpectAll(
                        status().isCreated()
                        , jsonPath("$.name", is(personRequestDto.name()))
                        , jsonPath("$.birthDate", is(personRequestDto.birthDate()))
                        , header().string("Location",
                                "http://localhost" + PATH + "/" + person.getId())
                );
    }

    @Test
    void createThrowsExceptionWhenBirthDateIsInvalid() throws Exception {
        String invalidDate = "82-04-03";

        PersonRequestDto personRequestDtoWithInvalidBirthDate = new PersonRequestDto(
                "Luis Zancanela",
                invalidDate
        );

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequestDtoWithInvalidBirthDate)))
                .andExpectAll(status().isInternalServerError()
                        , jsonPath("$.path", is(PATH))
                        , jsonPath("$.error", is("Erro no sistema :("))
                        , jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        , jsonPath("$.timestamp", anything())
                        , jsonPath("$.message", is("The date entered [" +
                                invalidDate +
                                "] cannot be converted"))
                );
    }

    @Test
    void createBatch() throws Exception {
        when(service.save(anyList())).thenReturn(List.of(person));

        mockMvc.perform(post(PATH + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequestListDto)))
                .andExpectAll(
                        status().isCreated()
                        , jsonPath("$.[0].name", is(personRequestListDto.data().getFirst().name()))
                        , jsonPath("$.[0].birthDate", is(personRequestListDto.data().getFirst().birthDate()))
                );
    }

    @Test
    void findAll() throws Exception {
        Page<Person> page = new PageImpl<>(Collections.singletonList(person));

        when(service.findAll(any(Pageable.class))).thenReturn(page);

                mockMvc.perform(get(PATH))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.content.[0].name", is(personRequestDto.name()))
                        , jsonPath("$.content.[0].birthDate", is(personRequestDto.birthDate()))
                        , jsonPath("$.size", is(10))
                        , jsonPath("$.totalElements", is(1))
                );
    }

    @Test
    void findAllByName() throws Exception {
        PersonNameRequestDto personNameRequestDto = new PersonNameRequestDto(personRequestDto.name());

        Page<Person> page = new PageImpl<>(Collections.singletonList(person));

        when(service.findAll(eq(personNameRequestDto.name()),any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(PATH + "/find-by-name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personNameRequestDto)))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.content.[0].name", is(personRequestDto.name()))
                        , jsonPath("$.content.[0].birthDate", is(personRequestDto.birthDate()))
                        , jsonPath("$.size", is(10))
                        , jsonPath("$.totalElements", is(1))
                );
    }

    @Test
    void findById() throws Exception {
        person.setAdresses(new ArrayList<>());

        when(service.findById(person.getId())).thenReturn(person);

        mockMvc.perform(get(PATH+"/"+person.getId()))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.name", is(personRequestDto.name()))
                        , jsonPath("$.birthDate", is(personRequestDto.birthDate()))
                );
    }

    @Test
    void findByIdExceptionWhenPersonIdIsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("")).when(service).findById(person.getId());

        mockMvc.perform(get(PATH+"/"+person.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        PersonRequestDto personRequestUpdateDto = new PersonRequestDto(
                "João Neves",
                "1800-04-01"
        );
        Person updatedPerson = PersonRequestDto.toEntity(personRequestUpdateDto);
        updatedPerson.setId(person.getId());

        when(service.update(eq(person.getId()),any(Person.class))).thenReturn(updatedPerson);

        mockMvc.perform(put(PATH+"/"+person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequestUpdateDto)))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.id", is(person.getId()))
                        , jsonPath("$.name", is(personRequestUpdateDto.name()))
                        , jsonPath("$.birthDate", is(personRequestUpdateDto.birthDate()))
                );

    }

    @Test
    void updateThrowExceptionWhenBodyIsInvalid() throws Exception {
        String invalidBody = "{\n" +
                "  \"name\": \"Luis\"\n" +
                "}";

        mockMvc.perform(put(PATH+"/"+person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpectAll(
                        status().isBadRequest()
                        , jsonPath("$.path", is(PATH+"/"+person.getId()))
                        , jsonPath("$.error", is("Bad Request Body"))
                        , jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value()))
                        , jsonPath("$.timestamp", anything())
                        , jsonPath("$.message", is("field:[birthDate] message:[Not be null, empty or blank]"))

                );
    }

    @Test
    void updateThrowExceptionWhenPersonIdIsNotFound() throws Exception {
        PersonRequestDto personRequestUpdateDto = new PersonRequestDto(
                "João Neves",
                "1800-04-01"
        );

        doThrow(new EntityNotFoundException("")).when(service).update(eq(person.getId()),any(Person.class));

        mockMvc.perform(put(PATH+"/"+person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personRequestUpdateDto)))
                .andExpect(status().isNotFound());
    }
}