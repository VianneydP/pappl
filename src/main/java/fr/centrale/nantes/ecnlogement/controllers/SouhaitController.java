/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.controllers;

import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Sort;

import fr.centrale.nantes.ecnlogement.repositories.ConnexionRepository;
import fr.centrale.nantes.ecnlogement.repositories.SouhaitRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Souhait;

@Controller
public class SouhaitController {

    @Autowired
    private SouhaitRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;
    
    /**
     * Méthode des controllers pour consituer la vue SouhaitList
     * @param user Connexion utilisée
     * @return Vue SouhaitList
     */
    private ModelAndView handleSouhaitList(Connexion user) {
        String modelName = "SouhaitList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "typeSouhait")));
        return returned;
    }
    
    /**
     * Controller pour afficher les souhaits (inutilisé)
     * @param request Requête HTTP
     * @return Vue SouhaitList
     */
    @RequestMapping(value="SouhaitList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTSouhaitList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handleSouhaitList(user);
        }
        return returned;
    }
    
    /**
     * Controller pour modifier un souhait (inutilisé)
     * @param request Requête HTTP
     * @return Vue SouhaitList
     */
    @RequestMapping(value="SouhaitEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTSouhaitEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeSouhait");
            Souhait item = repository.getByTypeSouhait( id );

            // Edit item
            String modelName = "SouhaitEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller pour créer un souhait (inutilisé)
     * @param request Requête HTTP
     * @return Vue SouhaitList
     */
    @RequestMapping(value="SouhaitCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTSouhaitCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            Souhait item = new Souhait();
            String modelName = "SouhaitEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller pour supprimer un souhait (inutilisé)
     * @param request Requête HTTP
     * @return Vue SouhaitList
     */
    @RequestMapping(value="SouhaitRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTSouhaitRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeSouhait");
            Souhait item = repository.getByTypeSouhait( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handleSouhaitList(user);
        }
        return returned;
    }
    
    /**
     * Controller pour enregistrer un souhait (inutilisé)
     * @param request Requête HTTP
     * @return Vue SouhaitList
     */
    @RequestMapping(value="SouhaitSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTSouhaitSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeSouhait");
            Souhait item = repository.getByTypeSouhait( id );

            Souhait dataToSave = new Souhait();

            // Retreive values from request

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create();
            }
            repository.update(item.getTypeSouhait(), dataToSave);

            // Return to the list
            returned = handleSouhaitList(user);
        }
        return returned;
    }
}
