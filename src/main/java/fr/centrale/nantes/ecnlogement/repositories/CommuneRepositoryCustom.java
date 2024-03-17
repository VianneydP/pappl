/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface CommuneRepositoryCustom {

    /**
     * Create new Commune
     * @param item
     * @return Commune
     */
    public Commune create(Commune item);

    /**
     * Create new Commune
     * @param nomCommune
     * @param codePostal
     * @param latitude
     * @param longitude
     * @param dansMetropoleNantes
     * @return Commune
     */
    public Commune create(String nomCommune, int codePostal, float latitude, float longitude, boolean dansMetropoleNantes);

    /**
     * Remove Commune
     * @param item
     */
    public void remove(Commune item);

    /**
     * Update a Commune
     * @param codeCommune
     * @param value
     * @return Commune
     */
    public Commune update(String codeCommune, Commune value);

    /**
     * Get a Commune
     * @param codeCommune
     * @return codeCommune
     */
    public Commune getByCodeCommune(String codeCommune);
    
    /**
     * Méthode pour donner la distance d'une ville par rapport à Nantes
     * @param codeCommune Ville dont on calcule la distance à Nantes
     * @return Distance obtenue
     */
    public Double distNantesCommune(String codeCommune);
    
    /**
     * Getter pour obtenir un objet COmmune à partir du département et du nom
     * @param dep Département
     * @param nom Nom de la commune
     * @return codeCommune Identifiant de la commune
     */
    public Commune getByDepNom(String dep,String nom);
}
