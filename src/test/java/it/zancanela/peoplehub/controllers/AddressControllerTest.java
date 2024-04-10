package it.zancanela.peoplehub.controllers;

import it.zancanela.peoplehub.dtos.requests.AddressRequestDto;
import it.zancanela.peoplehub.dtos.requests.AddressRequestListDto;
import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.enums.AddressType;
import it.zancanela.peoplehub.exceptions.RestExceptionHandler;
import it.zancanela.peoplehub.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static it.zancanela.peoplehub.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Address Controller Tests")
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    public static final String PATH = "/api/v1/address";

    @Mock
    private AddressService service;
    @InjectMocks
    private AddressController controller;

    private Address address;
    private AddressRequestDto addressRequestDto;
    private AddressRequestListDto addressRequestListDto;
    private String personId;
    private Address address2;
    private List<Address> adresses;

    private MockMvc mockMvc;

    @BeforeEach
    @Transactional
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        personId = UUID.randomUUID().toString();

        addressRequestDto = new AddressRequestDto(
                "Rua 01",
                100,
                "São Paulo",
                "SP",
                "01214-489",
                AddressType.HOME
        );
        addressRequestListDto = new AddressRequestListDto(List.of(addressRequestDto));

        address = AddressRequestDto.toEntity(addressRequestDto);
        address.setId(UUID.randomUUID().toString());

        address2 = new Address();
        address2.setPublicPlace("Rua 09");
        address2.setNumber(5897);
        address2.setCity("Niterói");
        address2.setState("RJ");
        address2.setZipCode("25784-489");
        address2.setAddressType(AddressType.COMMERCIAL);

        adresses = new ArrayList<>(Arrays.asList(address, address2));
    }

    @Test
    void addAddress() throws Exception {
        doNothing().when(service).addAddress(eq(personId), any(Address.class));

        mockMvc.perform(post(PATH + "/add-to-person/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressRequestDto)))
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    void addAddressThrowExceptionWhenBodyIsInvalid() throws Exception {
        String invalidBody = "{\n" +
                "  \"publicPlace\": \"Rua 09\"\n" +
                "}";

        mockMvc.perform(post(PATH + "/add-to-person/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpectAll(
                        status().isBadRequest()
                        , jsonPath("$.path", is(PATH + "/add-to-person/" + personId))
                        , jsonPath("$.error", is("Bad Request Body"))
                        , jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value()))
                        , jsonPath("$.timestamp", anything())
                        , jsonPath("$.message", anything())
                );
    }

    @Test
    void addAdresses() throws Exception {
        doNothing().when(service).addAdresses(eq(personId), anyList());

        mockMvc.perform(post(PATH + "/add-batch-to-person/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressRequestListDto)))
                .andDo(print())
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    void addAdressesThrowExceptionWhenBodyIsInvalid() throws Exception {
        String invalidBody = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"publicPlace\": \"Rua 09\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mockMvc.perform(post(PATH + "/add-batch-to-person/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpectAll(
                        status().isBadRequest()
                        , jsonPath("$.path", is(PATH + "/add-batch-to-person/" + personId))
                        , jsonPath("$.error", is("Bad Request Body"))
                        , jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value()))
                        , jsonPath("$.timestamp", anything())
                        , jsonPath("$.message", anything())
                );
    }

    @Test
    void findAllByPerson() throws Exception {
        String personId = UUID.randomUUID().toString();

        when(service.findAllByPerson(personId)).thenReturn(adresses);

        mockMvc.perform(get(PATH + "/find-by-person/" + personId))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.[0].publicPlace", is(address.getPublicPlace()))
                        , jsonPath("$.[0].number", is(address.getNumber()))
                        , jsonPath("$.[0].city", is(address.getCity()))
                        , jsonPath("$.[0].state", is(address.getState()))
                        , jsonPath("$.[0].zipCode", is(address.getZipCode()))
                        , jsonPath("$.[0].addressType", is(address.getAddressType().toString()))
                );
    }

    @Test
    void findById() throws Exception {
        Person person = new Person();
        person.setId(personId);
        person.setName("Luis Zancanela");
        person.setBirthDate(LocalDate.parse("1982-04-03"));
        address.setPerson(person);

        when(service.findById(address.getId())).thenReturn(address);

        mockMvc.perform(get(PATH + "/" + address.getId()))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.publicPlace", is(addressRequestDto.publicPlace()))
                        , jsonPath("$.number", is(addressRequestDto.number()))
                        , jsonPath("$.city", is(addressRequestDto.city()))
                        , jsonPath("$.state", is(addressRequestDto.state()))
                        , jsonPath("$.zipCode", is(addressRequestDto.zipCode()))
                        , jsonPath("$.addressType", is(addressRequestDto.addressType().toString()))
                        , jsonPath("$.personId", is(address.getPerson().getId()))
                );
    }

    @Test
    void findByIdThrowsExceptionWhenAddressDoesNoHasLinkedPerson() throws Exception {
        when(service.findById(address.getId())).thenReturn(address);

        mockMvc.perform(get(PATH + "/" + address.getId()))
                .andExpectAll(
                        status().isConflict()
                        , jsonPath("$.path", is(PATH + "/" + address.getId()))
                        , jsonPath("$.error", is("Data Integrity Violation"))
                        , jsonPath("$.status", is(HttpStatus.CONFLICT.value()))
                        , jsonPath("$.timestamp", anything())
                        , jsonPath("$.message", is("The address with id [" +
                                address.getId() +
                                "] does not have a linked person"))
                );
    }

    @Test
    void update() throws Exception {
        Person person = new Person();
        person.setId(personId);
        person.setName("Luis Zancanela");
        person.setBirthDate(LocalDate.parse("1982-04-03"));
        address.setPerson(person);

        when(service.update(eq(address.getId()), any(Address.class))).thenReturn(address);

        mockMvc.perform(put(PATH + "/" + address.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressRequestDto)))
                .andExpectAll(
                        status().isOk()
                        , jsonPath("$.publicPlace", is(addressRequestDto.publicPlace()))
                        , jsonPath("$.number", is(addressRequestDto.number()))
                        , jsonPath("$.city", is(addressRequestDto.city()))
                        , jsonPath("$.state", is(addressRequestDto.state()))
                        , jsonPath("$.zipCode", is(addressRequestDto.zipCode()))
                        , jsonPath("$.addressType", is(addressRequestDto.addressType().toString()))
                        , jsonPath("$.personId", is(address.getPerson().getId()))
                );
    }

    @Test
    void makeMain() throws Exception {
        doNothing().when(service).setMainAddress(any());

        mockMvc.perform(patch(PATH + "/make-main/" + address.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}