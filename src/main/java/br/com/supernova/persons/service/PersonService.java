package br.com.supernova.persons.service;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.entity.Person;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import br.com.supernova.persons.mapper.PersonMapper;
import br.com.supernova.persons.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    public MessageResponseDTO registerPerson(PersonDTO personDTO) {
        Person converterPerson = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(converterPerson);
        return createMessageResponse(savedPerson.getId(), "Created person with ID - ");
    }

    public List<PersonDTO> returnAllPeople() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findByPerson(Long id) throws PersonNotFoundException {
        Person localePerson = personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(id)
        );
        LocalDate birthDate = localePerson.getBirthDate();
        PersonDTO personDTO = getDateFormatterPersonDTO(localePerson, birthDate);
        return personDTO;
    }

    public void deletedPerson(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person converterPerson = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(converterPerson);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID - ");
    }

    private void verifyIfExists(Long id) throws PersonNotFoundException {
        personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(id)
        );
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }


    private PersonDTO getDateFormatterPersonDTO(Person localePerson, LocalDate birthDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDate = birthDate.format(dateTimeFormatter);
        PersonDTO personDTO = personMapper.toDTO(localePerson);
        personDTO.setBirthDate(formatDate);
        return personDTO;
    }
}
