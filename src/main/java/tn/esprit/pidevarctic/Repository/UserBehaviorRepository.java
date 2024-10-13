package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.pidevarctic.entities.UserBehavior;

import java.util.List;

public interface UserBehaviorRepository extends JpaRepository<UserBehavior, Long> {
    UserBehavior getUserBehaviorByResourceId(Long resourceId);
    @Query("SELECT u FROM UserBehavior u ORDER BY u.visited DESC limit 3")
    List<UserBehavior>findTopByVisited();

}