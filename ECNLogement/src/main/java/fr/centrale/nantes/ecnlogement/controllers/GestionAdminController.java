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
import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Dates;
import fr.centrale.nantes.ecnlogement.items.Eleve;

import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Role;

import fr.centrale.nantes.ecnlogement.ldap.LDAPManager;
import fr.centrale.nantes.ecnlogement.repositories.DatesRepository;
import fr.centrale.nantes.ecnlogement.repositories.EleveRepository;
import fr.centrale.nantes.ecnlogement.repositories.LogementRepository;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    @RequestMapping(value = "afficheDates.do", method = RequestMethod.POST)
    public ModelAndView handlePOSTGestionAdmin(HttpServletRequest request) {
        ModelAndView returned = null;
        Connexion user = ApplicationTools.checkAccess(connexionRepository, request);
        if (user == null) {
            returned = ApplicationTools.getModel( "loginAdmin", null );
        } else {
            returned=ApplicationTools.getModel("datesMissionLogement", user);
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
                logementRepository.deleteAll();
                personneRepository.deleteAll();
                eleveRepository.deleteAll();
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
}
