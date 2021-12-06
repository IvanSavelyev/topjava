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

    form.submit(function () {
        save();
    })
    let startDate = $("#startDate");
    let endDate = $("#endDate");

});

function updateFilter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        contentType: 'application/json',
        data: $("#filter").serialize()
    }).done(updateTableByGet);
}

function clear() {
    $("#filter")[0].reset();

    // $.get(mealAjaxUrl, updateTableWithGet)
}