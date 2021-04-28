package br.com.supernova.persons.controller;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.response.MessageResponseDTO;
import br.com.supernova.persons.exceptions.PersonNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Interface for the Control of People Management")
public interface PersonController {

    @ApiOperation(value = "Operation for people register")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Person successfully registered"),
            @ApiResponse(code = 400, message = "It was not possible to register the informed person")
    })
    MessageResponseDTO registerPeople(PersonDTO personDTO);

    @ApiOperation(value = "Operation to locate people")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Person found successfully"),
            @ApiResponse(code = 404, message = "Could not find person reported")
    })
    PersonDTO findByPerson(@PathVariable Long id) throws PersonNotFoundException;

    @ApiOperation(value = "Operation to exclude people")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Person registration successfully deleted"),
            @ApiResponse(code = 404, message = "Could not find the person registration for deletion")
    })
    void deleteById(@PathVariable Long id) throws PersonNotFoundException;

    @ApiOperation(value = "Operation to list people in management")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Person catalog successfully returned")
    })
    List<PersonDTO> listPeople();

    @ApiOperation(value = "Updating of registration data of a successful person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Person data updated successfully"),
            @ApiResponse(code = 404, message = "The person could not be found to update the data")
    })
    MessageResponseDTO updateByID(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException;
}
