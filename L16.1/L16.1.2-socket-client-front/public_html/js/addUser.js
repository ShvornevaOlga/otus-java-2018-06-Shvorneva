var ws;

init = function () {
     ws = new WebSocket("ws://localhost:8080/addUser");
        ws.onopen = function (event) {

        }
        ws.onmessage = function (event) {
            var userId = document.getElementById("userId");
            userId.textContent=event.data;
        }
        ws.onclose = function (event) {

        }
};

function sendMessageAddUser() {
    var adressDataSet ={
    adress: document.getElementById("adress").value
    };
    var phoneDataSet={
    number: document.getElementById("phoneNumber").value
    };
    var user = {
        name: document.getElementById("name").value,
        age: document.getElementById("age").value,
        phones: [phoneDataSet],
        adress: adressDataSet
    };
    var message = JSON.stringify(user);
    ws.send(message);
}