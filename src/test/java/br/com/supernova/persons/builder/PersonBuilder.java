package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.request.PersonDTO;
import br.com.supernova.persons.dto.request.PhoneDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;

import java.util.ArrayList;
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

    public static String jsonToString(PersonDTO personDTO){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(personDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PersonDTO toPersonDTO(){
        return new PersonDTO(PERSON_ID, FIRST_NAME, LAST_NAME, CPF, BIRTH_DATE, PHONES);
    }

}
