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
import fr.centrale.nantes.ecnlogement.repositories.CommuneRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Commune;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

@Controller
public class CommuneController {

    @Autowired
    private CommuneRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;
    
    /**
     * Méthode liée au controller handlePOSTCommuneList pour récupérer les données Communes
     * @param request Requête HTTP
     * @return ModelAndView CommuneList avec les communes
     */
    private ModelAndView handleCommuneList(Connexion user) {
        String modelName = "CommuneList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        returned.addObject("itemList", repository.findAll(Sort.by(Sort.Direction.ASC, "codeCommune")));
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneList
     */
    @RequestMapping(value="CommuneList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned = handleCommuneList(user);
        }
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneEdit
     */
    @RequestMapping(value="CommuneEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"codeCommune");
            Commune item = repository.getByCodeCommune( id );

            // Edit item
            String modelName = "CommuneEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneEdit
     */
    @RequestMapping(value="CommuneCreate.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Create new item
            Commune item = new Commune();
            String modelName = "CommuneEdit";
            returned = ApplicationTools.getModel( modelName, user );
            returned.addObject("item", item );
        }
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneList
     */
    @RequestMapping(value="CommuneRemove.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"codeCommune");
            Commune item = repository.getByCodeCommune( id );

            // Remove item
            repository.remove( item );

            // Back to the list
            returned = handleCommuneList(user);
        }
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneList
     */
    @RequestMapping(value="CommuneSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            String id = ApplicationTools.getStringFromRequest(request,"codeCommune");
            Commune item = repository.getByCodeCommune( id );

            Commune dataToSave = new Commune();

            // Retreive values from request
            dataToSave.setNomCommune(ApplicationTools.getStringFromRequest(request,"nomCommune"));
            dataToSave.setCodePostal(ApplicationTools.getIntFromRequest(request,"codePostal"));
            dataToSave.setLatitude(ApplicationTools.getFloatFromRequest(request,"latitude"));
            dataToSave.setLongitude(ApplicationTools.getFloatFromRequest(request,"longitude"));
            dataToSave.setDansMetropoleNantes(ApplicationTools.getBooleanFromRequest(request,"dansMetropoleNantes"));

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave.getNomCommune(), dataToSave.getCodePostal(), dataToSave.getLatitude(), dataToSave.getLongitude(), dataToSave.getDansMetropoleNantes());
            }
            repository.update(item.getCodeCommune(), dataToSave);

            // Return to the list
            returned = handleCommuneList(user);
        }
        return returned;
    }
    
    /**
     * Controller inutilisé
     * @param request Requête HTTP
     * @return Vue CommuneList
     */
    @RequestMapping(value="CommuneImport.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTCommuneImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            // Get CSV file
            File tempFile = ApplicationTools.getFileFromRequest(request, "importFile");
            ApplicationTools.importCSV(tempFile, this, "createItem");
            ApplicationTools.cleanRequest(request);

            returned = handleCommuneList(user);
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
        Commune item = new Commune();

        Method[] methods = Commune.class.getMethods();
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
                        case "int":
                            method.invoke(item, ApplicationTools.getIntFromString(value));
                            break;
                        case "float":
                            method.invoke(item, ApplicationTools.getFloatFromString(value));
                            break;
                        case "boolean":
                            method.invoke(item, ((!value.isEmpty()) && (ApplicationTools.getIntFromString(value)!=0)));
                            break;
                        default:
                            canDoIt = false;
                            break;
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(CommuneController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(CommuneController.class.getName()).log(Level.SEVERE, null, ex);
                    canDoIt = false;
                }
            }
        }
        if (canDoIt) {
            repository.create(item);
        }
    }
    
     /**
     * Controller lié au bouton "Mise en place BDD" des la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue gestionADmin (réussite) ou accueilAdmin (échec)
     */
    @RequestMapping(value="createDefaultCommune.do", method=RequestMethod.POST)
    private ModelAndView handlePOSTCreateDefaultCommune(HttpServletRequest request) throws IOException {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user != null) {
            try{
                String nomFichier = request.getServletContext().getRealPath("WEB-INF\\classes\\fr\\centrale\\nantes\\ecnlogement\\resources\\communes-departement-region.csv");
                BufferedReader fichier=new BufferedReader(new FileReader(nomFichier));
                String ligne=fichier.readLine();
                while ((ligne = fichier.readLine()) != null){
                    StringTokenizer st = new StringTokenizer(ligne,",");
                    if (st.countTokens()==5){
                        Commune item=new Commune();
                        item.setCodeCommune(st.nextToken());
                        item.setNomCommune(st.nextToken());
                        item.setCodePostal(ApplicationTools.getIntFromString(st.nextToken()));
                        item.setLatitude(ApplicationTools.getFloatFromString(st.nextToken()));
                        item.setLongitude(ApplicationTools.getFloatFromString(st.nextToken()));
                        repository.create(item);
                    }
                }
                fichier.close();
                returned=ApplicationTools.getModel("gestionAdmin", user);
            }catch(FileNotFoundException ex){
                returned=ApplicationTools.getModel("accueilAdmin", user);
            }
        }else {
            returned=ApplicationTools.getModel("loginAdmin", null);
        }
        return returned;
    }
}
