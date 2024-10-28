
package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.RessourceSpaceRepository;
import tn.esprit.pidevarctic.entities.RessourceSpace;


import java.util.List;
@Service
@AllArgsConstructor
public class SpaceServices implements IRessourceSpace {
    private RessourceSpaceRepository ressourceSpaceRepository;
    @Override
    public RessourceSpace addSpace(RessourceSpace ressourceSpace) {
        return ressourceSpaceRepository.save(ressourceSpace);
    }

    @Override
    public RessourceSpace updateSpace(RessourceSpace ressourceSpace) {
        return ressourceSpaceRepository.save(ressourceSpace);
    }

    @Override
    public void deleteSpace(Long spaceId) {
        ressourceSpaceRepository.deleteById(spaceId);

    }

    @Override
    public RessourceSpace getSpace(Long spaceId) {
        return ressourceSpaceRepository.findById(spaceId).orElse(null);
    }

    @Override
    public List<RessourceSpace> getAllSpaces() {
        return ressourceSpaceRepository.findAll();
    }




}
