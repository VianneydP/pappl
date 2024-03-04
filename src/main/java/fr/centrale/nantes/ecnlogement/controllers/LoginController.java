/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.controllers;

import static fr.centrale.nantes.ecnlogement.controllers.ApplicationTools.checkPassword;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.centrale.nantes.ecnlogement.repositories.ConnexionRepository;
import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import fr.centrale.nantes.ecnlogement.repositories.RoleRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Commune;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;
import fr.centrale.nantes.ecnlogement.items.Dates;

import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;
import fr.centrale.nantes.ecnlogement.repositories.DatesRepository;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.repositories.CommuneRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;
import org.springframework.data.domain.Sort;

@Controller
public class LoginController {

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private DatesRepository datesRepository;
    
    @Autowired
    private CommuneRepository communeRepository;
    
    @RequestMapping(value = "admin.do")
    public ModelAndView handleAdmin(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("loginAdmin", null);
        return returned;
    }
    
    @RequestMapping(value = "index.do")
    public ModelAndView handleIndex(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("accueil", null);
        return returned;
    }
    

    @RequestMapping(value = "connect.do", method = RequestMethod.GET)
    public ModelAndView handleGETConnect(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("login", null);
        return returned;
    }
    
     @RequestMapping(value = "reconnect.do", method = RequestMethod.GET)
    public ModelAndView handleGETReconnect(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("relogin", null);
        return returned;
    }

    @RequestMapping(value = "connectAdmin.do", method = RequestMethod.GET)
    public ModelAndView handleGETConnectAdmin(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("loginAdmin", null);
        return returned;
    }

    @RequestMapping(value = "connect.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTConnect(HttpServletRequest request) throws ParseException {
        ModelAndView returned = null;
        String nom = ApplicationTools.getStringFromRequest(request, "nom");
        nom=ApplicationTools.removeAccentsAndSpecialCharacters(nom);
        String prenom = ApplicationTools.getStringFromRequest(request, "prenom");
        prenom=ApplicationTools.removeAccentsAndSpecialCharacters(prenom);
        int numscei = ApplicationTools.getIntFromRequest(request, "numscei");
        if ((nom != null) && (prenom != null) && (numscei != -1)
                && (!nom.isEmpty()) && (!prenom.isEmpty())) {
            Eleve eleve = eleveRepository.getByPersonNomPrenomNumscei(nom, prenom, numscei);
            if (eleve != null) {
                if (eleve.getPersonne().getPersonneLogin() == null || eleve.getPersonne().getPersonneLogin().equals("")){
                    returned = choixVueConnexion(nom, prenom, numscei);                    
                }else{
                    returned = ApplicationTools.getModel("relogin", null);
                }
            }else{
                returned = ApplicationTools.getModel("login", null);
                returned.addObject("nonReco", true);
            }
        }else{
            returned=ApplicationTools.getModel("loginError", null);
        }
        return returned;
    }
    
    public ModelAndView choixVueConnexion(String nom, String prenom, int numscei) throws ParseException{
        ModelAndView returned = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Formatage de la date actuelle en chaine de caracteres avec le format specifie
        String formattedDate = dateFormat.format(now);
        int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
        Dates adminDates = datesRepository.getByAnnee(annee);
        if (adminDates!=null){
            if (now.before(adminDates.getDatesDebut())){
                returned = ApplicationTools.getModel("preouverture",null);
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr"));
                String dateDeb=dateFormat1.format(adminDates.getDatesDebut());
                returned.addObject("dateDebut", dateDeb);
            } if (now.after(adminDates.getDatesDebut()) && now.before(adminDates.getDatesFin())){
                Eleve eleve =eleveRepository.getByPersonNomPrenomNumscei(nom,prenom,numscei);
                Connexion user = connexionRepository.create(eleve.getPersonne());
                returned = ApplicationTools.getModel("password", user);
                returned.addObject("username", String.valueOf(eleve.getPersonne().getPersonneId())+String.valueOf(numscei));
                returned.addObject("eleve", eleve);
                returned.addObject("personne", eleve.getPersonne());
            } if (now.after(adminDates.getDatesFin())){
                returned = ApplicationTools.getModel("tropTard", null);
            }
        }else{
            returned = ApplicationTools.getModel("preouverture",null);
        }
        return returned;
    }
    
    @RequestMapping(value = "connectAdmin.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTConnectAdmin(HttpServletRequest request) {
        ModelAndView returned = null;
        String login = ApplicationTools.getStringFromRequest(request, "login");
        String pass = ApplicationTools.getStringFromRequest(request, "password");
        Connexion user = null;
        if ((login != null) && (pass != null) && (!login.isEmpty()) && (!pass.isEmpty())) {
            Personne person = personneRepository.getByPersonneLogin(login);
            if (person != null) {
                String savedPassword = person.getPersonnePassword();
                if ((user == null) && (savedPassword != null) && (!savedPassword.isEmpty()) && (ApplicationTools.checkPassword(pass, savedPassword))) {
                    if (person.getRoleId().getRoleId()==Role.ROLEASSIST || person.getRoleId().getRoleId()==Role.ROLEADMIN){
                        Collection<Connexion> coRep=connexionRepository.findAll();
                        boolean flag=connexionRepository.checkUnicity(person);
                        if (flag==true){
                            user = connexionRepository.create(person);
                        } else {
                            int numEssai = ApplicationTools.getIntFromRequest(request, "numEssai");
                            if (numEssai==3){
                                 connexionRepository.deleteByPerson(person);
                                user = connexionRepository.create(person);
                            }else{
                                returned = ApplicationTools.getModel("loginAdmin",null);
                                returned.addObject("loginForce", true);
                            }
                        }
                    }
                }
            }
        }
        if (user!=null){
            returned=ApplicationTools.getModel("accueilAdmin", user);
        }
        else {
            returned = ApplicationTools.getModel("loginAdmin",null);
        }
        
        return returned;
    }

    @RequestMapping(value = "disconnect.do")
    public ModelAndView handleDisconnect(HttpServletRequest request) {
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user != null) {
            connexionRepository.remove(user);
            user = null;
        }
        ModelAndView returned = ApplicationTools.getModel("accueil", user);
        return returned;
    }
    
    @RequestMapping(value = "reconnect.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTReconnect(HttpServletRequest request) throws ParseException {
        ModelAndView returned = null;
        String identifiant = ApplicationTools.getStringFromRequest(request, "identifiant");
        String mdp = ApplicationTools.getStringFromRequest(request, "password");
        Connexion user = null;
        if ((identifiant != null) && (mdp != null) && (!identifiant.isEmpty()) && (!mdp.isEmpty())) {
            Personne pers = personneRepository.getByPersonneLogin(identifiant);
            if (pers != null && checkPassword(mdp, pers.getPersonnePassword())) {
                user = connexionRepository.create(pers);
                Eleve eleve=eleveRepository.getByPersonneId(pers.getPersonneId());
                returned = choixVueReconnexion(user, eleve, pers);
            }else{
                returned=ApplicationTools.getModel("relogin", null);
                returned.addObject("mdpErrone", true);
            }
        }else{
            returned=ApplicationTools.getModel("reconnect", null);
        }
        return returned;
    }
    
    public ModelAndView choixVueReconnexion(Connexion user, Eleve eleve, Personne pers) throws ParseException{
        ModelAndView returned = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Formatage de la date actuelle en chaine de caracteres avec le format specifie
        String formattedDate = dateFormat.format(now);
        int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
        Dates adminDates = datesRepository.getByAnnee(annee);
        if (now.before(adminDates.getDatesDebut())){
            returned = ApplicationTools.getModel("preouverture",null);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr"));
                String dateDeb=dateFormat1.format(adminDates.getDatesDebut());
                returned.addObject("dateDebut", dateDeb);
        } if (now.after(adminDates.getDatesDebut()) && now.before(adminDates.getDatesFin())){
            returned = ApplicationTools.getModel("questionnaire", user);
            //Eleve eleve=eleveRepository.getByEleveId(getEleveIdByPersonneId(pers.getPersonneId()));
            Collection<Commune> maListe=communeRepository.findAll(Sort.by(Sort.Direction.ASC, "nomCommune"));
            returned.addObject("communeListe", maListe);
            returned.addObject("eleve", eleve);
            returned.addObject("personne", pers);
        } if (now.after(adminDates.getDatesFin()) && now.before(adminDates.getDatesResultats())){
            returned = ApplicationTools.getModel("attenteResultat", null);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr"));
            String dateRes=dateFormat1.format(adminDates.getDatesResultats());
            returned.addObject("dateResultats", dateRes);
        } if (now.after(adminDates.getDatesResultats())){
            returned = ApplicationTools.getModel("resultat", user);
        }        
        return returned;
    }
    @RequestMapping(value = "disconnectAdmin.do")
    public ModelAndView handleDisconnectAdmin(HttpServletRequest request) {
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user != null) {
            connexionRepository.remove(user);
            user = null;
        }
        ModelAndView returned = ApplicationTools.getModel("loginAdmin", user);
        return returned;
    }
}