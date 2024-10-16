var loginAttempts = 0; // Track login attempts
var loginTimeout; // Store login timeout reference

function attemptLogin() {
    var email = document.getElementById("lemail").value;
    var password = document.getElementById("lpassword").value;

    // Check if user input email
    if (email.trim() === "") {
        document.getElementById("loginResult").textContent = "You should input your email";
        document.getElementById("loginResult").style.color = "red";
        document.getElementById("loginResult").style.fontWeight = "bold";
        return;
    }
    // Check if user input password
    if (password.trim() === "") {
        document.getElementById("loginResult").textContent = "You should input your password";
        document.getElementById("loginResult").style.color = "red";
        document.getElementById("loginResult").style.fontWeight = "bold";
        return;
    }
    // Check user's mail valid 
    if (!email.match("^[A-Za-z0-9+_.-]+@(.+)$")) {
        document.getElementById("loginResult").textContent = "Your email is invalid";
        document.getElementById("loginResult").style.color = "red";
        document.getElementById("loginResult").style.fontWeight = "bold";
        return;
    }

    // Check if the user has exceeded login attempts exactly 3 times
    if (loginAttempts === 3) {
        // Display a message and disable the login button
        document.getElementById("loginResult").textContent = "Login attempts exceeded. Please try again later.";
        document.getElementById("loginResult").style.color = "red";
        document.getElementById("loginResult").style.fontWeight = "bold";
        document.getElementById("Login-button").disabled = true;

        // Set a timeout to re-enable the login button after 30 seconds
        loginTimeout = setTimeout(resetLoginAttempts, 30000); // 30,000 milliseconds = 30 seconds
    } else if (loginAttempts > 3) {
        // Display a message and disable the login button
        document.getElementById("loginResult").textContent = "Login attempts exceeded. Please try again 30 seconds later.";
        document.getElementById("loginResult").style.color = "red";
        document.getElementById("loginResult").style.fontWeight = "bold";
        document.getElementById("Login-button").disabled = true;
    } else {
        // Create an AJAX request to check login
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var result = xhr.responseText;
                if (result === "reload") {
                    // Prevent user login into 2 account at the same time in one device
                    resetLoginAttempts();
                    location.reload();
                } else if (result === "success") {
                    // Display the messasge
                    document.getElementById("loginResult").textContent = "Login successful!";
                    document.getElementById("loginResult").style.color = "gold";
                    document.getElementById("loginResult").style.fontWeight = "normal";
                    hideLoginPopup();
                    location.href = "home";
                    // Reset login attempts if login is successful
                    resetLoginAttempts();
                } else if (result === "wronginformation") {
                    // Display the messasge
                    document.getElementById("loginResult").textContent = "Wrong email or password. You have " + (3 - loginAttempts) + " times to wrong";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    // Increment login attempts on failed login
                    loginAttempts++;
                } else if (result === "inactive"){
                    // Display the messasge
                    document.getElementById("loginResult").textContent = "Your account is not active. Please click in activate link";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                } else {
                    
                }
            }
        };

        xhr.open("POST", "login", true); // specify whether the request should be asynchronous or not
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); // Determines the data type of the content sent in the HTTP request.
        xhr.send("lemail=" + email + "&lpassword=" + password);
    }
}

function showLoginPopup() {
    // Close register pop-up by default
    hideRegisterPopup();
    // Open the login pop-up
    var loginPopup = document.getElementById('loginPopup');
    loginPopup.style.display = 'flex';

    // If user click outside the popup, it also close
    var overlay = document.querySelector('.login-popup');
    overlay.addEventListener('click', overlayLoginClickHandler(overlay));
}


// Function to close popup if user click outside popup
function overlayLoginClickHandler(overlay) {
    return function (event) {
        if (event.target === overlay) {
            hideLoginPopup();
        }
    };
}

function hideLoginPopup() {
    // Remove overlay click event listener
    var overlay = document.querySelector('.login-popup');
    overlay.removeEventListener('click', overlayLoginClickHandler(overlay));
    // Remove the announcement
    document.getElementById("loginResult").textContent = "";
    // Remove the email value
    document.getElementById("lemail").value = "";
    // Remove the password value
    document.getElementById("lpassword").value = "";
    // Hide the popup
    document.getElementById('loginPopup').style.display = 'none';
}


function resetLoginAttempts() {
    // Reset login attempts, re-enable login button, and clear timeout
    loginAttempts = 0;
    document.getElementById("Login-button").disabled = false;
    clearTimeout(loginTimeout);
}

