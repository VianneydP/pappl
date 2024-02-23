<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="fr.centrale.nantes.ecnlogement.resources.messages" var="ressourcesBundle" />
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
    <link rel="stylesheet" href="css/theme.css"/>
    <script src="js/main.js"></script>
    <title>ECNLogement</title>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
    <div class="py-5">
      <div class="container col-md-9">
        <div class="row">
          <div class="col-md-12"><h3><fmt:message key="message.EleveInfo" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form method="POST">
              <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
              <input type="hidden" name="eleveId" value="${item.eleveId}" />
              <input type="hidden" name="field" value="Eleve" />
              <div class="table-responsive">
                <table class="table table-striped table-sm ">
                  <tbody>
                    <tr>
                      <th scope="col"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                      <td>${item.personne.personneNom}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>
                      <td>${item.personne.personnePrenom}</td>
                    </tr> 
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>
                      <td><fmt:formatDate value="${item.eleveDateNaissance}" pattern="dd/MM/yyyy" /></td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>
                      <td>${item.genre}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.elevePayshab" bundle="${ressourcesBundle}"/></th>
                      <td>${item.elevePayshab}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveCodepostal" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveCodepostal}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveVillehab" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveVillehab}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveMail}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveNumtel}</td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.eleveBoursier" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <c:if test="${item.eleveBoursier eq 'true'}">OUI</c:if>
                            <c:if test="${item.eleveBoursier eq 'false'}">NON</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.elevePMR" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <c:if test="${item.elevePMR eq 'true'}">OUI</c:if>
                            <c:if test="${item.elevePMR eq 'false'}">NON</c:if>
                        </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveInfosup" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveInfosup}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveInfosupVE" bundle="${ressourcesBundle}"/></th>
                      <td>${item.eleveInfosupVe}</td>
                    </tr>
                     <tr>
                        <th scope="col"><fmt:message key="message.typeSouhait" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <c:if test="${item.typeSouhait.typeSouhait=='Seul'}"><fmt:message key="message.questionnaireChoix1" bundle="${ressourcesBundle}"/></c:if>
                            <c:if test="${item.typeSouhait.typeSouhait=='SeulOuColoc'}"><fmt:message key="message.questionnaireChoix2" bundle="${ressourcesBundle}"/></c:if>
                            <c:if test="${item.typeSouhait.typeSouhait=='Coloc'}"><fmt:message key="message.questionnaireChoix3" bundle="${ressourcesBundle}"/></c:if>
                            <c:if test="${item.typeSouhait.typeSouhait=='Indifferent'}"><fmt:message key="message.questionnaireChoix4" bundle="${ressourcesBundle}"/></c:if>
                        </td>
                      </tr>
                    <tr>
                        <th scope="col" style="color:red"><strong><fmt:message key="message.eleveConfirm" bundle="${ressourcesBundle}"/></strong></th>
                        <td>
                            <c:if test="${item.eleveConfirm eq 'true'}">OUI</c:if>
                            <c:if test="${item.eleveConfirm eq 'false'}">NON</c:if>
                        </td>
                    </tr>

                    
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button type="submit" formaction="EleveEdit.do" class="btn btn-xs"><img src="img/edit.png" alt="edit" class="localButton" /></button>
                      </td>
                    </tr>
                  </tfoot>
                </table>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="footer.jspf" %>
  </body>
</html>
