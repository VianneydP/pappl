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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private ModelAndView handleEleveList(Connexion user) {
        String modelName = "EleveList";
        ModelAndView returned = ApplicationTools.getModel(modelName, user);
        Collection<Eleve> maListe=repository.findAll(Sort.by(Sort.Direction.ASC, "eleveId"));
        returned.addObject("itemList", maListe);
        return returned;
    }

    @RequestMapping(value = "EleveList.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveList(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            returned = handleEleveList(user);
        }
        return returned;
    }

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
            returned.addObject("codeCommuneList", communeRepository.findAll());
            returned.addObject("logementNumeroList", logementRepository.findAll());
            returned.addObject("personneList", personneRepository.findAll());
            returned.addObject("typeSouhaitList", souhaitRepository.findAll());
        }
        return returned;
    }

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
            returned.addObject("codeCommuneList", communeRepository.findAll());
            returned.addObject("logementNumeroList", logementRepository.findAll());
            returned.addObject("personneIdList", personneRepository.findAll());
            returned.addObject("typeSouhaitList", souhaitRepository.findAll());
        }
        return returned;
    }

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
    
    //Ajoute par moi
    private static String generateUniqueFileName(Path destination) {
        //String fileExtension = destination.getFileName().substring(destination.getFileName().lastIndexOf("."));
        //String fileExtension = getFileExtension(fileNameWithExtension);
        //return System.currentTimeMillis() + "_" + destination.getFileName().toString()+fileExtension;
        return System.currentTimeMillis() + "_" + destination.getFileName().toString();
    }
    /**
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return ""; // No extension found
    }
    **/

    @RequestMapping(value = "EleveSave.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTEleveSave(HttpServletRequest request) throws IOException {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel("index", null);
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request, "eleveId");
            Eleve item = repository.getByEleveId(id);
            //Ajoute par moi
            Integer id2 = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne pers = personneRepository.getByPersonneId( id2 );
            //Eleve item=repository.getByPersonneId(id2);
            //Eleve item=repository.getByPersonNomPrenomNumscei(pers.getPersonneNom(),pers.getPersonnePrenom(),ApplicationTools.getIntFromRequest(request, "login"));

            Eleve dataToSave = new Eleve();
            //Ajoute par moi
            File notif=ApplicationTools.getFileFromRequest(request,"eleveFile");
            String filename=notif.getName();
            //String extension=getFileExtension(filename);
            String targetDirectory = request.getServletContext().getRealPath("televersements");
            if(notif!=null){
            Path destination = Paths.get(targetDirectory);
            //Path destination = new File(targetDirectory).toPath();
            String newFileName = pers.getPersonneNom()+"_"+pers.getPersonnePrenom()+generateUniqueFileName(destination)+".pdf";
            Path destinationWithUniqueName =destination.resolve(newFileName);
            Files.copy(notif.toPath(), destinationWithUniqueName);
            }
            dataToSave.setNumscei(item.getNumscei());
            dataToSave.setNumscei(ApplicationTools.getIntFromRequest(request, "NumScei"));
            // Retreive values from request
            dataToSave.setEleveId(ApplicationTools.getIntFromRequest(request, "eleveId"));
            dataToSave.setEleveDateNaissance(ApplicationTools.getDateFromRequest(request, "eleveDateNaissance"));
            dataToSave.setGenre(ApplicationTools.getStringFromRequest(request, "genre"));
            dataToSave.setElevePayshab(ApplicationTools.correctString(ApplicationTools.getStringFromRequest(request, "elevePayshab")));
            dataToSave.setEleveVillehab(ApplicationTools.correctString(ApplicationTools.getStringFromRequest(request, "eleveVillehab")));
            dataToSave.setEleveCodepostal(ApplicationTools.getIntFromRequest(request, "eleveCodepostal"));
            dataToSave.setEleveMail(ApplicationTools.getStringFromRequest(request, "eleveMail"));
            dataToSave.setEleveNumtel(ApplicationTools.getStringFromRequest(request, "eleveNumtel"));
            dataToSave.setEleveBoursier(ApplicationTools.getBooleanFromRequest(request, "eleveBoursier"));
            dataToSave.setEleveInfosup(ApplicationTools.getStringFromRequest(request, "eleveInfosup"));
            dataToSave.setTypeSouhait(new Souhait(ApplicationTools.getStringFromRequest(request, "typeSouhait")));
            //Integer codeCommuneTemp = ApplicationTools.getIntFromRequest(request, "codeCommune");
            //dataToSave.setCodeCommune(communeRepository.getByCodeCommune(codeCommuneTemp));
            String logementNumeroTemp = ApplicationTools.getStringFromRequest(request, "logementNumero");
            dataToSave.setLogementNumero(logementRepository.getByLogementNumero(logementNumeroTemp));
            Integer personneIdTemp = ApplicationTools.getIntFromRequest(request, "personneId");
            dataToSave.setPersonne(personneRepository.getByPersonneId(personneIdTemp));
            //dataToSave.setCodeCommune(new Commune(ApplicationTools.findCodeForCommune(dataToSave.getEleveVillehab()).getCodeCommune()));
            
            //On set la commune grâce au nom de la ville
            dataToSave.setCodeCommune(ApplicationTools.findCodeForCommune(dataToSave.getEleveVillehab()));
            
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
            }
            //TODO : si code commune est encore null (n'a été trouvé ni à partir de la ville ni à partir du CP, créer une ALERTE pour la VE)
            //String typeSouhaitTemp = ApplicationTools.getStringFromRequest(request, "typeSouhait");
            //dataToSave.setTypeSouhait(souhaitRepository.getByTypeSouhait(typeSouhaitTemp));

            // Create if necessary then Update item
            if (item == null) {
                item = repository.create(dataToSave.getEleveId(), dataToSave.getEleveDateNaissance(), dataToSave.getGenre(), dataToSave.getElevePayshab(), dataToSave.getEleveVillehab(), dataToSave.getEleveCodepostal(), dataToSave.getPersonne(),dataToSave.getCodeCommune());
            }
            repository.update(item.getEleveId(), dataToSave);

            // Return to the list
            //returned = handleEleveList(user);
            returned=ApplicationTools.getModel("questionnaire", user);
        }
        return returned;
    }

    /**
     *
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
     *
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
     * Create an item form attribute lists
     *
     * @param header
     * @param values
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
                    case "eleveId":
                        item.setEleveId(ApplicationTools.getIntFromString(value));
                        break;
                    case "nom":
                        personne.setPersonneNom(value);
                        break;
                    case "prenom":
                        personne.setPersonnePrenom(value);
                        break;
                    case "naissance":
                        naissance = ApplicationTools.isDate(value);
                        item.setEleveDateNaissance(naissance);
                        break;
                    case "genre":
                        item.setGenre(value);
                        break;
                    case "pays":
                        item.setElevePayshab(value);
                        break;
                    case "ville":
                        item.setEleveVillehab(value);
                        break;
                    case "codepostal":
                        item.setEleveCodepostal(ApplicationTools.getIntFromString(value));
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
            if (((personne.getPersonneNom() != null) && (!personne.getPersonneNom().isEmpty()) && (item.getEleveDateNaissance() != null))
                    && ((personne.getPersonnePrenom() != null) && (!personne.getPersonnePrenom().isEmpty()))) {
                temp = repository.getByPersonNomPrenomNaissance(personne.getPersonneNom(), personne.getPersonnePrenom(), item.getEleveDateNaissance());
            }
            
            if (temp == null) {
                Personne tempP = personneRepository.create(personne.getPersonneNom(), personne.getPersonnePrenom(), roleId);
                temp = repository.create(item.getEleveId(),item.getEleveDateNaissance(), 
                        item.getGenre(), item.getElevePayshab(), item.getEleveVillehab(), item.getEleveCodepostal() , personne);
                repository.setPersonne(item, tempP, personneRepository);
            }
        }
    }
    
    @RequestMapping(value = "ouidef.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTouidef(HttpServletRequest request){
        ModelAndView returned=null;
        boolean oui = ApplicationTools.getBooleanFromRequest(request, "ouidef");
        Connexion user = ApplicationTools.checkAccess(connexionRepository,request);
        if (oui){
            returned=ApplicationTools.getModel("questionnaire", user);
        }else{
            returned=ApplicationTools.getModel("errorPage", null);
        }
        return returned;    
    }

}
