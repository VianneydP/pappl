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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elsaa
 */

@Controller
public class AdminController {
    
    
    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private ConnexionRepository connexionRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    
    
    @RequestMapping(value = "pageImport.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTPageImport(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned=ApplicationTools.getModel("importAdmin", user);
        }
        
        
        
            
        return returned;
    }
    
    @RequestMapping(value = "visuBDD.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTPageVisuBDD(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned=ApplicationTools.getModel("visuBDD", user);
        }
        return returned;
    }
    
    @RequestMapping(value = "alertes.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTPageAlertes(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned=ApplicationTools.getModel("alertes", user);
        }
        return returned;
    }
    
    @RequestMapping(value = "affectLogement.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTPageAffectLogement(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "index", null );
        } else {
            returned=ApplicationTools.getModel("affectLogement", user);
        }
        return returned;
    }
}
