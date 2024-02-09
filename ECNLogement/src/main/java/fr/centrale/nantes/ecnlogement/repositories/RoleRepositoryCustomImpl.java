/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;


@Repository
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    @Autowired
    @Lazy
    private RoleRepository repository;

    @Override
    public Role create(Role item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Role> result = repository.findById(item.getRoleId());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Role create(String roleNom) {
        if ((roleNom != null)) {
            Role item = new Role();
            item.setRoleNom(roleNom);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Role item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Role update(Integer roleId, Role value) {
          Role item = repository.getByRoleId(roleId);
          if ((item != null) && (value != null)) {
              item.setRoleNom(value.getRoleNom());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Role getByRoleId(Integer roleId) {
          Collection<Role> result = repository.findByRoleId(roleId);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
