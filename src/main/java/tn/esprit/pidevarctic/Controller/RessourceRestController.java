package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.RessourceType;

import java.util.List;


@RequestMapping("/ressource")
@AllArgsConstructor
@RestController
public class RessourceRestController {



    private IRessourceService ressourceService;
    @PostMapping("/add")
    public Ressource addRessource(@RequestBody Ressource ressource){
        return ressourceService.addRess(ressource);
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
    public List<Ressource> getRessourceByType(@PathVariable RessourceType type){
        return ressourceService.getByType(type);
    }
    @GetMapping("/showBySpace/{spaceName}")
    public List<Ressource> getBySpace(@PathVariable RessourceSpace spaceName){
        return ressourceService.getBySpace(spaceName);
    }


}
