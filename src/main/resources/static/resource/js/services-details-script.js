/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    // Xử lý sự kiện khi người dùng thay đổi giá trị tìm kiếm, loại dịch vụ hoặc trang
    var searchInputs = document.querySelectorAll(' select[name="serviceType"], select[name="staff"]');
    searchInputs.forEach(function (input) {
        input.addEventListener('change', function () {
            loadServices(1); // Gọi hàm loadServices() để tải danh sách dịch vụ
        });
    });
    document.querySelector('input[name="serviceTitle"]').addEventListener('input', function () {
            loadServices(1); // Gọi hàm loadServices() để tải danh sách dịch vụ
        });

    // Xử lý sự kiện khi người dùng nhấp vào nút phân trang
    var paginationButtons = document.querySelectorAll('.pagination-btn');
    paginationButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            if (!this.classList.contains('active')) {
                document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                    if (paginationBtn.classList.contains('active')) {
                        paginationBtn.classList.remove('active');
                    }
                });
                this.classList.remove('inactive');
                this.classList.add('active');

                var page = this.dataset.page;
                loadServices(page); // Gọi hàm loadServices() để tải danh sách dịch vụ của trang được chọn
            }
        });
    });

    // Hàm tải danh sách dịch vụ
    function loadServices(page = 1) {
        var serviceTitle = document.querySelector('input[name="serviceTitle"]').value;
        var serviceType = document.querySelector('select[name="serviceType"]').value;
        var staff = document.querySelector('select[name="staff"]').value.trim();
        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'service?event=service-list-in-details&serviceTitle=' + encodeURIComponent(serviceTitle) +
                '&serviceType=' + encodeURIComponent(serviceType) +
                '&staff=' + encodeURIComponent(staff) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#service-list').innerHTML = xhr.responseText;
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
    function loadPageServices(page) {
        var serviceTitle = document.querySelector('input[name="serviceTitle"]').value;
        var serviceType = document.querySelector('select[name="serviceType"]').value;
        var staff = document.querySelector('select[name="staff"]').value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'service?event=service-list-in-details&serviceTitle=' + encodeURIComponent(serviceTitle) +
                '&serviceType=' + encodeURIComponent(serviceType) +
                '&staff=' + encodeURIComponent(staff) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#service-list').innerHTML = xhr.responseText;
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
    