/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.controllers;

import static fr.centrale.nantes.ecnlogement.controllers.ApplicationTools.getMethod;
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
import fr.centrale.nantes.ecnlogement.repositories.LogementRepository;
import fr.centrale.nantes.ecnlogement.repositories.TypeAppartRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Logement;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;
import fr.centrale.nantes.ecnlogement.items.TypeAppart;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.StringTokenizer;

@Controller
public class LogementController {

    @Autowired
    private LogementRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private TypeAppartRepository typeAppartRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PersonneRepository personneRepository;
    
    @Autowired
    private EleveRepository eleveRepository;


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
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned = ApplicationTools.getModel( "LogementList", user );
        }
        File fichierRez=ApplicationTools.getFileFromRequest(request,"RezImport");
        String filename=fichierRez.getName();
        //String extension=getFileExtension(filename);
        String targetDirectory = request.getServletContext().getRealPath("FichierRez");
        if(fichierRez!=null){
            try {
                Path destinationOrigine = Paths.get(targetDirectory);
                //Path destination = new File(targetDirectory).toPath();
                String newFileName ="fichierRez.csv";
                Path destination =destinationOrigine.resolve(newFileName);
                Files.copy(fichierRez.toPath(), destination);
                importCsvRez(fichierRez) ;
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
     /**
     * Create an item form attribute lists
     *
     * @param header
     * @param values
     */
    public void createItem(List<String> header, List<String> values) {
        
        Logement item = new Logement();
        TypeAppart typeAppart ;
        
        
        Eleve eleve = new Eleve();   
        Personne personne = new Personne();
        personne.setRoleId(roleRepository.getByRoleId(Role.ROLEELEVE));

        boolean canDoIt = true;
        Iterator<String> valueIterator = values.iterator();
        for (String name : header) {
            
            if (valueIterator.hasNext()) {
                String value = valueIterator.next().trim();
                switch (name) {
                    case "Chambre":
                        item.setLogementNumero(value.substring(0, 5));
                        break;
                    case "Civilité":
                        switch (value){
                            case "Mme":
                                item.setLogementGenreRequis("F");
                                eleve.setGenre("F");
                                break;
                            case "Mr":
                                item.setLogementGenreRequis("M");
                                eleve.setGenre("M");
                                break;
                            default :
                                item.setLogementGenreRequis("");
                    }
                        break;
                    case "Colocation":
                        if (!value.equals("")){
                            item.setLogementPlacesDispo(ApplicationTools.getIntFromString(value));
                            typeAppart = typeAppartRepository.getByTypeAppartNom(TypeAppart.APPARTCOLOC);
                            item.setTypeAppartNom(typeAppart);
                        }
                        else{
                            item.setLogementPlacesDispo(1);
                            typeAppart = typeAppartRepository.getByTypeAppartNom(TypeAppart.APPARTSTUDIO);
                            item.setTypeAppartNom(typeAppart);
                        }
                    case "Prénom":
                        if (!value.equals("")){
                            personne.setPersonnePrenom(value);
                        }
                        break;
                    case "Nom":
                        if (!value.equals("")){
                            personne.setPersonneNom(value);
                        }
                        break;
                    case "Pays d'origine":
                        if (!value.equals("")){
                            eleve.setElevePayshab(value);
                        }
                        break;
                    case "Mail":
                        if (!value.equals("")){
                            eleve.setElevePayshab(value);
                        }
                        break;
                    default:
                        canDoIt = false;
                        break;
                }
            } else {
                canDoIt = false;
            }
        }

        //if (canDoIt) {
            Eleve temp = null;
            if (((personne.getPersonneNom() != null) && (!personne.getPersonneNom().isEmpty()) )
                    && ((personne.getPersonnePrenom() != null) && (!personne.getPersonnePrenom().isEmpty()))) {
                temp = eleveRepository.getByPersonNomPrenomMail(personne.getPersonneNom(), personne.getPersonnePrenom(),eleve.getEleveMail());
                if (temp == null) {
                    Personne tempP = personneRepository.create(personne.getPersonneNom(), personne.getPersonnePrenom(), personne.getRoleId());
                    temp = eleveRepository.create(-1, eleve.getEleveMail(), 
                            eleve.getGenre(), eleve.getElevePayshab(), tempP);
                    eleveRepository.setPersonne(eleve, tempP, personneRepository);
                    item.setLogementPlacesDispo(item.getLogementPlacesDispo()-1);
                }
            }
            Logement tempLog = null;
            if (((item.getLogementNumero() != null) && (!item.getLogementNumero().isEmpty()) )) {
                tempLog = repository.getByLogementNumero(item.getLogementNumero());
                if (tempLog == null) {
                    tempLog = repository.create(item);
                }
                else{
                    if (((personne.getPersonneNom()== null) )
                    && ((personne.getPersonnePrenom()== null) )) {
                        repository.update(item.getLogementNumero(), item,tempLog.getLogementPlacesDispo(),tempLog.getTypeAppartNom() );
                    }
                    else{
                        repository.update(item.getLogementNumero(), item,tempLog.getLogementPlacesDispo()-1,tempLog.getTypeAppartNom() );
                    }
                    
                }
            
            }
            
            
            
            
        //}
    }
    
    public void importCsvRez(File importFile) {
        // Build creation method
        // Read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));

            String line = reader.readLine();
            if (line != null) {
                // Get header
                List<String> header = new LinkedList<>();
                StringTokenizer st = new StringTokenizer(line, ";");
                while (st.hasMoreElements()) {
                    String name = st.nextToken().trim();
                    header.add(name);
                }
                // Get lines
                line = reader.readLine();
                while (line != null) {
                    //Pourquoi pas StringTokenizer ? Parce qu'il se débarrasse des éléments vides
                    // => problèmes d'indexation des informations... Essaie ! Tu verras
                    List<String> lineValues = new LinkedList<>();
                    int i = 0;
                    String elem = "";
                    while (i < line.length()) {
                        if (line.substring(i, i + 1).equals(";")) {
                            lineValues.add(elem);
                            elem = "";
                        } else {
                            if (line.substring(i, i + 1).equals(",")) {
                                elem += ".";
                            } else {
                                elem += line.substring(i, i + 1);
                            }
                        }
                        i++;
                        
                    }
                    // Create item with values
                    createItem(header, lineValues);
                    // Next line
                    line = reader.readLine();
                    }



                }

            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, "No file found", ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, "Error while reading file", ex);
        }
        
    }

}
