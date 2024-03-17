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
import fr.centrale.nantes.ecnlogement.repositories.TypeAppartRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.TypeAppart;

@Controller
public class TypeAppartController {

    @Autowired
    private TypeAppartRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;
    
    /**
     * Méthode des controllers pour consituer la vue TypeAppartList
     * @param user Connexion utilisée
     * @return Vue TypeAppartList
     */
    private ModelAndView handleTypeAppartList(Connexion user) {
        String modelName = "TypeAppartList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "typeAppartNom")));
        return returned;
    }
    
    /**
     * Controller pour afficher les typeAppart (inutilisé)
     * @param request Requête HTTP
     * @return Vue TypeAppartList
     */
    @RequestMapping(value="TypeAppartList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTypeAppartList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handleTypeAppartList(user);
        }
        return returned;
    }
    
    /**
     * Controller pour modifier un typeAppart (inutilisé)
     * @param request Requête HTTP
     * @return Vue TypeAppartList
     */
    @RequestMapping(value="TypeAppartEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTypeAppartEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeAppartNom");
            TypeAppart item = repository.getByTypeAppartNom( id );

            // Edit item
            String modelName = "TypeAppartEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller pour créer un typeAppart (inutilisé)
     * @param request Requête HTTP
     * @return Vue TypeAppartList
     */
    @RequestMapping(value="TypeAppartCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTypeAppartCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            TypeAppart item = new TypeAppart();
            String modelName = "TypeAppartEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller pour supprimer un typeAppart (inutilisé)
     * @param request Requête HTTP
     * @return Vue TypeAppartList
     */
    @RequestMapping(value="TypeAppartRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTypeAppartRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeAppartNom");
            TypeAppart item = repository.getByTypeAppartNom( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handleTypeAppartList(user);
        }
        return returned;
    }
    
    /**
     * Controller pour enregistrer un typeAppart (inutilisé)
     * @param request Requête HTTP
     * @return Vue TypeAppartList
     */
    @RequestMapping(value="TypeAppartSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTypeAppartSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"typeAppartNom");
            TypeAppart item = repository.getByTypeAppartNom( id );

            TypeAppart dataToSave = new TypeAppart();

            // Retreive values from request

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create();
            }
            repository.update(item.getTypeAppartNom(), dataToSave);

            // Return to the list
            returned = handleTypeAppartList(user);
        }
        return returned;
    }
}
