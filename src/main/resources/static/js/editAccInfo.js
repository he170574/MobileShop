$(document).ready(function () {
    $('#nav-content').find('.account-information').addClass('active');
    loadData();
    sendChangePasswordForm();
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
            if (!response || response.data === null) {
                window.location.href = '/home';
            } else {
                const account = response.data;
                $('#username1').val(account.username);
                $('#email1').val(account.email);
                $('#dob1').val(account.dateOfBirth);
                $('#fullName').val(account.fullName);
                $('#phoneNumber').val(account.phoneNumber);
                $('#address').val(account.address);
            }
        },
        complete: function () {
            lsdRing.addClass('d-none');
            updateAccountInformation();
        }
    });
}

function updateAccountInformation(){
    $('#submit-form').on('click', function (){
        const formData = {
            fullName: $('#fullName').val(),
            dateOfBirth: $('#dob1').val(),
            email: $('#email1').val(),
            username: $('#username1').val(),
            address: $('#address').val(),
            phoneNumber: $('#phoneNumber').val()
        };

        const fieldIds = ['fullName', 'dob1', 'email1', 'address', 'phoneNumber'];
        if (!checkNotEmpty(fieldIds)) {
            Swal.fire({
                title: "Update Fail",
                icon: "warning",
                text: "Please ensure all input fields are filled out.",
                confirmButtonText: "OK",
            });
            return;
        }

        if (!isValidEmail(formData.email)) {
            Swal.fire({
                title: "Update Fail",
                icon: "warning",
                text: "Invalid email address. Please enter a properly formatted email.",
                confirmButtonText: "OK",
            });
            $('#email1').addClass('border-danger');
            $('#email1').removeClass('border-dark-subtle');
            return;
        } else {
            $('#email1').removeClass('border-danger');
            $('#email1').addClass('border-dark-subtle');
        }

        // Proceed with AJAX request
        Swal.fire({
            title: "You sure want to update?",
            icon: "warning",
            confirmButtonText: "OK",
            showCancelButton: true,
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/update-account',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    beforeSend: function () {
                        lsdRing.removeClass('d-none');
                    },
                    success: function (response) {
                        Swal.fire({
                            title: "Update Success",
                            icon: "success",
                            text: response.message,
                            confirmButtonText: "OK",
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.reload();
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        Swal.fire({
                            title: "Update Fail",
                            icon: "error",
                            text: error,
                            confirmButtonText: "OK",
                        });
                    },
                    complete: function () {
                        lsdRing.addClass('d-none');
                    }
                });
            }
        });
    });
}

function sendChangePasswordForm(){
    $("#form-change-pass").click(function() {
        // Lấy giá trị từ các trường input
        var oldPassword = $("#old-pass").val();
        var newPassword = $("#new-pass").val();

        // Tạo một đối tượng chứa dữ liệu để gửi
        var data = {
            oldPassword: oldPassword,
            newPassword: newPassword
        };

        // Sử dụng phương thức jQuery AJAX để gửi dữ liệu đến controller
        $.ajax({
            type: "POST", // Hoặc "GET" tùy thuộc vào phương thức mà controller yêu cầu
            url: "/changePassword", // Đường dẫn tới controller
            contentType: 'application/json',
            data: JSON.stringify(data), // Dữ liệu cần gửi
            beforeSend:function (){
                lsdRing.removeClass('d-none');
            },
            success: function(response) {
                // Xử lý kết quả trả về từ controller nếu cần
                Swal.fire({
                    title: "Change Password Success",
                    icon: "success",
                    confirmButtonText: "OK",
                });
            },
            error: function(error) {
                // Xử lý lỗi nếu có
                Swal.fire({
                    title: "Change Password Fail",
                    icon: "error",
                    text: error.message,
                    confirmButtonText: "OK",
                });
            },
            complete: function (){
                lsdRing.addClass('d-none');
            }
        });
    });
}

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
