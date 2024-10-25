$(document).ready(function () {
    $('#nav-content').find('.account-information').addClass('active');
    loadData();
    handleFormSubmit();
});

let lsdRing = $('.lsd-ring-container');

function loadData() {
    $.ajax({
        url: '/get-account-using',
        type: 'GET',
        beforeSend: function () {
            lsdRing.removeClass('d-none');
        },
        success: function (response) {
            if (response.data === null) { // Not logged in
                window.location.href = '/home';
            } else { // Logged in
                const account = response.data;
                if (account.image !== null) {
                    $('#preview-img').attr('src', account.image);
                }
                $('#username1').val(account.username);
                $('#fullName').val(account.fullName);
                $('#email1').val(account.email);
                $('#phoneNumber').val(account.phoneNumber);
                $('#dob1').val(account.dateOfBirth);
                $('#address').val(account.address);
            }
        },
        complete: function () {
            lsdRing.addClass('d-none');
        }
    });
}


function handleFormSubmit() {
    $('#editAccountForm').on('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);

        $.ajax({
            url: '/update-account',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function () {
                lsdRing.removeClass('d-none');
            },
            success: function (response) {
                if (response.success) {
                    alert('Account updated successfully');
                    loadData(); // Reload the updated data
                } else {
                    alert('Failed to update account: ' + response.message);
                }
            },
            complete: function () {
                lsdRing.addClass('d-none');
            }
        });
    });
}
