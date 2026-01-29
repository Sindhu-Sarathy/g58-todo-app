package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {




    List<Person> findPersonByEmail(String email);


    boolean existsByEmail(String email);



}