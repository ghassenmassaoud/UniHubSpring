package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Repository.RessourceSpaceRepository;
import tn.esprit.pidevarctic.Service.IRessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RequestMapping("/Space")
@AllArgsConstructor
@RestController
@CrossOrigin
public class SpaceController {
    private IRessourceSpace spaceService;

    @PostMapping("/addSpace")
    public RessourceSpace addSpace(@RequestBody RessourceSpace ressourceSpace){
        return spaceService.addSpace(ressourceSpace);
    }

    @PutMapping("/updateSpace")
    public RessourceSpace updateSpace(@RequestBody RessourceSpace ressourceSpace){
        return spaceService.updateSpace(ressourceSpace);
    }
    @DeleteMapping("/deleteSpace/{spaceId}")
    public void deleteSpace(@PathVariable Long spaceId){
        spaceService.deleteSpace(spaceId);
    }
    @GetMapping("/showAll")
    public List<RessourceSpace>ShowAllSpaces(){
        return spaceService.getAllSpaces();
    }
    @GetMapping("/ShowOne/{spaceId}")
    public RessourceSpace showById(@PathVariable Long spaceId){
        return spaceService.getSpace(spaceId);
    }
//    @GetMapping("/mySpace/{userId}")
//    public List<RessourceSpace> mySpace(@PathVariable User userId){
//        return spaceService.getSpacesByUser(userId);
//    }
}