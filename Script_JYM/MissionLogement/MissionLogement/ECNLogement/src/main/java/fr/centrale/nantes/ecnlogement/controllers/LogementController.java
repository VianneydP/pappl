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
import fr.centrale.nantes.ecnlogement.repositories.LogementRepository;
import fr.centrale.nantes.ecnlogement.repositories.TypeAppartRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Logement;
import fr.centrale.nantes.ecnlogement.items.TypeAppart;

@Controller
public class LogementController {

    @Autowired
    private LogementRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private TypeAppartRepository typeAppartRepository;

    private ModelAndView handleLogementList(Connexion user) {
        String modelName = "LogementList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "logementNumero")));
        return returned;
    }

    @RequestMapping(value="LogementList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handleLogementList(user);
        }
        return returned;
    }

    @RequestMapping(value="LogementEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"logementNumero");
            Logement item = repository.getByLogementNumero( id );

            // Edit item
            String modelName = "LogementEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
            returned.addObject("typeAppartNomList", typeAppartRepository.findAll());
        }
        return returned;
    }

    @RequestMapping(value="LogementCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            Logement item = new Logement();
            String modelName = "LogementEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
            returned.addObject("typeAppartNomList", typeAppartRepository.findAll());
        }
        return returned;
    }

    @RequestMapping(value="LogementRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"logementNumero");
            Logement item = repository.getByLogementNumero( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handleLogementList(user);
        }
        return returned;
    }

    @RequestMapping(value="LogementSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"logementNumero");
            Logement item = repository.getByLogementNumero( id );

            Logement dataToSave = new Logement();

            // Retreive values from request
            dataToSave.setLogementGenreRequis(ApplicationTools.getStringFromRequest(request,"logementGenreRequis"));
            dataToSave.setLogementPlacesDispo(ApplicationTools.getIntFromRequest(request,"logementPlacesDispo"));
            String typeAppartNomTemp = ApplicationTools.getStringFromRequest(request,"typeAppartNom");
            dataToSave.setTypeAppartNom(typeAppartRepository.getByTypeAppartNom(typeAppartNomTemp));

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave.getLogementPlacesDispo(), dataToSave.getTypeAppartNom());
            }
            repository.update(item.getLogementNumero(), dataToSave);

            // Return to the list
            returned = handleLogementList(user);
        }
        return returned;
    }
}
