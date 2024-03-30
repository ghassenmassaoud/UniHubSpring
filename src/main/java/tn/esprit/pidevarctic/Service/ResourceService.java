package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.RessourceRepository;
import tn.esprit.pidevarctic.Repository.RessourceSpaceRepository;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.Speciality;

import java.util.List;
@Service
@AllArgsConstructor
public class ResourceService implements IRessourceService {

    private RessourceRepository ressourceRepository;
    private RessourceSpaceRepository ressourceSpaceRepository;

    @Override
    public Ressource addRess(Ressource ressource) {
        RessourceSpace ressourceSpace = ressourceSpaceRepository.findById(ressource.getRessourceSpace().getSpaceId()).orElse(null);
        if (ressourceSpace != null) {
            ressource.setRessourceSpace(ressourceSpace);
            return ressourceRepository.save(ressource);
        } else {
            // Handle case where the provided RessourceSpace identifier is invalid
            throw new IllegalArgumentException("Invalid RessourceSpace identifier");
        }
    }

    @Override
    public Ressource updateRess(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    @Override
    public void deleteRessource(Long resId) {
        ressourceRepository.deleteById(resId);

    }

    @Override
    public Ressource getRessourceById(Long resId) {
        return ressourceRepository.findById(resId).orElse(null);
    }

    @Override
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }


    //return resources by the name space
    public List<Ressource> getBySpace(Speciality ressourceSpace){
        return ressourceRepository.getRessourceByRessourceSpace_SpaceType(ressourceSpace);
    }


    //Return resources by the type :
    public List<Ressource> getByType(RessourceType resourceType){
        return ressourceRepository.getRessourceByRessourceType(resourceType);
    }




}
