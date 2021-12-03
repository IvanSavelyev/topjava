const mealAjaxUrl = "/rest/profile/meals";

const ctx = {ajaxUrl: mealAjaxUrl};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
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
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "decs"
                ]
            ]
        })
    );
    updateSearchResults();
});

function updateFilter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: "ggggg"
    }).done(function () {
        updateTable();
    });
}