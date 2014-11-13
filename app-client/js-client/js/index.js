// The root URL for the RESTful services
var REST_URL = "http://localhost:8080/users";
var state=-1;
var userId;
findAll();


$('#adderLink').click(function () {
    $( "#tricker" ).toggle( "fast" );
    state=0;
});

$('#sub').click(function () {
   var loginLength = $("#login").val().length;
   var nameLength = $("#name").val().length;

   if(loginLength==0 | nameLength==0) {
   alert("Please check your input.");
   return;
   }

   if(state==1) {
        updateUser();
   }
   else{
        addUser();
   }
});


$('#table1').on('click', 'a', function () {
    userId=$(this).data('identity');

    if($(this).data('goal')==1) {  //Delete
        deleteById(userId);
    }
    else {  //Update
        findById(userId);
        state=1;
    }
});
function clearAll (){
   $("#table1").find("tr:gt(0)").remove();
}


function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: REST_URL,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
}

function renderList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    clearAll();
    $.each(list, function (index, user) {
        $('#table1 tr:last').after('<tr> <td>' + user.userId +   '</td>' +
                                        '<td>' + user.userName + '</td>' +
                                        '<td>' + user.login +    '</td>'+
                                        '<td class=add>' + "<a href='#' data-goal='0' data-identity="+user.userId+">+</a>"+'</td>'+
                                        '<td class=del>' + "<a href='#' data-goal='1' data-identity="+user.userId+">-</a>"+'</td>'+'</tr>');
    });
}

function deleteById(id) {
        console.log('deleteUserById');
        $.ajax({
            type: 'DELETE',
            url: REST_URL + '/' + id,
            success: function(){
                findAll();
            },
            error: function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR, textStatus, errorThrown);

            }
        });
    }


function addUser() {
    console.log('addUser');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: REST_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data,textStatus, jqXHR) {
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
           console.log(jqXHR, textStatus, errorThrown);
           alert("addUser error -"+ formToJSON());
        }
    });
}

function formToJSONID() {
    return JSON.stringify({
        "type":"userimpl",
        "userId":userId,
        "login": $('#login').val(),
        "userName": $('#name').val()
    });
}
function formToJSON() {
    return JSON.stringify({
        "type":"userimpl",
        "login": $('#login').val(),
        "userName": $('#name').val()
    });
}

function findById(id) {

    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: REST_URL + '/' + id,
        dataType: "json",
        success: function (data) {
             console.log('findById success: ' + data.login);
             $('#login').val(data.login);
             $('#name').val(data.userName);
             $( "#tricker" ).show( "fast" );
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findById: ' + textStatus);
        }
    });
}
function updateUser() {
    console.log('updateUser');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: REST_URL,
        dataType: "json",
        data: formToJSONID(),
        success: function (data, textStatus, jqXHR) {
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('updateUser error: ' + formToJSONID());
        }
    });
}