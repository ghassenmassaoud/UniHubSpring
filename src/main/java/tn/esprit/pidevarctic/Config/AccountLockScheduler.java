package tn.esprit.pidevarctic.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccountLockScheduler {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 60000) // Run every minute
    public void unlockAccounts() {
        List<User> lockedUsers = userRepository.findByLockedReason("BadCredentials");
        System.out.println(lockedUsers);
        for (User user : lockedUsers) {
            if (user.getLockoutExpiration() != null && user.getLockoutExpiration().isBefore(LocalDateTime.now())) {
                user.setLocked(false);
                user.setLockedReason(" ");
                user.setLockoutExpiration(null);
                userRepository.save(user);
            }
        }

    }
}
