package se.lexicon.g58todoapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@ToString


@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @NonNull
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Setter
    @NonNull
    private LocalDate birthDate;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    // TODO : Equals & Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(birthDate, person.birthDate) && Objects.equals(createdAt, person.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, birthDate, createdAt);
    }


    // TODO : Life Cycle for createdAt;
    @PrePersist
    public void prePersist(){
        System.out.println("Before Persist:");
        System.out.println(this);

        this.createdAt=LocalDate.now();
    }

    public Person(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }
}
