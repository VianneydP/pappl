<%-- 
    Document   : gestionAdmin
    Created on : 31 janv. 2024, 14:00:17
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
      <div class="row mb-4">
        <div class="col-md-6 d-flex justify-content-center">
            <form id="c_form-h" class="" action="afficheDates.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                <button class="nav-link btn btn-ecnYellow text-black mx-2" type="submit">
                    <fmt:message key="button.datesml" bundle="${ressourcesBundle}"/></button>
            </form>
        </div>
        <div class="col-md-6 d-flex justify-content-center">
            <form id="c_form-h" class="" action="LogementList.do" value="RedirectLogementList" method="POST">
                <input type="hidden" name="nomEtape" value="redirect" />
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                    <button class="nav-link btn btn-ecnYellow text-black mx-2" type="submit">
                        <fmt:message key="button.logementList" bundle="${ressourcesBundle}"/></button>
            </form>
        </div>
      </div>
      <div class="row mb-4">
        <div class="col-md-6 d-flex justify-content-center">
            <form id="c_form-h" class="" action="gestionAssistants.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                    <button class="nav-link btn btn-ecnYellow text-black mx-2" type="submit">
                        <fmt:message key="button.gestionAssistants" bundle="${ressourcesBundle}"/>
                    </button>
            </form>
        </div>
        <div class="col-md-6 d-flex justify-content-center">
            <form id="c_form-h" class="" action="suppressionBDD.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                    <button class="nav-link btn btn-ecnYellow text-black mx-2" type="submit">
                        <fmt:message key="button.suppressionBDD" bundle="${ressourcesBundle}"/></button>
            </form>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="footer.jspf" %>
  </body>
</html>

