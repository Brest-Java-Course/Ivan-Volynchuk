function Formdata(data){

    if (data.description != null && data.description.value.length == 0 ) {
        alert('Empty description');
        return false;
    }
    if (data.description != null && data.description.value.length > 30 ) {
        alert('Too big length of description');
        return false;
    }

    if (data.durability != null && data.durability.value.length == 0 ) {
        alert('Not null durability');
        return false;
    }
    if (data.durability != null && data.durability.value.length > 30 ) {
        alert('Too big durability');
        return false;
    }

    if(isDate(data.creationDate.value)==false) {
        alert('Illegal date format');
        return false;
    }

    if (data.manaCost != null && data.manaCost.value.length == 0 ) {
        alert('Not null mana cost');
        return false;
    }
    if (data.manaCost != null && data.manaCost.value.length > 30 ) {
        alert('Too big mana cost');
        return false;
    }




}