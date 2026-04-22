function loadGender() {

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {

        if (request.readyState == 4 && request.status == 200) {

            var genders = request.responseText.split(",");
            var genderSelect = document.getElementById("gender");

            genderSelect.innerHTML = '<option value="" selected>Select gender</option>';

            for (var i = 0; i < genders.length; i++) {

                if (genders[i] === "") continue;

                var data = genders[i].split(":");

                var opt = document.createElement("option");
                opt.value = data[0];
                opt.innerHTML = data[1];

                genderSelect.appendChild(opt);
            }
        }
    };

    request.open("GET", "/signup/options", true);
    request.send();
}

function signup(event) {

    event.preventDefault();

    var fname = document.getElementById("fname");
    var lname = document.getElementById("lname");
    var email = document.getElementById("email");
    var mobile = document.getElementById("mobile");
    var gender = document.getElementById("gender");
    var password = document.getElementById("password");
    var cpassword = document.getElementById("cpassword");
    var error_msg = document.getElementById("msg");

    error_msg.classList.add("hidden");

    fname.addEventListener("input", function () {
        fname.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    lname.addEventListener("input", function () {
        lname.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    email.addEventListener("input", function () {
        email.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    mobile.addEventListener("input", function () {
        mobile.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    gender.addEventListener("input", function(){
        gender.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    })

    password.addEventListener("input", function () {
        password.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    cpassword.addEventListener("input", function () {
        cpassword.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    var msg = document.getElementById("msg");
    var msgdiv = document.getElementById("msgdiv");

    if (fname.value.trim() == "") {
        error_msg.innerText = "First name is required";
        error_msg.classList.remove("hidden");
        fname.classList.add("border-red-500");
        return
    }

    var namepattern = /^[A-Za-z]+$/;

    if (!namepattern.test(fname.value)) {
        error_msg.innerText = "First name must contain only letters";
        error_msg.classList.remove("hidden");
        fname.classList.add("border-red-500");
        return;
    }

    if (lname.value.trim() == "") {
        error_msg.innerText = "Last name is required";
        error_msg.classList.remove("hidden");
        lname.classList.add("border", "border-red-500");
        return;
    }

    if (!namepattern.test(lname.value)) {
        error_msg.innerText = "Last name must contain only letters";
        error_msg.classList.remove("hidden");
        lname.classList.add("border-red-500");
        return;
    }

    if (email.value.trim() == "") {
        error_msg.innerText = "Email is required";
        error_msg.classList.remove("hidden");
        email.classList.add("border-red-500");
        return;
    }

    if (!pattern.test(email.value)) {
        error_msg.innerText = "Invalid email, Enter correctly";
        error_msg.classList.remove("hidden");
        email.classList.add("border-red-500");
        return;
    }

    if (mobile.value.trim() == "") {
        error_msg.innerText = "Mobile number is required";
        error_msg.classList.remove("hidden");
        mobile.classList.add("border-red-500");
        return;
    }

    var mobilepattern = /^[0-9]+$/;

    if (!mobilepattern.test(mobile.value)) {
        error_msg.innerText = "Mobile number only have digits";
        error_msg.classList.remove("hidden");
        mobile.classList.add("border-red-500");
        return;
    }

    var mobileValue = mobile.value.trim();

    if (mobileValue.length !== 10) {
        error_msg.innerText = "Mobile number must have 10 digits";
        error_msg.classList.remove("hidden");
        return;
    }

    if (!gender.value) {
        msg.innerText = "Gender is required";
        msg.classList.remove("hidden");
        gender.classList.add("border", "border-red-500");
        return;
    }

    if (password.value == "") {
        error_msg.innerText = "Password is required";
        error_msg.classList.remove("hidden");
        password.classList.add("border-red-500");
        return;
    }

    if (password.value.length < 6) {
        error_msg.innerText = "Password must be at least 6 characters";
        error_msg.classList.remove("hidden");
        password.classList.add("border-red-500");
        return;
    }

    if (cpassword.value == "") {
        error_msg.innerText = "Confirm your Password";
        error_msg.classList.remove("hidden");
        cpassword.classList.add("border-red-500");
        return;
    }

    if(password.value !== cpassword.value){
        error_msg.innerText = "Passwords must be equal";
        error_msg.classList.remove("hidden");
        cpassword.classList.add("border-red-500");
        return;
    }

    var form = new FormData();
    form.append("first_name", fname.value);
    form.append("last_name", lname.value);
    form.append("email", email.value);
    form.append("password", password.value);
    form.append("mobile_number", mobile.value);
    form.append("gender_id", gender.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {

            var response = request.responseText;

            if(response !== "success"){
                error_msg.innerText = response;
                error_msg.classList.remove("hidden");
            }else{
                error_msg.classList.add("hidden");
                window.location.href = "/signin";
            }
        }
    }

    request.open("POST", "/signup", true);
    request.send(form);
}

function signin(event){

    event.preventDefault();

    var email = document.getElementById("login_email");
    var password = document.getElementById("login_password");
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
                window.location.href = "/driverregi";
            }
        }
    };

    request.open("POST", "/signin", true);
    request.send(form);
}

function openModal(){
    var modal = document.getElementById("reset-modal");
    var box = document.getElementById("modal-box");

    modal.classList.remove("hidden");

    setTimeout(() => {
        box.classList.remove("opacity-0", "scale-95");
        box.classList.add("opacity-100", "scale-100");
    }, 10);
}

function closeModal(){
    var modal = document.getElementById("reset-modal");
    var box = document.getElementById("modal-box");

    box.classList.remove("opacity-100", "scale-100");
    box.classList.add("opacity-0", "scale-95");

    setTimeout(() => {
        modal.classList.add("hidden");
    }, 300);
}

function sendCode(){
    var email = document.getElementById("reset_email");
    var modelmsg = document.getElementById("modelmsg");
    var sendcodemsg = document.getElementById("sendcodemsg");

    modelmsg.classList.add("hidden");
    modelmsg.innerText = "";

    email.addEventListener("input", function () {
        email.classList.remove("border-red-500");
        newPassword.classList.add("border-transparent");
        modelmsg.classList.add("hidden");
        modelmsg.innerText = "";
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.value.trim() == "") {
        email.classList.remove("border-transparent");
        email.classList.add("border", "border-red-500");
        return;
    }

    if (!pattern.test(email.value)) {
        modelmsg.innerText = "Invalid email, Enter correctly";
        modelmsg.classList.remove("hidden");
        return;
    }

    var form = new FormData();
    form.append("email", email.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function(){
        if (request.readyState == 4 && request.status == 200){
        var response = request.responseText;

            if (response == "success"){
                sendcodemsg.innerText = "Please check your email-inbox";
                sendcodemsg.classList.remove("hidden");

                setTimeout(function () {
                    sendcodemsg.classList.add("hidden");
                }, 5000);
            }else {
                modelmsg.innerText = response;
                modelmsg.classList.remove("hidden");
            }
        }
    }

    request.open("POST", "/send_code", true);
    request.send(form);
}

function savePassword(){
    var email = document.getElementById("reset_email");
    var code = document.getElementById("code");
    var newPassword = document.getElementById("newpassword");
    var confirmPassword = document.getElementById("confirmpassword");

    var modelmsg = document.getElementById("modelmsg");

    modelmsg.classList.add("hidden");
    modelmsg.innerText = "";

    newPassword.classList.remove("border-red-500");
    confirmPassword.classList.remove("border-red-500");

    email.addEventListener("input", function () {
        email.classList.remove("border-red-500");
        newPassword.classList.add("border-transparent");
    });

    code.addEventListener("input", function () {
        code.classList.remove("border-red-500");
    });

    newPassword.addEventListener("input", function(){
        newPassword.classList.remove("border-red-500");
        newPassword.classList.add("border-transparent");
    });

    confirmPassword.addEventListener("input", function(){
        confirmPassword.classList.remove("border-red-500");
        confirmPassword.classList.add("border-transparent");
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email.value.trim() == "") {
        email.classList.remove("border-transparent");
        email.classList.add("border", "border-red-500");
        return;
    }

    if (!pattern.test(email.value)) {
        modelmsg.innerText = "Invalid email, Enter correctly";
        modelmsg.classList.remove("hidden");
        return;
    }

    if (newPassword.value.trim() === ""){
        newPassword.classList.remove("border-transparent");
        newPassword.classList.add("border", "border-red-500");
        return;
    }

    if (code.value.trim() == ""){
        code.classList.remove("border-transparent");
        code.classList.add("border", "border-red-500");
        return
    }

    if (confirmPassword.value.trim() === ""){
        confirmPassword.classList.remove("border-transparent");
        confirmPassword.classList.add("border-red-500");
        return;
    }

    if (newPassword.value.length < 6){
        modelmsg.innerText = "Password must be at least 6 characters";
        modelmsg.classList.remove("hidden");
        return;
    }

    if (newPassword.value !== confirmPassword.value){
        modelmsg.innerText = "Passwords do not match";
        modelmsg.classList.remove("hidden");
        return;
    }

    var form = new FormData();
    form.append("email", email.value);
    form.append("code", code.value);
    form.append("newPassword", newPassword.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function(){
        if (request.readyState == 4 && request.status == 200){

            var response = request.responseText;

            if (response === "success"){
                closeModal();
            } else {
                modelmsg.innerText = response;
                modelmsg.classList.remove("hidden");
            }
        }
    };

    request.open("POST", "/reset_password", true);
    request.send(form);
}

function loadUser() {

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {

        if (request.readyState == 4 && request.status == 200) {

            var data = request.responseText.split("|");

            document.getElementById("email").value = data[0];
            document.getElementById("mobile").value = data[1];
        }
    };

    request.open("GET", "/driver/loadUser", true);
    request.send();
}

function loadDriverOptions(){
    var colorSelect = document.getElementById("colour");
    var typeSelect = document.getElementById("vehicle_type");

    var request = new XMLHttpRequest();

    request.onreadystatechange = function(){

        if(request.readyState == 4 && request.status == 200){

            var data = request.responseText.split("|");
            var type = data[0];
            var color = data[1];

            var types = type.split(",");
            var colors = color.split(",");

            typeSelect.innerHTML = '<option disabled value="" selected>Select type</option>';
            colorSelect.innerHTML = '<option disabled value="" selected>Select colour</option>';

            for (var i = 0; i < types.length; i++){

                if (types[i] === "") continue;

                var typeData = types[i].split(":");

                var opt = document.createElement("option");

                opt.value = typeData[0];
                opt.innerHTML = typeData[1];

                typeSelect.appendChild(opt);
            }

            for (var i = 0; i < colors.length; i++){

                if (colors[i] === "") continue;

                var colorData = colors[i].split(":");

                var opt = document.createElement("option");

                opt.value = colorData[0];
                opt.innerHTML = colorData[1];

                colorSelect.appendChild(opt);
            }
        }
    }

    request.open("GET", "/driver/loadDriverOptions", true);
    request.send();
}

document.getElementById("vehicle_type").addEventListener("change", function () {
    let typeId = this.value;
    loadBrands(typeId);
});

function loadBrands(typeId){

    var brandSelect = document.getElementById("vehicle_brand");

    brandSelect.disabled = false;
    brandSelect.innerHTML = '<option disabled selected>Loading...</option>';

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {

            var brands = request.responseText.split(",");

            brandSelect.innerHTML = '<option disabled selected>Select brand</option>';

            for (var i = 0; i < brands.length; i++) {

                if (brands[i] === "") continue;

                var data = brands[i].split(":");

                var opt = document.createElement("option");
                opt.value = data[0];
                opt.innerHTML = data[1];

                brandSelect.appendChild(opt);
            }
        }
    };

    request.open("GET", "/driver/loadBrands?typeId=" + typeId, true);
    request.send();
}