
package tn.esprit.pidevarctic.Service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;
import tn.esprit.pidevarctic.entities.User;


import java.io.IOException;
import java.util.List;

public interface IRessourceService {
    Ressource addRess (Ressource ressource,Long RessourceSpaceid);
    Ressource updateRess (Ressource ressource);
    void deleteRessource(Long resId);
    Ressource getRessourceById(Long resId);
    List<Ressource> getAllRessources();
    List<Ressource> getByType(RessourceType ressourceType);
    List<Ressource> getBySpace(RessourceSpace spaceId);
    Ressource uploadResource(MultipartFile file, String resourceName, RessourceType resourceType, Long resourceSpaceId,String description,Long userId)throws IOException;
    FileSystemResource downloadFile(String fileName);
    byte[] getFileContent(String fileName) throws IOException;
    List<Ressource> getByUser(User user);

}
