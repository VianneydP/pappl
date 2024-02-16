/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.init;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;

import fr.centrale.nantes.ecnlogement.controllers.ApplicationTools;
import fr.centrale.nantes.ecnlogement.items.*;

public class ApplicationInitializer implements ServletContextListener {

    private static final String PUNAME = "fr.centrale_nantes_ecnlogement_war_1.0PU";
    private static EntityManager em;

    /**
     * Initialise
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PUNAME);
        Cache theCache = emf.getCache();
        theCache.evictAll();
        em = emf.createEntityManager();
        // Initialize data
        LDAPManager dummy = new LDAPManager();
        createDefault();
        // close
        em.close();
        emf.close();
    }

    /**
     * End context
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    /* ----------------------------------------------------------------------- */
    /**
     * Create default values in Database. This is called once, when application
     * starts
     */
    private void createDefault(){
        createDefaultMenus();
        createDefaultRoles();
        createDefaultUsers();
        createDefaultTypeAppart();
        //createDefaultSouhaits();
    }

    /* ----------------------------------------------------------------------- */
    private Object getItemFromID(int id, String requestName, Class classType, String fieldName) {
        Object item = null;
        try {
            Query theQuery = em.createNamedQuery(requestName, classType);
            theQuery.setParameter(fieldName, id);
            item = theQuery.getSingleResult();
        } catch (NoResultException ex) {
        }
        return item;
    }

    private Object getItemFromString(String value, String requestName, Class classType, String fieldName) {
        Object item = null;
        try {
            Query theQuery = em.createNamedQuery(requestName, classType);
            theQuery.setParameter(fieldName, value);
            item = theQuery.getSingleResult();
        } catch (NoResultException ex) {
        }
        return item;
    }

    /* ----------------------------------------------------------------------- */
    private void createDefaultMenus() {
        

    }

    /* ----------------------------------------------------------------------- */
    private Role getRole(String name) {
        return (Role) getItemFromString(name, "Role.findByRoleNom", Role.class, "roleNom");
    }

    private Role createRole(int roleId, String name) {
        Role item = getRole(name);
        if (item == null) {
            // Does not exist
            item = new Role();
            item.setRoleId(roleId);
            item.setRoleNom(name);
            em.persist(item);
            em.flush();

            // Reload from database to be sure to get it
            item = getRole(name);
        }
        return item;
    }

    private void createDefaultRoles() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        createRole(Role.ROLEADMIN, "Admin");
        createRole(Role.ROLEASSIST,"Assistant");
        createRole(Role.ROLEELEVE, "Eleve");
        transaction.commit();
    }
    
    /* ----------------------------------------------------------------------- */
    
    private Souhait getSouhait(String name) {
        return (Souhait) getItemFromString(name, "Souhait.findBySouhaitNom", Souhait.class, "type_souhait");
    }
    
    private Souhait createSouhait( String name) {
        Souhait item = getSouhait(name);
        if (item == null) {
            // Does not exist
            item = new Souhait();
            item.setTypeSouhait(name);
            em.persist(item);
            em.flush();

            // Reload from database to be sure to get it
            item = getSouhait(name);
        }
        return item;
    }
    
    private void createDefaultSouhaits() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        createSouhait("Seul");
        createSouhait("SeulOuColoc");
        createSouhait("Coloc");
        createSouhait("Indifférent");
        transaction.commit();
    }

    /* ----------------------------------------------------------------------- */
    private Personne getPerson(String uid) {
        return (Personne) getItemFromString(uid, "Personne.findByPersonneLogin", Personne.class, "personneLogin");
    }

    private Personne createPerson(String name, String uid, String password) {
        Personne item = getPerson(uid);
        if (item == null) {
            // Does not exist
            item = new Personne();
            item.setPersonneNom(name);
            item.setPersonneLogin(uid);
            if (!password.isEmpty()) {
                item.setPersonnePassword(ApplicationTools.encryptPassword(password));
            }
            item.setPersonnePrenom(name);
            item.setRoleId(getRole("Admin"));
            em.persist(item);
            em.flush();

            // Reload from database to be sure to get it
            item = getPerson(uid);
        }
        return item;
    }

    private void createDefaultUsers() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        createPerson("Administrateur", "admin", "admin");
        transaction.commit();
    }

    /* ----------------------------------------------------------------------- */
    private TypeAppart getTypeAppart(String value) {
        return (TypeAppart) getItemFromString(value, "TypeAppart.findByTypeAppartNom", TypeAppart.class, "typeAppartNom");
    }
    
    private TypeAppart createTypeAppart(String value) {
        TypeAppart item = getTypeAppart(value);
        if (item == null) {
            // Does not exist
            item = new TypeAppart();
            item.setTypeAppartNom(value);
            em.persist(item);
            em.flush();

            // Reload from database to be sure to get it
            item = getTypeAppart(value);
        }
        return item;
    }

    private void createDefaultTypeAppart() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        // createTypeAppart("Sous les ponts");
        createTypeAppart(TypeAppart.APPARTSTUDIO);
        createTypeAppart(TypeAppart.APPARTCOLOC);
        transaction.commit();
    }

    /* ----------------------------------------------------------------------- */
}
