/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
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
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;

import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;

import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import java.util.Date;

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
    public ModelAndView handlePOSTConnect(HttpServletRequest request) {
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
                returned = ApplicationTools.getModel("ouidef", user);
            }else{
                returned = ApplicationTools.getModel("loginRe", null);
            }
        }else{
            returned=ApplicationTools.getModel("loginError", null);
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
