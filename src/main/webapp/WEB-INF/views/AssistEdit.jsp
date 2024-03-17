<%-- 
    Document   : AssistEdit
    Created on : 8 févr. 2024, 09:30:54
    Author     : viann
--%>

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
    <<link rel="stylesheet" href="css/theme.css"/>
    <script src="js/main.js"></script>
    <title>Edition de l'assistant</title>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
    <div class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-md-12"><h3><fmt:message key="message.Personne" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form method="POST">
              <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
              <input type="hidden" name="personneId" value="${item.personneId}" />
              <input type="hidden" name="field" value="Personne" />
              <div class="table-responsive">
                <table class="table table-striped table-sm ">
                  <tbody>
                    <tr>
                      <th scope="col"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <c:choose>
                          <c:when test="${! empty item}">
                             <input type="text" class="form-control" name="personneNom" placeholder="${item.personneNom}" />
                          </c:when>
                          <c:otherwise>
                             <input type="text" class="form-control" name="personneNom" placeholder=<fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/> />
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <c:choose>
                          <c:when test="${! empty item}">
                             <input type="text" class="form-control" name="personnePrenom" placeholder="${item.personnePrenom}" />
                          </c:when>
                          <c:otherwise>
                             <input type="text" class="form-control" name="personnePrenom" placeholder=<fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/> />
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personneLogin" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <c:choose>
                          <c:when test="${! empty item}">
                             <input type="text" class="form-control" name="personneLogin" placeholder="${item.personneLogin}" />
                          </c:when>
                          <c:otherwise>
                             <input type="text" class="form-control" name="personneLogin" placeholder=<fmt:message key="message.personneLogin" bundle="${ressourcesBundle}"/> />
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personnePassword" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="personnePassword" placeholder="<fmt:message key="message.votrePassword" bundle="${ressourcesBundle}"/>" style="font-style: italic;"/>
                      </td>
                    </tr>
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button class="btn btn-warning" formaction="gestionAssistants.do"><img src="img/cancel.png" alt="save" class="icon" /><fmt:message key="button.cancel" bundle="${ressourcesBundle}"/></button>
                        <button class="btn btn-success" formaction="AssistSave.do"><img src="img/save.png" alt="save" class="icon" /><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
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

