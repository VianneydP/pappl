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

public interface TypeAppartRepositoryCustom {

    /**
     * Create new TypeAppart
     * @param item
     * @return TypeAppart
     */
    public TypeAppart create(TypeAppart item);
    
    /**
     * Create new TypeAppart
     * @param typeAppartNom
     * @return TypeAppart
     */
    public TypeAppart create(String typeAppartNom);


    /**
     * Create new TypeAppart
     * @return TypeAppart
     */
    public TypeAppart create();

    /**
     * Remove TypeAppart
     * @param item
     */
    public void remove(TypeAppart item);

    /**
     * Update a TypeAppart
     * @param typeAppartNom
     * @param value
     * @return TypeAppart
     */
    public TypeAppart update(String typeAppartNom, TypeAppart value);

    /**
     * Get a TypeAppart
     * @param typeAppartNom
     * @return typeAppartNom
     */
    public TypeAppart getByTypeAppartNom(String typeAppartNom);

    

}
