function changeDriverStatus(btn) {
    var email = btn.getAttribute("data-email");

    var form = new FormData();
    form.append("email", email);

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

    request.open("POST", "/manageDriver/changeStatus", true);
    request.send(form);
}