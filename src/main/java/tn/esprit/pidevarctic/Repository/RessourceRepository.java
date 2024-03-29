package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.Speciality;

import java.util.List;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
    public List<Ressource> getRessourceByRessourceSpace(RessourceSpace ressourceSpace);
    public List<Ressource> getRessourceByRessourceType(RessourceType ressourceType);

}