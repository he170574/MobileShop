$(document).ready(function () {
    // Lấy URL hiện tại
    const url = window.location.href;
// Sử dụng biểu thức chính quy để lấy số ở cuối URL
    const match = url.match(/\/(\d+)$/);
// Nếu tìm thấy kết quả, lấy nhóm đầu tiên
    const orderId = match ? match[1] : null;
    console.log("orderId " + orderId); // Kết quả: 1
    viewOrder(orderId)
});

function viewOrder(orderId) {
    $.ajax({
        url: '/api/orders/' + orderId + '/detail',
        type: 'GET',
        dataType: 'json',  // Chỉ định loại dữ liệu trả về là JSON
        success: function (response) {
            // Kiểm tra xem API trả về thành công hay không
            if (response.message === "Success") {
                const orderData = response.data;

                // Lấy tbody của bảng để thêm hàng dữ liệu
                const tbody = document.getElementById("orderTableBody");

                // Xóa các hàng cũ trong bảng trước khi thêm dữ liệu mới (nếu cần)
                tbody.innerHTML = '';

                // Tạo một hàng mới và điền dữ liệu từ JSON
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>1</td>
                    <td>${orderData.orderCode}</td>
                    <td>${orderData.shippingCode}</td>
                    <td>${orderData.orderDate}</td>
                    <td>${orderData.shippingFee.toFixed(2)}</td>
                    <td>${orderData.totalAmount.toFixed(1)}</td>
                    <td>${orderData.expectedDeliveryTime}</td>
                    <td>${orderData.orderStatus}</td>
                `;
                let orderStatus = orderData.orderStatus;
                // Thêm hàng vào bảng
                tbody.appendChild(row);

                // Hiển thị chi tiết sản phẩm trong đơn hàng
                const orderDetailsTableBody = document.getElementById("orderDetailsTableBody");
                orderDetailsTableBody.innerHTML = '';
                orderData.orderDetails.forEach((detail, index) => {
                    const detailRow = document.createElement("tr");
                    detailRow.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${detail.product.productName}</td>
                        <td>${detail.quantity}</td>
                        <td>${detail.cost}</td>
                        <td>${detail.productAmount}</td>
                    `;
                    // Check order status and conditionally add the "Feedback" button
                    if (orderStatus === 'DELIVERY_SUCCESS' || orderStatus === 'CANCEL' || orderStatus === 'SUCCESS') {
                        const feedbackButtonCell = document.createElement("td");
                        feedbackButtonCell.innerHTML = `
                            <button class="btn btn-primary" onclick="viewFeedback(${orderData.account.accountId},${orderData.id},${detail.product.productId})">Feedback</button>
                        `;
                        detailRow.appendChild(feedbackButtonCell);
                    }
                    orderDetailsTableBody.appendChild(detailRow);
                });

                checkOrderStatusAndViewFeedback(orderData.orderStatus)
            } else {
                console.log("Không tìm thấy đơn hàng hoặc có lỗi xảy ra.");
            }
        },
        error: function (xhr, status, error) {
            console.log("Không Tìm Thấy Order");
        }
    });
}

function viewFeedback(accountId, orderId, productId) {
    console.log("viewFeedback accountId " + accountId)
    $.ajax({
        url: '/api/feedback/order?orderId=' + orderId + '&productId=' + productId,
        method: 'GET',
        success: function (response) {
            showFeedbackDetails(response.data, accountId, orderId, productId)
        },
        error: function (err) {
            showFeedbackDetails({}, accountId, orderId, productId)
        }
    })
}

function showFeedbackDetails(data, accountId, orderId, productId) {
    $('#commentText').val(data.commentText || null);
    $('#feedbackId').val(data.commentId || null);
    $('#ordersId').val(orderId);
    $('#productId').val(productId);
    $('#accountId').val(accountId);
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

// Đóng modal với JavaScript
function closeModal() {
    $('#viewFeedbackModal').modal('hide');
}

function saveFeedback() {
    // Lấy dữ liệu từ các trường trong modal
    const feedbackId = parseInt(document.getElementById("feedbackId").value, 10);
    const ordersId = parseInt(document.getElementById("ordersId").value, 10);
    const productId = parseInt(document.getElementById("productId").value, 10);
    const accountId = parseInt(document.getElementById("accountId").value, 10);
    const commentText = document.getElementById("commentText").value;
    // Đối tượng chứa dữ liệu gửi đi
    const feedbackData = {
        commentId: feedbackId,
        ordersId: ordersId,
        productId: productId,
        commentText: commentText,
        accountId: accountId,
        commentDate: null
    };
    if (!feedbackId) {
        // Gửi dữ liệu qua AJAX đến server (ví dụ là '/api/feedbacks/save')
        $.ajax({
            url: '/api/feedback',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(feedbackData),
            success: function (response) {
            },
            error: function (xhr, status, error) {
                console.log("Error:", error);
            }
        });
        $('#viewFeedbackModal').modal('hide');
    } else {
        $.ajax({
            url: '/api/feedback/' + feedbackId,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(feedbackData),
            success: function (response) {
                // Xử lý sau khi lưu thành công
            },
            error: function (xhr, status, error) {
                // Xử lý nếu có lỗi xảy ra
                console.log("Error:", error);
            }
        });
        $('#viewFeedbackModal').modal('hide');
    }

}

function checkOrderStatusAndViewFeedback(orderStatus) {
    console.log("orderStatus " + orderStatus)

    var feedbackHeader = document.getElementById('feed-back-thead');
    // Check if the order status is one of the valid statuses
    if (orderStatus === 'DELIVERY_SUCCESS' || orderStatus === 'CANCEL' || orderStatus === 'SUCCESS') {
        feedbackHeader.style.display = ''; // Show the header (default)
    } else {
        feedbackHeader.style.display = 'none'; // Hide the header
    }
}