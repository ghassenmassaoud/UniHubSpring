package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.State;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByEnableToken(String token);
    public Optional<User> findByCode(int code);
    public List<User> findByLockedTrueAndLockoutExpirationBefore(LocalDateTime date);
    public List<User> findByLockedReason(String reason);
    public List<User> findByRoles(Set<Role> roles);
    List<User> findAllByState(State state);
}