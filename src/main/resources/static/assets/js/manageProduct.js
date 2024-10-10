$(document).ready(function () {
    loadProducts();

    $('#searchButton').on('click', function () {
        var searchQuery = $('#searchInput').val();
        loadProducts(searchQuery, 1);
    });

    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        var searchQuery = $('#searchInput').val();

        // Next, Previous
        var currentPage = parseInt($('.pagination .active').text()) || 1;
        if (page === 'prev') {
            page = currentPage > 1 ? currentPage - 1 : 1;
        } else if (page === 'next') {
            page = currentPage + 1;
        } else {
            page = parseInt(page);
        }
        loadProducts(searchQuery, page);
    });

    $('#productTable th').on('click', function () {
        var columnIndex = $(this).index();
        sortProducts(columnIndex);
    });
});

// Load all product
function loadProducts(search = '', page = 1) {
    $.ajax({
        url: `/get-product`,
        type: 'GET',
        success: function (response) {
            var tableBody = $('#productTableBody');
            tableBody.empty();

            $.each(response.data, function (index, product) {
                var productHtml = createProductRow(product);
                tableBody.append(productHtml);
            });

            $('.edit-product').on('click', function () {
                var productId = $(this).data('product-id');
                editProduct(productId);
            });

            $('.delete-product').on('click', function () {
                var productId = $(this).data('product-id');
                deleteProduct(productId);
            });

            $('#noDataMessage').hide();
            $('#productTable').show();
        },
        error: function (xhr, status, error) {
            console.error("Error loading products:", error);
            $('#noDataMessage').show();
            $('#productTable').hide();
        }
    });
}

function createProductRow(product) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0
    });
    let priceFormatted = formatter.format(product.price);
    let price = priceFormatted.replace('₫', 'đ');

    return `
        <tr class="product">
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.productDetails}</td>
                    <td><img src="${product.productImageUrl}" alt="${product.productName}" width="200" /></td>
                    <td>${price}</td>
                    <td>${product.categoryID}</td>
                    <td>${product.stockQuantity}</td>
                    <td>
                        <button class="btn btn-success col me-3 edit-product" data-product-id="${product.productId}">Update</button>
                        <button class="btn btn-danger col delete-product" data-product-id="${product.productId}">Delete</button>
                    </td>
        </tr>
    `;
}

function showAddProductModal() {
    $('#addProductModal').modal('show');
    $('.modal-backdrop').remove();
}

// Add new product
$('#addProductForm').on('submit', function (event) {
    event.preventDefault();

    var formData = new FormData(this);

    $.ajax({
        url: '/save-new-product',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function (response) {
            alert("Add Success");
            loadProducts();
            $('#addProductModal').modal('hide');
            $('#addProductForm')[0].reset();
        },
        error: function (xhr) {
            if (xhr.status == 409) {
                var existingProduct = xhr.responseJSON.data;
                var newStockQuantity = existingProduct.stockQuantity + parseInt($('#productStock').val());

                if (confirm("Product already exists, do you want to update quantity?")) {
                    $.ajax({
                        url: '/save-edit-product',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            productId: existingProduct.productId,
                            stockQuantity: newStockQuantity
                        }),
                        success: function (updateResponse) {
                            alert("Update quantity success");
                            loadProducts();
                        },
                        error: function (updateError) {
                            console.error("Error updating quantity:", updateError);
                            alert("Error updating quantity");
                        }
                    });
                }
            } else {
                alert('Error adding product');
            }
        }
    });
});


// Edit product
function editProduct(productId) {
    $.ajax({
        url: '/edit-product',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            productId: productId
        }),
        success: function (response) {
            $('#editProductModal').modal('show');
            $('.modal-backdrop').remove();

            var product = response.data;
            $('#editProductId').val(product.productId);
            $('#editProductName').val(product.productName);
            $('#editProductDescription').val(product.productDetails);
            $('#editImageUrl').val(product.productImage); // Nếu bạn muốn hiển thị URL cũ
            $('#editProductPrice').val(product.price);
            $('#editCategoryId').val(product.categoryID);
            $('#editProductStock').val(product.stockQuantity);
            $('#editProductStatus').val(product.status);
            $('#editProductModal').modal('show');
        },
        error: function (xhr, status, error) {
            alert('Error loading product details: ' + error);
        }
    });
}


// Update
$('#editProductForm').on('submit', function (event) {
    event.preventDefault();

    var formData = new FormData(this);

    $.ajax({
        url: '/save-edit-product',
        type: 'POST',
        processData: false, // Không xử lý dữ liệu
        contentType: false, // Không gửi content type
        data: formData, // Gửi dữ liệu FormData
        success: function (response) {
            alert(response.message);
            $('#editProductModal').modal('hide');
            loadProducts();
        },
        error: function () {
            alert('Error updating product');
        }
    });
});


// Delete product
function deleteProduct(productId) {
    if (confirm('Are you sure you want to delete this product?')) {
        $.ajax({
            url: '/delete-product',
            type: 'POST',
            data: {id: productId},  // Truyền productId vào đây
            success: function (response) {
                alert(response.message);  // Hiển thị message trong response
                loadProducts();
            },
            error: function () {
                alert('Error deleting product');
            }
        });
    }
}

function sortProducts(columnIndex) {
    // Chỉ cho phép sắp xếp khi nhấn vào cột ID, Price hoặc Stock
    if (columnIndex !== 0 && columnIndex !== 4 && columnIndex !== 6) {
        return; // Không làm gì nếu không phải là cột ID, Price hoặc Stock
    }

    var tableBody = $('#productTableBody');
    var rows = tableBody.find('tr').toArray();

    var isAscending = tableBody.data('sortOrder') === 'asc';
    tableBody.data('sortOrder', isAscending ? 'desc' : 'asc');

    rows.sort(function (a, b) {
        var aValue = $(a).children('td').eq(columnIndex).text();
        var bValue = $(b).children('td').eq(columnIndex).text();

        // Chuyển đổi giá trị thành số nếu là Price hoặc Stock
        if (columnIndex === 0 || columnIndex === 4 || columnIndex === 6) {
            aValue = parseFloat(aValue);
            bValue = parseFloat(bValue);
        }

        return isAscending ? (aValue > bValue ? 1 : -1) : (aValue < bValue ? 1 : -1);
    });

    // Cập nhật lại bảng
    rows.forEach(function (row) {
        tableBody.append(row);
    });
}