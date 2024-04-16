package tn.esprit.pidevarctic.Service;

import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.UserBehaviorRepository;
import tn.esprit.pidevarctic.entities.UserBehavior;

@Service
public class UserbehaviorService implements IUserbehavior {



        private  UserBehaviorRepository userBehaviorRepository;


        public void logUserBehavior(UserBehavior userBehavior) {
            userBehaviorRepository.save(userBehavior);
        }

}
