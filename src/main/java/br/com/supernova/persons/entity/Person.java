package br.com.supernova.persons.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Unique entity identifier", required = true)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String firstName;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String lastName;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String cpf;

    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Phone> phones;
}
