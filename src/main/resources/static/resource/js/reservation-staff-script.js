/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
    var reservationIdInput = document.getElementById('reservationId');
    var customerNameInput = document.getElementById('customerName');
    var fromDateInput = document.getElementById('from');
    var toDateInput = document.getElementById('to');
    var sortInput = document.getElementById('sort');
    var statusInput = document.getElementById('status');

    reservationIdInput.addEventListener('input',function(){handleInputChange(1);} );
    customerNameInput.addEventListener('input', function(){handleInputChange(1);});
    fromDateInput.addEventListener('change', function(){handleInputChange(1);});
    toDateInput.addEventListener('change', function(){handleInputChange(1);});
    sortInput.addEventListener('change', function(){handleInputChange(1);});
    statusInput.addEventListener('change', function(){handleInputChange(1);});

    // Xử lý sự kiện khi người dùng nhấp vào nút phân trang
    var paginationButtons = document.querySelectorAll('.pagination-btn a');
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
                handleInputChange(page);
            }
        });
    });

    function handleInputChange(page = 1) {
        var reservationId = reservationIdInput.value;
        var customerName = customerNameInput.value;
        var fromDate = fromDateInput.value;
        var toDate = toDateInput.value;
        var sortBy = sortInput.value;
        var status = statusInput.value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'staff?event=reservation-of-staff&reservationId=' + encodeURIComponent(reservationId) +
                '&customerName=' + encodeURIComponent(customerName) +
                '&fromDate=' + encodeURIComponent(fromDate) +
                '&toDate=' + encodeURIComponent(toDate) +
                '&sortBy=' + encodeURIComponent(sortBy) +
                '&status=' + encodeURIComponent(status) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#reservations-list').innerHTML = xhr.responseText;
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

function loadReservation(event,page = 1) {
    event.preventDefault();
    var reservationIdInput = document.getElementById('reservationId');
    var customerNameInput = document.getElementById('customerName');
    var fromDateInput = document.getElementById('from');
    var toDateInput = document.getElementById('to');
    var sortInput = document.getElementById('sort');
    var statusInput = document.getElementById('status');

        var reservationId = reservationIdInput.value;
        var customerName = customerNameInput.value;
        var fromDate = fromDateInput.value;
        var toDate = toDateInput.value;
        var sortBy = sortInput.value;
        var status = statusInput.value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'staff?event=reservation-of-staff&reservationId=' + encodeURIComponent(reservationId) +
                '&customerName=' + encodeURIComponent(customerName) +
                '&fromDate=' + encodeURIComponent(fromDate) +
                '&toDate=' + encodeURIComponent(toDate) +
                '&sortBy=' + encodeURIComponent(sortBy) +
                '&status=' + encodeURIComponent(status) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#reservations-list').innerHTML = xhr.responseText;
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