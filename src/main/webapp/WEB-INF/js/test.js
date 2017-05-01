var n = (document.layers) ? 1 : 0;
var was_cursor_init = 0;

function cursorInit() {
    if (n) document.captureEvents(Event.SELECTSTART);
    if (n) document.captureEvents(Event.CONTEXTMENU);
    document.onselectstart = m;
    document.oncontextmenu = m;
    was_cursor_init = 1;
}
function m(e) {
    return false;
}
function st() {
    if (was_cursor_init == 0) cursorInit();
}

onload = cursorInit;
