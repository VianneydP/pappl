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
    <script src="js/main.js"></script>
    <title>ECNLogement</title>
  </head>

  <body>
    <%@ include file="header.jspf" %>
    <div class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-md-12"><h3><fmt:message key="message.Logement" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form method="POST">
              <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
              <input type="hidden" name="logementNumero" value="${item.logementNumero}" />
              <input type="hidden" name="field" value="Logement" />
              <div class="table-responsive">
                <table class="table table-striped table-sm ">
                  <tbody>
                    <tr>
                      <th scope="col"><fmt:message key="message.logementGenreRequis" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="logementGenreRequis">
                        ${item.logementGenreRequis}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.logementPlacesDispo" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="logementPlacesDispo" value="${item.logementPlacesDispo}" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.typeAppartNom" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <select name="typeAppartNom">
                        <c:forEach var="itemIter" items="${typeAppartNomList}">
                          <c:choose>
                          <c:when test="${itemIter.typeAppartNom==item.typeAppartNom.typeAppartNom}"><option value="${itemIter.typeAppartNom}" selected></option></c:when>
                          <c:otherwise><option value="${itemIter.typeAppartNom}"></option></c:otherwise>
                          </c:choose>
                        </c:forEach>
                        </select>
                      </td>
                    </tr>
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button class="btn btn-warning" formaction="LogementList.do"><img src="img/cancel.png" alt="save" class="icon" /><fmt:message key="button.cancel" bundle="${ressourcesBundle}"/></button>
                        <button class="btn btn-success" formaction="LogementSave.do"><img src="img/save.png" alt="save" class="icon" /><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
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
  </body>
</html>
