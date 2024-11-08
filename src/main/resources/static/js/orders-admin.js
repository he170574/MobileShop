let pageSize = 10;
let keyword = null;
let orderStatus = null;
let accountId = null;
let sortBy = 'orderCode';
let orderBy = 'desc';
handleSearchInput()
handleStatusSelection()
getDataOrderManage(keyword, orderStatus, accountId, sortBy, orderBy, 1)

function getDataOrderManage(keywordValue, orderStatusValue, accountIdValue, sortByValue, orderByValue, currentPageValue) {
    keyword = keywordValue;
    orderStatus = orderStatusValue;
    accountId = accountIdValue;
    sortBy = sortByValue;
    orderBy = orderByValue;
    const data = {
        pageNum: currentPageValue,
        pageSize: pageSize,
    };
    if (keyword !== null) {
        data.keyword = keyword;
    }
    if (orderStatus !== null) {
        data.orderStatus = orderStatus;
    }
    if (accountId !== null) {
        data.accountId = accountId;
    }
    if (sortBy !== null) {
        data.sortBy = sortBy;
    }
    if (orderBy !== null) {
        data.orderBy = orderBy;
    }

    $.ajax({
        url: '/api/orders/list-manage',
        method: 'GET',
        data: data,
        success: function (response) {
            displayOrder(response.data)
            updatePagination(response.totalPages, response.currentPage)
        },
        error: function (err) {
            console.error('Error loading users:', err);
            alert('Failed to load users');
        }
    });
}

function displayOrder(orders) {
    const orderTableBody = $('#order-table tbody');
    orderTableBody.empty(); // Xóa nội dung trước đó
    orders.forEach(order => {
        // Kiểm tra và gán Order Code
        const ordersCode = order.orderCode
            ? `<a href="">${order.orderCode}</a>`
            : 'N/A';
        // Kiểm tra và gán User Name
        const fullName = order.account && order.account.fullName
            ? order.account.fullName
            : 'N/A';
        // Định dạng ngày đặt hàng
        const formattedDate = formatDate(new Date(order.orderDate));
        // Định dạng số tiền theo VND
        const totalAmount = order.totalAmount
            ? order.totalAmount.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'}) :
            'N/A';
        // Kiểm tra và gán Order Status
        const orderStatus = order.orderStatus ? order.orderStatus : 'N/A';
        // Xây dựng hàng cho bảng
        const orderRow = `
            <tr>
                <td>${ordersCode}</td>
                <td>${fullName}</td>
                <td>${formattedDate}</td>
                <td>${totalAmount}</td>
                <td>${orderStatus}</td>
                <td>
                    <button class="btn btn-primary" onclick="viewOrder(${order.id})">View</button>
                </td>
            </tr>
        `;
        orderTableBody.append(orderRow);
    });
}

function goToPage(page) {
    getDataOrderManage(keyword, orderStatus, accountId, sortBy, orderBy, page)
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
        // Nếu tổng số trang nhỏ hơn hoặc bằng 5, hiển thị tất cả các trang
        startPage = 1;
        endPage = totalPages;
    } else {
        // Nếu tổng số trang lớn hơn 5, chỉ hiển thị 5 trang xung quanh trang hiện tại
        if (currentPage <= 3) {
            // Nếu trang hiện tại là 1, 2, hoặc 3
            startPage = 1;
            endPage = 5;
        } else if (currentPage + 2 >= totalPages) {
            // Nếu trang hiện tại gần cuối (còn lại 2 trang)
            startPage = totalPages - 4;
            endPage = totalPages;
        } else {
            // Ở giữa, hiển thị trang hiện tại và 2 trang xung quanh
            startPage = currentPage - 2;
            endPage = currentPage + 2;
        }
    }

    // Hiển thị các nút số trang
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

// Hàm sắp xếp
function sortOrderTable(column) {
    // Nếu cột hiện tại là cột đã sắp xếp trước đó, đảo hướng sắp xếp
    if (sortBy === column) {
        orderBy = (orderBy === 'asc') ? 'desc' : 'asc';
    } else {
        // Nếu là cột mới, đặt cột mới và reset hướng sắp xếp
        sortBy = column;
        orderBy = 'asc'; // Bắt đầu với 'asc' khi chuyển sang cột mới
    }
    // Cập nhật mũi tên
    updateArrowIcons();
    // Tải và hiển thị lại dữ liệu với sắp xếp mới
    getDataOrderManage(keyword, orderStatus, accountId, sortBy, orderBy, 1)
}

function updateArrowIcons() {
    // Loại bỏ mũi tên trước đó
    $('#order-table th i').removeClass('fa-chevron-up fa-chevron-down');

    // Cập nhật mũi tên của cột đã chọn
    const activeColumn = $('#order-table th').filter(function () {
        // Kiểm tra nếu cột hiện tại là cột đang sắp xếp
        return $(this).text().trim() === sortBy;
    });

    if (orderBy === 'asc') {
        activeColumn.find('i').addClass('fa-chevron-up');  // Mũi tên lên
    } else {
        activeColumn.find('i').addClass('fa-chevron-down');  // Mũi tên xuống
    }
}

// Hàm xử lý khi nhập vào ô tìm kiếm
function handleSearchInput() {
    const searchInput = document.getElementById("searchInput");
    searchInput.addEventListener("input", function () {
        const searchValue = this.value;
        let key = null;
        if (searchValue) {
            key = searchValue;
        }
        getDataOrderManage(key, orderStatus, accountId, sortBy, orderBy, 1)
        searchInput.value = searchValue;
    });
}

// Hàm xử lý khi thay đổi giá trị trong <select>
function handleStatusSelection() {
    const statusSelect = document.getElementById("status-selection");
    statusSelect.addEventListener("change", function () {
        let status = this.value;
        let statusValue = null;
        if (status) {
            statusValue = status;
        }
        getDataOrderManage(keyword, statusValue, accountId, sortBy, orderBy, 1)
    });
}

function viewOrder(orderId) {
    console.log("viewOrder " + orderId)
    $.ajax({
        url: '/api/orders/' + orderId + '/detail',
        type: 'GET',
        dataType: 'json',  // Chỉ định loại dữ liệu trả về là JSON
        success: function (response) {
            // Xử lý dữ liệu khi API trả về thành công
            if (response.message === "Success") {
                updateModalWithOrderData(response.data);
            }
        },
        error: function (xhr, status, error) {
            console.log("Không Tìm Thấy Order")
        }
    });
}

// Function to update modal with order data
function updateModalWithOrderData(orderData) {
    // Cập nhật thông tin vào các input fields
    document.getElementById("ordersCode").value = orderData.orderCode || "N/A"; // Nếu orderCode null thì hiển thị "N/A"
    document.getElementById("userName").value = orderData.account.fullName;
    document.getElementById("orderStatus").value = orderData.orderStatus || "N/A";
    document.getElementById("totalAmount").value = orderData.totalAmount.toLocaleString() + " ₫"; // Định dạng số theo kiểu VNĐ

    // Hiển thị danh sách sản phẩm
    const productDetailsDiv = document.getElementById("productDetails");
    productDetailsDiv.innerHTML = ""; // Clear any previous content

    orderData.orderDetails.forEach(product => {
        const productDiv = document.createElement("div");
        productDiv.classList.add("product-item");

        // Create HTML structure for each product
        productDiv.innerHTML = `
            <div class="product-info">
                <img src="${product.product.productImage}" alt="${product.product.productName}" width="50" height="50">
                <span>${product.product.productName}</span> (Quantity: ${product.quantity || 'N/A'}, Price: ${product.cost.toLocaleString()} ₫)
            </div>
        `;

        // Append product info to the container
        productDetailsDiv.appendChild(productDiv);
    });

    // Comment Date and Text
    document.getElementById("commentDate").value = orderData.orderDate || "N/A";
    document.getElementById("commentText").value = "No comments available."; // You can add real comment text if available
    $('#viewFeedbackModal').modal('show');
}
