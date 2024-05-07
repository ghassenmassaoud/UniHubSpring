
package tn.esprit.pidevarctic.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface IRessourceSpace  {
    RessourceSpace addSpace (RessourceSpace ressourceSpace);
    RessourceSpace updateSpace (RessourceSpace ressourceSpace);
    void deleteSpace (Long spaceId);
    RessourceSpace getSpace(Long spaceId);
    List<RessourceSpace> getAllSpaces();
//    List<RessourceSpace> getSpacesByUser(User userId);
}
