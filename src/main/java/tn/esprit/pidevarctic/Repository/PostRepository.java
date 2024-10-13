package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}