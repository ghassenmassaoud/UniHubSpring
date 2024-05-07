
package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.User;
import tn.esprit.pidevarctic.entities.UserBehavior;

import java.util.List;

public interface IUserbehavior {
    void logUserBehavior(UserBehavior userBehavior);
    UserBehavior getByResource(Long resourceId);
    UserBehavior getById(Long id);
    List<UserBehavior>getAll();
    List<UserBehavior>findtop();
}
