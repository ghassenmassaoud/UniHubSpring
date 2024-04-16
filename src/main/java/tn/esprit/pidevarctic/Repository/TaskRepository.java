package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}