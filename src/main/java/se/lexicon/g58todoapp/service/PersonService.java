package se.lexicon.g58todoapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.lexicon.g58todoapp.entity.Person;
import se.lexicon.g58todoapp.exception.PersonNotFoundException;
import se.lexicon.g58todoapp.repo.PersonRepository;
import se.lexicon.notify.model.Email;
import se.lexicon.notify.service.MessageService;

import java.util.List;

@Slf4j
@Service
public class PersonService {

    // TODO: Expand Service Layer methods

    PersonRepository personRepository;
    MessageService<Email> messageService;

    public PersonService(PersonRepository personRepository, MessageService<Email> messageService) {
        this.personRepository = personRepository;
        this.messageService = messageService;
    }

    public void createPerson(Person person) {

        person = personRepository.save(person);


        if (person.getId() != null){
            boolean sentMessage = messageService.sendMessage(new Email(
                    person.getEmail(),
                    "Welcome 🫡",
                    """
                            Hello, %s
                            Thank you for signing up to our App.
                            We hope you enjoy using it. 🎉
                            """.formatted(person.getName())));

            if (!sentMessage){
                log.error("Failed to send welcome email to: {}", person.getEmail());
            }else{
                log.info("Successfully sent welcome email to: {}",person.getEmail());
            }
        }
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findById(Long id){
        return personRepository.findById(id).orElseThrow(()-> new PersonNotFoundException("Person Not Found"));
    }


    public Person updatePerson(Long id, Person person){
        Person existing=personRepository.findById(id).orElseThrow(()-> new PersonNotFoundException("Person Not Found"));

        existing.setName(person.getName());
        existing.setEmail(person.getEmail());
        existing.setBirthDate(person.getBirthDate());
        return personRepository.save(existing);
    }


    public void deleteById(Long id){
        personRepository.deleteById(id);
    }



    public List<Person> findByEmail(String email){
        return personRepository.findPersonByEmail(email);
    }

    public boolean existsEmail(String email){
       return personRepository.existsByEmail(email);
    }
}