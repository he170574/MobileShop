$(document).ready(function() {
    let currentPage = 0;
    const pageSize = 20;

    loadUsers(currentPage, pageSize);

    // Hàm tải danh sách người dùng
    function loadUsers(page, size) {
        const search = $('#searchInput').val();
        $.ajax({
            url: '/get-all-users',
            method: 'GET',
            data: { pageNumber: page, pageSize: size, search: search }, // Thêm tham số tìm kiếm
            success: function(data) {
                displayUsers(data.data.lstUsers);
                $('#pageInfo').text(`Page ${data.data.pageNumber + 1} of ${data.data.totalPage}`);
            },
            error: function(err) {
                console.error('Error loading users:', err);
            }
        });
    }

    // Hàm hiển thị người dùng trong bảng
    function displayUsers(users) {
        const tbody = $('#userTable tbody');
        tbody.empty(); // Xóa nội dung cũ

        users.forEach(user => {
            const statusButton = user.deleted
                ? `<button onclick="toggleUserStatus(${user.accountId}, false)">Activate</button>`
                : `<button onclick="toggleUserStatus(${user.accountId}, true)">Deactivate</button>`;

            tbody.append(`
                <tr>
                    <td>${user.accountId}</td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.deleted ? 'Inactive' : 'Active'}</td>
                    <td>${statusButton}</td>
                </tr>
            `);
        });
    }

    // Hàm thay đổi trạng thái người dùng
    window.toggleUserStatus = function(accountId, isDeactivating) {
        $.ajax({
            url: '/toggle-account-status', // Đường dẫn API
            method: 'POST',
            data: { accountId: accountId },
            success: function(response) {
                alert(response.message);
                loadUsers(currentPage, pageSize); // Tải lại danh sách người dùng
            },
            error: function(err) {
                console.error('Error toggling user status:', err);
                alert('Failed to update user status');
            }
        });
    };

    // Hàm tìm kiếm người dùng
    window.searchUsers = function() {
        currentPage = 0; // Reset về trang đầu
        loadUsers(currentPage, pageSize);
    };

    // Hàm thay đổi trang
    window.changePage = function(direction) {
        currentPage += direction;
        loadUsers(currentPage, pageSize);
    };
});