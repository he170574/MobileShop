$(document).ready(function () {
    loadProducts();
    loadCategories();

    $('#searchButton').on('click', function () {
        loadProducts(1);
    });

    $('#searchInput').on('keypress', function (e) {
        if (e.which === 13) {
            loadProducts(1);
        }
    });
});

// Load all product
function loadProducts(page = 1) {
    const searchTerm = $('#searchInput').val();
    $.ajax({
        url: `/get-product`,
        type: 'GET',
        data: {
            search: searchTerm,
            page: page,
            size: 1
        },
        success: function (response) {
            var tableBody = $('#productTableBody');
            tableBody.empty();

            $.each(response.data, function (index, product) {
                var productHtml = createProductRow(product);
                tableBody.append(productHtml);
            });

            handlePagination(response.totalPages, page);

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

function handlePagination(totalPages, currentPage) {
    const paginationContainer = $('#pagination');
    paginationContainer.empty();

    // Previous button
    if (currentPage > 1) {
        paginationContainer.append(`<button onclick="loadProducts(${currentPage - 1})">Previous</button>`);
    }

    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        paginationContainer.append(`<button onclick="loadProducts(${i})">${i}</button>`);
    }

    // Next button
    if (currentPage < totalPages) {
        paginationContainer.append(`<button onclick="loadProducts(${currentPage + 1})">Next</button>`);
    }
}
// Load all category
function loadCategories() {
    $.ajax({
        url: '/get-category',
        type: 'GET',
        success: function (response) {
            var categorySelect = $('#categoryName, #editCategoryName');
            categorySelect.empty();

            $.each(response.data, function (index, category) {
                categorySelect.append($('<option>', {
                    value: category.categoryId,
                    text: category.categoryName
                }));
            });
        },
        error: function (xhr, status, error) {
            console.error("Error loading categories:", error);
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
                    <td><img src="${product.productImageUrl}" alt="${product.productName}" width="100" /></td>
                    <td>${price}</td>
                    <td>${product.categoryName}</td>
                    <td>${product.stockQuantity}</td>
                    <td>
                <button class="btn btn-warning col me-3 edit-product" data-product-id="${product.productId}">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-danger col delete-product" data-product-id="${product.productId}">
                    <i class="fas fa-trash"></i> 
                </button>
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
            $('#editImageUrl').val(product.productImage);
            $('#editProductPrice').val(product.price);
            $('#editCategoryName').val(product.categoryName);
            $('#editProductStock').val(product.stockQuantity);
            $('#editProductModal').modal('show');
            loadCategories();
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

// Sort
function sortProducts(columnIndex) {
    if (columnIndex !== 0 && columnIndex !== 4 && columnIndex !== 6) {
        return;
    }

    var tableBody = $('#productTableBody');
    var rows = tableBody.find('tr').toArray();

    var isAscending = tableBody.data('sortOrder') === 'asc';
    tableBody.data('sortOrder', isAscending ? 'desc' : 'asc');

    rows.sort(function (a, b) {
        var aValue = $(a).children('td').eq(columnIndex).text();
        var bValue = $(b).children('td').eq(columnIndex).text();

        if (columnIndex === 0 || columnIndex === 4 || columnIndex === 6) {
            aValue = parseFloat(aValue);
            bValue = parseFloat(bValue);
        }

        return isAscending ? (aValue > bValue ? 1 : -1) : (aValue < bValue ? 1 : -1);
    });

    rows.forEach(function (row) {
        tableBody.append(row);
    });
}

// Modal add new category
function showAddCategoryModal() {
    $('#addCategoryModal').modal('show');
    $('.modal-backdrop').remove();
    loadCategories();
}

// Save category
function saveNewCategory() {
    var newCategoryName = $('#newCategoryName').val();
    if (!newCategoryName) {
        alert("Please enter a category name.");
        return;
    }

    $.ajax({
        url: '/admin/product/add-new-category',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ categoryName: newCategoryName }),
        success: function (response) {
            alert("Category added successfully!");
            $('#addCategoryModal').modal('hide');
            $('#newCategoryName').val('');

            loadCategories();
        },
        error: function (xhr, status, error) {
            console.error("Error adding category:", error);
            alert("Error adding category.");
        }
    });
}
