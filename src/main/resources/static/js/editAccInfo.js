$(document).ready(function () {
    console.log("Document is ready");
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
            lsdRing.removeClass('d-none'); // Hiển thị loading khi đang tải
        },
        success: function (response) {
            console.log("API Response: ", response); // Kiểm tra phản hồi của server

            if (response && response.data) {
                const account = response.data;
                account.cartTotal = account.cartTotal || 0; // Đảm bảo cartTotal luôn có giá trị

                console.log("Account Data: ", account); // Kiểm tra dữ liệu account

                // Cập nhật giá trị cho các trường input
                $('#username').val(account.username || '');
                $('#fullName').val(account.fullName || '');
                $('#email').val(account.email || '');
                $('#phoneNumber').val(account.phoneNumber || '');
                $('#dob').val(account.dateOfBirth || '');
                $('#address').val(account.address || '');

                // Cập nhật số lượng sản phẩm trong giỏ hàng ở header
                $('#countItemCartHeder').empty();
                $('#countItemCartHeder').append(`(${account.cartTotal})`);
            } else {
                console.error("Không có dữ liệu tài khoản");
                alert('Không thể tải dữ liệu tài khoản');
            }
        },
        complete: function () {
            lsdRing.addClass('d-none'); // Ẩn loading khi đã tải xong
        },
        error: function () {
            console.error("Lỗi khi gọi API");
            alert('Error loading account data');
            lsdRing.addClass('d-none');
        }
    });
}

function handleFormSubmit() {
    $('#editAccountForm').on('submit', function (e) {
        e.preventDefault(); // Ngừng hành động mặc định của form

        const formData = new FormData(this); // Tạo đối tượng FormData để gửi dữ liệu form
        // In dữ liệu từ FormData ra console để kiểm tra
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        $.ajax({
            url: '/update-account',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function () {
                lsdRing.removeClass('d-none'); // Hiển thị loading khi gửi form
            },
            success: function (response) {
                console.log("Update Response: ", response); // Kiểm tra phản hồi sau khi cập nhật
                if (response.success) {
                    alert('Account updated successfully');
                    loadData(); // Reload dữ liệu sau khi cập nhật thành công
                } else {
                    alert('Failed to update account: ' + response.message); // Thông báo lỗi nếu cập nhật thất bại
                }
            },
            complete: function () {
                lsdRing.addClass('d-none'); // Ẩn loading khi hoàn thành gửi form
            },
            error: function () {
                console.error("Lỗi khi gửi yêu cầu cập nhật");
                alert('Error updating account');
                lsdRing.addClass('d-none');
            }
        });
    });
}
