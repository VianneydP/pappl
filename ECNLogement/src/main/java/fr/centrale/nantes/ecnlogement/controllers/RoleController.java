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
import fr.centrale.nantes.ecnlogement.repositories.RoleRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Role;

@Controller
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;
    
    private ModelAndView handleRoleList(Connexion user) {
        String modelName = "RoleList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "roleId")));
        return returned;
    }

    @RequestMapping(value="RoleList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handleRoleList(user);
        }
        return returned;
    }

    @RequestMapping(value="RoleEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"roleId");
            Role item = repository.getByRoleId( id );

            // Edit item
            String modelName = "RoleEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }

    @RequestMapping(value="RoleCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            Role item = new Role();
            String modelName = "RoleEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }

    @RequestMapping(value="RoleRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"roleId");
            Role item = repository.getByRoleId( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handleRoleList(user);
        }
        return returned;
    }

    @RequestMapping(value="RoleSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"roleId");
            Role item = repository.getByRoleId( id );

            Role dataToSave = new Role();

            // Retreive values from request
            dataToSave.setRoleNom(ApplicationTools.getStringFromRequest(request,"roleNom"));

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave.getRoleNom());
            }
            repository.update(item.getRoleId(), dataToSave);

            // Return to the list
            returned = handleRoleList(user);
        }
        return returned;
    }

    @RequestMapping(value="RoleImport.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTRoleImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get CSV file
            File tempFile = ApplicationTools.getFileFromRequest(request, "importFile");
            ApplicationTools.importCSV(tempFile, this, "createItem");
            ApplicationTools.cleanRequest(request);

            returned = handleRoleList(user);
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
        Role item = new Role();

        Method[] methods = Role.class.getMethods();
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
                        default:
                            canDoIt = false;
                            break;
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                }
            }
        }
        if (canDoIt) {
            repository.create(item);
        }
    }
}
