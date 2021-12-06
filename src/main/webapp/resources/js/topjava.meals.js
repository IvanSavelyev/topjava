const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

let table_meal = $("#datatable").DataTable({
    "paging": false,
    "info": true,
    "columns": [{
        "data": "dateTime"
    }, {
        "data": "description"
    }, {
        "data": "calories"
    }, {
        "defaultContent": "Edit",
        "orderable": false
    }, {
        "defaultContent": "Delete",
        "orderable": false
    }],
    "order": [[
        0,
        "desc"
    ]]
});

$(function () {
    makeEditable(table_meal);
});

function filter() {
    let filter = $("#filter");

    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: filter.serialize()
    }).done(updateTableByData).fail(failNoty);
}

function clear() {
    location.reload();
}