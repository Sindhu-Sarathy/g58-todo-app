package se.lexicon.g58todoapp.entity;


import jakarta.persistence.*;
import lombok.*;

// TODO IMPLEMENT
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@ToString

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String fileName;

    @ManyToOne
    private Todo todo;


}
