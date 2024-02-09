<%-- 
    Document   : questionnaire
    Created on : 15 déc. 2023, 15:35:04
    Author     : viann
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
        
        <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* 100% de la hauteur de la vue */
        }

        .container {
            flex: 1; /* Occupe l'espace disponible restant */
        }

        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
         h1 {
            text-align: center;
        }
        .custom-select {
            width: 40px; /* Définissez la largeur souhaitée */
        }
    </style>
        
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
        <%@ include file="header.jspf" %>
        <div class="py-5">
            <div class="container md-10 justify-content-center">
                <h1>Formulaire</h1>
                <br>
                <h5>Veuillez remplir les informations ci-dessous pour vous affecter dans un logement. (Ceci est destiné aux élèves en "Oui définitif" sur SCEI, les autres ne seront pas pris en compte).</h5>
                <br>
                    <div class="col-md-12">
                        <form id="c_form-h" class="" action="EleveSave.do" method="POST" enctype="multipart/form-data">
                            <input type="hidden" name="connexion" value="<c:if test="${! empty user}">${user.connectionId}</c:if>" />
                            <input type="hidden" name="eleveId" value="${eleve.eleveId}" />
                            <input type="hidden" name="NumScei" value="${eleve.numscei}" />
                            <input type="hidden" name="personneId" value="${personne.personneId}" />
                            <div class="form-group row">
                                <label for="inputnaissanceh" class="col-2 col-form-label"><fmt:message key="message.naissance" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <input type="date" class="form-control" id="inputnaissanceh" placeholder="<fmt:message key="message.naissance" bundle="${ressourcesBundle}"/>" name="eleveDateNaissance" required="required">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputmailh" class="col-2 col-form-label"><fmt:message key="message.questionnaireMail" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <input type="email" class="form-control" id="inputmailh" placeholder="<fmt:message key="message.questionnaireMail" bundle="${ressourcesBundle}"/>" name="eleveMail" required="required">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputnumtelh" class="col-2 col-form-label"><fmt:message key="message.questionnaireTelephone" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <input type="text" class="form-control" id="inputnumtelh" pattern="[+0-9]{10,13}" placeholder="<fmt:message key="message.questionnaireTelephone" bundle="${ressourcesBundle}"/>" name="eleveNumtel" required="required">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputpaysh" class="col-2 col-form-label"><fmt:message key="message.questionnairePays" bundle="${ressourcesBundle}"/> </label>
                                <div class="col-10">
                                    <select id="country" name="elevePayshab" required style="width: 200px;">
                                    <option value="afghanistan">Afghanistan</option>
                                    <option value="albania">Albania</option>
                                    <option value="algeria">Algeria</option>
                                    <option value="andorra">Andorra</option>
                                    <option value="angola">Angola</option>
                                    <option value="antigua-and-barbuda">Antigua and Barbuda</option>
                                    <option value="argentina">Argentina</option>
                                    <option value="armenia">Armenia</option>
                                    <option value="australia">Australia</option>
                                    <option value="austria">Austria</option>
                                    <option value="azerbaijan">Azerbaijan</option>
                                    <option value="bahamas">Bahamas</option>
                                    <option value="bahrain">Bahrain</option>
                                    <option value="bangladesh">Bangladesh</option>
                                    <option value="barbados">Barbados</option>
                                    <option value="belarus">Belarus</option>
                                    <option value="belgium">Belgium</option>
                                    <option value="belize">Belize</option>
                                    <option value="benin">Benin</option>
                                    <option value="bhutan">Bhutan</option>
                                    <option value="bolivia">Bolivia</option>
                                    <option value="bosnia-and-herzegovina">Bosnia and Herzegovina</option>
                                    <option value="botswana">Botswana</option>
                                    <option value="brazil">Brazil</option>
                                    <option value="brunei">Brunei</option>
                                    <option value="bulgaria">Bulgaria</option>
                                    <option value="burkina-faso">Burkina Faso</option>
                                    <option value="burundi">Burundi</option>
                                    <option value="cabo-verde">Cabo Verde</option>
                                    <option value="cambodia">Cambodia</option>
                                    <option value="cameroon">Cameroon</option>
                                    <option value="canada">Canada</option>
                                    <option value="central-african-republic">Central African Republic</option>
                                    <option value="chad">Chad</option>
                                    <option value="chile">Chile</option>
                                    <option value="china">China</option>
                                    <option value="colombia">Colombia</option>
                                    <option value="comoros">Comoros</option>
                                    <option value="congo">Congo</option>
                                    <option value="costa-rica">Costa Rica</option>
                                    <option value="cote-divoire">Côte d'Ivoire</option>
                                    <option value="croatia">Croatia</option>
                                    <option value="cuba">Cuba</option>
                                    <option value="cyprus">Cyprus</option>
                                    <option value="czech-republic">Czech Republic</option>
                                    <option value="denmark">Denmark</option>
                                    <option value="djibouti">Djibouti</option>
                                    <option value="dominica">Dominica</option>
                                    <option value="dominican-republic">Dominican Republic</option>
                                    <option value="east-timor">East Timor (Timor-Leste)</option>
                                    <option value="ecuador">Ecuador</option>
                                    <option value="egypt">Egypt</option>
                                    <option value="el-salvador">El Salvador</option>
                                    <option value="equatorial-guinea">Equatorial Guinea</option>
                                    <option value="eritrea">Eritrea</option>
                                    <option value="estonia">Estonia</option>
                                    <option value="eswatini">Eswatini</option>
                                    <option value="ethiopia">Ethiopia</option>
                                    <option value="fiji">Fiji</option>
                                    <option value="finland">Finland</option>
                                    <option value="france">France</option>
                                    <option value="gabon">Gabon</option>
                                    <option value="gambia">Gambia</option>
                                    <option value="georgia">Georgia</option>
                                    <option value="germany">Germany</option>
                                    <option value="ghana">Ghana</option>
                                    <option value="greece">Greece</option>
                                    <option value="grenada">Grenada</option>
                                    <option value="guatemala">Guatemala</option>
                                    <option value="guinea">Guinea</option>
                                    <option value="guinea-bissau">Guinea-Bissau</option>
                                    <option value="guyana">Guyana</option>
                                    <option value="haiti">Haiti</option>
                                    <option value="honduras">Honduras</option>
                                    <option value="hungary">Hungary</option>
                                    <option value="iceland">Iceland</option>
                                    <option value="india">India</option>
                                    <option value="indonesia">Indonesia</option>
                                    <option value="iran">Iran</option>
                                    <option value="iraq">Iraq</option>
                                    <option value="ireland">Ireland</option>
                                    <option value="israel">Israel</option>
                                    <option value="italy">Italy</option>
                                    <option value="jamaica">Jamaica</option>
                                    <option value="japan">Japan</option>
                                    <option value="jordan">Jordan</option>
                                    <option value="kazakhstan">Kazakhstan</option>
                                    <option value="kenya">Kenya</option>
                                    <option value="kiribati">Kiribati</option>
                                    <option value="korea-north">Korea, North (North Korea)</option>
                                    <option value="korea-south">Korea, South (South Korea)</option>
                                    <option value="kosovo">Kosovo</option>
                                    <option value="kuwait">Kuwait</option>
                                    <option value="kyrgyzstan">Kyrgyzstan</option>
                                    <option value="laos">Laos</option>
                                    <option value="latvia">Latvia</option>
                                    <option value="lebanon">Lebanon</option>
                                    <option value="lesotho">Lesotho</option>
                                    <option value="liberia">Liberia</option>
                                    <option value="libya">Libya</option>
                                    <option value="liechtenstein">Liechtenstein</option>
                                    <option value="lithuania">Lithuania</option>
                                    <option value="luxembourg">Luxembourg</option>
                                    <option value="madagascar">Madagascar</option>
                                    <option value="malawi">Malawi</option>
                                    <option value="malaysia">Malaysia</option>
                                    <option value="maldives">Maldives</option>
                                    <option value="mali">Mali</option>
                                    <option value="malta">Malta</option>
                                    <option value="marshall-islands">Marshall Islands</option>
                                    <option value="mauritania">Mauritania</option>
                                    <option value="mauritius">Mauritius</option>
                                    <option value="mexico">Mexico</option>
                                    <option value="micronesia">Micronesia</option>
                                    <option value="moldova">Moldova</option>
                                    <option value="monaco">Monaco</option>
                                    <option value="mongolia">Mongolia</option>
                                    <option value="montenegro">Montenegro</option>
                                    <option value="morocco">Morocco</option>
                                    <option value="mozambique">Mozambique</option>
                                    <option value="myanmar">Myanmar (Burma)</option>
                                    <option value="namibia">Namibia</option>
                                    <option value="nauru">Nauru</option>
                                    <option value="nepal">Nepal</option>
                                    <option value="netherlands">Netherlands</option>
                                    <option value="new-zealand">New Zealand</option>
                                    <option value="nicaragua">Nicaragua</option>
                                    <option value="niger">Niger</option>
                                    <option value="nigeria">Nigeria</option>
                                    <option value="north-macedonia">North Macedonia (formerly Macedonia)</option>
                                    <option value="norway">Norway</option>
                                    <option value="oman">Oman</option>
                                    <option value="pakistan">Pakistan</option>
                                    <option value="palau">Palau</option>
                                    <option value="panama">Panama</option>
                                    <option value="papua-new-guinea">Papua New Guinea</option>
                                    <option value="paraguay">Paraguay</option>
                                    <option value="peru">Peru</option>
                                    <option value="philippines">Philippines</option>
                                    <option value="poland">Poland</option>
                                    <option value="portugal">Portugal</option>
                                    <option value="qatar">Qatar</option>
                                    <option value="romania">Romania</option>
                                    <option value="russia">Russia</option>
                                    <option value="rwanda">Rwanda</option>
                                    <option value="saint-kitts-and-nevis">Saint Kitts and Nevis</option>
                                    <option value="saint-lucia">Saint Lucia</option>
                                    <option value="saint-vincent-and-the-grenadines">Saint Vincent and the Grenadines</option>
                                    <option value="samoa">Samoa</option>
                                    <option value="san-marino">San Marino</option>
                                    <option value="sao-tome-and-principe">Sao Tome and Principe</option>
                                    <option value="saudi-arabia">Saudi Arabia</option>
                                    <option value="senegal">Senegal</option>
                                    <option value="serbia">Serbia</option>
                                    <option value="seychelles">Seychelles</option>
                                    <option value="sierra-leone">Sierra Leone</option>
                                    <option value="singapore">Singapore</option>
                                    <option value="slovakia">Slovakia</option>
                                    <option value="slovenia">Slovenia</option>
                                    <option value="solomon-islands">Solomon Islands</option>
                                    <option value="somalia">Somalia</option>
                                    <option value="south-africa">South Africa</option>
                                    <option value="south-sudan">South Sudan</option>
                                    <option value="spain">Spain</option>
                                    <option value="sri-lanka">Sri Lanka</option>
                                    <option value="sudan">Sudan</option>
                                    <option value="suriname">Suriname</option>
                                    <option value="sweden">Sweden</option>
                                    <option value="switzerland">Switzerland</option>
                                    <option value="syria">Syria</option>
                                    <option value="taiwan">Taiwan</option>
                                    <option value="tajikistan">Tajikistan</option>
                                    <option value="tanzania">Tanzania</option>
                                    <option value="thailand">Thailand</option>
                                    <option value="togo">Togo</option>
                                    <option value="tonga">Tonga</option>
                                    <option value="trinidad-and-tobago">Trinidad and Tobago</option>
                                    <option value="tunisia">Tunisia</option>
                                    <option value="turkey">Turkey</option>
                                    <option value="turkmenistan">Turkmenistan</option>
                                    <option value="tuvalu">Tuvalu</option>
                                    <option value="uganda">Uganda</option>
                                    <option value="ukraine">Ukraine</option>
                                    <option value="united-arab-emirates">United Arab Emirates</option>
                                    <option value="united-kingdom">United Kingdom</option>
                                    <option value="united-states">United States</option>
                                    <option value="uruguay">Uruguay</option>
                                    <option value="uzbekistan">Uzbekistan</option>
                                    <option value="vanuatu">Vanuatu</option>
                                    <option value="vatican-city">Vatican City</option>
                                    <option value="venezuela">Venezuela</option>
                                    <option value="vietnam">Vietnam</option>
                                    <option value="yemen">Yemen</option>
                                    <option value="zambia">Zambia</option>
                                    <option value="zimbabwe">Zimbabwe</option>
                                    </select>
                                </div>
                            </div>

                            <div id="franceFields" style="display: none;">
                                <div class="form-group row">
                                    <label for="inputcodepostalh" class="col-2 col-form-label d-none d-md-block"><fmt:message key="message.questionnaireCodePostal" bundle="${ressourcesBundle}"/></label>
                                        <div class="col-10">
                                            <input type="int" class="form-control" id="inputcodepostalh" required style="width: 180px;" placeholder="<fmt:message key="message.questionnaireCodePostal" bundle="${ressourcesBundle}"/>" name="eleveCodepostal">
                                        </div>
                                </div>
                            </div> 
                            <br>

                            <div class="form-group row">
                                <label for="inputvilleh" class="col-2 col-form-label"><fmt:message key="message.questionnaireVille" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <input type="text" class="form-control" id="inputvilleh" pattern="[a-zA-ZÀ-ÖØ-öø-ÿ-]+$" placeholder="<fmt:message key="message.questionnaireVille" bundle="${ressourcesBundle}"/>" name="eleveVillehab" required="required">
                                </div>
                            </div>


                            <div class="form-group row"> 
                                <label for="inputboursierh" class="col-2 col-form-label"><fmt:message key="message.questionnaireBoursier" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <div class="d-block my-3">
                                        <div class="custom-control custom-radio">
                                            <input id="inputboursiernon" name="eleveBoursier" type="radio" class="custom-control-input" required="" value="false"> <label class="custom-control-label" for="inputboursiernon">Non</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="inputboursieroui" name="eleveBoursier" type="radio" class="custom-control-input" required="" value="true"> <label class="custom-control-label" for="inputboursieroui">Oui</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                
                            <div class="form-group row">
                                <div id="scholarshipCertificate" class="col-12" style="display: none;">
                                    <label for="fileInput"><fmt:message key="message.questionnaireNotif" bundle="${ressourcesBundle}"/></label>
                                    <input type="file" id="fileInput" name="eleveFile" accept=".pdf">
                                </div>
                            </div>

                            <div class="form-group row"> 
                                <label for="inputgenre" class="col-2 col-form-label"><fmt:message key="message.questionnaireGenre" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <div class="d-block my-3">
                                        <div class="custom-control custom-radio">
                                            <input id="masculin" name="genre" type="radio" class="custom-control-input" required="",checked="", value="M">
                                            <label class="custom-control-label" for="masculin"><fmt:message key="message.questionnaireMasculin" bundle="${ressourcesBundle}"/></label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="feminin" name="genre" type="radio" class="custom-control-input" required="",checked="", value="F">
                                            <label class="custom-control-label" for="feminin"><fmt:message key="message.questionnaireFeminin" bundle="${ressourcesBundle}"/></label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input id="autre" name="genre" type="radio" class="custom-control-input" required="",checked="", value="A">
                                            <label class="custom-control-label" for="autre"><fmt:message key="message.questionnaireAutre" bundle="${ressourcesBundle}"/></label>
                                        </div>
                                    </div>
                                </div>
                            </div>         

                            <div class="form-group row"> 
                                <label for="inputtypeSouhait" class="col-2 col-form-label"><fmt:message key="message.questionnairePreference" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
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
                            </div>
                            <div class="form-group row">
                                <label for="inputinfosuph" class="col-2 col-form-label"><fmt:message key="message.questionnaireInfosup" bundle="${ressourcesBundle}"/></label>
                                <div class="col-10">
                                    <input type="text" class="form-control" id="inputinfosuph" placeholder="<fmt:message key="message.questionnaireInfosup" bundle="${ressourcesBundle}"/>" name="eleveInfosup"></div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                <button type="submit" class="btn btn-primary"><fmt:message key="button.save" bundle="${ressourcesBundle}"/></button>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-12">
                                    <h5 class="">Pour toute information ou problème, merci de contacter le [numéro permanence logement]</h5>
                                </div>
                            </div>
                        </form>
                    </div>
            </div>
        </div>
        <%@ include file="footer2.jspf" %>
        
        <script>
        document.getElementById('country').addEventListener('change', function () {
            var franceFields = document.getElementById('franceFields');
            if (this.value === 'france') {
                franceFields.style.display = 'block';
            } else {
                franceFields.style.display = 'none';
            }
        });
        
         document.getElementById('country').addEventListener('change', function () {
            var franceFields = document.getElementById('franceFields2');
            if (this.value === 'france') {
                franceFields.style.display = 'block';
            } else {
                franceFields.style.display = 'none';
            }
        });

        document.getElementById('inputboursieroui').addEventListener('change', function () {
            var scholarshipCertificate = document.getElementById('scholarshipCertificate');
            if (this.checked) {
            //if (this.value==='oui') {
                scholarshipCertificate.style.display = 'block';
            } else {
                scholarshipCertificate.style.display = 'none';
            }
        });
        
        document.getElementById('inputboursiernon').addEventListener('change', function () {
            var scholarshipCertificate = document.getElementById('scholarshipCertificate');
            if (this.checked) {
                scholarshipCertificate.style.display = 'none';
            }
        });
    </script>
    </body>
</html>
