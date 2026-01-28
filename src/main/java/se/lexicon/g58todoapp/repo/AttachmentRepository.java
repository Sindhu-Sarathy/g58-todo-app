package se.lexicon.g58todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.g58todoapp.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
