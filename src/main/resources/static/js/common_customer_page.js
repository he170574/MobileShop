$(document).ready(function () {
    loadAccountData();

    $('.login-btn').on('click', function () {
        $('#registerModal').modal('hide');
    });
    $('#register-btn').on('click', function () {
        $('#loginModal').modal('hide');
    });
});

let lsdRing = $('.lsd-ring-container');

function checkNotEmpty(fieldIds) {
    let allFilled = true;
    fieldIds.forEach(function (fieldId) {
        const valueTag = $('#' + fieldId);
        const value = valueTag.val();
        if (!value) {
            allFilled = false;
            valueTag.addClass('border-danger')
            valueTag.removeClass('border-dark-subtle')
        } else {
            valueTag.removeClass('border-danger')
            valueTag.addClass('border-dark-subtle')
        }
    });
    return allFilled;
}

function isValidEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailRegex.test(email);
}

function register() {
    $('#submit-form-register').on('click', function (e) {

        const formData = {
            username: $('#username-register').val(),
            password: $('#password-register').val(),
            fullName: $('#full-name').val(),
            gender: $('input[name="gender"]:checked').val(),
            dateOfBirth: $('#dob').val(),
            email: $('#email').val(),
            phoneNumber: $('#phone-number').val()
        };
        const fieldIds = ['username-register', 'password-register', 'confirm-password', 'full-name', 'dob', 'email', 'phone-number']; // Add all your input field IDs here
        const confirmPasswordTag = $('#confirm-password');


        if (!checkNotEmpty(fieldIds)) {
            Swal.fire({
                title: "Register Fail",
                icon: "warning",
                text: "Please ensure all input fields are filled out.",
                confirmButtonText: "OK",
            });
            return;
        }
        //Check Password
        if (confirmPasswordTag.val() !== formData.password) {
            confirmPasswordTag.addClass('border-danger')
            confirmPasswordTag.removeClass('border-dark-subtle')
            Swal.fire({
                title: "Register Fail",
                icon: "warning",
                text: "Passwords do not match. Please try again",
                confirmButtonText: "OK",
            });
            return;
        } else {
            confirmPasswordTag.removeClass('border-danger')
            confirmPasswordTag.addClass('border-dark-subtle')
        }
        //Check Email
        if (!isValidEmail(formData.email)) {
            Swal.fire({
                title: "Register Fail",
                icon: "warning",
                text: "Invalid email address. Please enter a properly formatted email.",
                confirmButtonText: "OK",
            });
            $('#email').addClass('border-danger')
            $('#email').removeClass('border-dark-subtle')
            return;
        } else {
            $('#email').removeClass('border-danger')
            $('#email').addClass('border-dark-subtle')
        }

        //Check Done => Send data
        let lsdRing = $('.lsd-ring-container');
        $.ajax({
            url: '/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            beforeSend: function () {
                console.log(JSON.stringify(formData))
                lsdRing.removeClass('d-none');
            },
            success: function (response) {
                // Code to run on successful response
                Swal.fire({
                    title: "Register Success",
                    icon: "success",
                    text: "Welcome To MobileShop",
                    confirmButtonText: "Let's Go",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.reload();
                    }
                });
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    title: "Register Fail",
                    icon: "warning",
                    text: "MB sr u",
                    confirmButtonText: "Let's Go",
                });
                // Code to run if the request fails
                console.log('Error:', error);
            },
            complete: function () {
                lsdRing.addClass('d-none');
            }
        });


    });

}

function login() {
    // Submit form
    $('#send-form-login').on('click', function (event) {
        let isValidForm = true;
        $('input[name="username"], input[name="password"]').each(function () {
            if ($(this).val() === '') {
                isValidForm = false;
                $(this).addClass('border-danger').removeClass('border-dark-subtle');
            } else {
                $(this).removeClass('border-danger').addClass('border-dark-subtle');
            }
        });
        if (!isValidForm) {
            return;
        }
        // Ngăn submit mặc định
        event.preventDefault();
        // Lấy data
        const formData = {
            'username': $('input[name=username]').val(),
            'password': $('input[name=password]').val()
        };
        // Ajax request
        $.ajax({
            type: 'POST',
            url: '/login',
            data: formData,
            success: function (response) {
                // Xử lý khi đăng nhập thành công
                loadAccountData();
                window.location.href = '/home';
            },
            error: function (error) {
                Swal.fire({
                    title: "Login Fail",
                    icon: "error",
                    text: "Invalid Username Or Password.",
                    confirmButtonText: "OK",
                });
            }
        });

    });
}

function loadAccountData() {
    let lsdRing = $('.lsd-ring-container');
    $.ajax({
        url: '/get-account-using',
        type: 'GET',
        beforeSend: function () {
            lsdRing.removeClass('d-none');
        },
        success: function (response) {
            if (response.data === null) {    // NO LOGIN
                $('#accountBtn').css('display', 'none');
                register();
                login();
            } else {                          //Logged

                // $('#account-img').attr('src', account.image);
                $('#logout-btn').css('display', 'block');
                $('#login-btn').css('display', 'none');
                $('#accountBtn').css('display', 'block');

                if (response.data.role === 'ROLE_ADMIN' || response.data.role === 'ROLE_STAFF') {
                    $('#manage-btn').css('display', 'block');
                }
            }
        },
        error: function (xhr, status, error) {
            console.log('Error:', error);
            register();
            login();
        },
        complete: function (xhr, status) {
            lsdRing.addClass('d-none');
        }
    });


}

