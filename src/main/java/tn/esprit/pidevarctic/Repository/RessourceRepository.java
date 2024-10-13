package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.*;

import java.util.List;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
    public List<Ressource> getRessourceByRessourceSpace(RessourceSpace ressourceSpace);
    public List<Ressource> getRessourceByRessourceType(RessourceType ressourceType);
    public List<Ressource> findRessourcesByStudents(User user);

}