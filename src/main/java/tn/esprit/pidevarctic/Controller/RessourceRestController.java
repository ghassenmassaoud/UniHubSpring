package tn.esprit.pidevarctic.Controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.Service.IRessourceSpace;
import tn.esprit.pidevarctic.Service.IUserService;
import tn.esprit.pidevarctic.entities.*;
import java.io.IOException;
import java.util.List;

@RequestMapping("/ressource")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RessourceRestController {

    private IRessourceSpace ressourceSpace;

    private IRessourceService ressourceService;
    private IUserService userService;
    @PostMapping("/add/{SpaceId}")
    public Ressource addRessource(@RequestBody Ressource ressource, @PathVariable Long SpaceId){
        return ressourceService.addRess(ressource,SpaceId);
    }

    @PutMapping("/update")
    public Ressource updateRessource(@RequestBody Ressource ressource){
        return ressourceService.updateRess(ressource);
    }
    @GetMapping("/get/{ressourceId}")
    public Ressource getById (@PathVariable Long ressourceId){

        return ressourceService.getRessourceById(ressourceId);
    }

    @DeleteMapping("/Delete/{ressourceId}")
    public void removeRessource(@PathVariable Long ressourceId){

        ressourceService.deleteRessource(ressourceId);
    }

    @GetMapping("/All")
    public List<Ressource> getAll(){
        return ressourceService.getAllRessources();
    }
    @GetMapping("/showByType/{type}")
    public List<Ressource> getRessourceByType(@PathVariable String type){
        RessourceType ressourceType = RessourceType.valueOf(type.toUpperCase());
        return ressourceService.getByType(ressourceType);
    }
    @GetMapping("/showBySpace/{spaceName}")
    public List<Ressource> getBySpace(@PathVariable RessourceSpace spaceName){
        return ressourceService.getBySpace(spaceName);
    }



    @PostMapping("/upload")
    public ResponseEntity<Ressource> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String resourceName,
            @RequestParam("type") RessourceType resourceType,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("description")String description,
            @RequestParam("user") long userid
    ) {
        try {
            Ressource savedResource = ressourceService.uploadResource(file, resourceName, resourceType, spaceId,description,userid);
            return ResponseEntity.ok(savedResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileSystemResource> download(@PathVariable String fileName) {
        try {
            FileSystemResource fileSystemResource = ressourceService.downloadFile(fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileSystemResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/files/{fileName}")
    public ResponseEntity<ByteArrayResource> openFile(@PathVariable String fileName) throws IOException {
        byte[] fileContent = ressourceService.getFileContent(fileName);

        ByteArrayResource resource = new ByteArrayResource(fileContent);



        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline    " + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileContent.length)
                .body(resource);
    }
    @GetMapping("/bystudent/{student}")
    public List<Ressource>getbyStudent(@PathVariable Long student)throws IOException{
        User user=  userService.getUserById(student);
        return ressourceService.getByUser(user);
    }

}