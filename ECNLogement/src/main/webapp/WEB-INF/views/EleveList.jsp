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
    <!-- Datatable -->
    <link rel="stylesheet" type="text/css" href="dataTables/css/jquery.dataTables.css"/>
    <link rel="stylesheet" type="text/css" href="dataTables/css/buttons.dataTables.css">
    <link rel="stylesheet" type="text/css" href="dataTables/css/responsive.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="dataTables/css/rowReorder.dataTables.min.css">
    <script type="text/javascript" src="dataTables/js/jquery.dataTables.js" ></script>
    <script type="text/javascript" src="dataTables/js/dataTables.buttons.js"></script>
    <script type="text/javascript" src="dataTables/js/buttons.html5.js"></script>
    <script type="text/javascript" src="dataTables/js/buttons.print.js"></script>
    <script type="text/javascript" src="dataTables/js/dataTables.select.js"></script>
    <script type="text/javascript" src="dataTables/js/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="dataTables/js/dataTables.rowReorder.min.js"></script>
    <title>ECNLogement</title>
  </head>

  <body>
    <%@ include file="headerAdmin.jspf" %>
    <div class="py-5">
      <div class="container">
        <div class="row">
          <hr/>
          <div class="col-md-12"><h3><fmt:message key="message.EleveList" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="table-responsive">
                <table id="EleveList" class="table table-striped table-md sortable" style="">
                <thead>
                  <tr>
                    <th scope="col" class="col-md-1"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                    <%--<th scope="col" class="col-md-1"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>--%>
                    <th scope="col" class="col-md-1"><fmt:message key="message.personneLogin" bundle="${ressourcesBundle}"/></th>
                    <%--<th scope="col" class="col-md-1"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>--%>
                    <%--<th scope="col" class="col-md-1"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>--%>
                    <%--<th scope="col" class="col-md-1"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>--%>
                    <%--<th scope="col" class="col-md-1"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>--%>
                    <th scope="col" class="col-md-1"><fmt:message key="message.eleveBoursier" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-1"><fmt:message key="message.elevePMR" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-2"><fmt:message key="message.eleveInfosup" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-3"><fmt:message key="message.eleveInfosupVE" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-1"><fmt:message key="message.eleveConfirm" bundle="${ressourcesBundle}"/></th>
                    <th class="col-md-2"></th>
                  </tr>
                </thead>
                <tbody class="bodyTable">
                <c:forEach var="item" items="${itemList}" varStatus="count">
                    <tr>
                    <td class="text-left">${item.personne.personneNom}</td>
                    <%--<td class="text-left">${item.personne.personnePrenom}</td>--%>
                    <td class="text-left">${item.personne.personneLogin}</td>
                    <%--<td class="text-left">${item.genre}</td>--%>
                    <%--<td class="text-left"><fmt:formatDate value="${item.eleveDateNaissance}" pattern="dd/MM/yyyy" /></td>--%>
                    <%--<td class="text-left">${item.eleveMail}</td>--%>
                    <%--<td class="text-left">${item.eleveNumtel}</td>--%>
                    <td class="text-center">
                        <c:if test="${item.eleveBoursier}">
                            <div style="color: red">OUI</div>
                        </c:if><c:if test="${!item.eleveBoursier}">
                            <div>non</div>
                        </c:if>
                    </td>
                    <td class="text-center">
                        <c:if test="${item.elevePMR}">
                            <div style="color: red">OUI</div>
                        </c:if><c:if test="${!item.elevePMR}">
                            <div>non</div>
                        </c:if>
                    </td>
                    <td class="text-left">${item.eleveInfosup}</td>
                    <td class="text-left">${item.eleveInfosupVe}</td>
                    <td class="text-center">
                        <c:if test="${item.eleveConfirm}">
                            <div style="font-weight: bold; color: green">OUI</div>
                        </c:if><c:if test="${!item.eleveConfirm}">
                            <div style="font-weight: bold; color: red">NON</div>
                        </c:if>
                    </td>
                    <td class="text-center">
                      <form action="#" method="POST" calss="d-inline">
                        <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                        <input type="hidden" name="eleveId" value="${item.eleveId}">
                        <div class="btn-group" role="group" aria-label="Actions">
                            <button type="submit" formaction="EleveShow.do" class="btn btn-xs"><img src="img/show.png" alt="show" class="localButton" /></button>
                            <button type="submit" formaction="EleveEdit.do" class="btn btn-xs"><img src="img/edit.png" alt="edit" class="localButton" /></button>
                            <button type="submit" formaction="EleveRemove.do" class="btn btn-xs"><img src="img/delete.png" alt="delete" class="localButton" onclick="return confirm('Voulez-vous vraiment supprimer ? L\'opération est irréversible');" /></button>
                        </div>
                      </form>
                    </td>
                   </tr>
                </c:forEach>
                </tbody>
                <%--<tfoot>
                  <tr>
                    <th></th>
                    <th id="spersonneNom"><fmt:message key="message.personneNom" bundle="${ressourcesBundle}"/></th>
                    <th id="spersonnePrenom"><fmt:message key="message.personnePrenom" bundle="${ressourcesBundle}"/></th>
                    <th id="sgenre"><fmt:message key="message.genre" bundle="${ressourcesBundle}"/></th>
                    <th id="selevePayshab"><fmt:message key="message.eleveDateNaissance" bundle="${ressourcesBundle}"/></th>
                    <th id="seleveVillehab"><fmt:message key="message.eleveMail" bundle="${ressourcesBundle}"/></th>
                    <%--<th id="seleveCodepostal"><fmt:message key="message.eleveNumtel" bundle="${ressourcesBundle}"/></th>
                    <th id="seleveDateNaissance"><fmt:message key="message.eleveBoursier" bundle="${ressourcesBundle}"/></th>
                    <%--<th id="sroleId"><fmt:message key="message.elevePMR" bundle="${ressourcesBundle}"/></th>
                    <th id="sroleId"><fmt:message key="message.eleveInfosup" bundle="${ressourcesBundle}"/></th>
                    <th id="sroleId"><fmt:message key="message.eleveInfosupVE" bundle="${ressourcesBundle}"/></th>
                    <th id="sroleId"><fmt:message key="message.eleveConfirm" bundle="${ressourcesBundle}"/></th>
                  </tr>
                </tfoot>--%>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="footer.jspf" %>
    </body>
    <script>
        var tableName = "EleveList";
        $(document).ready(function () {
            // Setup - add a text input to each footer cell
            $('#' + tableName + ' tfoot th').each(function () {
                var theId = $(this).attr("id");
                if ((theId !== null) && (typeof theId !== 'undefined') && (theId !== "")) {
                    var title = $(this).text();
                    $(this).html('<input type="text" name="search_' + theId + '" placeholder="' + title + '" value="" />');
                }
            });

            // addDataTableButtonCopy();
            // addDataTableButtonCsv();
            // addDataTableButtonPrint();
            // addDataTableButtonExcel();
            // addDataTableButtonSelectAll();
            // addDataTableButtonDeselectAll();
            // addDataTableButtonImport("Eleve");
            // addDataTableButtonOther("Eleve", "buttonName", "actionName");
            addDataTableButtonNew("Eleve");
            var table = buildTable(tableName);

            // Apply the search
            table.columns().every(function () {
                var that = this;
                $('input', this.footer()).on('keyup change', function () {
                    if (that.search() !== this.value) {
                        that.search(this.value).draw();
                    }
                });
            });
        });
    </script>
 

</html>
