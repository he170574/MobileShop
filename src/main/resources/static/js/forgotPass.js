$(document).ready(function() {
    sendForm();
});
let lsdRing = $('.lsd-ring-container');
function sendForm(){
    $('#form-forgot').on('click', function (e) {
        const email = $('#email-forgot-password').val().trim().toLowerCase(); // Ensure the email is lowercase and trimmed

        $.ajax({
            url: '/forgot-pass',
            data: JSON.stringify({ email: email }), // Make sure this sends the correct JSON
            type: 'POST',
            contentType: 'application/json',
            beforeSend: function () {
                lsdRing.removeClass('d-none');
            },
            success: function (response) {
                Swal.fire({
                    title: "Get Password Success",
                    icon: "success",
                    text: "Please check your mail for the new password",
                    confirmButtonText: "OK",
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/home';
                    }
                });
            },
            error: function (xhr, status, error) {
                const response = JSON.parse(xhr.responseText);
                Swal.fire({
                    title: "Get Password Fail",
                    icon: "error",
                    text: response.message || "The email does not exist",
                    confirmButtonText: "OK",
                });
            },
            complete: function () {
                lsdRing.addClass('d-none');
            }
        });
    });

}