$( document ).ready(function() {
   clearFilter();
});

function clearFilter() {
    $('#date1').val("");
    $('#date2').val("");
    $('#date1').hide();
    $('#date2').hide();
    $('#filter').hide();
    $('.table tr').not(':first').show();
}

var value;
$('select').on('change', function (e) {
    value = $("option:selected", this).val();

    clearFilter();

    if(value==0) {

    }else if(value==1) {
         $('#date1').show();
         $('#filter').show();
    }else if(value==2) {
         $('#date1').show();
         $('#filter').show();
    }else if(value==3) {
         $('#date1').show();
         $('#date2').show();
         $('#filter').show();
    }

});

$( "#clearer" ).click(function() {
    clearFilter();
});
$( "#filter" ).click(function() {
    var table = $('#table');
    //Between dates
    if(value==3) {

        if(isDate($("#date1").val()) & isDate($("#date2").val()))
        {
            var date1 = new Date($("#date1").val());
            var date2 = new Date($("#date2").val());
            if(date2>date1) {

                $('tr:not(:first) td:nth-child(4)', table).each(function() {

                     var date = new Date($(this).html());
                     if(date>date1 & date<date2) {
                        $(this).parent().show();
                     }else {
                        $(this).parent().hide();
                     }
                 });

            }else {
                alert("Invalid time period.");
            }

        }else {
            alert("Invalid date format.")
        }
    }else { //After or Before Date

        if(isDate($("#date1").val())) {

            var date1 = new Date($("#date1").val());

            if(value==1) { //After

                $('tr:not(:first) td:nth-child(4)', table).each(function() {

                    var date = new Date($(this).html());

                    if(date>date1) {
                        $(this).parent().show();
                    }else {
                        $(this).parent().hide();
                    }
                });
            }else { //Before

                $('tr:not(:first) td:nth-child(4)', table).each(function() {

                    var date = new Date($(this).html());

                    if(date<date1) {
                        $(this).parent().show();
                    }else {
                        $(this).parent().hide();
                    }
                });
            }
        }else {
            alert("Invalid date format.")
        }
    }
});
