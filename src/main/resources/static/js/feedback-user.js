let currentPage = 1;
let pageSize = 10;
let productId = null;
let accountId = null;
getDataFeedbackManage(currentPage)

function getDataFeedbackManage(page) {
    currentPage = page;
    console.log("getDataFeedbackManage")
    const data = {
        pageNum: currentPage,
        pageSize: pageSize,
    };
    if (accountId !== null) {
        data.accountId = accountId;
    }
    if (productId !== null) {
        data.productId = productId;
    }

    $.ajax({
        url: '/api/feedback/feedback-manage',
        method: 'GET',
        data: data,
        success: function (response) {
            console.log("Feedback data loaded:", response.data);
            displayFeedback(response.data)
            updatePagination(response.totalPages, response.currentPage)
        },
        error: function (err) {
            console.error('Error loading users:', err);
            alert('Failed to load users');
        }
    });
}

function displayFeedback(feedbackList) {
    const feedbackTableBody = $('#userTable tbody');
    feedbackTableBody.empty(); // Clear previous content

    feedbackList.forEach(feedback => {
        const ordersCode = feedback.orders && feedback.orders.ordersId
            ? `<a href="/product/detail/${feedback.orders.ordersId}">${feedback.orders.ordersCode}</a>`
            : 'N/A';

        const fullName = feedback.account && feedback.account.fullName
            ? feedback.account.fullName
            : 'N/A';

        const formattedDate = formatDate(new Date(feedback.commentDate));

        const feedbackRow = `
            <tr>
                <td>${ordersCode}</td>
                <td>${fullName}</td>
                <td>${formattedDate}</td>
                <td>
                    <button class="btn btn-primary" onclick="viewFeedback(${feedback.commentId})">View</button>
                    <button class="btn btn-danger" onclick="deleteFeedback(${feedback.commentId})">Delete</button>
                </td>
            </tr>
        `;
        feedbackTableBody.append(feedbackRow);
    });
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

function deleteFeedback(commentId) {
    $.ajax({
        url: '/api/feedback/' + commentId,
        method: 'DELETE',
        success: function (response) {
            if (response.message === "Success") {
                alert('Feedback deleted successfully');
                console.log("Feedback deleted successfully");
                // Gọi hàm để lấy lại danh sách phản hồi và cập nhật giao diện
                getDataFeedbackManage(1);
            } else {
                console.error("Delete failed:", response.message);
                alert('Failed to delete feedback: ' + response.message);
            }
        },
        error: function (err) {
            console.error('Error deleting feedback:', err);
            alert('Failed to delete feedback');
        }
    });
}

function viewFeedback(commentId) {
    $.ajax({
        url: '/api/feedback/' + commentId + '/detail',
        method: 'GET',
        success: function (response) {
            console.log("response " + response.data)
            showFeedbackDetails(response.data)
        },
        error: function (err) {
            console.error('Error deleting feedback:', err);
            alert('Failed to delete feedback');
        }
    });
}

function showFeedbackDetails(data) {
    // Kiểm tra và điền dữ liệu vào các trường input
    $('#ordersCode').val(data.orders && data.orders.ordersCode ? data.orders.ordersCode : 'N/A');
    $('#userName').val(data.account && data.account.fullName ? data.account.fullName : 'N/A');
    $('#productName').val(data.product && data.product.productName ? data.product.productName : 'N/A');

    let date = formatDate(new Date(data.commentDate));
    console.log("date: " + date);
    $('#commentDate').val(date);
    $('#commentText').val(data.commentText || ''); // Nếu không có commentText, để trống

    // Hiện modal (nếu bạn sử dụng Bootstrap modal, hãy thay đổi theo cách của bạn)
    $('#viewFeedbackModal').modal('show');
}

// Hàm định dạng ngày
function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
}
