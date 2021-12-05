const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

let table = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "data": null,
                "className": "edit",
                "defaultContent": '<i class="fa fa-pencil"/>',
                "orderable": false
            },
            {
                "data": "",
                "className": "delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "decs"
            ]
        ]
    }
);

$(function () {
    makeEditable(table);
    // updateFilter();
});

function updateFilter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        contentType: 'application/json',
        data: $("#filter").serialize()
    }).done(updateTableWithGet);
}

function clear() {
    $("filter")[0].reset();
    $.get(mealAjaxUrl, updateTableWithGet)
}