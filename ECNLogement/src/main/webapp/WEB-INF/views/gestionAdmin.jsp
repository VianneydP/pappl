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
    <script src="js/main.js"></script>
    <title>ECNLogement</title>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <ul class="d-flex align-items-center mx-auto">
            <form id="c_form-h" class="" action="afficheDates.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                <li class="nav-item"><button class="nav-link btn btn-light text-black mx-2" type="submit">
                    <fmt:message key="button.datesMissionLogement" bundle="${ressourcesBundle}"/></button>
                </li>
            </form>
            <form id="c_form-h" class="" action="alertes.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                <li class="nav-item">
                    <button class="nav-link btn btn-light text-black mx-2" type="submit">
                        <fmt:message key="button.alertes" bundle="${ressourcesBundle}"/></button>
                </li>
            </form>
            <form id="c_form-h" class="" action="pageImport.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                <li class="nav-item">
                    <button class="nav-link btn btn-light text-black mx-2" type="submit">
                        <fmt:message key="button.importAdmin" bundle="${ressourcesBundle}"/>
                    </button>
                </li>
            </form>
            <form id="c_form-h" class="" action="suppressionBDD.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                <li class="nav-item">
                    <button class="nav-link btn btn-light text-black mx-2" type="submit">
                        <fmt:message key="button.suppressionBDD" bundle="${ressourcesBundle}"/></button>
                </li>
            </form>
          </ul>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="footer2.jspf" %>
  </body>
</html>

