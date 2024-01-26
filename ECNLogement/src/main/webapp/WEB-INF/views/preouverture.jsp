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
        <script src="js/main.js"></script>
        <title>ECNLogement</title>
    </head>
    <body>
        <%@ include file="header.jspf" %>
        <h1><fmt:message key="message.titrePreouverture" bundle="${ressourcesBundle}"/></h1>
        <h2 class=""><fmt:message key="message.messagePreouverture" bundle="${ressourcesBundle}"/></h2>
        <%@ include file="footer.jspf" %>
    </body>
</html>
