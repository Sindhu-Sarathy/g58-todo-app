package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    
    // TODO : Tasks assigned to a specific Person
    // TODO : 📌 Count all tasks assigned to a person
    // TODO : ✅ Find completed tasks assigned to a specific person
    
    // TODO : 🔍 Find todos by title keyword (case-insensitive contains)
    // TODO : ✅ Find todos by completed status
    // TODO : 🗓️ Find todos between two due dates
    // TODO :️ Find todo due before a specific date and not completed
    // TODO :🔥 Find unfinished and overdue task
    // TODO : Find tasks that are not assigned to anyone
    // TODO : 📅 Find all with no due date
    
}