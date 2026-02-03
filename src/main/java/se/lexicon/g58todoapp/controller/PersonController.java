package se.lexicon.g58todoapp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/people")
@RestController
public class PersonController {

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam String name){
        return "Hello, " + name;
    }

}
