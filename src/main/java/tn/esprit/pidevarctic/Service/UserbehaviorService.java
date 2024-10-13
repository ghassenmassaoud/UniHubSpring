
package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.UserBehaviorRepository;
import tn.esprit.pidevarctic.entities.UserBehavior;

import java.util.List;

@Service
@AllArgsConstructor
public class UserbehaviorService implements IUserbehavior {



    private  UserBehaviorRepository userBehaviorRepository;


    public void logUserBehavior(UserBehavior userBehavior) {
        userBehaviorRepository.save(userBehavior);
    }

    @Override
    public UserBehavior getByResource(Long resourceId) {
        return userBehaviorRepository.getUserBehaviorByResourceId(resourceId);
    }

    @Override
    public UserBehavior getById(Long id) {
        return userBehaviorRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserBehavior> getAll() {
        return userBehaviorRepository.findAll();
    }

    @Override
    public List<UserBehavior> findtop() {
        return userBehaviorRepository.findTopByVisited();
    }


}
