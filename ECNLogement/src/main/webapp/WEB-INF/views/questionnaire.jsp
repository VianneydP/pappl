<%-- 
    Document   : questionnaire
    Created on : 15 déc. 2023, 15:35:04
    Author     : viann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="fr.centrale.nantes.ecnlogement.resources.messages_fr" var="ressourcesBundle" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap -->
        
        <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* 100% de la hauteur de la vue */
        }

        .container {
            flex: 1; /* Occupe l'espace disponible restant */
        }

        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
         h1 {
            text-align: center;
        }
    </style>
        
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Local -->
        <link rel="stylesheet" href="css/main.css">
        <script src="js/main.js"></script>
        <title>ECNLogement</title>
    </head>
    <body>
        <%@ include file="header.jspf" %>
        <h1>Questionnaire</h1>
        <br>
        <h5>Veuillez-remplir les informations ci-dessous pour vous affecter dans un logement. (Ceci est destiné aux élèves en "Oui définitif" sur SCEI, les autres ne seront pas pris en compte).</h5>
        <br>
        <div class="row">
            <div class="col-md-12">
                <form id="c_form-h" class="" action="EleveSave.do" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                    <input type="hidden" name="eleveId" value="${eleve.eleveId}" />
                    <input type="hidden" name="personneId" value="${personne.personneId}" />
                    <div class="form-group row">
                        <label for="inputnaissanceh" class="col-2 col-form-label"><fmt:message key="message.naissance" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="date" class="form-control" id="inputnaissanceh" placeholder="<fmt:message key="message.naissance" bundle="${ressourcesBundle}"/>" name="eleveDateNaissance" required="required">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputmailh" class="col-2 col-form-label"><fmt:message key="message.questionnaireMail" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="email" class="form-control" id="inputmailh" placeholder="<fmt:message key="message.questionnaireMail" bundle="${ressourcesBundle}"/>" name="eleveMail" required="required">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputnumtelh" class="col-2 col-form-label"><fmt:message key="message.questionnaireTelephone" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="text" class="form-control" id="inputnumtelh" pattern="[+0-9]{10,13}" placeholder="<fmt:message key="message.questionnaireTelephone" bundle="${ressourcesBundle}"/>" name="eleveNumtel" required="required">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputpaysh" class="col-2 col-form-label"><fmt:message key="message.questionnairePays" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="text" class="form-control" id="inputpaysh" pattern="[a-zA-ZÀ-ÖØ-öø-ÿ-]+$" placeholder="<fmt:message key="message.questionnairePays" bundle="${ressourcesBundle}"/>" name="elevePayshab" required="required">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputvilleh" class="col-2 col-form-label"><fmt:message key="message.questionnaireVille" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="texte" class="form-control" id="inputvilleh" pattern="[a-zA-ZÀ-ÖØ-öø-ÿ-]+$" placeholder="<fmt:message key="message.questionnaireVille" bundle="${ressourcesBundle}"/>" name="eleveVillehab" required="required">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputcodepostalh" class="col-2 col-form-label"><fmt:message key="message.questionnaireCodePostal" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="int" class="form-control" id="inputcodepostalh" placeholder="<fmt:message key="message.questionnaireCodePostal" bundle="${ressourcesBundle}"/>" name="eleveCodepostal">
                        </div>
                    </div>
                    </div>
                    <div class="form-group row"> 
                        <label for="inputboursierh" class="col-2 col-form-label"><fmt:message key="message.questionnaireBoursier" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <div class="d-block my-3">
                                <div class="custom-control custom-radio">
                                    <input id="inputboursiernon" name="eleveBoursier" type="radio" class="custom-control-input" checked="" required="" value="non"> <label class="custom-control-label" for="inputboursionnon">Non</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="inputboursieroui" name="eleveBoursier" type="radio" class="custom-control-input" checked="" required="" value="oui"> <label class="custom-control-label" for="inputboursieroui">Oui</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--<div class="form-group row">
                        <label for="inputnotif" class="col-2 col-form-label"><fmt:message key="message.questionnaireNotif" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <button class="btn btn-primary" type="button">
                            <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Upload file</button>
                        </div>
                    </div> -->
                    <div class="form-group row">
                        <label for="inputnotif" class="col-2 col-form-label"><fmt:message key="message.questionnaireNotif" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                                <label for="fileInput">Téléverser:</label>
                                <input type="file" id="fileInput" name="eleveFile" accept=".pdf">
                                <br>
                        </div>
                    </div>
                    
                    
                    <div class="form-group row"> 
                        <label for="inputgenre" class="col-2 col-form-label"><fmt:message key="message.questionnaireGenre" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <div class="d-block my-3">
                                <div class="custom-control custom-radio">
                                    <input id="masculin" name="genre" type="radio" class="custom-control-input" required="",checked="", value="M">
                                    <label class="custom-control-label" for="masculin"><fmt:message key="message.questionnaireMasculin" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="feminin" name="genre" type="radio" class="custom-control-input" required="",checked="", value="F">
                                    <label class="custom-control-label" for="feminin"><fmt:message key="message.questionnaireFeminin" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="autre" name="genre" type="radio" class="custom-control-input" required="",checked="", value="A">
                                    <label class="custom-control-label" for="autre"><fmt:message key="message.questionnaireAutre" bundle="${ressourcesBundle}"/></label>
                                </div>
                            </div>
                        </div>
                    </div>         
                                
                    <div class="form-group row"> 
                        <label for="inputtypeSouhait" class="col-2 col-form-label"><fmt:message key="message.questionnairePreference" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <div class="d-block my-3">
                                <div class="custom-control custom-radio">
                                    <input id="choix1" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Seul">
                                    <label class="custom-control-label" for="choix1"><fmt:message key="message.questionnaireChoix1" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix2" name="typeSouhait" type="radio" class="custom-control-input" required="" value="SeulOuColoc">
                                    <label class="custom-control-label" for="choix2"><fmt:message key="message.questionnaireChoix2" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix3" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Coloc">
                                    <label class="custom-control-label" for="choix3"><fmt:message key="message.questionnaireChoix3" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix4" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Indifferent">
                                    <label class="custom-control-label" for="choix4"><fmt:message key="message.questionnaireChoix4" bundle="${ressourcesBundle}"/></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputinfosuph" class="col-2 col-form-label"><fmt:message key="message.questionnaireInfosup" bundle="${ressourcesBundle}"/></label>
                        <div class="col-10">
                            <input type="text" class="form-control" id="inputinfosuph" placeholder="<fmt:message key="message.questionnaireInfosup" bundle="${ressourcesBundle}"/>" name="eleveInfosup"></div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-6">
                        <button type="submit" class="btn btn-primary"><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <h5 class="">Pour toute information ou problème, merci de contacter le [numéro permanence logement]</h5>
                        </div>
                    </div>
                </form>
            </div>
        </div> 

        <%@ include file="footer2.jspf" %>
    </body>
</html>
