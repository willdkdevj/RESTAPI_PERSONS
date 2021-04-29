package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.request.PhoneDTO;
import br.com.supernova.persons.entity.Phone;
import br.com.supernova.persons.enums.PhoneType;
import lombok.Builder;

@Builder
public class PhoneBuilder {
    @Builder.Default
    private static final long PHONE_ID = 1L;
    @Builder.Default
    private static final String PHONE_NUMBER = "011 2098-2093";
    @Builder.Default
    private static final PhoneType PHONE_TYPE = PhoneType.HOME;

    public PhoneDTO toPhoneDTO(){
        return new PhoneDTO(PHONE_ID,  PHONE_TYPE, PHONE_NUMBER);
    }
    /*
    public static PhoneDTO phoneDTOFake() {
        return PhoneDTO.builder()
                .id(PHONE_ID)
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

     */
}
