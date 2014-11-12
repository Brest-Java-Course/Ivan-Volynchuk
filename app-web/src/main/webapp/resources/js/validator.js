function Formdata(data){

if (data.userName != null && data.userName.value.length == 0 ) {
alert('Empty userName');
return false;
}
if (data.userName != null && data.userName.value.length > 20 ) {
alert('Too big length of userName');
return false;
}


if (data.login != null && data.login.value.length == 0 ) {
alert('Empty login');
return false;
}
if (data.login != null && data.login.value.length > 20 ) {
alert('Too big length of login');
return false;
}

}