function Formdata(data){

    if (data.name != null && data.name.value.length == 0 ) {
        alert('Empty name');
        return false;
    }
    if (data.name != null && data.name.value.length > 30 ) {
        alert('Too big length of name');
        return false;
    }

    if (data.level != null && data.level.value.length == 0 ) {
        alert('Empty level');
        return false;
    }
    if (data.level != null && data.level.value.length > 30 ) {
        alert('Too big length of level');
        return false;
    }

    if (data.experience != null && data.experience.value.length == 0 ) {
        alert('Empty experience');
        return false;
    }
    if (data.experience != null && data.experience.value.length > 30 ) {
        alert('Too big length of experience');
        return false;
    }

}