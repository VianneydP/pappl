<%-- 
    Document   : datesMissionLogement
    Created on : 2 févr. 2024, 09:39:37
    Author     : viann
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
    <%@ include file="headerAdmin.jspf" %>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h2 class=""><fmt:message key="message.titreDates" bundle="${ressourcesBundle}"/></h2>
          <p class=""><i><fmt:message key="message.consignesDates" bundle="${ressourcesBundle}"/></i></p>
          <c:if test="${erreurChronologique}"><p style="color :red"><strong><fmt:message key="message.erreurChronologique" bundle="${ressourcesBundle}"/></strong></p></c:if>
          <c:if test="${empty dateAnnee}">
              <p class="">Remplissez les dates pour cette année.</p>
          </c:if>
          <c:if test="${! empty dateAnnee}">
            <p class="">Voici les dates de la mission de l'année ${dateAnnee}</p>
            <ul>
              <li>Date de début (ouverture questionnaire) : ${dateDebut}</li>
              <li>Date de fin (fermeture questionnaire) : ${dateFin}</li>
              <li>Date de la publication des résultats : ${dateResultats}</li>
            </ul>
          </c:if>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
            <form id="c_form-h" class="" action="majDates.do" method="POST">
            <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
            <div class="form-group row"> <label for="inputanneeh" class="col-4 col-form-label"><fmt:message key="message.annee" bundle="${ressourcesBundle}"/></label>
              <div class="col-2">
                <input type="number" class="form-control" id="inputanneeh" name="annee" required="required"></div>
            </div><br>
            <div class="form-group row"> <label for="inputdebuth" class="col-4 col-form-label"><fmt:message key="message.datedebut" bundle="${ressourcesBundle}"/></label>
              <div class="col-3">
                <input type="datetime-local" class="form-control" id="inputdebuth" placeholder="<fmt:message key="message.timestampformat" bundle="${ressourcesBundle}"/>" name="debut"></div>
            </div><br>
            <div class="form-group row"> <label for="inputfinh" class="col-4 col-form-label"><fmt:message key="message.datefin" bundle="${ressourcesBundle}"/></label>
              <div class="col-3">
                <input type="datetime-local" class="form-control" id="inputfinh" placeholder="<fmt:message key="message.timestampformat" bundle="${ressourcesBundle}"/>" name="fin"></div>
            </div><br>
            <div class="form-group row"> <label for="inputresultatsh" class="col-4 col-form-label"><fmt:message key="message.dateresultats" bundle="${ressourcesBundle}"/></label>
              <div class="col-3">
                <input type="datetime-local" class="form-control" id="inputresultatsh" placeholder="<fmt:message key="message.timestampformat" bundle="${ressourcesBundle}"/>" name="resultats"></div>
            </div><br>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
          </form>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="footer2.jspf" %>
  </body>
</html>

