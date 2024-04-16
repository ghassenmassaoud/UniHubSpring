package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}