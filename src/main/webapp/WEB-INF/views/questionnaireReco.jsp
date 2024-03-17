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
  <a href="questionnaireReco.jsp"></a>
    <script type="text/javascript" src="js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Local -->
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/theme.css"/>
    <script src="js/main.js"></script>
    <title>ECNLogement</title>
  </head>

  <body>
    <%@ include file="header.jspf" %>
    <div class="py-5">
      <div class="container col-md-10">
        <h3><fmt:message key="message.formulaire" bundle="${ressourcesBundle}"/></h3>
        <br>
        <h5>${texte.texteContenu}</h5>
        <div class="row">
          <div class="col-md-12">
            <form method="POST">
              <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
              <input type="hidden" name="eleveId" value="${eleve.eleveId}" />
              <input type="hidden" name="personneId" value="${personne.personneId}" />
              <div class="table-responsive">
                <table class="table table-striped table-sm ">
                    <colgroup>
                        <col style="width: 25%">
                        <col style="width: 75%">
                    </colgroup>
                  <tbody>
                    <tr>
                      <th scope="col"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.personne.personneNom}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.personne.personnePrenom}</td>
                    </tr> 
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>
                      <td><fmt:formatDate value="${eleve.eleveDateNaissance}" pattern="dd/MM/yyyy" /></td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.genre}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.elevePayshab" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.elevePayshab}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveCodepostal" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.eleveCodepostal}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveVillehab" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.eleveVillehab}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.eleveMail}</td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>
                      <td>${eleve.eleveNumtel}</td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.questionnaireBoursier" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <div class="col-10">
                                <div class="d-block my-3">
                                    <div class="custom-control custom-radio">
                                        <input id="inputboursiernon" name="eleveBoursier" type="radio" class="custom-control-input"  value="false"> <label class="custom-control-label" for="inputboursiernon">Non</label>
                                    </div>
                                    <div class="custom-control custom-radio">
                                        <input id="inputboursieroui" name="eleveBoursier" type="radio" class="custom-control-input" value="true"> <label class="custom-control-label" for="inputboursieroui">Oui</label>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.questionnaireNotif" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <div id="scholarshipCertificate" style="display: none;">
                                <input type="file" id="fileInput" name="eleveFile" accept=".pdf">
                                <label for="fileInput"><fmt:message key="message.questionnaireNotif2" bundle="${ressourcesBundle}"/></label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="message.typeSouhait" bundle="${ressourcesBundle}"/></th>
                        <td>
                            <div class="d-block">
                                <div class="custom-control custom-radio">
                                    <input id="choix1" name="typeSouhait" type="radio" class="custom-control-input" value="Seul">
                                    <label class="custom-control-label" for="choix1"><fmt:message key="message.questionnaireChoix1" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix2" name="typeSouhait" type="radio" class="custom-control-input" value="SeulOuColoc">
                                    <label class="custom-control-label" for="choix2"><fmt:message key="message.questionnaireChoix2" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix3" name="typeSouhait" type="radio" class="custom-control-input" value="Coloc">
                                    <label class="custom-control-label" for="choix3"><fmt:message key="message.questionnaireChoix3" bundle="${ressourcesBundle}"/></label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input id="choix4" name="typeSouhait" type="radio" class="custom-control-input" value="Indifferent">
                                    <label class="custom-control-label" for="choix4"><fmt:message key="message.questionnaireChoix4" bundle="${ressourcesBundle}"/></label>
                                </div>
                            </div>
                            <br>
                            <div class=""><fmt:message key="message.questionnaireColoc" bundle="${ressourcesBundle}"/></div>       
                        </td>
                    </tr>
                    <tr>
                      <th scope="col"><fmt:message key="message.questionnaireInfosup" bundle="${ressourcesBundle}"/></th>
                      <td>
                          <div class="col-10">
                            <input type="text" class="form-control" id="inputinfosuph" value="${eleve.eleveInfosup}" name="eleveInfosup"></div>
                      </td>
                    </tr>
                    
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button type="submit" formaction="EleveResave.do" class="btn btn-primary"><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
                      </td>
                    </tr>
                  </tfoot>
                </table>
              </div>
            </form>
            <div class="row">
                <div class="col-md-12">
                    <h5 class="">${texteContact.texteContenu}</h5>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="footer.jspf" %>
    <script>
        document.getElementById('inputboursieroui').addEventListener('change', function () {
            var scholarshipCertificate = document.getElementById('scholarshipCertificate');
            if (this.checked) {
            //if (this.value==='oui') {
                scholarshipCertificate.style.display = 'block';
                document.getElementById('fileInput').setAttribute('required', 'required');
            } else {
                scholarshipCertificate.style.display = 'none';
                document.getElementById('fileInput').removeAttribute('required');
            }
        });
        
        document.getElementById('inputboursiernon').addEventListener('change', function () {
            var scholarshipCertificate = document.getElementById('scholarshipCertificate');
            if (this.checked) {
                scholarshipCertificate.style.display = 'none';
                document.getElementById('fileInput').removeAttribute('required');
            }
        });
    </script>
  </body>
</html>
