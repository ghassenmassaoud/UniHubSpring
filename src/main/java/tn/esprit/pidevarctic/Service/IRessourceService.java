package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.Speciality;

import java.util.List;

public interface IRessourceService {
    Ressource addRess (Ressource ressource);
    Ressource updateRess (Ressource ressource);
    void deleteRessource(Long resId);
    Ressource getRessourceById(Long resId);
    List<Ressource> getAllRessources();
    List<Ressource> getByType(RessourceType ressourceType);
    List<Ressource> getBySpace(Speciality spaceName);

}
