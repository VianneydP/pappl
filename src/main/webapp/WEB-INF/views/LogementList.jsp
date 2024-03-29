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
    <link rel="stylesheet" href="css/theme.css">
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
          <div class="col-md-12"><h3><fmt:message key="message.LogementList" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="table-responsive">
               <div id="fountainG">
                    <div id="fountainG_1" class="fountainG"></div>
                    <div id="fountainG_2" class="fountainG"></div>
                    <div id="fountainG_3" class="fountainG"></div>
                    <div id="fountainG_4" class="fountainG"></div>
                    <div id="fountainG_5" class="fountainG"></div>
                    <div id="fountainG_6" class="fountainG"></div>
                    <div id="fountainG_7" class="fountainG"></div>
                    <div id="fountainG_8" class="fountainG"></div>
                </div>
                <table id="LogementList" class="table table-striped table-md sortable" style="">
                <thead>
                  <tr>
                    <th class="col-md-2"></th>
                    <th scope="col" class="col-md-2"><fmt:message key="message.logementNumero" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-2"><fmt:message key="message.logementTypeAppart" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-2"><fmt:message key="message.logementGenre" bundle="${ressourcesBundle}"/></th>
                    <th scope="col" class="col-md-2"><fmt:message key="message.logementPlacesDispo" bundle="${ressourcesBundle}"/></th>
                    <th class="col-md-2"></th>
                  </tr>
                </thead>
                <tbody class="bodyTable">
                <c:forEach var="item" items="${itemList}" varStatus="count">
                    <tr>
                    <td class="text-center">${count.index+1}</td>
                    <td class="text-left">${item.logementNumero}</td>
                    <td class="text-left">${item.getTypeAppartNom().getTypeAppartNom()}</td>
                    <td class="text-left">${item.logementGenreRequis}</td>
                    <td class="text-left">${item.logementPlacesDispo}</td>
                    <td class="text-center">
                      <form action="#" method="POST">
                        <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                        <input type="hidden" name="logementNumero" value="${item.logementNumero}">
                        <div class="btn-group" role="group" aria-label="Actions">
                            <button class="btn btn-xs" formaction="LogementEdit.do"><img src="img/edit.png" alt="edit" class="localButton" /></button>
                            <button class="btn btn-xs" formaction="LogementRemove.do"><img src="img/delete.png" alt="delete" class="localButton" /></button>
                        </div>
                      </form>
                    </td>
                   </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                  <tr>
                    <th></th>
                    <th id="slogementNumero"><fmt:message key="message.logementNumero" bundle="${ressourcesBundle}"/></th>
                    <th id="slogementTypeAppartNom"><fmt:message key="message.logementTypeAppart" bundle="${ressourcesBundle}"/></th>
                    <th id="slogementGenreRequis"><fmt:message key="message.logementGenre" bundle="${ressourcesBundle}"/></th>
                    <th id="slogementPlacesDispo"><fmt:message key="message.logementPlacesDispo" bundle="${ressourcesBundle}"/></th>
                    <th></th>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script>
        var tableName = "LogementList";
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
            // addDataTableButtonImport("Logement");
            // addDataTableButtonOther("Logement", "buttonName", "actionName");
            addDataTableButtonNew("Logement");
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
 
  </body>
</html>
