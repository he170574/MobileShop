let currentPage = 1;
let pageSize = 5;
let productIdValue = null;
let accountId = null;
$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    if (productId) {
        productIdValue = productId;
        $.ajax({
            url: '/get-product-detail',
            method: 'GET',
            data: {id: productId},
            success: function (product) {
                $('#product-name').text(product.productName);
                $('#product-image').attr('src', product.productImage);
                $('#product-price').text(product.price + ' đ');
                $('#product-description').text(product.productDetails);
                $('#name-comment-product').text(product.productName);
            },
            error: function(error) {
                console.error("Error fetching product details:", error);
                alert('Unable to fetch product details. Please try again later.');
            }
        });
    } else {
        alert('Product ID is missing.');
    }
});

getDataFeedbackManage(currentPage)

function getDataFeedbackManage(page) {
    currentPage = page;
    const data = {
        pageNum: currentPage,
        pageSize: pageSize,
    };
    if (accountId !== null) {
        data.accountId = accountId;
    }
    if (productIdValue !== null) {
        data.productIdValue = productIdValue;
    }
    $.ajax({
        url: '/api/feedback/feedback-manage',
        method: 'GET',
        data: data,
        success: function (response) {
            console.log("Feedback data loaded:", response.data);
            displayComments(response.data)
            updatePagination(response.totalPages, response.currentPage)
        },
        error: function (err) {
            console.error('Error loading users:', err);
            alert('Failed to load users');
        }
    });
}

// Hàm để hiển thị bình luận
function displayComments(comments) {
    const commentUserDiv = document.getElementById("comment-user");
    commentUserDiv.innerHTML = ""; // Xóa các bình luận trước nếu có
    comments.forEach(comment => {
        // Tạo một phần tử `div` chứa thông tin bình luận
        const commentDiv = document.createElement("div");
        commentDiv.classList.add("comment-item");
        // Thêm nội dung HTML vào `div` này
        commentDiv.innerHTML = `
            <p><strong>${comment.account.fullName}</strong> - ${formatDate(comment.commentDate)}</p>
            <p>${comment.commentText}</p>
        `;
        // Thêm phần tử bình luận vào `comment-user` div
        commentUserDiv.appendChild(commentDiv);
    });
}

// Hàm để định dạng ngày tháng
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); // Định dạng thời gian thành dạng dễ đọc
}

function updatePagination(totalPages, currentPage) {
    const paginationContainer = $('#pagination');
    paginationContainer.empty(); // Xóa nội dung cũ của phân trang

    // Nút "Trước" với biểu tượng
    if (currentPage > 1) {
        const prevButton = `
            <button class="page-btn" onclick="goToPage(${currentPage - 1})">
                <i class="fas fa-chevron-left"></i> <!-- Biểu tượng "Trước" -->
            </button>
        `;
        paginationContainer.append(prevButton);
    }

    // Tính toán số trang hiển thị
    let startPage, endPage;
    if (totalPages <= 5) {
        startPage = 1;
        endPage = totalPages;
    } else {
        if (currentPage <= 3) {
            startPage = 1;
            endPage = 5;
        } else if (currentPage + 2 >= totalPages) {
            startPage = totalPages - 4;
            endPage = totalPages;
        } else {
            startPage = currentPage - 2;
            endPage = currentPage + 2;
        }
    }

    // Số trang
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = `
            <button 
                class="page-btn ${i === currentPage ? 'active' : ''}" 
                onclick="goToPage(${i})">
                ${i}
            </button>
        `;
        paginationContainer.append(pageButton);
    }

    // Nút "Sau" với biểu tượng
    if (currentPage < totalPages) {
        const nextButton = `
            <button class="page-btn" onclick="goToPage(${currentPage + 1})">
                <i class="fas fa-chevron-right"></i> <!-- Biểu tượng "Sau" -->
            </button>
        `;
        paginationContainer.append(nextButton);
    }
}

function goToPage(page) {
    getDataFeedbackManage(page); // Gọi hàm để tải dữ liệu của trang mới
}