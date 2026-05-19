function adminSignin(event){

    event.preventDefault();

    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var msg = document.getElementById("msg");

    msg.classList.add("hidden");

    email.addEventListener("input", function(){
        email.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    password.addEventListener("input", function(){
        password.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.value.trim() == "") {
        msg.innerText = "Email is required";
        msg.classList.remove("hidden");
        email.classList.add("border-red-500");
        return;
    }

    if (!pattern.test(email.value)) {
        msg.innerText = "Invalid email, Enter correctly";
        msg.classList.remove("hidden");
        email.classList.add("border-red-500");
        return;
    }

    if (password.value == "") {
        msg.innerText = "Password is required";
        msg.classList.remove("hidden");
        password.classList.add("border-red-500");
        return;
    }

    var form = new FormData();
    form.append("email", email.value);
    form.append("password", password.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function(){
        if (request.readyState == 4 && request.status == 200){

            var response = request.responseText;
            if(response !== "success"){
                msg.classList.remove("hidden");
                msg.innerHTML = response;
            }else{
                msg.classList.add("hidden");
                window.location.href = "/adminDashboard";
            }
        }
    };

    request.open("POST", "/adminSignin", true);
    request.send(form);
}