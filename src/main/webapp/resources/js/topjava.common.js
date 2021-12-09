let form;

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;

    form = $('#detailsForm');

    $('.delete').on('click', function () {
        deleteRow($(this).closest('tr').attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    if (confirm('Are you sure?')) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            updateTableByGet();
            successNoty("Deleted");
        }).always(printLog("Delete ajax method with path: " + ctx.ajaxUrl + id));
    }
}

function updateTableByGet() {
    $.get(ctx.ajaxUrl, updateTableByData);
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    let formData = form.serialize()
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: formData
    }).done(function () {
        $("#editRow").modal("hide");
        updateTableByGet();
        successNoty("Saved");
    }).always(printLog("Post ajax method with path: " + ctx.ajaxUrl + formData));
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}

function printLog(msg) {
    console.log(msg);
}