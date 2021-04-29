package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.request.PhoneDTO;
import br.com.supernova.persons.entity.Phone;
import br.com.supernova.persons.enums.PhoneType;

public class PhoneBuilder {

    private static final long PHONE_ID = 1L;
    private static final String PHONE_NUMBER = "011 2098-2093";
    private static final PhoneType PHONE_TYPE = PhoneType.HOME;

    public static PhoneDTO phoneDTOFake() {
        return PhoneDTO.builder()
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static Phone phoneEntityFake() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }
}
