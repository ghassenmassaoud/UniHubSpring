package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tn.esprit.pidevarctic.Service.RoleService;
@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private RoleService roleService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!roleService.hasRoles()) {
            roleService.loadRoles();
        }
    }
}
