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
        // "render": function (data, type, row) {
        //     if(type === "display"){
        //         return "<a class=\"fa fa-pencil\">"
        //     }
        // }
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


function clearFilter() {
    $("#filter").find(":input").val("");
    updateTableByGet();
}

function filter() {
    let filter = $("#filter");

    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: filter.serialize()
    }).done(updateTableByData).always(console.log("Filter url:" + ctx.ajaxUrl + "filter"));
}