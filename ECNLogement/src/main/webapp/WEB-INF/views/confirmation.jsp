<%-- 
    Document   : confirmation
    Created on : 17 févr. 2024, 20:02:11
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
                <h1>Merci, vous avez bien rempli le formulaire.</h1>
                <br>
                </div>
            </div>                
        <%@ include file="footer.jspf" %>
    </body>
</html>

