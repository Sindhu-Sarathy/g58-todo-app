package se.lexicon.g58todoapp.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.g58todoapp.entity.Person;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;


    @Test
    @DisplayName("Save and find by Email")
    void testSaveAndFindPersonByEmail() {
        //Arrange
        Person person=new Person("Sindhu","sarathy.sindhu@gmail.com");
        personRepository.save(person);

        //Act
        var foundPerson=personRepository.findPersonByEmail("sarathy.sindhu@gmail.com");

        //Assert
        assertTrue(foundPerson.isPresent());
        assertEquals("Sindhu",foundPerson.get().getName());
        assertEquals("sarathy.sindhu@gmail.com",foundPerson.get().getEmail());
    }


    @Test
    @DisplayName("Retrieve all Persons")
    void testFindAllPersons(){
        //Arrange
        Person person1=new Person("Sindhu","sarathy.sindhu@gmail.com");
        Person person2=new Person("Test","test1@gmail.com");
        personRepository.save(person1);
        personRepository.save(person2);

        //Act
        var personList=personRepository.findAll();

        //Assert
        assertEquals(2,personList.size());
    }


    @Test
    @DisplayName("Exists by email should return true")
    void testExistsByEmail() {
        //Arrange
        Person person1=new Person("Sindhu","sarathy.sindhu@gmail.com");
        Person person2=new Person("Test","test1@gmail.com");
        personRepository.save(person1);
        personRepository.save(person2);

        //Act
        boolean isExisted= personRepository.existsByEmail("test1@gmail.com");

        //Assert
        assertTrue(isExisted);
    }

    //TODO - Students
    @Test
    @DisplayName("Exists by email should return false for unknown email")
    void testExistsByEmailFalse() {
        //Arrange
        Person person1=new Person("Sindhu","sarathy.sindhu@gmail.com");
        personRepository.save(person1);

        //Act
        boolean isExisted= personRepository.existsByEmail("test1@gmail.com");

        //Assert
        assertFalse(isExisted);
    }

    @Test
    @DisplayName("Delete a person by email and verify existence")
    void testDeletePersonByEmail() {
        //Arrange
        Person person2=new Person("Test","test1@gmail.com");
        personRepository.save(person2);

       //Assert
        assertTrue(personRepository.existsByEmail("test1@gmail.com"));

        //Act
        personRepository.deleteByEmail("test1@gmail.com");

        //Assert
        assertFalse(personRepository.existsByEmail("test1@gmail.com"));

    }
}