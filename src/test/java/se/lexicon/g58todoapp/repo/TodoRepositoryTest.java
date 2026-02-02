package se.lexicon.g58todoapp.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.g58todoapp.entity.Person;
import se.lexicon.g58todoapp.entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private PersonRepository personRepository;

    private Person testPerson;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        // Create a test person
        testPerson = new Person("Test Person", "test@example.com");
        testPerson = personRepository.save(testPerson);

        now = LocalDateTime.now();
    }


    @Test
    @DisplayName("Save Todo should persist the Todo and return it with generated ID")
    void saveTodo_ShouldPersistTodo() {
        Todo newTodo = new Todo("New Task", "Description", now.plusDays(1));
        Todo saved = todoRepository.save(newTodo);

        assertNotNull(saved.getId());
        assertEquals("New Task", saved.getTitle());
        assertEquals("Description", saved.getDescription());
        assertFalse(saved.getCompleted());
        assertEquals(now.plusDays(1).withNano(0), saved.getDueDate().withNano(0));
    }


    @Test
    @DisplayName("Find Todos containing case-insensitive title substring should return matching Todos")
    void findByTitleContainingIgnoreCase_ShouldReturnMatchingTodos() {
       Todo todo1=new Todo("Wash","Need to do Car wash",LocalDateTime.now().plusDays(2));
       Todo todo2=new Todo("wash","Do hariwash",LocalDateTime.now());
       todoRepository.save(todo1);
       todoRepository.save(todo2);


       List<Todo> foundTodoList=todoRepository.findByTitleContainingIgnoreCase("wash");
       assertEquals(2,foundTodoList.size());
       assertTrue(foundTodoList.stream().anyMatch(t -> t.getTitle().equals("wash")));


    }

    @Test
    @DisplayName("Find Todo's by assigned id")
    void findByAssignedTo_Id() {
    Todo todo1=new Todo("Wash","Do laundry",LocalDateTime.now(),testPerson);
    todoRepository.save(todo1);

    Todo todo2=new Todo("Shopping","Buy Groceries",LocalDateTime.now(),testPerson);
    todoRepository.save(todo2);

    List<Todo> foundAssignedTo = todoRepository.findByAssignedTo_Id(testPerson.getId());

    assertEquals(2,foundAssignedTo.size());
    assertTrue(foundAssignedTo.stream().allMatch(todo -> todo.getAssignedTo().getId().equals(testPerson.getId())));

    }

    @Test
    @DisplayName("Find Todo's by completed status")
    void findByCompleted() {
        Todo todo1=new Todo("Shopping","Buy Groceries",true,LocalDateTime.now());
        todoRepository.save(todo1);

        Todo todo2=new Todo("Wash","Do laundry",true,LocalDateTime.now());
        todoRepository.save(todo2);

        List<Todo> foundByCompleted=todoRepository.findByCompleted(true);

        assertEquals(2,foundByCompleted.size());
        assertTrue(foundByCompleted.stream().allMatch(todo -> todo.getCompleted() == true));
    }

    @Test
    @DisplayName("Find Todos by due date range")
    void findByDueDateBetween() {
        Todo todo1 = new Todo("Task 1", "First task", true, now.minusDays(1));
        Todo todo2 = new Todo("Task 2", "Second task", false, now.plusDays(1));
        Todo todo3 = new Todo("Task 3", "Third task", true, now.plusDays(5));

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        LocalDateTime start= LocalDateTime.now().minusHours(12);
        LocalDateTime end=LocalDateTime.now().plusDays(1);
        List<Todo> foundBYDueDate=todoRepository.findByDueDateBetween(start,end);

        assertEquals(1,foundBYDueDate.size());
        assertTrue(foundBYDueDate.stream().allMatch(todo -> !todo.getDueDate().isBefore(start)
        && !todo.getDueDate().isAfter(end)));
        assertEquals("Task 2",foundBYDueDate.getFirst().getTitle());
    }

    @Test
    @DisplayName("Find overdue and incomplete todos")
    void findByDueDateBeforeAndCompletedFalse() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), testPerson);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);
        Todo t4 = new Todo("Read book", "Finish reading", false, null, testPerson);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);
        todoRepository.save(t4);

        LocalDateTime now=LocalDateTime.now();
        List<Todo> todos=todoRepository.findByDueDateBeforeAndCompletedFalse(now);

        assertEquals(2,todos.size());
        assertTrue(todos.stream().allMatch(todo -> !todo.getCompleted() && todo.getDueDate().isBefore(now)));

    }

    @Test
    @DisplayName("Find Unassigned todos")
    void findByAssignedToIsNull() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), null);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);

        List<Todo> todos=todoRepository.findByAssignedToIsNull();

        assertEquals(2,todos.size());
        assertNull(todos.getFirst().getAssignedTo());

    }

    @Test
    @DisplayName("Find in completed task that has been overdue")
    void findByCompletedFalseAndDueDateBefore() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), testPerson);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);
        Todo t4 = new Todo("Read book", "Finish reading", false, null, testPerson);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);
        todoRepository.save(t4);

        LocalDateTime now=LocalDateTime.now();

        List<Todo> todos=todoRepository.findByCompletedFalseAndDueDateBefore(now);

        assertEquals(2,todos.size());
        assertTrue(todos.stream().allMatch(todo -> !todo.getCompleted() && todo.getDueDate() != null));


    }

    @Test
    @DisplayName("Find completed task by assigned id")
    void findByAssignedTo_IdAndCompletedTrue() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), testPerson);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);
        Todo t4 = new Todo("Read book", "Finish reading", false, null, testPerson);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);
        todoRepository.save(t4);

        List<Todo> todos=todoRepository.findByAssignedTo_IdAndCompletedTrue(testPerson.getId());

        assertEquals(1,todos.size());
        assertTrue(todos.stream().allMatch(todo -> todo.getAssignedTo().getId().equals(testPerson.getId())));

    }

    @Test
    @DisplayName("Find the todos which doesnt have due date")
    void findByDueDateIsNull() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), testPerson);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);
        Todo t4 = new Todo("Read book", "Finish reading", false, null, testPerson);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);
        todoRepository.save(t4);

        List<Todo> todos=todoRepository.findByDueDateIsNull();

        assertEquals(1,todos.size());
        assertNull(todos.getFirst().getDueDate());

    }

    @Test
    @DisplayName("Count the todos list by assigned id")
    void countByAssignedTo_Id() {
        Todo t1 = new Todo("Wash", "Do laundry", false, LocalDateTime.now().minusDays(2), testPerson);
        Todo t2 = new Todo("Shopping", "Buy groceries", true, LocalDateTime.now().plusDays(1), testPerson);
        Todo t3 = new Todo("Call Mom", "Phone call", false, LocalDateTime.now().minusDays(1), null);
        Todo t4 = new Todo("Read book", "Finish reading", false, null, testPerson);

        todoRepository.save(t1);
        todoRepository.save(t2);
        todoRepository.save(t3);
        todoRepository.save(t4);

        long count= todoRepository.countByAssignedTo_Id(testPerson.getId());

        assertEquals(3,count);
    }
}