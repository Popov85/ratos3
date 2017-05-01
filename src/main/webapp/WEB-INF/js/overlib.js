var n = (document.layers) ? 1 : 0;
var inside = 0;
var our_obj = 0;
var was_cursor_init = 0;

function cursorInit() {
    maper = new marker('hint');
    if (n) document.captureEvents(Event.MOUSEMOVE);
    document.onmousemove = move;
    was_cursor_init = 1;
}
function b_moveIt(x, y) {
    this.x = x;
    this.y = y;
    lay.left = this.x;
    lay.top = this.y;
}
function marker(obj) {
    lay = (n) ? eval('document.' + obj) : eval('document.all.' + obj + '.style');
    this.x = (n) ? lay.left : lay.pixelLeft;
    this.y = (n) ? lay.top : lay.pixelTop;
    this.moveIt = b_moveIt;
    return this;
}
function move(e) {
    our_obj = e;
    if (inside == 1) {
        x = n ? e.pageX : event.x + document.body.scrollLeft;
        y = n ? e.pageY : event.y + document.body.scrollTop;
        maper.moveIt(x - 70, y + 15);
    }
}
function Show(help) {
    inside = 1;
    if (was_cursor_init == 0) cursorInit();
    if (our_obj != 0) move(our_obj);
    if (n) {
        document.layers["hint"].document.open();
        document.layers["hint"].document.writeln('<table width=150 border=1 cellpadding=2 cellspacing=0 bordercolordark=#FEF5DC><tr><td class=ahint>' + help + '</td></tr></table>');
        document.layers["hint"].document.close();
        document.layers["hint"].visibility = "visible";
    } else {
        document.all("hint").innerHTML = '<table width=150 border=1 cellpadding=2 cellspacing=0 bordercolordark=#FEF5DC><tr><td class=ahint>' + help + '</td></tr></table>';
        document.all.hint.style.visibility = "visible";
    }
}
function Out() {
    if (was_cursor_init == 0) cursorInit();
    inside = 0;
    lay.visibility = "hidden";
}

onload = cursorInit;
