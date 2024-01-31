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

import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;
import fr.centrale.nantes.ecnlogement.items.Dates;

import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;
import fr.centrale.nantes.ecnlogement.repositories.DatesRepository;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

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

    @RequestMapping(value = "connectAdmin.do", method = RequestMethod.GET)
    public ModelAndView handleGETConnectAdmin(HttpServletRequest request) {
        ModelAndView returned = ApplicationTools.getModel("loginAdmin", null);
        return returned;
    }

    @RequestMapping(value = "connect.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTConnect(HttpServletRequest request) throws ParseException {
        ModelAndView returned = null;
        String nom = ApplicationTools.getStringFromRequest(request, "nom");
        nom=ApplicationTools.correctString(nom);
        String prenom = ApplicationTools.getStringFromRequest(request, "prenom");
        prenom=ApplicationTools.correctString(prenom);
        int numscei = ApplicationTools.getIntFromRequest(request, "numscei");
        Connexion user = null;
        if ((nom != null) && (prenom != null) && (numscei != -1)
                && (!nom.isEmpty()) && (!prenom.isEmpty())) {
            Eleve eleve = eleveRepository.getByPersonNomPrenomNumscei(nom, prenom, numscei);
            if (eleve == null) {
                Personne pers=personneRepository.create(nom,prenom,roleRepository.getByRoleId(Role.ROLEELEVE)); 
                eleve=eleveRepository.create(numscei,pers);
                user = connexionRepository.create(eleve.getPersonne());
                returned=choixVueConnexion(user);
            }else{
                returned = ApplicationTools.getModel("loginRe", null);
            }
        }else{
            returned=ApplicationTools.getModel("loginError", null);
        }
        return returned;
    }
    
    public ModelAndView choixVueConnexion(Connexion user) throws ParseException{
        ModelAndView returned = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Formatage de la date actuelle en chaîne de caractères avec le format spécifié
        String formattedDate = dateFormat.format(now);
        int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
        Dates adminDates = datesRepository.getByAnnee(2024);
        if (adminDates!=null){
            if (now.before(adminDates.getDatesDebut())){
                returned = ApplicationTools.getModel("preouverture",null);
            } if (now.after(adminDates.getDatesDebut()) && now.before(adminDates.getDatesFin())){
                returned = ApplicationTools.getModel("password", user);
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
                // Try to authenticate
                LDAPManager ldapManager = new LDAPManager();
                if ((user == null) && (ldapManager.isAvailable()) && (ldapManager.authenticate(login, pass))) {
                    // User is LDAP authenticated
                    user = connexionRepository.create(person);
                }
                String savedPassword = person.getPersonnePassword();
                if ((user == null) && (savedPassword != null) && (!savedPassword.isEmpty()) && (ApplicationTools.checkPassword(pass, savedPassword))) {
                    // User is Database authenticated
                    user = connexionRepository.create(person);
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
                choixVueReconnexion(user);
            }
        }else{
            returned=ApplicationTools.getModel("loginError", null);
        }
        return returned;
    }
    
    public ModelAndView choixVueReconnexion(Connexion user) throws ParseException{
        ModelAndView returned = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Formatage de la date actuelle en chaîne de caractères avec le format spécifié
        String formattedDate = dateFormat.format(now);
        int annee=ApplicationTools.getIntFromString(formattedDate.substring(0,4));
        Dates adminDates = datesRepository.getByAnnee(2024);
        if (now.before(adminDates.getDatesDebut())){
            returned = ApplicationTools.getModel("preouverture",null);
        } if (now.after(adminDates.getDatesDebut()) && now.before(adminDates.getDatesFin())){
            returned = ApplicationTools.getModel("questionnaire", user);
        } if (now.after(adminDates.getDatesFin()) && now.before(adminDates.getDatesResultats())){
            returned = ApplicationTools.getModel("attente", null);
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
