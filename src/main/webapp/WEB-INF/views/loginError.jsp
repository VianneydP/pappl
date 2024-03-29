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
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h2 class=""><fmt:message key="message.messagenom" bundle="${ressourcesBundle}"/></h2>
        </div>
        <div class="col-md-30">
          <label><fmt:message key="message.loginerror" bundle="${ressourcesBundle}"/></label>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
            <form id="c_form-h" class="" action="connect.do" method="POST">
            <div class="form-group row"> <label for="inputnomh" class="col-2 col-form-label"><fmt:message key="message.nom" bundle="${ressourcesBundle}"/></label>
              <div class="col-10">
                <input type="text" class="form-control" id="inputnomh" placeholder="<fmt:message key="message.nom" bundle="${ressourcesBundle}"/>" name="nom" required="required"></div>
            </div>
            <div class="form-group row"> <label for="inputprenomh" class="col-2 col-form-label"><fmt:message key="message.prenom" bundle="${ressourcesBundle}"/></label>
              <div class="col-10">
                <input type="text" class="form-control" id="inputprenomh" placeholder="<fmt:message key="message.prenom" bundle="${ressourcesBundle}"/>" name="prenom" required="required"></div>
            </div>
            <div class="form-group row"> <label for="inputnumsceih" class="col-2 col-form-label"><fmt:message key="message.numscei" bundle="${ressourcesBundle}"/></label>
              <div class="col-10">
                <input type="integer" class="form-control" id="inputnumsceih" placeholder="<fmt:message key="message.numscei" bundle="${ressourcesBundle}"/>" name="numscei" required="required"></div>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.connect" bundle="${ressourcesBundle}"/></button>
          </form>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="footer.jspf" %>
  </body>
</html>
