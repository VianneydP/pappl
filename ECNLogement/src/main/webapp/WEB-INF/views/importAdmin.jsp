<%-- 
    Document   : importeAdmin
    Created on : 25 janv. 2024, 09:46:00
    Author     : elsaa
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
        <title>ECNLogement</title>
    </head>
    <body>
        <%@ include file="headerAdmin.jspf" %>
        <h1><fmt:message key="message.titreImport" bundle="${ressourcesBundle}"/></h1>
        <h2 class=""><fmt:message key="message.importREZ" bundle="${ressourcesBundle}"/></h2> 
        <div>
            <form action="LogementList.do" method="POST" enctype="multipart/form-data">   <!--Bouton import rez-->
            <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
            <label for="inputRez" class="col-20 col-form-label"><fmt:message key="message.sousTitreImportRez" bundle="${ressourcesBundle}"/></label>
            <div class="col-10">
                    <label for="fileInput">Téléverser:</label>
                    <input type="file" id="fileInput" name="RezImport" accept=".csv, .CSV">
                    <br>
                    <div class="row">
                        <div class="col-md-6">
                        <button type="submit" class="btn btn-primary"><fmt:message key="button.validerImport" bundle="${ressourcesBundle}"/></button>
                        </div>
                    </div>
                    <br>
            </div>
            </form>    
        </div>
        <h2 class=""><fmt:message key="message.importSCEI" bundle="${ressourcesBundle}"/></h2> 
        <div>
                    <!--Celui ci est bon<div class="form-group row"></div>-->
            <label for="inputSCEI" class="col-20 col-form-label"><fmt:message key="message.sousTitreImportSCEI" bundle="${ressourcesBundle}"/></label>
            <div class="col-10">
                    <label for="fileInput">Téléverser:</label>
                    <input type="file" id="fileInput" name="SCEIImport" accept=".csv">
                    <br>
            </div>
        </div>
                    
        <%@ include file="footer.jspf" %>
    </body>
</html>
