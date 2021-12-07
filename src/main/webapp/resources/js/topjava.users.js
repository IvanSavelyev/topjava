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
        "defaultContent": "Edit",
        "orderable": false
    }, {
        "defaultContent": "Delete",
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

function userEnable(id) {
    let enable = $("#checkbox").is(":checked");

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id,
        data: "enable=" + enable
    }).done(function () {
        $("#checkbox").prop("checked", !!enable );
        successNoty(enable ? "Enabled": "Disabled");
    }).always(printLog("Post ajax enable user method with status: " + enable + "\nwith path:" + ctx.ajaxUrl + id));
}