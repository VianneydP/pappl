/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Date;

public interface EleveRepositoryCustom {

    /**
     * Create new Eleve
     * @param item
     * @return Eleve
     */
    public Eleve create(Eleve item);
    
    /**
     * Create new Eleve
     * @param numscei
     * @param personne
     * @return 
     */
    public Eleve create(int numscei,Personne personne);
    
    /**
     * Create new Eleve
     * @param numscei 
     * @param eleveDateNaissance
     * @param genre
     * @param elevePayshab
     * @param eleveVillehab
     * @param eleveCodepostal
     * @param personne
     * @return Eleve
     */
    public Eleve create(int numscei, Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal, Personne personne);
    
     /**
     * Create new Eleve à partir du fichier des logements
     * @param numscei 
     * @param mail
     * @param genre
     * @param elevePayshab
     * @param personne
     * @param eleveConfirm
     * @return Eleve
     */
    public Eleve create(int numscei, String mail, String genre, String elevePayshab, Personne personne,boolean eleveConfirm) ;
    
        /**
     * Create new Eleve
     * @param numscei 
     * @param eleveDateNaissance
     * @param genre
     * @param elevePayshab
     * @param eleveVillehab
     * @param eleveCodepostal
     * @param personne
     * @param commune
     * @return Eleve
     */
    public Eleve create(int numscei, Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal, Personne personne, Commune commune);

    /**
     * Remove Eleve
     * @param item
     */
    public void remove(Eleve item);

    /**
     * Update a Eleve
     * @param eleveId
     * @param value
     * @return Eleve
     */
    public Eleve update(Integer eleveId, Eleve value);
    
    /**
     * Update a Eleve
     * @param eleveId
     * @param value
     * @return Eleve
     */
    public Eleve updateRez(Integer eleveId, Eleve value);


    /**
     * Get a Eleve
     * @param eleveId
     * @return eleveId
     */
    public Eleve getByEleveId(Integer eleveId);

    /**
     * Get a Eleve
     * @param personneNom
     * @param personnePrenom
     * @param eleveNaissance
     * @return 
     */
    public Eleve getByPersonNomPrenomNaissance(String personneNom, String personnePrenom, Date eleveNaissance);
    
    /**
     * Get a Eleve
     * @param personneNom
     * @param personnePrenom
     * @param numscei
     * @return 
     */
    public Eleve getByPersonNomPrenomNumscei(String personneNom, String personnePrenom,int numscei);
    
    /**
     * Get a Eleve
     * @param personneNom
     * @param personnePrenom
     * @param mail
     * @return 
     */
    public Eleve getByPersonNomPrenomMail(String personneNom, String personnePrenom,String eleveMail);
    
    
    /**
     * Set Personne
     * @param item
     * @param toSet
     * @param altRepository 
     */
    public void setPersonne(Eleve item, Personne toSet, PersonneRepository altRepository);
    
    /**
     * Get a Eleve
     * @param personneId
     * @return 
     */
    
    public Eleve getByPersonneId(int personneId);
}
