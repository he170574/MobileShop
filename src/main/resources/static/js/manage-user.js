let currentPage = 1;
let pageSize = 10;
let keyword = null;
let roles = []

getDataMember(keyword, roles, currentPage)
handleSearchInput()
handleRoleSelection()

function getDataMember(keywordValue, rolesValue, currentPageValue) {
    currentPage = currentPageValue;
    keyword = keywordValue;
    roles = rolesValue;
    const data = {
        pageNum: currentPageValue,
        pageSize: pageSize,
    };
    if (keyword !== null) {
        data.keyword = keyword;
    }
    if (roles !== null) {
        data.roles = roles;
    }

    $.ajax({
        url: '/list-account',
        method: 'GET',
        data: data,
        success: function (response) {
            displayAccount(response.data)
            updatePagination(response.totalPages, response.currentPage)
        },
        error: function (err) {
            console.error('Error loading users:', err);
            alert('Failed to load users');
        }
    });
}

function displayAccount(accounts) {
    const feedbackTableBody = $('#accountTable tbody');
    feedbackTableBody.empty(); // Clear previous content
    accounts.forEach(account => {
        // Xử lý giá trị null/undefined
        const fullName = account.fullName || 'N/A';
        const email = account.email || 'N/A';
        const registerDate = account.registerDate || 'N/A';
        const address = account.address || 'N/A';
        const roleName = account.role && account.role.roleName ? account.role.roleName : 'N/A';
        const accountId = account.accountId || '';
        // Escape dữ liệu để tránh lỗi bảo mật XSS
        const escapeHTML = (str) =>
            str.replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#039;");

        const feedbackRow = `
            <tr>
                <td>${escapeHTML(fullName)}</td>
                <td>${escapeHTML(email)}</td>
                <td>${escapeHTML(registerDate)}</td>
                <td>${escapeHTML(address)}</td>
                <td>${escapeHTML(roleName)}</td>
                <td>
                    <button class="btn btn-danger delete-account-btn" data-id="${accountId}">Delete</button>
                </td>
            </tr>
        `;
        feedbackTableBody.append(feedbackRow);
    });

    // Gắn sự kiện cho nút xóa (sử dụng sự kiện động)
    feedbackTableBody.on('click', '.delete-account-btn', function () {
        const accountId = $(this).data('id');
        deleteAccount(accountId);
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
    getDataMember(keyword, roles, page); // Gọi hàm để tải dữ liệu của trang mới
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
        getDataMember(key, roles, 1);
        searchInput.value = searchValue;
    });
}

// Hàm xử lý khi thay đổi giá trị trong <select>
function handleRoleSelection() {
    const statusSelect = document.getElementById("role-selection");
    statusSelect.addEventListener("change", function () {
        let status = this.value;
        let statusValue = null;
        if (status) {
            statusValue = status;
        }
        getDataMember(keyword, statusValue, 1);
    });
}

function deleteAccount(accountId) {
    $.ajax({
        url: '/delete-account/' + accountId,
        method: 'DELETE',
        success: function (response) {
            if (response.message === "Success") {
                alert('Account deleted successfully');
                // Gọi hàm để lấy lại danh sách phản hồi và cập nhật giao diện
                getDataMember(null, [], 1)
            } else {
                alert('Failed to delete account');
            }
        },
        error: function (err) {
            alert('Failed to delete account');
        }
    });
}

