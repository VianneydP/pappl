<%-- 
    Document   : accueil
    Created on : 22 janv. 2024, 17:08:35
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
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
        <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Local -->
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/theme.css">
        <script src="js/main.js"></script>
        <title>Accueil</title>
    </head>
    <body>
        <%@ include file="header.jspf" %>
        <div class="py-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="text-center"><fmt:message key="message.missionLogement" bundle="${ressourcesBundle}"/></h1>
                        <div class="text-center col-md-7 mx-auto"> <i class="fa fa-bullseye fa-5x mb-4" style="color: #fab600" ></i></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h4 class="text-center"><fmt:message key="message.messageAccueil" bundle="${ressourcesBundle}"/></h4>
                    </div>
                </div>
                <br>
                <div class="form-group row">
                    <form action="#" method="GET" class="d-flex justify-content-center align-items-center">
                        <button type="submit" class="btn btn-light" formaction="connect.do" formmethod="GET"><fmt:message key="button.inscription" bundle="${ressourcesBundle}"/></button>
                        <div class="mx-2"></div>
                        <button type="submit" class="btn btn-light" formaction="reconnect.do" formmethod="GET"><fmt:message key="button.connect" bundle="${ressourcesBundle}"/></button>
                    </form>
                </div>
            </div>
        </div>
        <%@ include file="footer.jspf" %>
    </body>
</html>
