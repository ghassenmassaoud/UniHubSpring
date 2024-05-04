package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.pidevarctic.entities.*;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
  //  List<User> findBySubscriptionsPostAndSubscriptionsType(Post post, SubscriptionType type_subs);
//  @Query("SELECT DISTINCT u FROM User u JOIN u.posts p JOIN u.subscriptions s WHERE p = :post AND s = :subscription")
//  Set<User> findByPostsAndSubscriptions(Post post, Subscription subscription);

    List<User> findByCommentsContains(Comment comment);
}