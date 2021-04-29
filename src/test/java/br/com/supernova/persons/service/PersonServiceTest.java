package br.com.supernova.persons.service;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.entity.Person;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import br.com.supernova.persons.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.supernova.persons.builder.MessageBuilder.createInspectMessageResponse;
import static br.com.supernova.persons.builder.PersonBuilder.personDTOFake;
import static br.com.supernova.persons.builder.PersonBuilder.personEntityFake;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Test
    void testPersonDTOProvidedThenReturnSavedMessage() {
        PersonDTO personDTO = personDTOFake();
        Person inspectSavedPerson = personEntityFake();

        // When
        when(repository.save(any(Person.class))).thenReturn(inspectSavedPerson);

        // Then
        MessageResponseDTO expectedSuccessMessage = service.registerPerson(personDTO);
        MessageResponseDTO expectedSuccessMessageID = createInspectMessageResponse(inspectSavedPerson.getId());

        Assertions.assertEquals(expectedSuccessMessageID, expectedSuccessMessage);
    }


    @Test
    void testProvidedPersonDTOIDIncorrectlyThenAnExceptionThrows(){
        final Long INCORRECT_ID = 2L;
        when(repository.findById(INCORRECT_ID)).thenThrow(PersonNotFoundException.class);
    }
}
