package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.entities.Badge;
import tn.esprit.pidevarctic.entities.TaskState;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BadgeService {
    public void assignBadgeToUser(List<User> userList) {
        // Initialiser une variable pour stocker l'utilisateur de la semaine
        User userOfTheWeek = null;

        // Parcourir la liste des utilisateurs
        for (User user : userList) {
            // Vérifier si l'utilisateur répond aux critères pour obtenir le badge "Utilisateur de la semaine"
            if (isUserOfTheWeek(user)) {
                // Mettre à jour l'utilisateur de la semaine si celui-ci n'est pas encore défini ou si l'utilisateur actuel a plus de posts
                if (userOfTheWeek == null || user.getPosts().size() > userOfTheWeek.getPosts().size()) {
                    userOfTheWeek = user;
                }
            }
        }

        // Si un utilisateur répond aux critères, lui attribuer le badge "Utilisateur de la semaine"
        if (userOfTheWeek != null) {
            userOfTheWeek.setBadges(Badge.USER_OF_THE_WEEK);
        }
    }

    private boolean isUserOfTheWeek(User user) {
        // Récupérer la date de la semaine précédente
        LocalDate lastWeekDate = LocalDate.now().minusWeeks(1);

        // Récupérer les posts de l'utilisateur publiés la semaine dernière
        long numberOfPostsLastWeek = user.getPosts().stream()
                .filter(post -> post.getDatePost().isAfter(lastWeekDate))
                .count();

        // Récupérer les tâches de l'utilisateur terminées la semaine dernière
        long numberOfTasksCompletedLastWeek = user.getReplyTasks().stream()
                .filter(task -> task.getTaskState() == TaskState.TURNED_IN && task.getTask().getDeadline().toLocalDate().isAfter(lastWeekDate))
                .count();

        // Vérifier si l'utilisateur a publié au moins 5 posts et a terminé au moins 3 tâches la semaine dernière
        return numberOfPostsLastWeek >= 5
                && numberOfTasksCompletedLastWeek >= 3;
    }
}
