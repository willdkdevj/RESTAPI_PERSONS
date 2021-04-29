package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonBuilder {

    private static final Long PERSON_ID = 1L;
    private static final String FIRST_NAME = "William";
    private static final String LAST_NAME = "Dias";
    private static final String CPF = "322.562.325-43";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 07, 11);

    public static PersonDTO personDTOFake() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF)
                .birthDate("11-07-2010")
                .phones(Collections.singletonList(PhoneBuilder.phoneDTOFake()))
                .build();
    }

    public static Person personEntityFake() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneBuilder.phoneEntityFake()))
                .build();
    }
}
