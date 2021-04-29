package br.com.supernova.persons.controller;

import br.com.supernova.persons.builder.MessageBuilder;
import br.com.supernova.persons.builder.PersonBuilder;
import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import br.com.supernova.persons.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.util.Collections;
import java.util.List;

import static br.com.supernova.persons.builder.PersonBuilder.jsonToString;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String URL_PATH = "/api/v1/people";

    private MockMvc mockMvc;

    @InjectMocks
    private PersonControllerImpl controller;

    @Mock
    private PersonService service;

    @BeforeEach
    void setUp() {
        controller = new PersonControllerImpl(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testWhenPOSTIsCalledThenAPersonShouldBeCreated() throws Exception {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
        MessageResponseDTO messageResponseDTO = MessageBuilder.createInspectMessageResponse(personDTO.getId());

        when(service.registerPerson(personDTO)).thenReturn(messageResponseDTO);

        mockMvc.perform(post(URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(personDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(messageResponseDTO.getMessage())));
    }

    @Test
    void testWhenGETWithValidIsCalledThenAPersonShouldBeReturned() throws Exception {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();

        when(service.findByPerson(personDTO.getId())).thenReturn(personDTO);

        mockMvc.perform(get(URL_PATH + "/" + personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(personDTO.getId())))
                .andExpect(jsonPath("$.firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(personDTO.getLastName())));
    }

    @Test
    void testWhenGETWithInvalidIsCalledThenAnErrorMessageShouldBeReturned() throws Exception {
        final Long INVALID_ID = 2L;

        when(service.findByPerson(INVALID_ID)).thenThrow(PersonNotFoundException.class);

        mockMvc.perform(get(URL_PATH + "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testWhenGETIsCalledThenPeopleListShouldBeReturned() throws Exception {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
        List<PersonDTO> personDTOList = Collections.singletonList(personDTO);

        when(service.returnAllPeople()).thenReturn(personDTOList);

        mockMvc.perform(get(URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(personDTO.getLastName())));
    }
}
