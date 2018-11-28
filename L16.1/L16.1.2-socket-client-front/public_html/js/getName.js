var ws;

init = function () {
     ws = new WebSocket("ws://localhost:8080/getName");
        ws.onopen = function (event) {

        }
        ws.onmessage = function (event) {
            var name = document.getElementById("name");
            name.textContent=event.data;
        }
        ws.onclose = function (event) {

        }
};

function sendMessageGetName() {
        var message = document.getElementById("idUser").value;
        ws.send(message);
}