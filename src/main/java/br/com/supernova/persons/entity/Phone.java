package br.com.supernova.persons.entity;

import br.com.supernova.persons.enums.PhoneType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Unique entity identifier", required = true)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Value cannot be null", required = true)
    private PhoneType type;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Value cannot be null", required = true)
    private String number;
}
