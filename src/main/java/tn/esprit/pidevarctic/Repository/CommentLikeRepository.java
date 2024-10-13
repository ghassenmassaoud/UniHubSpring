package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
}
