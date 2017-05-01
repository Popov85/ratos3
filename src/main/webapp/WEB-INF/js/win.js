var max = 70;
var min = -200;
var i = 0;
var i2 = 0;
var tmp;
var tmp2;
function show(obj) {
    tmp = obj;
    if (i < max) {
        obj.style.top = i;
        i += 3;
        setTimeout("show(tmp)", 5);
    }
}

function hidehd() {
    hide(hd);
}

function hide(obj) {
    tmp = obj;
    if (i > -400) {
        obj.style.top = i;
        i -= 3;
        setTimeout("hide(tmp)", 5);
    } else {
        hd.style.visibility = 'hidden';
    }
}

function showhelp(obj, obj2) {
    i = min;
    obj.style.visibility = 'visible';

    max = (screen.availHeight - 550) / 2;

    obj.style.left = (screen.availWidth - 750) / 2;
    obj.style.width = 750;

    show(obj);
}

function show2(obj) {
    tmp2 = obj;
    if (i2 < max) {
        obj.style.top = i2;
        i2 += 3;
        setTimeout("show2(tmp2)", 5);
    }
}

function hidehd2() {
    hide2(hd2);
}

function hide2(obj) {
    tmp2 = obj;
    if (i2 > -400) {
        obj.style.top = i2;
        i2 -= 3;
        setTimeout("hide2(tmp2)", 5);
    } else {
        hd2.style.visibility = 'hidden';
    }
}

function showhint(obj, obj2) {
    i2 = min;
    obj.style.visibility = 'visible';

    max = (screen.availHeight - 550) / 2;

    obj.style.left = (screen.availWidth - 500) / 2;
    obj.style.width = 500;

    show2(obj);
}