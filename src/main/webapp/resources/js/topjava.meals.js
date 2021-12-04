const mealAjaxUrl = "profile/meals";

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
                "defaultContent": "",
                "orderable": false
            },
            {
                "defaultContent": "",
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
        makeEditable(table)
        // ctx.updateTable();
    }
);


function updateFilter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "/filter",
        data: $("#filter").serialize()
    }).done(updateTable);
}

function clear() {
    $("filter")[0].reset();
    $.get(mealAjaxUrl, updateTable)
}