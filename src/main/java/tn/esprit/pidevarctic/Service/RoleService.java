package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.RoleRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.util.*;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long numRole) {
        roleRepository.deleteById(numRole);
    }

    @Override
    public Role getRoleById(Long numRole) {
        return roleRepository.findById(numRole).orElse(null);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
    @Override
    public boolean hasRoles() {
        // Vérifie si la table de rôles contient déjà des données
        return roleRepository.count() > 0;
    }
    @Override
    public void loadRoles() {
        // Chargez vos rôles et enregistrez-les dans la base de données
        Role student = new Role();
        Role teacher = new Role();
        Role staff = new Role();
        Role delegate = new Role();
        student.setRoleName("ROLE_STUDENT");
        teacher.setRoleName("ROLE_TEACHER");
        staff.setRoleName("ROLE_STAFF");
        delegate.setRoleName("ROLE_DELEGATE");
        roleRepository.saveAll(Arrays.asList(student, teacher, staff, delegate));
        User stud = new User();
        User teatch = new User();
        User sta = new User();
        stud.setFirstName("student");
        Set<Role> studentRoles = new HashSet<>();
        studentRoles.add(student);
        studentRoles.add(delegate);
        stud.setRoles(studentRoles);
        teatch.setFirstName("teacher");
        Set<Role> teacherRoles = new HashSet<>();
        teacherRoles.add(teacher);
        teatch.setRoles(teacherRoles);
        sta.setFirstName("staff");
        Set<Role> staffRoles = new HashSet<>();
        staffRoles.add(staff);
        sta.setRoles(staffRoles);
        userRepository.saveAll(Arrays.asList(stud,teatch,sta));
    }
}
