package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Person;
import se.lexicon.g58todoapp.entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    
    // TODO : Tasks assigned to a specific Person

    List<Todo> findTodoByAssignedTo(Person assignedTo);


    // TODO : 📌 Count all tasks assigned to a person

    long countByAssignedTo(Person assignedTo);

    // TODO : ✅ Find completed tasks assigned to a specific person
    
    List<Todo> findByCompletedAndAssignedTo(Boolean completed, Person assignedTo);
    
    // TODO : 🔍 Find todos by title keyword (case-insensitive contains)

    List<Todo> findTodosByTitleContainsIgnoreCase(String title);


    // TODO : ✅ Find todos by completed status

    List<Todo> findTodosByCompleted(Boolean completed);

    // TODO : 🗓️ Find todos between two due dates

    List<Todo> findTodosByDueDateBetween(LocalDateTime dueDateAfter, LocalDateTime dueDateBefore);

    // TODO :️ Find todo due before a specific date and not completed

    List<Todo> findTodosByDueDateBeforeAndCompletedNot(LocalDateTime dueDateBefore, Boolean completed);

    // TODO :🔥 Find unfinished and overdue task

    List<Todo> findTodosByCompletedNotAndDueDateBefore(Boolean completed, LocalDateTime dueDateBefore);
    
    // TODO : Find tasks that are not assigned to anyone

    List<Todo> findTodosByAssignedToIsNull(Person assignedTo);

    // TODO : 📅 Find all with no due date
    
    List<Todo> findAllByDueDateIsNull();
    
}