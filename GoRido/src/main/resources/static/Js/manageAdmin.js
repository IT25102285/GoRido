function openAdminModal(btn) {

    var fname = btn.getAttribute("data-first");
    var lname = btn.getAttribute("data-last");
    var email = btn.getAttribute("data-email");
    var password = btn.getAttribute("data-password");

        const modal = document.getElementById("admin-modal");
        const box = document.getElementById("admin-box");

        modal.classList.remove("hidden");

        setTimeout(() => {
            box.classList.remove("opacity-0", "scale-95");
            box.classList.add("opacity-100", "scale-100");
        }, 50);

        document.getElementById("oldEmail").value = email;
        document.getElementById("newEmail").value = email;
        document.getElementById("newPassword").value = password;
        document.getElementById("newFname").value = fname;
        document.getElementById("newLname").value = lname;
    }

    function closeAdminModal() {
        const modal = document.getElementById("admin-modal");
        const box = document.getElementById("admin-box");

        box.classList.add("opacity-0", "scale-95");

        setTimeout(() => {
            modal.classList.add("hidden");
        }, 200);
    }

    function openModalUpdate(){
    var modal = document.getElementById("new-admin-model");
    var box = document.getElementById("new-admin-box");

    modal.classList.remove("hidden");

    setTimeout(() => {
        box.classList.remove("opacity-0", "scale-95");
        box.classList.add("opacity-100", "scale-100");
    }, 10);
}

function closeModalUpdate(){
    var modal = document.getElementById("new-admin-model");
    var box = document.getElementById("new-admin-box");

    box.classList.remove("opacity-100", "scale-100");
    box.classList.add("opacity-0", "scale-95");

    setTimeout(() => {
        modal.classList.add("hidden");
    }, 300);

    window.location.reload();
}

function addAdmin() {
    var fname = document.getElementById("fname");
    var lname = document.getElementById("lname");
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var error_msg = document.getElementById("modelmsg2");

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

    password.addEventListener("input", function () {
        password.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

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

    var form = new FormData();
    form.append("first_name", fname.value);
    form.append("last_name", lname.value);
    form.append("email", email.value);
    form.append("password", password.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {

            var response = request.responseText;

            if(response !== "success"){
                error_msg.innerText = response;
                error_msg.classList.remove("hidden");
            }else{
                error_msg.classList.add("hidden");
                window.location.reload();
            }
        }
    }

    request.open("POST", "/addAdmin", true);
    request.send(form);
}

function changeStatus(btn) {
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

    request.open("POST", "/manageAdmin/changeStatus", true);
    request.send(form);
}

function deleteAdmin(btn) {
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

    request.open("POST", "/manageAdmin/delete", true);
    request.send(form);
}

function updateAdmin() {
    var fname = document.getElementById("newFname");
    var lname = document.getElementById("newLname");
    var oldEmail = document.getElementById("oldEmail");
    var email = document.getElementById("newEmail");
    var password = document.getElementById("newPassword");
    var error_msg = document.getElementById("modelmsg");

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

    password.addEventListener("input", function () {
        mobile.classList.remove("border-red-500");
        error_msg.classList.add("hidden");
    });

    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

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

    var form = new FormData();
    form.append("first_name", fname.value);
    form.append("last_name", lname.value);
    form.append("oldEmail", oldEmail.value);
    form.append("email", email.value);
    form.append("password", password.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {

            var response = request.responseText;

            if(response !== "success"){
                error_msg.innerText = response;
                error_msg.classList.remove("hidden");
            }else{
                error_msg.classList.add("hidden");
                window.location.reload();
            }
        }
    }

    request.open("POST", "/updateAdmin", true);
    request.send(form);
}