package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}