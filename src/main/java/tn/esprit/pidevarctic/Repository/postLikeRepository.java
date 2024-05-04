package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.PostLike;

public interface postLikeRepository extends JpaRepository<PostLike,Long> {
}
