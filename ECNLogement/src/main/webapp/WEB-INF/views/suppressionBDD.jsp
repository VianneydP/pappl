<%-- 
    Document   : suppressionBDD
    Created on : 2 févr. 2024, 12:27:42
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
    <title>SuppressionBDD</title>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h2 class=""><fmt:message key="message.suppressionBDDTitre" bundle="${ressourcesBundle}"/></h2>
          <p class=""><i><fmt:message key="message.suppressionConsignes" bundle="${ressourcesBundle}"/></i></p>
          <c:if test="${! empty suppressionFaite}">
            <c:if test="${suppressionFaite}">
                <p style="color :forestgreen"><strong><fmt:message key="message.suppressionFaite" bundle="${ressourcesBundle}"/></strong></p>
            </c:if>
            <c:if test="${!suppressionFaite}">
                <p style="color :red"><strong><fmt:message key="message.suppressionNonFaite" bundle="${ressourcesBundle}"/></strong></p>
            </c:if>
          </c:if>
          <p class=""><fmt:message key="message.suppressionListe" bundle="${ressourcesBundle}"/></p>
          <ul>
              <li><fmt:message key="message.suppressionPersonnes" bundle="${ressourcesBundle}"/></li>
              <li><fmt:message key="message.suppressionAffectations" bundle="${ressourcesBundle}"/></li>
              <li><fmt:message key="message.suppressionFichiers" bundle="${ressourcesBundle}"/></li>
              <li><fmt:message key="message.suppressionConservationDates" bundle="${ressourcesBundle}"/></li>
          </ul><br>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <form id="c_form-h" class="" action="suppressionConfirmation.do" method="POST">
             <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
             <button type="submit" class="btn-delete"><fmt:message key="button.suppressionDonnees" bundle="${ressourcesBundle}"/></button>
          </form>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="footer.jspf" %>
  </body>
</html>

