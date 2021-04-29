package br.com.supernova.persons.service;

import br.com.supernova.persons.builder.PersonBuilder;
import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.entity.Person;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import br.com.supernova.persons.mapper.PersonMapper;
import br.com.supernova.persons.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.supernova.persons.builder.MessageBuilder.createInspectMessageResponse;
import static br.com.supernova.persons.builder.MessageBuilder.updateInspectMessageResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonMapper mapper = PersonMapper.INSTANCE;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Test
    void testPersonDTOProvidedThenReturnSavedMessage() {
        PersonDTO personDTO = PersonDTO.builder().build();
        Person convertedPerson = mapper.toModel(personDTO);

        // When
        when(repository.save(any(Person.class))).thenReturn(convertedPerson);

        // Then
        MessageResponseDTO expectedSuccessMessage = service.registerPerson(personDTO);
        MessageResponseDTO expectedSuccessMessageID = createInspectMessageResponse(convertedPerson.getId());

        assertEquals(expectedSuccessMessageID, expectedSuccessMessage);
    }

    @Test
    void testProvidedValidPersonDTOIDThenReturnThePerson() throws PersonNotFoundException {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
        Person convertedPerson = mapper.toModel(personDTO);

        // WHEN - O QUE O MÉTODO FAZ
        when(repository.findById(convertedPerson.getId())).thenReturn(Optional.of(convertedPerson));

        // THEN - COMO O MÉTODO É INVOCADO
        PersonDTO returnedPerson = service.findByPerson(convertedPerson.getId());

        assertEquals(personDTO, returnedPerson);
        assertEquals(personDTO.getId(), returnedPerson.getId());
        assertEquals(returnedPerson.getFirstName(), personDTO.getFirstName());

    }

    @Test
    void testProvidedPersonDTOIDIncorrectlyThenAnExceptionThrown() {
        final Long INCORRECT_ID = 2L;
        when(repository.findById(INCORRECT_ID)).thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> service.findByPerson(INCORRECT_ID));
    }

    @Test
    void testSRequestedPeopleThenReturnAllPeopleRegistered() {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
        Person inspectPerson = mapper.toModel(personDTO);
        List<Person> inspectRegisteredPeople = Collections.singletonList(inspectPerson);

        when(repository.findAll()).thenReturn(inspectRegisteredPeople);

        List<PersonDTO> returnedPeopleDTO = service.returnAllPeople();

        assertFalse(returnedPeopleDTO.isEmpty());
        assertEquals(returnedPeopleDTO.get(0).getId(), personDTO.getId());
    }

    @Test
    void testProvidedValidPersonDTOIDThenReturnSuccessOnUpdate() throws PersonNotFoundException {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();

        Person inspectPerson = mapper.toModel(personDTO);
        inspectPerson.setLastName("Souza");
        Person inspectUpdatePerson = mapper.toModel(personDTO);

        when(repository.findById(inspectPerson.getId())).thenReturn(Optional.of(inspectUpdatePerson));
        when(repository.save(any(Person.class))).thenReturn(inspectUpdatePerson);

        // Then
        MessageResponseDTO expectedSuccessMessage = service.updateById(inspectUpdatePerson.getId(), personDTO);
        MessageResponseDTO expectedSuccessMessageID = updateInspectMessageResponse(inspectUpdatePerson.getId());

        assertEquals(expectedSuccessMessageID, expectedSuccessMessage);
    }

    @Test
    void testProvidedToUpdateInvalidPersonDTOIDThenAnExceptionThrown() {
        final Long INVALID_ID = 2L;
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();

        when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.updateById(INVALID_ID, personDTO));

    }

    @Test
    void testProvidedValidPersonIDThenReturnSuccessOnDelete() throws PersonNotFoundException {
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
        Person inspectPerson = mapper.toModel(personDTO);

        when(repository.findById(inspectPerson.getId())).thenReturn(Optional.of(inspectPerson));

        service.deletedPerson(inspectPerson.getId());

        verify(repository, times(1)).deleteById(inspectPerson.getId());
    }
    
    @Test
    void testProvidedToDeleteInvalidPersonDTOIDThenAnExceptionThrown() {
        final Long INVALID_ID=2L;
        PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
    }
}
