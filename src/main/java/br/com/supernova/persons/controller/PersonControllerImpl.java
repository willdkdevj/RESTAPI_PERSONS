package br.com.supernova.persons.controller;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import br.com.supernova.persons.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController{

    private final PersonService personService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO registerPeople(@RequestBody PersonDTO personDTO) {
        return personService.registerPerson(personDTO);
    }

    @Override
    @GetMapping("/{id}")
    public PersonDTO findByPerson(@PathVariable Long id) throws PersonNotFoundException {
        return personService.findByPerson(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
        personService.deletedPerson(id);
    }

    @Override
    @GetMapping
    public List<PersonDTO> listPeople() {
        return personService.returnAllPeople();
    }

    @Override
    @PutMapping("/{id}")
    public MessageResponseDTO updateByID(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
        return personService.updateById(id, personDTO);
    }
}
