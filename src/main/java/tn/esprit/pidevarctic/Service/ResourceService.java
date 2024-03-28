package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.RessourceRepository;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;
@Service
@AllArgsConstructor
public class ResourceService implements IRessourceService {

    private RessourceRepository ressourceRepository;

    @Override
    public Ressource addRess(Ressource ressource) {
        return ressourceRepository.save(ressource);
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



}
