package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {




    Optional<Person> findPersonByEmail(String email);


    boolean existsByEmail(String email);


    void deleteByEmail(String mail);
}