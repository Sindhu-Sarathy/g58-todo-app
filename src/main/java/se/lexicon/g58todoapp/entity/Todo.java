package se.lexicon.g58todoapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // TODO: make sure to create/update this info. AUDITING? - Life Cycle methods
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    @PrePersist
    public void prePersist(){
        System.out.println("Before Persist:");
        System.out.println(this);
        this.dueDate= LocalDateTime.now().plusDays(7);
    }

    @ManyToOne
    private Person assignedTo;

    //TODO ATTACHMENT
    Set<Attachment> attachmentSet;

    // TODO Add one more Constructor, Title, description

    public Todo(String title, String description, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Todo(String title, String description, Boolean completed, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public Todo(String title, String description, LocalDateTime dueDate, Person assignedTo) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // TODO : Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(description, todo.description) && Objects.equals(completed, todo.completed) && Objects.equals(createdAt, todo.createdAt) && Objects.equals(updatedAt, todo.updatedAt) && Objects.equals(dueDate, todo.dueDate) && Objects.equals(assignedTo, todo.assignedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, completed, createdAt, updatedAt, dueDate, assignedTo);
    }
}
