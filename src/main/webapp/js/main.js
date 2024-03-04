/**
    ------------------------------------------
    main.js
    
    javascript main functions
    
    JY Martin
    Ecole Centrale Nantes
    ------------------------------------------
*/

// -----------------------------------------------------------------------------
//.Launching actions

/**
    launchNav
    launch new command from Menu
    @param actionName the action to launch
*/

function launchNav(actionName) {
    if ((actionName !== null) && (actionName !== undefined)) {
        // collect data
        var formRef = document.getElementById("navLaunch");
        if (formRef !== null) {
            // set form action
            formRef.action = actionName + ".do";
            // launch
            formRef.submit();
        }
    }
}

/**
    launchAction
    launch new command from screen
    @param actionName the action to launch
    @param dataValue data to take into account
    @param curAction current action
*/

function launchAction(actionName, dataValue, curAction) {
    if ((actionName !== null) && (actionName !== undefined)) {
        // collect data
        var formRef = document.getElementById("actionLaunch");
        var dataRef = document.getElementById("dataAction");
        var curRef = document.getElementById("curAction");
        if ((formRef !== null) && (dataRef !== null) && (curRef !== null)) {
            // fill form
            dataRef.value = dataValue;
            curRef.value = curAction;
            formRef.action = actionName + ".do";
            // launch
            formRef.submit();
        }
    }
}

/**
    launchForm
    launch new command from FORM
    @param formId the form ID
    @param actionName the action to launch
*/

function launchForm(formId, actionName) {
    if ((actionName !== null) && (actionName !== undefined)) {
        // collect data
        var formRef = document.getElementById(formId);
        if (formRef !== null) {
            // launch
            formRef.action = actionName + ".do";
            formRef.submit();
        }
    }
}

// -----------------------------------------------------------------------------
/**
 * Launch ajax call
 * @param {type} action
 * @param {type} data
 * @param {type} applySuccess
 * @returns {undefined}
 */
function ajaxCall(action, data, applySuccess) {
    var id_code = document.getElementById("connexion");
    data.connexion = id_code.value;
    data.action = action;

    $.ajax({
        url: "ajax.do",
        data: data,
        method: "POST",
        async: false,
        success: function (result) {
            if (result.ok === 1) {
                applySuccess(result, data);
            } else {
                    console.log("refused call " + action);
   
            }
        },
        error: function (resultat, statut, erreur) {
            console.log("error call " + action + " result = " + resultat + statut + erreur);
        }
    });
}

// -----------------------------------------------------------------------------
function edit(itemName, idValue) {
	var actionName = itemName + "Edit";
	var dataValue = new Object();
	dataValue.id = idValue;
	var curAction = itemName + "List";
	
	launchAction(actionName, dataValue, curAction);
}

function create(itemName, idValue) {
	var actionName = itemName + "Create";
	var dataValue = new Object();
	dataValue.id = idValue;
	var curAction = itemName + "List";
	
	launchAction(actionName, dataValue, curAction);
}

function applySuccessRemoveItem(result, data) {
    if (result.id > 0) {
        var theTR = document.getElementById(data.name + result.id);
        if (theTR !== null) {
            theTR.parentNode.removeChild(theTR);
        }
    }
}

/**
 * remove item
 * @param {type} itemName
 * @param {type} buttunRef
 * @param {type} idValue
 * @returns {undefined}
 */
function remove(itemName, buttunRef, idValue) {
    var data = new Object();
    data.name = itemName;
    data.id = idValue;

	ajaxCall(itemName + "Remove", data, applySuccessRemoveItem);
}

// -----------------------------------------------------------------------------
// DATATABLE Tools

var myButtons = new Array();

/**
	Display table using Datatable
*/
function showDataTable(tableName) {
    var theTable = document.getElementById(tableName);
    if (theTable !== null) {
        let spinner = document.getElementById("fountainG");
        if (spinner !== null) {
            spinner.parentElement.removeChild(spinner);
        }
        theTable.style.visibility = "visible";
        theTable.style.width = "100%";
    }
}

function addDataTableButtonCopy() {
    myButtons.push({
        extend: 'copy',
        exportOptions: {rows: {selected: true}}
    });
}

function addDataTableButtonCsv() {
    myButtons.push({
        extend: 'csv',
        exportOptions: {rows: {selected: true}}
    });
}

function addDataTableButtonPrint() {
    myButtons.push({
        extend: 'print',
        exportOptions: {rows: {selected: true}}
    });
}

function addDataTableButtonExcel() {
    myButtons.push({
        extend: 'excelHtml5',
        exportOptions: {rows: {selected: true}},
        customize: function (xlsx) {
            var sheet = xlsx.xl.worksheets['sheet1.xml'];
            $('row c[r^="C"]', sheet).attr('s', '2');
        }
    });
}

function addDataTableButtonSelectAll() {
    myButtons.push('selectAll');
}

function addDataTableButtonDeselectAll() {
    myButtons.push('selectNone');
}

function addDataTableButtonImport(itemName) {
    myButtons.push({
        text: 'Import',
        action: function (e, dt, node, conf) {
        	var formRef = document.getElementById("formImporter");
        	formRef.action = itemName + "Import.do";
        	var element = document.querySelector("#importFile");
	        element.click();
        }
    });
}

function addDataTableButtonOther(itemName, buttonName, actionScriptName) {
    myButtons.push({
        text: buttonName,
        action: function (e, dt, node, conf) {
			var actionName = itemName + actionScriptName;
			var dataValue = new Object();
			var curAction = itemName + "List";
	
			launchAction(actionName, dataValue, curAction);
        }
    });
}
function addDataTableButtonNew(itemName) {
    myButtons.push({
        text: 'NEW',
        action: function (e, dt, node, conf) {
			create(itemName, -1);
        }
    });
}

function buildTable(tableName) {
    // Structure table
    let table = $('#' + tableName).DataTable({
        "fnDrawCallback": function (oSettings) {
            showDataTable(tableName);
        },
        rowReorder: {
            selector: 'td:nth-child(1)'
        },
        "responsive": true,
        "lengthChange": true,
        "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]],
        "pageLength": 10,
/*        "language": {
            "sProcessing": "Traitement en cours...",
//            "sSearch": "Rechercher&nbsp;:",
//            "sLengthMenu": "Afficher _MENU_ &eacute;l&eacute;ments",
//            "sInfo": "Affichage de l'&eacute;l&eacute;ment _START_ &agrave; _END_ sur _TOTAL_ &eacute;l&eacute;ments",
//            "sInfoEmpty": "Affichage de l'&eacute;l&eacute;ment 0 &agrave; 0 sur 0 &eacute;l&eacute;ment",
//            "sInfoFiltered": "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
//            "sInfoPostFix": ""
            "sLoadingRecords": "Chargement en cours...",
            "sZeroRecords": "Aucun &eacute;l&eacute;ment &agrave; afficher",
            "sEmptyTable": "Aucune donn&eacute;e disponible",
            "oPaginate": {
                "sFirst": "Premier",
                "sPrevious": "Pr&eacute;c&eacute;dent",
                "sNext": "Suivant",
                "sLast": "Dernier"
            },
            "oAria": {
                "sSortAscending": ": activer pour trier la colonne par ordre croissant",
                "sSortDescending": ": activer pour trier la colonne par ordre d&eacute;croissant"
            }
        },*/
        dom: 'Bfrtipl',
        buttons: myButtons,
        select: true
    });

    return table;
}
