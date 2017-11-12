function join(symbol, text, attr) {
    attr = attr || "";
    return "<" + symbol + " " + attr + ">" + text + "</" + symbol + ">";
}

function joinA(url) {
    return "<a href='" + url + "'>查看</a>";
}

function formatDate(time) {
    var date = new Date(time);
    return date.getFullYear() + "-" + fillZero(date.getMonth() + 1) + "-" + fillZero(date.getDate()) + " "
        + fillZero(date.getHours()) + ":" + fillZero(date.getMinutes()) + ":" + fillZero(date.getSeconds());
}

function fillZero(num) {
    if (num < 10) {
        return "0" + num;
    }
    return num;
}