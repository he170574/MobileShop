/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



document.addEventListener('DOMContentLoaded', function () {
    var patientNameInput = document.getElementById('patientName');
    var fromDateInput = document.getElementById('from');
    var toDateInput = document.getElementById('to');
    var serviceInput = document.getElementById('service');

    patientNameInput.addEventListener('input',function(){handleInputChange(1);} );
    fromDateInput.addEventListener('change', function(){handleInputChange(1);});
    toDateInput.addEventListener('change', function(){handleInputChange(1);});
    serviceInput.addEventListener('change', function(){handleInputChange(1);});

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
        var patientName = patientNameInput.value;
        var fromDate = fromDateInput.value;
        var toDate = toDateInput.value;
        var service = serviceInput.value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'medical-examination?event=get-medical-examination-page-manager&patientName=' + encodeURIComponent(patientName)+
                '&fromDate=' + encodeURIComponent(fromDate) +
                '&toDate=' + encodeURIComponent(toDate) +
                '&service=' + encodeURIComponent(service) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#medical-list').innerHTML = xhr.responseText;
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

function handlePageChangex( page = 1) {
        var patientName = document.getElementById('patientName').value;
        var fromDate = document.getElementById('from').value;
        var toDate = document.getElementById('to').value;
        var service = document.getElementById('service').value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'medical-examination?event=get-medical-examination-page-manager&patientName=' + encodeURIComponent(patientName)+
                '&fromDate=' + encodeURIComponent(fromDate) +
                '&toDate=' + encodeURIComponent(toDate) +
                '&service=' + encodeURIComponent(service) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#medical-list').innerHTML = xhr.responseText;
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

function handlePageChange(event, page = 1) {
    event.preventDefault();
        var patientName = document.getElementById('patientName').value;
        var fromDate = document.getElementById('from').value;
        var toDate = document.getElementById('to').value;
        var service = document.getElementById('service').value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'medical-examination?event=get-medical-examination-page-manager&patientName=' + encodeURIComponent(patientName)+
                '&fromDate=' + encodeURIComponent(fromDate) +
                '&toDate=' + encodeURIComponent(toDate) +
                '&service=' + encodeURIComponent(service) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#medical-list').innerHTML = xhr.responseText;
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