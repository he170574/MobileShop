$(document).ready(function () {
    // Switch to forgot password form
    $('#forgotPasswordLink').click(function (e) {
        e.preventDefault();
        $('#loginForm').hide();
        $('#forgotPasswordForm').show();
    });

    // Submit login form
    $('#loginForm').submit(function (e) {
        e.preventDefault();
        $.post('/login', $(this).serialize(), function (response) {
            if (response.success) {
                window.location.href = '/home';
            } else {
                alert('Invalid credentials');
            }
        });
    });

    // Submit register form
    $('#registerForm').submit(function (e) {
        e.preventDefault();
        $.post('/register', $(this).serialize(), function (response) {
            if (response.success) {
                alert('Registration successful! Please login.');
                $('#registerForm').trigger('reset'); // Clear the register form
                $('#loginModal').modal('hide');
            } else {
                alert(response.message);
            }
        });
    });

    // Submit forgot password form
    $('#forgotPasswordForm').submit(function (e) {
        e.preventDefault();
        $.post('/forgot-password', $(this).serialize(), function (response) {
            if (response.success) {
                alert('A new password has been sent to your email.');
                $('#forgotPasswordForm').trigger('reset');
                $('#forgotPasswordForm').hide();
                $('#loginForm').show();
            } else {
                alert(response.message);
            }
        });
    });
});
