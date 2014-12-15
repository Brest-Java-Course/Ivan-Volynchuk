$(document).ready(function() {
    $('#sub').click(function(e) {
        var isValid = true;
        $('input[type="text"]').each(function() {
            if ($.trim($(this).val()) == '') {
                isValid = false;
                $(this).attr('class', 'warn');
            }else {
                $(this).attr('class', '');
            }
        });
        $('input[type="number"]').each(function() {
            if ($.trim($(this).val()) == '') {
                isValid = false;
                $(this).attr('class', 'warn');
            }else {
                $(this).attr('class', '');
            }
        });
        if(isDate($('#datepicker').val())==false) {
            $('#datepicker').attr('class', 'warn');
            isValid = false;
        }else {
            $('#datepicker').attr('class', '');
        }
        if (isValid == false)
            e.preventDefault();
    });
});