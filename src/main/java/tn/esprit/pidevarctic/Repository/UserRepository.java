package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}