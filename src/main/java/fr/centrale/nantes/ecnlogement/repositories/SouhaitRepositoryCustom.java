/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface SouhaitRepositoryCustom {

    /**
     * Create new Souhait
     * @param item
     * @return Souhait
     */
    public Souhait create(Souhait item);

    /**
     * Create new Souhait
     * @return Souhait
     */
    public Souhait create();

    /**
     * Remove Souhait
     * @param item
     */
    public void remove(Souhait item);

    /**
     * Update a Souhait
     * @param typeSouhait
     * @param value
     * @return Souhait
     */
    public Souhait update(String typeSouhait, Souhait value);

    /**
     * Get a Souhait
     * @param typeSouhait
     * @return typeSouhait
     */
    public Souhait getByTypeSouhait(String typeSouhait);

}
