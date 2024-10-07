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
});

// Load all product
function loadProducts(search = '', page = 1) {
    $.ajax({
        url: `/get-product`,
        type: 'GET',
        success: function (response) {
            var parser = new DOMParser();
            var doc = parser.parseFromString(response, 'text/html');


            var tableBody = $('#productTableBody');
            tableBody.empty();

            $.each(response.data, function (index, product) {
                var productHtml = `
                <tr class="product">
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.productDetails}</td>
                    <td><img src="${product.productImage}" alt="${product.productName}" width="200" /></td>
                    <td>${product.price}</td>
                    <td>${product.categoryID}</td>
                    <td>${product.stockQuantity}</td>
                    <td>
                        <button class="btn btn-success col me-3 edit-product" data-product-id="${product.productId}">Update</button>
                        <button class="btn btn-danger col delete-product" data-product-id="${product.productId}">Delete</button>
                    </td>
                </tr>
                `;
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
    const priceInDong = product.price * 1000000;
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0
    });
    let priceFormatted = formatter.format(product.price);
    let price = priceFormatted.replace('₫', 'đ');

    return `
        <tr data-product-id="${product.id}">
            <td class="id-column">${product.id}</td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td><img src="${product.image}" alt="${product.name}" width="50"></td>
            <td>${price}</td>
            <td>${product.categoryId}</td>
            <td>${product.stock}</td>
            <td>${product.status}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-warning edit-product">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger delete-product">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
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

    var formData = {
        productName: $('#productName').val(),
        productDetails: $('#productDescription').val(),
        productImage: $('#imageUrl').val(),
        price: $('#productPrice').val(),
        categoryID: $('#categoryId').val(),
        stockQuantity: $('#productStock').val()
    };

    $.ajax({
        url: '/save-new-product',
        type: 'POST',
        contentType: 'application/json',  // Gửi dữ liệu dưới dạng JSON
        data: JSON.stringify(formData),    // Chuyển dữ liệu thành chuỗi JSON
        success: function (response) {
            alert("Add Success");
            loadProducts();
            $('#addProductModal').modal('hide');
            $('#addProductForm')[0].reset();
        },
        error: function (xhr) {
            if (xhr.status == 409) {
                var existingProduct = xhr.responseJSON.data;
                if (confirm("Product already exists, do you want to update it?")) {
                    var newStockQuantity = existingProduct.stockQuantity + parseInt($('#productStock').val());
                    $.ajax({
                        url: '/save-edit-product',
                        type: 'POST',
                        contentType: 'application/json', // Chuyển dữ liệu thành JSON
                        data: JSON.stringify({
                            productId: existingProduct.productId,
                            stockQuantity: newStockQuantity
                        }),
                        success: function (updateResponse) {
                            console.log(updateResponse);
                            alert("Update quantity success");
                            loadProducts();
                        },
                        error: function () {
                            alert("Error update quantity");
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
            $('#editImageUrl').val(product.productImage);
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

    var formData = {
        productId: $('#editProductId').val(),
        productName: $('#editProductName').val(),
        productDetails: $('#editProductDescription').val(),
        productImage: $('#editImageUrl').val(),
        price: $('#editProductPrice').val(),
        stockQuantity: $('#editProductStock').val(),
        categoryID: $('#editCategoryId').val()
    };

    $.ajax({
        url: '/save-edit-product',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
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
            data: { id: productId },  // Truyền productId vào đây
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



