package br.com.supernova.persons.repository;

import br.com.supernova.persons.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
