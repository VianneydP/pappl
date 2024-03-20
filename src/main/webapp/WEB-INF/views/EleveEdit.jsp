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
                      <th scope="col"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="personneNom" value="${item.personne.personneNom}" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="personnePreom" value="${item.personne.personnePrenom}" />
                      </td>
                    </tr> 
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="date" class="form-control" name="eleveDateNaissance" value="${dateNaissance}" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="genre" value="${item.genre}">
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.elevePayshab" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="elevePayshab" value="${item.elevePayshab}">
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveCodepostal" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="int" class="form-control" name="eleveCodepostal" value="${item.eleveCodepostal}" />
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveVillehab" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="eleveVillehab" value="${item.eleveVillehab}">
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="text" class="form-control" name="eleveMail" value="${item.eleveMail}">
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <input type="int" class="form-control" name="eleveNumtel" value="${item.eleveNumtel}">
                      </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.eleveBoursier" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <label>
                              <input type="radio" name="eleveBoursier" value="true" <c:if test="${item.eleveBoursier eq 'true'}">checked</c:if>> Oui
                            </label>
                            <label>
                              <input type="radio" name="eleveBoursier" value="false" <c:if test="${item.eleveBoursier eq 'false'}">checked</c:if>> Non
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.elevePMR" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <label>
                              <input type="radio" name="elevePMR" value="true" <c:if test="${item.elevePMR eq 'true'}">checked</c:if>> Oui
                            </label>
                            <label>
                              <input type="radio" name="elevePMR" value="false" <c:if test="${item.elevePMR eq 'false'}">checked</c:if>> Non
                            </label>
                        </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveInfosup" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveInfosup">${item.eleveInfosup}</textarea>
                      </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveInfosupVE" bundle="${ressourcesBundle}"/></th>
                      <td>
                        <textarea class="form-control" name="eleveInfosupVe">${item.eleveInfosupVe}</textarea>
                      </td>
                    </tr>
                     <tr>
                        <th scope="col"><fmt:message key="message.typeSouhait" bundle="${ressourcesBundle}"/></th>
                        <td>
                          <!-- select name="typeSouhait">
                              <!-- Menu déroulant qui ne fonctionne pas  
                              <c:forEach var="itemIter" items="${typeSouhaitList}">
                              <option value="${itemIter.typeSouhait}" <c:if test="${itemIter.typeSouhait eq item.typeSouhait}">selected</c:if>>${itemIter.typeSouhait}</option>
                            </c:forEach> -->
                            <!-- Cases à cochées qui fonctionnent-->
                            <div class="col-5">
                                  <div class="d-block my-3">
                                      <div class="custom-control custom-radio">
                                          <input id="choix1" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Seul">
                                          <label class="custom-control-label" for="choix1"><fmt:message key="message.questionnaireChoix1" bundle="${ressourcesBundle}"/></label>
                                      </div>
                                      <div class="custom-control custom-radio">
                                          <input id="choix2" name="typeSouhait" type="radio" class="custom-control-input" required="" value="SeulOuColoc">
                                          <label class="custom-control-label" for="choix2"><fmt:message key="message.questionnaireChoix2" bundle="${ressourcesBundle}"/></label>
                                      </div>
                                      <div class="custom-control custom-radio">
                                          <input id="choix3" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Coloc">
                                          <label class="custom-control-label" for="choix3"><fmt:message key="message.questionnaireChoix3" bundle="${ressourcesBundle}"/></label>
                                      </div>
                                      <div class="custom-control custom-radio">
                                          <input id="choix4" name="typeSouhait" type="radio" class="custom-control-input" required="" value="Indifferent">
                                          <label class="custom-control-label" for="choix4"><fmt:message key="message.questionnaireChoix4" bundle="${ressourcesBundle}"/></label>
                                      </div>
                                  </div>
                              </div>
                          </select>
                        </td>
                      </tr>
                    <tr>
                        <th scope="col" style="color:red"><strong><fmt:message key="message.eleveConfirm" bundle="${ressourcesBundle}"/></strong></th>
                        <td>
                            <label>
                                <input type="radio" name="eleveConfirm" value="true" <c:if test="${item.eleveConfirm eq 'true'}">checked</c:if>/> Oui
                            </label>
                            <label>
                                <input type="radio" name="eleveConfirm" value="false" <c:if test="${item.eleveConfirm eq 'false'}">checked</c:if>/> Non
                            </label>
                        </td>
                    </tr>

                    
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button class="btn btn-warning" formaction="EleveList.do"><img src="img/cancel.png" alt="save" class="icon" /><fmt:message key="button.cancel" bundle="${ressourcesBundle}"/></button>
                        <button class="btn btn-success" formaction="EleveSaveAdmin.do"><img src="img/save.png" alt="save" class="icon" /><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
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
