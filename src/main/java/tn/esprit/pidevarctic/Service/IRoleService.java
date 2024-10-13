package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Role;

import java.util.List;

public interface IRoleService {
    Role addRole(Role role);
    Role updateRole(Role role);
    void deleteRole(Long numRole);
    Role getRoleById(Long numRole);
    List<Role> getAllRole();
    public boolean hasRoles();
    public void loadRoles();
}
