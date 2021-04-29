package br.com.supernova.persons.controller;

import br.com.supernova.persons.builder.MessageBuilder;
import br.com.supernova.persons.builder.PersonBuilder;
import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
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


import static br.com.supernova.persons.builder.PersonBuilder.jsonToString;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
