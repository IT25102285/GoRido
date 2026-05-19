function changeVehicleStatus(btn) {
    var id = btn.getAttribute("data-id");

    var form = new FormData();
    form.append("id", id);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {
            var response = request.responseText;
            if(response !== "success"){
                alert(response);
            }else{
                location.reload();
            }
        }
    };

    request.open("POST", "/manageVehicle/changeStatus", true);
    request.send(form);
}