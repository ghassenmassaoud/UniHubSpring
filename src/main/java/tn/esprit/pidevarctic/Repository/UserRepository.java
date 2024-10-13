package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.pidevarctic.entities.*;

import java.util.List;
import java.util.Set;
import tn.esprit.pidevarctic.entities.Speciality;
import tn.esprit.pidevarctic.entities.User;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  //  List<User> findBySubscriptionsPostAndSubscriptionsType(Post post, SubscriptionType type_subs);
//  @Query("SELECT DISTINCT u FROM User u JOIN u.posts p JOIN u.subscriptions s WHERE p = :post AND s = :subscription")
//  Set<User> findByPostsAndSubscriptions(Post post, Subscription subscription);

    List<User> findByCommentsContains(Comment comment);
    List<User>findBySpeciality(Speciality speciality);


    public Optional<User> findByEmail(String email);
    public Optional<User> findByEnableToken(String token);
    public Optional<User> findByCode(int code);
    public List<User> findByLockedTrueAndLockoutExpirationBefore(LocalDateTime date);
    public List<User> findByLockedReason(String reason);
    public List<User> findByRoles(Set<Role> roles);
    List<User> findAllByState(State state);

}