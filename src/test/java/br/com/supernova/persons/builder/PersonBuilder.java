package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.request.PhoneDTO;
import br.com.supernova.persons.entity.Person;
import br.com.supernova.persons.enums.PhoneType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
public class PersonBuilder {

    @Builder.Default
    private static final Long PERSON_ID = 1L;
    @Builder.Default
    private static final String FIRST_NAME = "William";
    @Builder.Default
    private static final String LAST_NAME = "Dias";
    @Builder.Default
    private static final String CPF = "322.562.325-43";
    @Builder.Default
    private static final String BIRTH_DATE = "07-11-2000";
    @Builder.Default
    private static final List<PhoneDTO> PHONES = new ArrayList<>();

    static {
        PHONES.add(PhoneBuilder.builder().build().toPhoneDTO());
    }

    public PersonDTO toPersonDTO(){
        return new PersonDTO(PERSON_ID, FIRST_NAME, LAST_NAME, CPF, BIRTH_DATE, PHONES);
    }
    /*
    public static PersonDTO personDTOFake() {
        return PersonDTO.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF)
                .birthDate("11-07-2000")
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

     */
}
