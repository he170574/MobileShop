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
            console.log(response); // Kiểm tra phản hồi của server

            if (!response || response.data === null) {
                window.location.href = '/home';
            } else {
                const account = response.data;
                console.log(account); // Kiểm tra dữ liệu account

                $('#username1').val(account.username || '');
                $('#email1').val(account.email || '');
                $('#dob1').val(account.dob || '');
                $('#fullName').val(account.fullName || '');
                $('#phoneNumber').val(account.phoneNumber || '');
                $('#address').val(account.address || '');
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
