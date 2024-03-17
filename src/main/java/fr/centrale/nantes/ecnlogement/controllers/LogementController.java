/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
//-*- coding: utf-8 
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Set;
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

    /**
     * Méthode des controllers pour consituer la vue LogementList
     * @param user Connexion utilisée
     * @return Vue LogementList
     */
    private ModelAndView handleLogementList(Connexion user) {
        String modelName = "LogementList";
        ModelAndView returned = ApplicationTools.getModel( modelName, user );
        Collection<Logement> maListe=repository.findAll(Sort.by(Sort.Direction.ASC, "logementNumero"));
        returned.addObject("itemList", maListe);
        return returned;
    }
    
    /**
     * Controller lié à l'affichage des logements et à l'import du fichier Rez
     * @param request Requête HTTP
     * @return Vue LogementList
     */
    @RequestMapping(value="LogementList.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTLogementList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        String action = ApplicationTools.getStringFromRequest(request, "nomEtape");
        
            if (user == null) {
                returned = ApplicationTools.getModel( "loginAdmin", null );
            } else {
                returned = handleLogementList(user);
            }
        if ("importLogement".equals(action)) {
            File fichierRez=ApplicationTools.getFileFromRequest(request,"RezImport");
            String filename=fichierRez.getName();
            
            String targetDirectory = request.getServletContext().getRealPath("FichierRez");
            
            
            if(fichierRez!=null&& fichierRez.length()>0){
                try {
                    Path path = Paths.get(targetDirectory);
                    if (!Files.exists(path)) {
                        try {
                            Files.createDirectories(path);
                        } catch (IOException e) {
                        }
                    }
                    //Path destination = new File(targetDirectory).toPath();
                    String newFileName ="fichierRez.csv";
                    Path destination =path.resolve(newFileName);
                    //On regarde si le fichier e déjà été importé
                    //S'il l'a été on supprime les données qui ont été importées
                    //avec le premier import pour ne garder que les données du second
                    if (Files.exists(destination)) { 
                        Files.delete(destination);
                        //FIXME supprimer les personnes associées aux élèves, truc bizarre lors d'un deuxième importph
                        Collection<Eleve> elevesToDelete=eleveRepository.findByNumscei(-1);
                        ArrayList<Personne> personnesToDelete=new ArrayList<>();
                        for (Eleve eleve : elevesToDelete) {
                            personnesToDelete.add(eleve.getPersonne());
                        }
                        eleveRepository.deleteAll(elevesToDelete);
                        personneRepository.deleteAll(personnesToDelete);
                        repository.deleteAll();
                        Files.copy(fichierRez.toPath(), destination);
                        importCsvRez(fichierRez) ;
                    }else {
                        Files.copy(fichierRez.toPath(), destination);
                        importCsvRez(fichierRez) ;
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    returned = handleLogementList(user);
                }
                
        }
        }   
        return returned;
    }
    
    /**
     * Controller lié à la modification d'un logement (inutlisé)
     * @param request Requête HTTP
     * @return Vue LogementEdit
     */
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
    
    /**
     * Controller lié à la création d'un logement (inutilisé)
     * @param request Requête HTTP
     * @return Vue LogementEdit
     */
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
    
    /**
     * Controller lié à la suppression d'un logement (inutilisé)
     * @param request Requête HTTP
     * @return Vue LogementList
     */
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
    
    /**
     * Controller lié à la sauvagarde des modification d'un logement (inutilisé)
     * @param request Requête HTTP
     * @return Vue LogementList
     */
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
     * Cree un logement et son habitant s'il y en a déjà un à partir d'une 
     * Liste de valeurs
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
                    case "COLOCATION" :
                        
                        if (!value.equals("")){
                            item.setLogementPlacesDispo(ApplicationTools.getIntFromString(value));
                            if (item.getTypeAppartNom()==null){
                                typeAppart = typeAppartRepository.getByTypeAppartNom(TypeAppart.APPARTCOLOC);
                            }else{
                                typeAppart=item.getTypeAppartNom();
                            }
                            
                        }
                        else{
                            item.setLogementPlacesDispo(1);
                            if (item.getTypeAppartNom()==null){
                                typeAppart = typeAppartRepository.getByTypeAppartNom(TypeAppart.APPARTSTUDIO);
                            }else{
                                typeAppart=item.getTypeAppartNom();
                            }      
                        }
                        item.setTypeAppartNom(typeAppart);
                        break;
                    case "CHAMBRE":
                        item.setLogementNumero(value.substring(0, 5));
                        
                        break;
                    case "CIVILITE":
                        switch (value){
                            case "MME":
                                item.setLogementGenreRequis("F");
                                eleve.setGenre("F");
                                break;
                            case "MR":
                                item.setLogementGenreRequis("M");
                                eleve.setGenre("M");
                                break;
                            default :
                                item.setLogementGenreRequis("");
                                break;
                    }
                        break;
                    
                    case "PRENOM":
                        if (!value.equals("")){
                            if (value.equals("LOGEMENT PMR AU BESOIN")){
                                typeAppart = typeAppartRepository.getByTypeAppartNom(TypeAppart.APPARTPMR);
                                item.setTypeAppartNom(typeAppart);     
                            }else{
                                personne.setPersonnePrenom(value);
                            }
                            
                        }
                        break;
                    case "NOM":
                        if (!value.equals("")){
                            personne.setPersonneNom(value);
                        }
                        break;
                    case "PAYS D ORIGINE":
                        if (!value.equals("")){
                            eleve.setElevePayshab(value);
                        }
                        break;
                    case "MAIL":
                        if (!value.equals("")){
                            eleve.setEleveMail(value );
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

            Eleve temp = null; 
            if (personne.isPersonneValid() ) {
                temp = eleveRepository.getByPersonNomPrenomMail(personne.getPersonneNom(), personne.getPersonnePrenom(),eleve.getEleveMail());
                if (temp == null) {
                    Personne tempP = personneRepository.create(personne.getPersonneNom(), personne.getPersonnePrenom(), personne.getRoleId());
                    eleve.setEleveConfirm(true);
                    eleve.setNumscei(-1);
                    eleveRepository.setPersonne(eleve, tempP, personneRepository);
                    temp=eleveRepository.getByPersonNomPrenomMail(personne.getPersonneNom(), personne.getPersonnePrenom(),eleve.getEleveMail());
                    item.setLogementPlacesDispo(item.getLogementPlacesDispo()-1);
                }
            }
            Logement tempLog = null;
            if (((item.getLogementNumero() != null) && (!item.getLogementNumero().isEmpty()) )) {
                tempLog = repository.getByLogementNumero(item.getLogementNumero());
                if (tempLog == null) {
                    handleNewLogement(item,temp);
                }
                else{ 
                    handleExistingLogement(item, tempLog,temp);
                    
                }
            }
    }
    
    public void handleNewLogement(Logement item, Eleve eleve) {  
        Logement tempLog = repository.create(item);
            if (eleve!=null) {
                eleve.setLogementNumero(tempLog);
                int id =eleve.getEleveId();
                eleveRepository.updateRez(id, eleve);
            }
    }

    /**
     *
     * @param item Logement lu dans le fichier csv
     * @param tempLog Logement récupéré dans la base de données 
     * @param eleve
     */
    public void handleExistingLogement(Logement item, Logement tempLog,Eleve eleve) {
        if (eleve!=null) {
            repository.update(item.getLogementNumero(), item, tempLog.getLogementPlacesDispo() - 1, tempLog.getTypeAppartNom());
            eleve.setLogementNumero(tempLog);
            int id =eleve.getEleveId();
            eleveRepository.updateRez(id, eleve);
        } 
    }
    
        
    public void importCsvRez(File importFile) {
        // Build creation method
        // Read file
        try {
            
            FileInputStream fis = new FileInputStream(importFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();
            if (line != null) {
                // Get header
                List<String> header = new LinkedList<>();
                StringTokenizer st = new StringTokenizer(line, ";");
                while ((st.hasMoreElements())&& (header.size()<14)) {
                    String name = st.nextToken().trim();
                    header.add(ApplicationTools.removeAccentsAndSpecialCharacters(name));
                }
                // Get lines
                line = reader.readLine();
                while (line != null) {
                    //Pourquoi pas StringTokenizer ? Parce qu'il se d�barrasse des �l�ments vides
                    // => probl�mes d'indexation des informations... Essaie ! Tu verras
                    List<String> lineValues = new LinkedList<>();
                    int i = 0;
                    String elem = "";
                    while ((i < line.length())&&(lineValues.size()<14)) {
                        if (line.substring(i, i + 1).equals(";")) {
                            lineValues.add(ApplicationTools.removeAccentsAndSpecialCharacters(elem));
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
