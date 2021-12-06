let form;

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;
    form = $('#detailsForm');

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    })

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
        }).fail(failNoty);
    }
}


function updateTableByGet() {
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    let formData = $('#detailsForm').serializeArray();

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        data: formData
    }).done(function () {
        $("#editRow").modal("hide");
        updateTableByGet();
        successNoty("Saved");
    }).fail(failNoty).always(console.log(formData));
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