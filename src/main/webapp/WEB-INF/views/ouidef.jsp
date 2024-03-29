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
    <%@ include file="header.jspf" %>
  <center>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h2 class=""><fmt:message key="message.titreouidef" bundle="${ressourcesBundle}"/></h2>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
            <form id="c_form-h" class="" action="ouidef.do" method="POST">
                <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
            <div class=""> 
                <input type="checkbox" id="inputouidefh" name="ouidef"/>
                <label for="inputouidefh"><fmt:message key="message.ouidef" bundle="${ressourcesBundle}"/></label>
            </div>
            <br>
            <div class="col-md-12">
                <label>Cette information sera vérifiée lors du traitement des données.</label>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.valider" bundle="${ressourcesBundle}"/></button>
          </form>
        </div>
      </div>
    </div>
  </div>
  </center>
    <%@ include file="footer.jspf" %>
  </body>
</html>
