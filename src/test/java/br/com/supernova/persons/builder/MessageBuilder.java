package br.com.supernova.persons.builder;

import br.com.supernova.persons.dto.response.MessageResponseDTO;

public class MessageBuilder {

    public static final MessageResponseDTO createInspectMessageResponse(Long id){
        return MessageResponseDTO.builder()
                .message("Created person with ID - " + id)
                .build();
    }

    public static final MessageResponseDTO updateInspectMessageResponse(Long id){
        return MessageResponseDTO.builder()
                .message("Updated person with ID - " + id)
                .build();
    }
}
