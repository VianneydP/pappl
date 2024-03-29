/**
 *
 * @author viann
 */
package fr.centrale.nantes.ecnlogement.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.centrale.nantes.ecnlogement.repositories.ConnexionRepository;
import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import fr.centrale.nantes.ecnlogement.repositories.RoleRepository;
import fr.centrale.nantes.ecnlogement.repositories.TexteRepository;
import fr.centrale.nantes.ecnlogement.items.*;

import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;
import fr.centrale.nantes.ecnlogement.repositories.DatesRepository;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.repositories.LogementRepository;
import fr.centrale.nantes.ecnlogement.repositories.TypeAppartRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

@Controller
public class GestionAdminController {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private DatesRepository datesRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private LogementRepository logementRepository;
    
    @Autowired
    private TexteRepository texteRepository;
    
    
    /**
     * Controller lié au bouton "Import" dans la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue de la page d'import
     */
    @RequestMapping(value = "pageImport.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTPageImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null);
        } else {
            returned=ApplicationTools.getModel("importAdmin", user);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton "Dates" dans la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue datesMissionLogement
     */
    @RequestMapping(value = "afficheDates.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTGestionAdmin(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned=ApplicationTools.getModel("datesMissionLogement", user);
            //Récupération de l'année actuelle
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);
            int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
            Dates adminDates = datesRepository.getByAnnee(annee);
            if (adminDates!=null){
                returned.addObject("dateAnnee", annee);
                returned.addObject("dateDebut",dateFormat.format(adminDates.getDatesDebut()));
                returned.addObject("dateFin",dateFormat.format(adminDates.getDatesFin()));
                returned.addObject("dateResultats",dateFormat.format(adminDates.getDatesResultats()));
            }
        }
        return returned;
    }
    
    /**
     * Controller lié à la validation des dates dans la page datesMissionLogement
     * @param request Requête HTTP
     * @return Vue datesMissionLogement
     */
    @RequestMapping(value = "majDates.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTMajDates(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned = ApplicationTools.getModel("datesMissionLogement", user);
            int dateAnnee=ApplicationTools.getIntFromRequest(request, "annee");
            Date dateDebut=ApplicationTools.getTimestampFromRequest(request, "debut");
            Date dateFin=ApplicationTools.getTimestampFromRequest(request, "fin");
            Date dateResultats=ApplicationTools.getTimestampFromRequest(request, "resultats");
            if (datesRepository.getByAnnee(dateAnnee)==null){
                if (dateDebut!=null && dateFin!=null && dateResultats!=null){
                    //Verification de la cohérence chronologique des dates
                    if (dateDebut.before(dateFin) && dateFin.before(dateResultats)){
                        datesRepository.create(dateAnnee, dateDebut, dateFin, dateResultats);
                    }else{
                        returned.addObject("erreurChronologique", true);
                    }
                } 
            }else{
                if(dateDebut==null){
                    dateDebut=datesRepository.getByAnnee(dateAnnee).getDatesDebut();
                }if(dateFin==null){
                    dateFin=datesRepository.getByAnnee(dateAnnee).getDatesFin();
                }if(dateResultats==null){
                    dateResultats=datesRepository.getByAnnee(dateAnnee).getDatesResultats();
                }
                //Verification de la cohérence chronologique des dates
                if (dateDebut.before(dateFin) && dateFin.before(dateResultats)){
                    datesRepository.create(dateAnnee, dateDebut, dateFin, dateResultats);
                }else {
                    returned.addObject("erreurChronologique", true);
                }         
            }
            //Recuperation des donnees pour le nouvel affichage
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(now);
            int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
            Dates adminDates = datesRepository.getByAnnee(annee);
            if (adminDates!=null){
                returned.addObject("dateAnnee", annee);
                returned.addObject("dateDebut",dateFormat.format(adminDates.getDatesDebut()));
                returned.addObject("dateFin",dateFormat.format(adminDates.getDatesFin()));
                returned.addObject("dateResultats",dateFormat.format(adminDates.getDatesResultats()));
            }
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton "Suppression BDD" de la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue suppressionBDD
     */
    @RequestMapping(value = "suppressionBDD.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTSuppressionBDD(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned=ApplicationTools.getModel("suppressionBDD", user);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton rouge de la page suppressionBDD
     * @param request Requête HTTP
     * @return Vue suppressionConfirmation
     */
    @RequestMapping(value = "suppressionConfirmation.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTSuppressionConfirmation(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned=ApplicationTools.getModel("suppressionConfirmation", user);
        }
        return returned;
    }
    
    /**
     * Controller lié à la validation dans suppressionConfirmation
     * @param request Requête HTTP
     * @return Vue suppressionBDD avec message reussite/erreur
     */
    @RequestMapping(value = "suppressionConfirmee.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTSuppressionConfirmee(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            //Test de la correspondance des phrases
            //ATTENTION : doit correspondre à message.suppressionConfMessage
            if (ApplicationTools.getStringFromRequest(request, "conf").contentEquals("Je confirme la suppression de toutes les données.")){
                Personne moi=user.getPersonne();
                //Suppression des donnees
                connexionRepository.deleteAll();
                eleveRepository.deleteAll();
                personneRepository.deleteAll();
                logementRepository.deleteAll();
                //Remise de la connexion de l'admin et de sa personne
                Connexion user1 = connexionRepository.create(user);
                returned=ApplicationTools.getModel("suppressionBDD", user1);
                returned.addObject("suppressionFaite",true);
            }else{
                returned=ApplicationTools.getModel("suppressionBDD", user);
                returned.addObject("suppressionFaite",false);
            }
        }
        return returned;
    }
    
    /**
     * Méthode liée au controller handlePOSTGestionAssistants pour récupérer les données Assistants
     * @param user Connexion utilisée
     * @return ModelAndView AssisList avec les assistants
     */
    private ModelAndView handleAssistList(Connexion user) {
        String modelName = "AssistList";
        ModelAndView returned = ApplicationTools.getModel(modelName, user);
        Collection<Personne> maListe=personneRepository.findAll(Sort.by(Sort.Direction.ASC, "personneNom"));
        // Filtrer maListe pour retirer toutes les personnes dont le roleId est égal à Role.ROLEASSIST
        List<Personne> filteredList = new ArrayList<>();
        // Parcourir la liste et ajouter les personnes avec un roleId différent de Role.ROLEASSIST à la nouvelle liste
        for (Personne personne : maListe) {
            if (personne.getRoleId().getRoleId()==Role.ROLEASSIST) {
                filteredList.add(personne);
            }
        }
        maListe.clear();
        returned.addObject("itemList", filteredList);
        return returned;
    }
    
    /**
     * Controller lié au bouton "Gestion des Assistants" sur la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue AssisList
     */
    @RequestMapping(value = "gestionAssistants.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTGestionAssistants(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned=handleAssistList(user);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton de modification/édition d'un assistant sur la page AssisList
     * @param request Requête HTTP
     * @return Vue AssistEdit (avec assistant à modifier)
     */
    @RequestMapping(value="AssistEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTAssistEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne item = personneRepository.getByPersonneId( id );
            returned = ApplicationTools.getModel("AssistEdit", user );
            if (item!=null){
                // Envoi de l'item pour modification
                returned.addObject("item", item );
            }
        }
        return returned;
    }
    
    /**
     * Controller lié à la validation de la page AssistEdit
     * @param request Requête HTTP
     * @return Vue AssisList
     */
    @RequestMapping(value="AssistSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTAssistSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Integer id = ApplicationTools.getIntFromRequest(request,"personneId");
            Personne item = personneRepository.getByPersonneId( id );
            Personne dataToSave = new Personne();
            // Retreive values from request
            dataToSave.setPersonneNom(ApplicationTools.getStringFromRequest(request,"personneNom"));
            dataToSave.setPersonnePrenom(ApplicationTools.getStringFromRequest(request,"personnePrenom"));
            dataToSave.setPersonneLogin(ApplicationTools.getStringFromRequest(request,"personneLogin"));
            dataToSave.setPersonnePassword(ApplicationTools.encryptPassword(ApplicationTools.getStringFromRequest(request,"personnePassword")));
            dataToSave.setRoleId(roleRepository.getByRoleId(Role.ROLEASSIST));

            // Create if necessary then Update item
            if (item == null){
                if (!dataToSave.getPersonneNom().equals("") && !dataToSave.getPersonnePrenom().equals("") && !dataToSave.getPersonneLogin().equals("") && !dataToSave.getPersonnePassword().equals("")) {
                    item = personneRepository.create(dataToSave.getPersonneNom(), dataToSave.getPersonnePrenom(), dataToSave.getRoleId());
                    personneRepository.update(item.getPersonneId(), dataToSave);
                }
            }else{
                if (dataToSave.getPersonneNom().equals("")){
                    dataToSave.setPersonneNom(item.getPersonneNom());
                } if (dataToSave.getPersonnePrenom().equals("")){
                    dataToSave.setPersonnePrenom(item.getPersonnePrenom());
                } if (dataToSave.getPersonneLogin().equals("")){
                    dataToSave.setPersonneLogin(item.getPersonneLogin());
                } if (dataToSave.getPersonnePassword().equals("")){
                    dataToSave.setPersonnePassword(item.getPersonnePassword());
                }
                personneRepository.update(item.getPersonneId(), dataToSave);
            }
            // Return to the list
            returned = handleAssistList(user);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton "Gestion des textes" sur la page GestionAdmin
     * @param request Requête HTTP
     * @return Vue TexteList
     */
    @RequestMapping(value = "gestionTextes.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTGestionTextes(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned = ApplicationTools.getModel("TexteList", user);
            Collection<Texte> maListe=texteRepository.findAll(Sort.by(Sort.Direction.ASC, "texteNom"));
            returned.addObject("itemList", maListe);
        }
        return returned;
    }
    
    /**
     * Controller lié au bouton de modification des textes sur la page TexteList
     * @param request Requête HTTP
     * @return Vue TexteEdit
     */
    @RequestMapping(value="TexteEdit.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTexteEdit(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            // Retreive item (null if not created)
            Texte item = texteRepository.getByTexteNom(ApplicationTools.getStringFromRequest(request, "texteNom"));
            returned = ApplicationTools.getModel("TexteEdit", user);
            if (item!=null){
                // Envoi de l'item pour modification
                returned.addObject("item", item);
            }
        }
        return returned;
    }
    
    /**
     * Controller lié à la validation sur la page TexteEdit
     * @param request Requête HTTP
     * @return Vue TexteList
     */
    @RequestMapping(value="TexteSave.do", method=RequestMethod.POST)
    public ModelAndView handlePOSTTexteSave(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            // Get item to save request
            // Retreive item (null if not created)
            Texte item = texteRepository.getByTexteNom(ApplicationTools.getStringFromRequest(request, "texteNom"));
            Texte dataToSave = new Texte();
            // Retreive values from request
            dataToSave.setTexteNom(item.getTexteNom());
            dataToSave.setTexteContenu(ApplicationTools.getStringFromRequest(request,"texteContenu"));
            texteRepository.update(item.getTexteNom(), dataToSave);
            // Return to the list
            returned = ApplicationTools.getModel("TexteList", user);
            Collection<Texte> maListe=texteRepository.findAll(Sort.by(Sort.Direction.ASC, "texteNom"));
            returned.addObject("itemList", maListe);
        }
        return returned;
    }
}
