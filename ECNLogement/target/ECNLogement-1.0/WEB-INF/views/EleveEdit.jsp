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
          <div class="col-md-12"><h3><fmt:message key="message.Eleve" bundle="${ressourcesBundle}"/></h3></div>
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
                      <th scope="col"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="date" class="form-control" name="eleveDateNaissance" value="1" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="genre">
                        ${item.genre}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.elevePayshab" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="elevePayshab">
                        ${item.elevePayshab}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveVillehab" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveVillehab">
                        ${item.eleveVillehab}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveCodepostal" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="eleveCodepostal" value="${item.eleveCodepostal}" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveMail">
                        ${item.eleveMail}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveNumtel">
                        ${item.eleveNumtel}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveBoursier" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="checkbox" class="form-control" name="eleveBoursier" value="1" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveInfosup" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveInfosup">
                        ${item.eleveInfosup}
                        </textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.codeCommune" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <select name="codeCommune">
                        <c:forEach var="itemIter" items="${codeCommuneList}">
                          <c:choose>
                          <c:when test="${itemIter.codeCommune==item.codeCommune.codeCommune}"><option value="${itemIter.codeCommune}" selected>${itemIter.nomCommune}</option></c:when>
                          <c:otherwise><option value="${itemIter.codeCommune}">${itemIter.nomCommune}</option></c:otherwise>
                          </c:choose>
                        </c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.logementNumero" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <select name="logementNumero">
                        <c:forEach var="itemIter" items="${logementNumeroList}">
                          <c:choose>
                          <c:when test="${itemIter.logementNumero==item.logementNumero.logementNumero}"><option value="${itemIter.logementNumero}" selected>${itemIter.logementPlacesDispo}</option></c:when>
                          <c:otherwise><option value="${itemIter.logementNumero}">${itemIter.logementPlacesDispo}</option></c:otherwise>
                          </c:choose>
                        </c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personneId" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <select name="personneId">
                        <c:forEach var="itemIter" items="${personneIdList}">
                          <c:choose>
                          <c:when test="${itemIter.personneId==item.personneId.personneId}"><option value="${itemIter.personneId}" selected>${itemIter.personneNom}</option></c:when>
                          <c:otherwise><option value="${itemIter.personneId}">${itemIter.personneNom}</option></c:otherwise>
                          </c:choose>
                        </c:forEach>
                        </select>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.typeSouhait" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <select name="typeSouhait">
                        <c:forEach var="itemIter" items="${typeSouhaitList}">
                          <c:choose>
                          <c:when test="${itemIter.typeSouhait==item.typeSouhait.typeSouhait}"><option value="${itemIter.typeSouhait}" selected></option></c:when>
                          <c:otherwise><option value="${itemIter.typeSouhait}"></option></c:otherwise>
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
                        <button class="btn btn-warning" formaction="EleveList.do"><img src="img/cancel.png" alt="save" class="icon" /><fmt:message key="button.cancel" bundle="${ressourcesBundle}"/></button>
                        <button class="btn btn-success" formaction="EleveSave.do"><img src="img/save.png" alt="save" class="icon" /><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
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
