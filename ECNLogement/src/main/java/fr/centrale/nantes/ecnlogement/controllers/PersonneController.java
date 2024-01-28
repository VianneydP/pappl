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
import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import fr.centrale.nantes.ecnlogement.repositories.RoleRepository;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;

@Controller
public class PersonneController {

    @Autowired
    private PersonneRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;
    
    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private RoleRepository roleRepository;

    private ModelAndView handlePersonneList(Connexion user) {
        String modelName = "PersonneList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "personneId")));
        return returned;
    }

    @RequestMapping(value="PersonneList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handlePersonneList(user);
        }
        return returned;
    }

    @RequestMapping(value="PersonneEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne item = repository.getByPersonneId( id );

            // Edit item
            String modelName = "PersonneEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
            returned.addObject("roleIdList", roleRepository.findAll());
        }
        return returned;
    }

    @RequestMapping(value="PersonneCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            Personne item = new Personne();
            String modelName = "PersonneEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
            returned.addObject("roleIdList", roleRepository.findAll());
        }
        return returned;
    }

    @RequestMapping(value="PersonneRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne item = repository.getByPersonneId( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handlePersonneList(user);
        }
        return returned;
    }

    @RequestMapping(value="PersonneSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne item = repository.getByPersonneId( id );
            //Ajoute par moi
            Integer id2 = ApplicationTools.getIntFromRequest(request,"eleveId");
            Eleve eleve = eleveRepository.getByEleveId( id2 );

            Personne dataToSave = new Personne();

            // Retreive values from request
            dataToSave.setPersonneNom(ApplicationTools.getStringFromRequest(request,"personneNom"));
            dataToSave.setPersonnePrenom(ApplicationTools.getStringFromRequest(request,"personnePrenom"));
            dataToSave.setPersonneLogin(ApplicationTools.getStringFromRequest(request,"personneLogin"));
            dataToSave.setPersonnePassword(ApplicationTools.encryptPassword(ApplicationTools.getStringFromRequest(request,"personnePassword")));
            //Integer roleIdTemp = ApplicationTools.getIntFromRequest(request,"personneRole");
            //dataToSave.setRoleId(roleRepository.getByRoleId(roleIdTemp));
            dataToSave.setRoleId(item.getRoleId());

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave.getPersonneNom(), dataToSave.getPersonnePrenom(), dataToSave.getRoleId());
            }
            repository.update(item.getPersonneId(), dataToSave);

            // Return to the list
            //returned = handlePersonneList(user);
            returned = ApplicationTools.getModel( "questionnaire", user );
            returned.addObject("personne", item);
            returned.addObject("eleve", eleve);
            
        }
        return returned;
    }

    @RequestMapping(value="PersonneImport.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTPersonneImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get CSV file
            File tempFile = ApplicationTools.getFileFromRequest(request, "importFile");
            ApplicationTools.importCSV(tempFile, this, "createItem");
            ApplicationTools.cleanRequest(request);

            //returned = handlePersonneList(user);
            returned = ApplicationTools.getModel( "loginRe", user );
        }
        return returned;
    }

    /**
     * Create an item form attribute lists
     *
     * @param header
     * @param values
     */
    public void createItem(List<String> header, List<String> values) {
        Personne item = new Personne();

        Method[] methods = Personne.class.getMethods();
        boolean canDoIt = true;
        Iterator<String> valueIterator = values.iterator();
        for (String name : header) {
            String value = valueIterator.next().trim();

            // Search for the setter method
            String setterName = ApplicationTools.buildSetter(name);
            Method method = null;
            Class type = null;
            for (Method aMethod : methods) {
                if (aMethod.getName().equals(setterName)) {
                    Class[] types = aMethod.getParameterTypes();
                    if (types.length == 1) {
                        method = aMethod;
                        type = types[0];
                        break;
                    }
                }
            }

            if (method == null) {
                canDoIt = false;
            } else {
                try {
                    switch (type.getSimpleName()) {
                        case "String":
                            method.invoke(item, value);
                            break;
                        case "Role":
                            method.invoke(item, roleRepository.getByRoleId(ApplicationTools.getIntFromString(value)));
                            break;
                        default:
                            canDoIt = false;
                            break;
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(PersonneController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(PersonneController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                }
            }
        }
        if (canDoIt) {
            repository.create(item);
        }
    }
}
