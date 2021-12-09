const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

let table_user = $("#datatable").DataTable({
    "paging": false,
    "info": true,
    "columns": [{
        "data": "name"
    }, {
        "data": "email"
    }, {
        "data": "roles"
    }, {
        "data": "enabled"
    }, {
        "data": "registered"
    }, {
        "data": null,
        "className": "edit",
        "defaultContent": '<i class="fa fa-pencil"/>',
        "orderable": false
    }, {
        "data": null,
        "className": "delete",
        "defaultContent": '<i class="fa fa-remove"/>',
        "orderable": false
    }],
    "order": [[
        0,
        "asc"
    ]]
});

// $(document).ready(function () {
$(function () {
    makeEditable(table_user);
});

function userEnable(id, checkBox) {
    let enable = checkBox.is(":checked");
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id,
        data: "enable=" + enable
    }).done(function () {
        checkBox.closest("tr").attr("data-user-enable", enable);
        successNoty(enable ? "Enabled" : "Disabled");
    });
}