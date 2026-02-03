package se.lexicon.g58todoapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g58todoapp.entity.Person;
import se.lexicon.g58todoapp.repo.PersonRepository;

import java.util.List;

@RequestMapping("/api/people")
@RestController
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam String name){
        return "Hello, " + name;
    }


    //GET localhost:9090/api/people
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> getPerson(){
        return personRepository.findAll();
    }


    //PathVariable = {id}
    //GET localhost:9090/api/people/1
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    //POST localhost:9090/api/people
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createPerson(@RequestBody Person person){
        personRepository.save(person);
    }

    //DELETE Localhost:9090/api/people/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id){
        personRepository.deleteById(id);
    }


}
