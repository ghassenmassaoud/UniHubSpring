
package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.RessourceSpace;


import java.util.List;

public interface IRessourceSpace  {
    RessourceSpace addSpace (RessourceSpace ressourceSpace);
    RessourceSpace updateSpace (RessourceSpace ressourceSpace);
    void deleteSpace (Long spaceId);
    RessourceSpace getSpace(Long spaceId);
    List<RessourceSpace> getAllSpaces();

}
