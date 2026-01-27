package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {



    // TODO: Find person with a email
    List<Person> findPersonByEmail(String email);

    // TODO: Is there a person with a specific email? - return boolean
    boolean existsByEmail(String email);


}