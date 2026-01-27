package se.lexicon.g58todoapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


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
    // TODO : Life Cycle for createdAt;
}
