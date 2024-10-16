// Variable to track whether the registration button is disabled
var registrationDisabled = false;

function attemptRegister() {
    if (registrationDisabled) {
        // Inform the user to wait
        document.getElementById("registerResult").textContent = "Please wait for 60 seconds before registering again.";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }

    var firstname = document.getElementById("rfirstname").value;
    var lastname = document.getElementById("rlastname").value;
    var email = document.getElementById("remail").value;
    var address = document.getElementById("raddress").value;
    var genderBox = document.getElementById("rgender");
    var gender = genderBox.options[genderBox.selectedIndex].value;
    var phonenumber = document.getElementById("rphonenumber").value;
    var password = document.getElementById("rpassword").value;
    var repeat_password = document.getElementById("rrepeat_password").value;

    // Check if user input firstname
    if (firstname.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your first name";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input lastname
    if (lastname.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your last name";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input gender
    if (gender.trim() === "") {
        document.getElementById("registerResult").textContent = "You must select your gender";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input email
    if (email.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your email";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input phonenumber
    if (phonenumber.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your phone number";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input address
    if (address.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your address";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input password
    if (password.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your password";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input repeat_password
    if (repeat_password.trim() === "") {
        document.getElementById("registerResult").textContent = "You must input your repeat password";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if password matches repeat_password
    if (password !== repeat_password) {
        document.getElementById("registerResult").textContent = "You password not match with your repeat password";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user's email matches regex for username@domain
    if (!email.match("^[A-Za-z0-9+_.-]+@(.+)$")) {
        document.getElementById("registerResult").textContent = "You email is invalid";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check if user's phonenumber matches regex for vietnamese phone number 
    const regexPhoneNumber = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
    if (!phonenumber.match(regexPhoneNumber)) {
        document.getElementById("registerResult").textContent = "You phone number is invalid";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }
    // Check valid gender
    if (gender !== "Male" && gender !== "Female" && gender !== "Others") {
        document.getElementById("registerResult").textContent = "You gender is invalid";
        document.getElementById("registerResult").style.color = "red";
        document.getElementById("registerResult").style.fontWeight = "bold";
        return;
    }

    // Create an AJAX request to check login
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var result = xhr.responseText;
            document.getElementById("registerResult").textContent = result;
            if (result === "success") {
                document.getElementById("registerResult").textContent = "Your email should receive an mail for verification";
                document.getElementById("registerResult").style.color = "green";
                document.getElementById("registerResult").style.fontWeight = "normal";
                // Disable the registration button for 60 seconds
                registrationDisabled = true;
                setTimeout(function () {
                    registrationDisabled = false; // Re-enable the button after 60 seconds
                }, 60000); // 60 seconds = 60000 milliseconds
            } else if (result === "email existed") {
                document.getElementById("registerResult").textContent = "Your email has been used";
                document.getElementById("registerResult").style.color = "red";
                document.getElementById("registerResult").style.fontWeight = "bold";
            } else {
                document.getElementById("registerResult").textContent = "You cannot register right now!!";
                document.getElementById("registerResult").style.color = "red";
                document.getElementById("registerResult").style.fontWeight = "bold";
            }
        }
    };

    xhr.open("POST", "register", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("rfirstname=" + firstname + "&rlastname=" + lastname + "&remail=" + email
            + "&rgender=" + gender + "&raddress=" + address + "&rphonenumber=" + phonenumber
            + "&rpassword=" + password);

}

function showRegisterPopup() {
    // Close login pop-up by default
    hideLoginPopup();
    // Open the register pop-up
    var registerPopup = document.getElementById('registerPopup');
    registerPopup.style.display = 'flex';

    // If user click outside the popup, it also close
    var overlay = document.querySelector('.register-popup');
    overlay.addEventListener('click', overlayRegisterClickHandler(overlay));
}

// Function to close popup if user click outside popup
function overlayRegisterClickHandler(overlay) {
    return function (event) {
        if (event.target === overlay) {
            hideRegisterPopup();
        }
    };
}

function hideRegisterPopup() {
    var overlay = document.querySelector('.register-popup');
    overlay.removeEventListener('click', overlayRegisterClickHandler(overlay));
    document.getElementById('registerPopup').style.display = 'none';
    document.getElementById("registerResult").textContent = "";
    document.getElementById("rfirstname").value = "";
    document.getElementById("rlastname").value = "";
    document.getElementById("remail").value = "";
    document.getElementById("raddress").value = "";
    document.querySelector(".genderBox").selectedIndex = 0;
    document.getElementById("rphonenumber").value = "";
    document.getElementById("rpassword").value = "";
    document.getElementById("rrepeat_password").value = "";
}