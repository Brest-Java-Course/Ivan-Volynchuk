$( document ).ready(function() {
   clearFilter();
});

function clearFilter() {

    $('#date1').val("");
    $('#date2').val("");
    $('#date1').hide();
    $('#date2').hide();
    $('#filter').hide();
}

var destination;
var value;
$('select').on('change', function (e) {
    value = $("option:selected", this).val();

    clearFilter();

    if(value==0) {

    }else if(value==1) {
         $('#date1').show();
         $('#filter').show();
         destination="/scroll/filter/after?";
    }else if(value==2) {
         $('#date1').show();
         $('#filter').show();
         destination="/scroll/filter/before?";
    }else if(value==3) {
         $('#date1').show();
         $('#date2').show();
         $('#filter').show();
         destination="/scroll/filter/between?";
    }

});

$( "#filter" ).click(function() {

    if(value==3) {
        if(isDate($("#date1").val()) & isDate($("#date2").val()))
        {
            var date1 = new Date($("#date1").val());
            var date2 = new Date($("#date2").val());
            if(date2>date1) {

                destination+="date1="+$("#date1").val();
                destination+="&date2="+$("#date2").val();
                window.location.href = destination;
            }else {
                alert("Invalid time period.");
            }

        }else {
            alert("Invalid date format.")
        }
    }else {
        if(isDate($("#date1").val())) {
            destination+="date="+$("#date1").val();
            window.location.href = destination;
        }else {
            alert("Invalid date format.")
        }
    }

});
