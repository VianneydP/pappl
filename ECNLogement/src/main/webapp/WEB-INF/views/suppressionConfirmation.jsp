<%-- 
    Document   : suppressionConfirmation
    Created on : 2 févr. 2024, 14:05:40
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
    <link rel="stylesheet" href="css/main.css" type="text/css">
    <script src="js/main.js"></script>
    <title>ECNLogement</title>
    <style>
        .btn-delete {
            background-color: red;
            color: black;
            border: 2px solid black;
            border-radius: 50%;
            width: 150px; /* Ajustez la largeur et la hauteur selon vos besoins */
            height: 150px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            display: block; /* Pour centrer le bouton */
            margin: 0 auto; /* Pour centrer le bouton */
            text-align: center; /* Pour centrer le texte à l'intérieur du bouton */
            line-height: 150px; /* Pour centrer le texte à l'intérieur du bouton */
            outline: none; /* Pour supprimer la bordure de focus */
          }
    </style>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h2 class=""><fmt:message key="message.suppressionBDDTitre" bundle="${ressourcesBundle}"/></h2>
          <br>
          <form id="c_form-h" class="" action="suppressionConfirmee.do" method="POST">
              <div class="form-group row"> <label for="inputconfh" class="col-2 col-form-label"><fmt:message key="message.suppressionConfMessage" bundle="${ressourcesBundle}"/></label>
              <div class="col-10">
                <input type="text" class="form-control" id="inputconfh" name="conf" required="required"></div>
              </div>
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

