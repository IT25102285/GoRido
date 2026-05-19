//function payCash() {
//    const msg = document.getElementById('statusMsg');

//    document.getElementById('cardSection').classList.add('hidden');

//    msg.className = 'mt-6 rounded-2xl px-5 py-4 text-sm font-medium bg-green-100 text-green-700 block';

//    msg.innerHTML = '✓ Cash payment selected. Please pay the driver LKR 560.00 on arrival.';
//}

function payCash(btn) {

    var hireId = btn.getAttribute("data-hire-id");
    var totalFare = btn.getAttribute("data-total-fare");

    var form = new FormData();
    form.append("hireId", hireId);
    form.append("totalFare", totalFare);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {

        if (request.readyState == 4 && request.status == 200) {

            var response = request.responseText;

            if (response !== "success") {
                alert(response);
            } else {
                window.location.href = "/myBookings";
            }
        }
    };

    request.open("POST", "/hire/payCash", true);
    request.send(form);
}

function submitCard(btn){

    var hireId = btn.getAttribute("data-hire-id");
    var totalFare = btn.getAttribute("data-total-fare");

    var cardHolder = document.getElementById("cardName");
    var cardNum = document.getElementById("cardNum");
    var expiry = document.getElementById("expiry");
    var cvv = document.getElementById("cvv");

    var msg = document.getElementById("modelmsg");

    msg.classList.add("hidden");

    cardHolder.addEventListener("input", function(){
        cardHolder.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    cardNum.addEventListener("input", function(){
        cardNum.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    expiry.addEventListener("input", function(){
        expiry.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    cvv.addEventListener("input", function(){
        cvv.classList.remove("border-red-500");
        msg.classList.add("hidden");
    });

    if(cardHolder.value.trim() === ""){
        msg.innerText = "Cardholder name is required";
        msg.classList.remove("hidden");
        cardHolder.classList.add("border-red-500");
        return;
    }

    if(cardNum.value.replace(/\s/g,'').length < 16){
        msg.innerText = "Invalid card number";
        msg.classList.remove("hidden");
        cardNum.classList.add("border-red-500");
        return;
    }

    if(expiry.value.trim() === ""){
        msg.innerText = "Expiry date is required";
        msg.classList.remove("hidden");
        expiry.classList.add("border-red-500");
        return;
    }

    if(cvv.value.trim().length < 3){
        msg.innerText = "Invalid CVV";
        msg.classList.remove("hidden");
        cvv.classList.add("border-red-500");
        return;
    }

    var form = new FormData();
    form.append("hireId", hireId);
    form.append("totalFare", totalFare);
    form.append("cardHolder", cardHolder.value);
    form.append("cardNumber", cardNum.value.replace(/\s/g,''));
    form.append("expiry", expiry.value);
    form.append("cvv", cvv.value);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {

        if (request.readyState == 4 && request.status == 200) {

            var response = request.responseText;

            if(response.startsWith("redirect:")){

                window.location.href = response.replace("redirect:", "");

            } else {

                msg.innerText = response;
                msg.classList.remove("hidden");
            }
        }
    };

    request.open("POST", "/hire/payCard", true);
    request.send(form);
}