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
          <div class="col-md-12"><h3><fmt:message key="message.Souhait" bundle="${ressourcesBundle}"/></h3></div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form method="POST">
              <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
              <input type="hidden" name="typeSouhait" value="${item.typeSouhait}" />
              <input type="hidden" name="field" value="Souhait" />
              <div class="table-responsive">
                <table class="table table-striped table-sm ">
                  <tbody>
                  </tbody>
                  <tfoot>
                    <tr id="save">
                      <th scope="col"></th>
                      <td class="text-center">
                        <button class="btn btn-warning" formaction="SouhaitList.do"><img src="img/cancel.png" alt="save" class="icon" /><fmt:message key="button.cancel" bundle="${ressourcesBundle}"/></button>
                        <button class="btn btn-success" formaction="SouhaitSave.do"><img src="img/save.png" alt="save" class="icon" /><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
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
