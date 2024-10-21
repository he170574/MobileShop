/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



var nameInput = document.getElementById('nameUser');
var emailInput = document.getElementById('emailUser');
var mobileInput = document.getElementById('mobileUser');
var filterRole = document.getElementById('role');
var filterGender = document.getElementById('gender');
var filterStatus = document.getElementById('status');
var sortOption = document.getElementById('sort');
document.addEventListener('DOMContentLoaded', function () {


    nameInput.addEventListener('input', function () {
        handleInputChange(1);
    });
    emailInput.addEventListener('input', function () {
        handleInputChange(1);
    });
    mobileInput.addEventListener('input', function () {
        handleInputChange(1);
    });
    filterRole.addEventListener('change', function () {
        handleInputChange(1);
    });
    filterGender.addEventListener('change', function () {
        handleInputChange(1);
    });
    filterStatus.addEventListener('change', function () {
        handleInputChange(1);
    });
    sortOption.addEventListener('change', function () {
        handleInputChange(1);
    });

    // Xử lý sự kiện khi người dùng nhấp vào nút phân trang
    var paginationButtons = document.querySelectorAll('.pagination-btn a');
    paginationButtons.forEach(function (button) {
        button.addEventListener('click', function (e) {
            e.preventDefault();
            if (!this.classList.contains('active')) {
                document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                    if (paginationBtn.classList.contains('active')) {
                        paginationBtn.classList.remove('active');
                    }
                });
                this.classList.remove('inactive');
                this.classList.add('active');

                var page = this.dataset.page;
                handleInputChange(page);
            }
        });
    });

    function handleInputChange(page = 1) {
        let name = nameInput.value;
        let email = emailInput.value;
        let mobile = mobileInput.value;
        let roleFilter = filterRole.value;
        let genderFilter = filterGender.value;
        let statusFilter = filterStatus.value;
        let sortBy = sortOption.value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'user?action=render-user-by-admin&name=' + encodeURIComponent(name) +
                '&email=' + encodeURIComponent(email) +
                '&mobile=' + encodeURIComponent(mobile) +
                '&role=' + encodeURIComponent(roleFilter) +
                '&gender=' + encodeURIComponent(genderFilter) +
                '&status=' + encodeURIComponent(statusFilter) +
                '&sortBy=' + encodeURIComponent(sortBy) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#user-list').innerHTML = xhr.responseText;
                document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
            } else {
                console.error('Error:', xhr.status);
            }
        };

        xhr.onerror = function () {
            console.error('Error:', xhr.status);
        };

        xhr.send();
    }


});

function pagination(event, page) {
    event.preventDefault();
    let name = nameInput.value;
    let email = emailInput.value;
    let mobile = mobileInput.value;
    let roleFilter = filterRole.value;
    let genderFilter = filterGender.value;
    let statusFilter = filterStatus.value;
    let sortBy = sortOption.value;

    // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'user?action=render-user-by-admin&name=' + encodeURIComponent(name) +
            '&email=' + encodeURIComponent(email) +
            '&mobile=' + encodeURIComponent(mobile) +
            '&role=' + encodeURIComponent(roleFilter) +
            '&gender=' + encodeURIComponent(genderFilter) +
            '&status=' + encodeURIComponent(statusFilter) +
            '&sortBy=' + encodeURIComponent(sortBy) +
            '&page=' + encodeURIComponent(page));

    xhr.onload = function () {
        if (xhr.status === 200) {
            // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
            document.querySelector('#user-list').innerHTML = xhr.responseText;
            document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
        } else {
            console.error('Error:', xhr.status);
        }
    };

    xhr.onerror = function () {
        console.error('Error:', xhr.status);
    };

    xhr.send();
}