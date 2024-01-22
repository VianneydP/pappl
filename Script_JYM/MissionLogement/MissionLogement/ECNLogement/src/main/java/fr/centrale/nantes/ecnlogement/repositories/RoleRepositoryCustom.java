/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface RoleRepositoryCustom {

    /**
     * Create new Role
     * @param item
     * @return Role
     */
    public Role create(Role item);

    /**
     * Create new Role
     * @param roleNom
     * @return Role
     */
    public Role create(String roleNom);

    /**
     * Remove Role
     * @param item
     */
    public void remove(Role item);

    /**
     * Update a Role
     * @param roleId
     * @param value
     * @return Role
     */
    public Role update(Integer roleId, Role value);

    /**
     * Get a Role
     * @param roleId
     * @return roleId
     */
    public Role getByRoleId(Integer roleId);

}
