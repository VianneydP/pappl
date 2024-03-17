/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */

//-*- coding: utf-8 
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
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.repositories.CommuneRepository;
import fr.centrale.nantes.ecnlogement.repositories.LogementRepository;
import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import fr.centrale.nantes.ecnlogement.repositories.SouhaitRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Commune;
import fr.centrale.nantes.ecnlogement.items.Logement;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;
import fr.centrale.nantes.ecnlogement.items.Souhait;
import fr.centrale.nantes.ecnlogement.repositories.RoleRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

@Controller
public class EleveController {

    @Autowired
    private EleveRepository repository;

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private LogementRepository logementRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private SouhaitRepository souhaitRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Méthode des controllers pour consituer la vue EleveList
     * @param user Connexion utilisée
     * @return Vue EleveList
     */
    private ModelAndView handleEleveList(Connexion user) {
        String modelName = "EleveList";
        ModelAndView returned = ApplicationTools.getModel(modelName, user);
        Collection<Eleve> maListe=repository.findAll(Sort.by(Sort.Direction.ASC, "eleveId"));
        returned.addObject("itemList", maListe);
        return returned;
    }
    
    /**
     * Controller lié au bouton "Visualiation" et à l'import du fichier 
     * SCEI (après l'import, on affiche la liste pour vérification)
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "EleveList.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        String action = ApplicationTools.getStringFromRequest(request, "nomEtape");
        
        if (user == null) {
            returned = ApplicationTools.getModel("loginAdmin", null);
        } else {
            returned = handleEleveList(user);
        }
        if ("importScei".equals(action)) {
            File fichierScei=ApplicationTools.getFileFromRequest(request,"SceiImport");
            String filename=fichierScei.getName();
            
            String targetDirectory = request.getServletContext().getRealPath("FichierScei");
            
            
            if(fichierScei!=null){
                try {
                    Path path = Paths.get(targetDirectory);
                    if (!Files.exists(path)) {
                        try {
                            Files.createDirectories(path);
                        } catch (IOException e) {
                        }
                    }
                    
                    //String newFileName ="fichierScei.csv";
                    
                    String newFileName = "fichierScei"+ApplicationTools.generateUniqueFileName(path)+".csv";
                    Path destinationWithUniqueName =path.resolve(newFileName);
                    
                    //Files.copy(fichierScei.toPath(), destinationWithUniqueName);
                    //Path destination =path.resolve(newFileName);
//                    
                    Files.copy(fichierScei.toPath(), destinationWithUniqueName);
                    importCsvScei(fichierScei) ;
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    returned = handleEleveList(user);
                }
                
        }
        }   
        return returned;
    }
    
    /**
     * Controller lié au bouton de modification d'un élève sur la page EleveList
     * @param request Requête HTTP
     * @return Vue EleveEdit
     */
    @RequestMapping(value = "EleveEdit.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);

            // Edit item
            String modelName = "EleveEdit";
            returned = ApplicationTools.getModel(modelName, user);
            returned.addObject("item", item);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(item.getEleveDateNaissance());
            returned.addObject("dateNaissance", formattedDate);
            returned.addObject("codeCommuneList", communeRepository.findAll());
            returned.addObject("logementNumeroList", logementRepository.findAll());
            returned.addObject("personneList", personneRepository.findAll());
            returned.addObject("typeSouhaitList", souhaitRepository.findAll());
            List<String> genres =new ArrayList<String>(3);
            genres.addAll(List.of("M","F","A"));
            returned.addObject("genreList", genres);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton d'information d'un élève sur la page EleveList
     * @param request Requête HTTP
     * @return Vue EleveShow
     */
    @RequestMapping(value = "EleveShow.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveShow(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);

            // Edit item
            String modelName = "EleveShow";
            returned = ApplicationTools.getModel(modelName, user);
            returned.addObject("item", item);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton "New" de la page EleveList
     * @param request Requête HTTP
     * @return Vue EleveEdit
     */
    @RequestMapping(value = "EleveCreate.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveCreate(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Create new item
            Eleve item = new Eleve();
            String modelName = "EleveEdit";
            returned = ApplicationTools.getModel(modelName, user);
            returned.addObject("item", item);
            returned.addObject("communeList", communeRepository.findAll(Sort.by(Sort.Direction.ASC, "nomCommune")));
            returned.addObject("personneIdList", personneRepository.findAll());
            returned.addObject("typeSouhaitList", souhaitRepository.findAll());
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton de suppression de la page EleveList
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "EleveRemove.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveRemove(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);

            // Remove item
            repository.remove(item);

            // Back to the list
            returned = handleEleveList(user);
        }
        return returned;
    }
    
    /**
     * Controller lié à la première validation du formulaire par un élève
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "EleveSave.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveSave(HttpServletRequest request) throws IOException {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("reconnect", null);
        } else {
            // Récupération de l'élève qui a rempli le formulaire
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);
            Eleve dataToSave = new Eleve();
            
            //Vérification (création si besoin) du dossier pour les notifs de bourse
            //Exportation des fichiers
            File notif=ApplicationTools.getFileFromRequest(request,"eleveFile");
            String targetDirectory = request.getServletContext().getRealPath("televersements");
            Path path = Paths.get(targetDirectory);
            if (!Files.exists(path)) {
                // Créer le répertoire s'il n'existe pas
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                }
            }
            if(ApplicationTools.getBooleanFromRequest(request, "eleveBoursier")){
                Path destination = Paths.get(targetDirectory);
                //Path destination = new File(targetDirectory).toPath();
                String newFileName = item.getPersonne().getPersonneNom()+"_"+item.getPersonne().getPersonnePrenom()+"_bourse_"+ApplicationTools.generateUniqueFileName(destination)+".pdf";
                Path destinationWithUniqueName =destination.resolve(newFileName);
                Files.copy(notif.toPath(), destinationWithUniqueName);
            }
            String infosupVE=dataToSave.getEleveInfosupVe();
            dataToSave.setNumscei(item.getNumscei());
            // Retreive values from request
            dataToSave.setEleveId(ApplicationTools.getIntFromRequest(request, "eleveId"));
            dataToSave.setEleveDateNaissance(ApplicationTools.getDateFromRequest(request, "eleveDateNaissance"));
            //Comparaison des données SCEI
            if (!dataToSave.getEleveDateNaissance().equals(item.getEleveDateNaissance())){
                infosupVE+="\n## DateNaissProb: SCEI="+item.getEleveDateNaissance()+"//Form="+dataToSave.getEleveDateNaissance();
            }
            dataToSave.setGenre(ApplicationTools.getStringFromRequest(request, "genre"));
            if (!dataToSave.getGenre().equals(item.getGenre())){
                infosupVE+="\n## GenreProb: SCEI="+item.getGenre()+"//Form="+dataToSave.getGenre();
            }
            dataToSave.setElevePayshab(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "elevePayshab")));
            if (!dataToSave.getElevePayshab().equalsIgnoreCase(item.getElevePayshab())){
                infosupVE+="\n## PaysHabProb: SCEI="+item.getElevePayshab()+"//Form="+dataToSave.getElevePayshab();
            }
            dataToSave.setEleveVillehab(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "eleveVillehab")));
            if (dataToSave.getElevePayshab().equalsIgnoreCase("france")){
                dataToSave.setEleveCodepostal(ApplicationTools.getIntFromRequest(request, "eleveCodepostal"));
                dataToSave.setEleveVillehab(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "eleveVillehabFr")));
                int cp_form=dataToSave.getEleveCodepostal();
                String dep_form=Integer.toString(cp_form);
                dep_form=dep_form.substring(0, 2);
                Integer cp_begin=item.getEleveCodepostal();
                String dep_begin=Integer.toString(cp_begin);
                dep_begin=dep_begin.substring(0, 2);
                if (dep_begin.equals(dep_form)){
                    Commune co=communeRepository.getByDepNom(dep_begin, dataToSave.getEleveVillehab());
                    dataToSave.setCommune(co);
                    if (dataToSave.getCommune()!=null){
                        dataToSave.setEleveVillehab(dataToSave.getCommune().getNomCommune());
                    } else {
                        infosupVE+="\n## CommuneProb: non rec. dans bdd (verifier nom+CP)";
                    }
                }
            }
            dataToSave.setEleveMail(ApplicationTools.getStringFromRequest(request, "eleveMail"));
            dataToSave.setEleveNumtel(ApplicationTools.getStringFromRequest(request, "eleveNumtel"));
            dataToSave.setEleveBoursier(ApplicationTools.getBooleanFromRequest(request, "eleveBoursier"));
            dataToSave.setEleveInfosup(ApplicationTools.getStringFromRequest(request, "eleveInfosup"));
            dataToSave.setTypeSouhait(new Souhait(ApplicationTools.getStringFromRequest(request, "typeSouhait")));
            dataToSave.setPersonne(item.getPersonne());
            dataToSave.setEleveInfosupVe(infosupVE);
            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave);
            }
            repository.update(item.getEleveId(), dataToSave);

            // Return to the list
            //returned = handleEleveList(user);
            returned=ApplicationTools.getModel("confirmation", user);
        }
        return returned;
    }
    
    /**
     * Controller lié à la nième validation du formulaire par un élève
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "EleveResave.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveResave(HttpServletRequest request) throws IOException {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("reconnect", null);
        } else {
            // Récupération de l'élève qui a rempli le formulaire
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);
            Eleve dataToSave = new Eleve();
            
            //Vérification (création si besoin) du dossier pour les notifs de bourse
            String targetDirectory = request.getServletContext().getRealPath("televersements");
            Path path = Paths.get(targetDirectory);
            if (!Files.exists(path)) {
                // Créer le répertoire s'il n'existe pas
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                }
            }
            
            dataToSave.setNumscei(item.getNumscei());
            dataToSave.setEleveId(item.getEleveId());
            dataToSave.setEleveDateNaissance(item.getEleveDateNaissance());
            dataToSave.setEleveCodepostal(item.getEleveCodepostal());
            dataToSave.setEleveVillehab(item.getEleveVillehab());
            dataToSave.setEleveMail(item.getEleveMail());
            dataToSave.setEleveNumtel(item.getEleveNumtel());
            dataToSave.setPersonne(item.getPersonne());
            dataToSave.setEleveInfosupVe(item.getEleveInfosupVe());
            dataToSave.setEleveBoursier(false);
            boolean bours=ApplicationTools.getBooleanFromRequest(request, "eleveBoursier");
            if (bours==true){
                dataToSave.setEleveBoursier(true);
                File notif=ApplicationTools.getFileFromRequest(request,"eleveFile");
                if (notif!=null){
                    Path destination = Paths.get(targetDirectory);
                    //Path destination = new File(targetDirectory).toPath();
                    String newFileName = item.getPersonne().getPersonneNom()+"_"+item.getPersonne().getPersonnePrenom()+"_bourse_"+ApplicationTools.generateUniqueFileName(destination)+".pdf";
                    Path destinationWithUniqueName =destination.resolve(newFileName);
                    Files.copy(notif.toPath(), destinationWithUniqueName); 
                }
            }
            
            dataToSave.setTypeSouhait(new Souhait(ApplicationTools.getStringFromRequest(request, "typeSouhait")));
            if (dataToSave.getTypeSouhait()==null){
                dataToSave.setTypeSouhait(item.getTypeSouhait());
            }
            dataToSave.setEleveInfosup(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "eleveInfosup")));
            repository.update(item.getEleveId(), dataToSave);
            
            //Return to the list
            //returned = handleEleveList(user);
            returned=ApplicationTools.getModel("confirmation", user);
        }
        return returned;
    }
    
    /**
     * Controller lié à la modification d'un élève par un assistant
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "EleveSaveAdmin.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveSaveAdmin(HttpServletRequest request) throws IOException {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);
            Personne pers=item.getPersonne();
      
            Eleve dataToSave = new Eleve();
            
            dataToSave.setPersonne(pers);
            dataToSave.setNumscei(item.getNumscei());
            // Retreive values from request
            dataToSave.setEleveId(ApplicationTools.getIntFromRequest(request, "eleveId"));
            dataToSave.setEleveDateNaissance(ApplicationTools.getDateFromRequest(request, "eleveDateNaissance"));
            dataToSave.setGenre(ApplicationTools.getStringFromRequest(request, "genre"));
            dataToSave.setElevePayshab(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "elevePayshab")));
            dataToSave.setEleveVillehab(ApplicationTools.removeAccentsAndSpecialCharacters(ApplicationTools.getStringFromRequest(request, "eleveVillehab")));
            dataToSave.setEleveCodepostal(ApplicationTools.getIntFromRequest(request, "eleveCodepostal"));
            dataToSave.setEleveMail(ApplicationTools.getStringFromRequest(request, "eleveMail"));
            dataToSave.setEleveNumtel(ApplicationTools.getStringFromRequest(request, "eleveNumtel"));
            dataToSave.setEleveBoursier(ApplicationTools.getBooleanFromRequest(request, "eleveBoursier"));
            dataToSave.setElevePMR(ApplicationTools.getBooleanFromRequest(request, "elevePMR"));
            dataToSave.setEleveInfosup(ApplicationTools.getStringFromRequest(request, "eleveInfosup"));
            dataToSave.setEleveInfosupVe(ApplicationTools.getStringFromRequest(request, "eleveInfosupVE"));
            dataToSave.setTypeSouhait(new Souhait(ApplicationTools.getStringFromRequest(request, "typeSouhait")));
            dataToSave.setEleveConfirm(ApplicationTools.getBooleanFromRequest(request, "eleveConfirm"));
            //Integer codeCommuneTemp = ApplicationTools.getIntFromRequest(request, "codeCommune");
            //dataToSave.setCodeCommune(communeRepository.getByCodeCommune(codeCommuneTemp));
            
            //On set la commune grâce au nom de la ville
            if (dataToSave.getElevePayshab().equalsIgnoreCase("france")){
                dataToSave.setEleveCodepostal(ApplicationTools.getIntFromRequest(request, "eleveCodepostal"));
                int cp_form=dataToSave.getEleveCodepostal();
                String dep_form=Integer.toString(cp_form);
                dep_form=dep_form.substring(0, 2);
                Integer cp_begin=item.getEleveCodepostal();
                String dep_begin=Integer.toString(cp_begin);
                dep_begin=dep_begin.substring(0, 2);
                Commune saCommune = communeRepository.getByDepNom(dep_begin, dataToSave.getEleveVillehab());
                if (saCommune!=null){
                    dataToSave.setCommune(saCommune);
                }
            }
            /*
            //Si l'élève n'a pas renseigné le code postal, on le rajoute
            if(dataToSave.getEleveCodepostal()==-1 || dataToSave.getEleveCodepostal()==0){
                dataToSave.setEleveCodepostal(dataToSave.getCodeCommune().getCodePostal());
            }
            
            //Si la commune n'a pas été trouvée à partir du nom (faute de frappe dans le nom) et que l'élève a renseigné un code postal, on la set à partir du code postal
            if(dataToSave.getEleveCodepostal()!=-1 && dataToSave.getEleveCodepostal()!=0 && dataToSave.getCodeCommune()==null){
                dataToSave.setCodeCommune(ApplicationTools.findCodeForCodePostal(dataToSave.getEleveCodepostal()));
                //Si la commune a été trouvé à partir du CP, il doit être bon, on rectifie donc le nom de la ville
                if(dataToSave.getCodeCommune()!=null){
                    dataToSave.setEleveVillehab(dataToSave.getCodeCommune().getNomCommune());
                }
            }*/
            
            //TODO : si la commune est encore null (n'a été trouvé ni à partir de la ville ni à partir du CP, créer une ALERTE pour la VE)
            //String typeSouhaitTemp = ApplicationTools.getStringFromRequest(request, "typeSouhait");
            //dataToSave.setTypeSouhait(souhaitRepository.getByTypeSouhait(typeSouhaitTemp));

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave);
            }
            repository.update(item.getEleveId(), dataToSave);

            // Return to the list
            returned = handleEleveList(user);
        }
        return returned;
    }

    /**
     * NON utilisé, gééré automatiquement, l'import renvoie sur
     * la liste des élèves (pour vérifier la réussite de l'import)
     * voir handlePOSTEleveList
     * @param request
     * @return
     */
    @RequestMapping(value = "EleveImport.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Get CSV file
            File tempFile = ApplicationTools.getFileFromRequest(request, "importFile");
            if (tempFile != null) {
                importCSV(tempFile);
            }
            ApplicationTools.cleanRequest(request);

            returned = handleEleveList(user);
        }
        return returned;
    }

    /**
     * Import CSV file
     * NON Utilisé, fonction générée automatiquement
     * @param importFile
     */
    private void importCSV(File importFile) {
        // Read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));

            String line = reader.readLine();
            if (line != null) {
                // Get header
                List<String> header = new LinkedList<>();
                StringTokenizer st = new StringTokenizer(line, "\t");
                while (st.hasMoreElements()) {
                    String name = st.nextToken().trim();
                    header.add(name);
                }

                // Get lines
                line = reader.readLine();
                while (line != null) {
                    st = new StringTokenizer(line, "\t");
                    List<String> lineValues = new LinkedList<>();
                    while (st.hasMoreElements()) {
                        String value = st.nextToken();
                        lineValues.add(value);
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

    /**
     * Crée un élève à partir d'une ligne d'un csv formaté type ( le type est 
     * détaillé dans la doc admin)
     * @param header valeurs de l'entête des colonnes du csv
     * @param values valeurs associées pour l'élève
     */
    public void createItem(List<String> header, List<String> values) {
        Role roleId = roleRepository.getByRoleId(Role.ROLEELEVE);
        Eleve item = new Eleve();
        Personne personne = new Personne();
        personne.setRoleId(roleId);
        Date naissance;
        
        boolean canDoIt = true;
        Iterator<String> valueIterator = values.iterator();
        for (String name : header) {
            if (valueIterator.hasNext()) {
                String value = valueIterator.next().trim();
                switch (name) {
                    case "NUM SCEI":
                        item.setNumscei(ApplicationTools.getIntFromString(value));
                        break;
                    case "NOM":
                        personne.setPersonneNom(value);
                        break;
                    case "PRENOM":
                        personne.setPersonnePrenom(value);
                        break;
                    case "NAISSANCE":
                        naissance = ApplicationTools.isDate(value);
                        item.setEleveDateNaissance(naissance);
                        break;
                    case "CIVILITE":
                        switch (value){
                            case "MME":
                                item.setGenre("F");
                                break;
                            case "M.":
                                item.setGenre("M");
                                break;
                            default :
                                item.setGenre("NON RENSEIGNE");
                                break;
                    }
                        break;
                    case "PAYS ADRESSE":
                        item.setElevePayshab(value);
                        break;
                    case "COMMUNE":
                        item.setEleveVillehab(value);
                        break;
                    case "CODE POSTAL":
                        item.setEleveCodepostal(ApplicationTools.getIntFromString(value));
                        break;
                    case "STATUT SCEI":
                        if (!value.equals("OUI DEFINITIF")){
                            canDoIt=false;
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

        if (canDoIt) {
            Eleve temp = null;
            if (personne.isPersonneValid()){
                temp = repository.getByPersonNomPrenomNaissance(personne.getPersonneNom(), personne.getPersonnePrenom(), item.getEleveDateNaissance());
                if (temp == null) {
                Personne tempP = personneRepository.create(personne.getPersonneNom(), personne.getPersonnePrenom(), roleId);
                item.setEleveConfirm(false);
                repository.setPersonne(item, tempP, personneRepository);
            }
            }
            
            
        }
    }
    
    /**
     * Controller pour trier les élèves confirmés en vue de les affecter aux logements (inutilisé pour l'instant)
     * @param request Requête HTTP
     * @return Vue EleveList
     */
    @RequestMapping(value = "trier.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTtrier(HttpServletRequest request){
        ModelAndView returned=null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository,request);
        if (user!=null){
            returned=ApplicationTools.getModel("EleveList", user);
            Collection<Eleve> aTrier=repository.findAll();
            Collection<Eleve> boursiers=new ArrayList<>();
            Collection<Eleve> internat=new ArrayList<>();
            Collection<Eleve> autres=new ArrayList<>();
            for(Eleve item:aTrier){
                if (item.getNumscei()!=-1 && item.getEleveConfirm()==true){
                    boolean flag=false;
                    if(!item.getElevePayshab().equalsIgnoreCase("france") && !flag){
                        internat.add(item);
                        flag=true;
                    }if(item.getEleveBoursier() && !flag){
                        boursiers.add(item);
                        flag=true;
                    }if(!flag){
                        autres.add(item);
                    }
                }
            }
            ArrayList<Eleve>retAutres=trierElevesDistance(autres);
            ArrayList<Eleve> retBours=trierElevesDistance(boursiers);
            ArrayList<Eleve> retInter=trierElevesDistance(internat);
            System.out.println(autres.size());
            System.out.println(retAutres.size());
            returned.addObject("boursiers",retBours);
            returned.addObject("internat",retInter);
            returned.addObject("autres",retAutres);
            ecritureCSVTri(request,retInter,retBours,retAutres);
        }else{
            returned=ApplicationTools.getModel("loginAdmin", null);
        }
    return returned;    
    }
    
    /**
     * Méthode du controller handlePOSTtrier qui trie les listes d'élèves
     * @param aTrier Collection d'élèves qui doit être triée
     * @return ArrayList d'élèves triée
     */
    public ArrayList<Eleve> trierElevesDistance(Collection<Eleve> aTrier){
        //Création du contenant de la future liste triée
        ArrayList<Eleve> returned=new ArrayList<>();
        ArrayList<Double> distances=new ArrayList<>();
        for (Eleve item:aTrier){
            Commune ville=item.getCommune();
            Double dist=communeRepository.distNantesCommune(ville.getCodeCommune());
            if(returned.isEmpty()){
                returned.add(item);
                distances.add(dist);
            }else{
                int i=0;
                while(i<distances.size() && dist<distances.get(i)){
                    i+=1;
                }
                distances.add(i, dist);
                returned.add(i,item);
            }
        }
        return returned;
    }
    
    /**
     * Méthode du controller handlePOSTtrier qui écrit le fichier csv contenant les élèves triés
     * @param request Requête HTTP
     * @param inter ArrayList triée des élèves internationaux
     * @param bours ArrayList triée des élèves boursiers
     * @param autres ArrayList triée des élèves autres
     */
    public void ecritureCSVTri(HttpServletRequest request,ArrayList<Eleve> inter,ArrayList<Eleve> bours,ArrayList<Eleve> autres){
        //String extension=getFileExtension(filename);
        String targetDirectory = request.getServletContext().getRealPath("tri_eleves");
        Path path = Paths.get(targetDirectory);
        if (!Files.exists(path)) {
            // Créer le répertoire s'il n'existe pas
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
            }
        }
        String nomFichier = request.getServletContext().getRealPath("tri_eleves\\eleves_tries");
        boolean Flag=false;
        int i=1;
        String newNomFichier=nomFichier;
        while (Flag==false){
            File fichier=new File(newNomFichier+".csv");
            if (fichier.exists()){
                newNomFichier=nomFichier+i;
                i+=1;
            }else{
                Flag=true;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newNomFichier+".csv"))) {
            
            // Écrire les lignes de données
            writer.write("$$ INTERNATIONAUX");
            writer.newLine();
            for (Eleve item:inter){
                writer.write(item.getEleveId()+";"+item.getPersonne().getPersonneNom()+";"+item.getPersonne().getPersonnePrenom()+";"+item.getNumscei().toString());
                writer.newLine();
            }
            writer.write("$$ BOURSIERS");
            writer.newLine();
            for (Eleve item:bours){
                writer.write(item.getEleveId()+";"+item.getPersonne().getPersonneNom()+";"+item.getPersonne().getPersonnePrenom()+";"+item.getNumscei().toString());
                writer.newLine();
            }
            writer.write("$$ AUTRES");
            writer.newLine();
            for (Eleve item:autres){
                writer.write(item.getEleveId()+";"+item.getPersonne().getPersonneNom()+";"+item.getPersonne().getPersonnePrenom()+";"+item.getNumscei().toString());
                writer.newLine();
            }

        } catch (IOException e) {
        }
    }
    
    /**
     * Méthode pour importer le fichier SCEI
     * @param importFile fichier à importer
     */
    public void importCsvScei(File importFile) {
        // Build creation method
        // Read file
        try {
            FileInputStream fis = new FileInputStream(importFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();
            if (line != null) {
                // Get header (valeur de l'entête des colonnes)
                List<String> header = new LinkedList<>();
                StringTokenizer st = new StringTokenizer(line, ";");
                while (st.hasMoreElements()&& (header.size()<10)) {
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
                    while ((i < line.length())&&(lineValues.size()<10)) {
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
                    lineValues.add(ApplicationTools.removeAccentsAndSpecialCharacters(elem));
                    // On crée un élève avec les valeurs d'une ligne
                    createItem(header, lineValues);
                    // On passe à la ligne suivante
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