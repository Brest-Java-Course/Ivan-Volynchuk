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

var value;
$('select').on('change', function (e) {
    value = $("option:selected", this).val();

    clearFilter();

    if(value==0) {

    }else if(value==1) {
         $('#date1').show();
         $('#filter').show();
    }else if(value==2) {
         $('#date2').show();
         $('#filter').show();
    }else if(value==3) {
         $('#date1').show();
         $('#date2').show();
         $('#filter').show();
    }

});

$( "#filter" ).click(function() {

    if(value==3) {
        if(isDate($("#date1").val()) & isDate($("#date2").val()))
        {
            var date1 = new Date($("#date1").val());
            var date2 = new Date($("#date2").val());
            if(date2>date1) {
                return true;
            }else {
                alert("Invalid time period.");
                return false;
            }

        }else {
            alert("Invalid date format.")
            return false;
        }
    }else if(value==2) {
        if(isDate($("#date2").val())) {
            return true;
        }else {
            alert("Invalid before date format.");
            return false;
        }
    }else if(value==1) {
         if(isDate($("#date1").val())) {
            return true;
         }else {
            alert("Invalid after date format.");
            return false;
         }
    }

});
