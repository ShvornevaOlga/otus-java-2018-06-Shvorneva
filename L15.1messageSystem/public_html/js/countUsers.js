var ws;

init = function () {
     ws = new WebSocket("ws://localhost:8080/countUsers");
        ws.onopen = function (event) {

        }
        ws.onmessage = function (event) {
            var name = document.getElementById("count");
            name.textContent=event.data;
        }
        ws.onclose = function (event) {

        }
};

function sendMessageGetCount() {
        var message = '';
        ws.send(message);
}