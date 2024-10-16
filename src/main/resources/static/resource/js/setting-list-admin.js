/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
var valuInput = document.getElementById('valuSetting');
var filterType = document.getElementById('filterSetting');
var sortOption = document.getElementById('sort');

document.addEventListener('DOMContentLoaded', function () {


    valuInput.addEventListener('input', function () {
        handleInputChange(1);
    });

    filterType.addEventListener('change', function () {
        handleInputChange(1);
    });

    sortOption.addEventListener('change', function () {
        handleInputChange(1);
    });
    // Xử lý sự kiện khi người dùng nhấp vào nút phân trang

    function handleInputChange(page) {
        let value = valuInput.value;
        let filter = filterType.value;
        let sortBy = sortOption.value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'setting?event=fillter&value=' + value
                + '&type=' + filter + '&sort=' + sortBy + '&page=' + page);

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

function paging(page) {

    let value = valuInput.value;
    let filter = filterType.value;
    let sortBy = sortOption.value;

    // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'setting?event=fillter&value=' + value
            + '&type=' + filter + '&sort=' + sortBy + '&page=' + page);

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

    var paginationButtons = document.querySelectorAll('.pagination-btn');
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
            }
        });
    });
}
