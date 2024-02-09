<%-- 
    Document   : passeword
    Created on : 26 janv. 2024, 11:08:58
    Author     : Céline
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="fr.centrale.nantes.ecnlogement.resources.messages_fr" var="ressourcesBundle" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap -->
        <style>
    .username-container {
        font-size: 20px; /* Taille de la police */
        border: 1px solid #000; /* Bordure de 1 pixel solide en noir */
        padding: 5px; /* Marge intérieure de 5 pixels */
        display: inline-block; /* Pour que la bordure entoure le texte sans occuper toute la largeur */
    }
        </style>
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Local -->
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/theme.css">
        <script src="js/main.js"></script>
        <title>ECNLogement</title>
        </head>
    <body>
        <%@ include file="header.jspf" %>
        <div class="py-5">
            <div class="container">
                <h1>Créez un compte</h1>
                <br>
                <h5> Voici votre identifiant. Notez-le bien, à partir de maintenant il sera nécessaire pour vous connecter.</h5>  
                <br>
                <div class="row">
                   <span class="username-container">${username}</span>
                </div>
                <br>
                <h5> Définissez votre mot de passe. Notez-le bien, à partir de maintenant, il sera nécessaire pour vous connecter.</h5>  
                <br>
                <form id="c_form-h" class="" action="PersonneSave.do" method="POST">
                            <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                            <input type="hidden" name="eleveId" value="${eleve.eleveId}" />
                            <input type="hidden" name="personneId" value="${personne.personneId}" />
                            <input type="hidden" name="personneNom" value="${personne.personneNom}" />
                            <input type="hidden" name="personnePrenom" value="${personne.personnePrenom}" />
                            <input type="hidden" name="personneRole" value="${personne.roleId}" />
                            <input type="hidden" name="eleve" value="${eleve}" />
                            <input type="hidden" name="personneLogin" value="${username}"
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group row"> <label for="inputpasswordh" class="col-2 col-form-label"><fmt:message key="message.messagepassword" bundle="${ressourcesBundle}"/></label>
                            <div class="col-10">
                            <input type="password" class="form-control" id="inputpasswordh" placeholder="<fmt:message key="message.password" bundle="${ressourcesBundle}"/>" name="personnePassword" required="required">
                            </div>
                        </div>
                        <div class="form-group row"> <label for="inputpassword2h" class="col-2 col-form-label"><fmt:message key="message.messagepassword2" bundle="${ressourcesBundle}"/></label>
                            <div class="col-10">
                            <input type="password" class="form-control" id="inputpassword2h" placeholder="<fmt:message key="message.password" bundle="${ressourcesBundle}"/>" name="2" required="required" oninput="this.setCustomValidity(this.value != personnePassword.value ? 'Les champs doivent avoir la même valeur.' : '')">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary"><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
                        </form>
                    </div>
                </div>
            </div>                
        <%@ include file="footer.jspf" %>
    </body>
</html>
