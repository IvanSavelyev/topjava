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


