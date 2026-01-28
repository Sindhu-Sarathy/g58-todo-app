package se.lexicon.g58todoapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    @ManyToOne
    private Person assignedTo;

    @OneToMany(mappedBy = "todo",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true )
    Set<Attachment> attachmentSet = new HashSet<>();

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

    public void addAttachment(Attachment attachment){
        if(attachmentSet==null){
            attachmentSet=new HashSet<>();
        }

        attachmentSet.add(attachment);
        attachment.setTodo(this);

    }

    public void removeAttachment(Attachment attachment){
        attachmentSet.remove(attachment);
        attachment.setTodo(null);
    }

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
