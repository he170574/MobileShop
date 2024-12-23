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

function formatCurrency(value) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0
    });
    return formatter.format(value).replace('₫', 'đ');
}

// Load all product
function loadProducts(page = 1) {
    const searchTerm = $('#searchInput').val();
    const pageSize = 6;

    $.ajax({
        url: `/get-all-product`,
        type: 'GET',
        data: {
            search: searchTerm,
            page: page - 1,
            size: pageSize
        },
        success: function (response) {
            response.data.sort((a, b) => b.productId - a.productId);

            renderProductTable(response.data);
            renderPagination(response.totalPages, page);

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

// Product Table
function renderProductTable(products) {
    const tableBody = $('#productTableBody');
    tableBody.empty();

    if (products.length) {
        products.forEach(product => {
            const productHtml = `
                <tr>
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.productDetails}</td>
                    <td><img src="${product.productImageUrl}" alt="${product.productName}" width="100" /></td>
                    <td>${formatCurrency(product.cost)}</td>
                    <td>${formatCurrency(product.price)}</td>
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
            tableBody.append(productHtml);
        });

        $('#productTable').show();
        $('#noDataMessage').hide();
    } else {
        // Hiển thị thông báo nếu không có sản phẩm nào
        $('#productTable').hide();
        $('#noDataMessage').show();
    }
}

// Paging
function renderPagination(totalPages, currentPage) {
    const paginationContainer = $('#pagination');
    paginationContainer.empty(); // Xóa các nút phân trang cũ

    if (totalPages > 1) {
        // Nút Previous nếu không ở trang đầu tiên
        if (currentPage > 1) {
            paginationContainer.append(`<li class="page-item"><button class="page-link page-btn" data-page="${currentPage - 1}">Previous</button></li>`);
        }

        // Hiển thị tối đa 5 nút phân trang xung quanh trang hiện tại
        const maxDisplayPages = 5;
        let startPage = Math.max(currentPage - Math.floor(maxDisplayPages / 2), 1);
        let endPage = Math.min(startPage + maxDisplayPages - 1, totalPages);

        if (endPage - startPage < maxDisplayPages - 1) {
            startPage = Math.max(endPage - maxDisplayPages + 1, 1);
        }

        // Lặp qua các trang và tạo các nút phân trang
        for (let i = startPage; i <= endPage; i++) {
            const activeClass = i === currentPage ? 'active' : '';
            paginationContainer.append(`<li class="page-item ${activeClass}"><button class="page-link page-btn" data-page="${i}">${i}</button></li>`);
        }

        // Nút Next nếu không ở trang cuối cùng
        if (currentPage < totalPages) {
            paginationContainer.append(`<li class="page-item"><button class="page-link page-btn" data-page="${currentPage + 1}">Next</button></li>`);
        }
    }

    // Gắn sự kiện click cho mỗi nút phân trang
    $('.page-btn').on('click', function () {
        const selectedPage = $(this).data('page');
        loadProducts(selectedPage);
    });
}

// Modal Add
function showAddProductModal() {
    $('#addProductModal').modal('show');
    $('.modal-backdrop').remove();
}

// Add new product
$('#addProductForm').on('submit', function (event) {
    event.preventDefault();

    var isValid = true;
    var productName = $('#productName').val().trim();
    var productDescription = $('#productDescription').val().trim();
    var productPrice = $('#productPrice').val();
    var productStock = $('#productStock').val();

    var namePattern = /^[^\s]+(\s+[^\s]+)*$/; // Chuỗi không chứa khoảng trắng ở đầu hoặc cuối, và không có khoảng trắng kép
    if (!namePattern.test(productName)) {
        alert("Product name should not contain extra spaces.");
        isValid = false;
    }

    // // Kiểm tra tên sản phẩm có độ dài tối thiểu là 3
    // if (productName.length < 3) {
    //     alert("Product name must be at least 3 characters long.");
    //     isValid = false;
    // }

    var desPattern = /^[^\s]+(\s+[^\s]+)*$/;
    if(!desPattern.test(productDescription)){
        alert("Product description should not contain extra spaces.");
        isValid = false;
    }

    // // Kiểm tra tên sản phẩm có độ dài tối thiểu là 3
    // if (productDescription.length < 3) {
    //     alert("Product description must be at least 3 characters long.");
    //     isValid = false;
    // }

    // Kiểm tra giá sản phẩm
    if (!productPrice || isNaN(productPrice) || parseFloat(productPrice) <= 0) {
        alert("Please enter a valid price greater than 0.");
        isValid = false;
    }

    // Kiểm tra số lượng trong kho
    if (!productStock || isNaN(productStock) || parseInt(productStock) < 1) {
        alert("Please enter a valid stock quantity (integer greater than or equal to 1).");
        isValid = false;
    }

    if (isValid) {
        // Kiểm tra xem sản phẩm có tồn tại dựa trên tên sản phẩm hay không
        $.ajax({
            url: '/check-product-exists',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ productName: productName }),
            success: function (response) {
                if (response.exists) {
                    // Nếu sản phẩm đã tồn tại, thông báo cho người dùng và hỏi có muốn cập nhật không
                    var existingProduct = response.data;
                    var newStockQuantity = existingProduct.stockQuantity + productStock;

                    if (confirm("Product already exists. Do you want to update the quantity?")) {
                        $.ajax({
                            url: '/save-edit-product',
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({
                                productId: existingProduct.productId,
                                stockQuantity: newStockQuantity
                            }),
                            success: function (updateResponse) {
                                alert("Product quantity updated successfully");
                                loadProducts();
                                $('#addProductModal').modal('hide');
                                $('#addProductForm')[0].reset();
                            },
                            error: function (updateError) {
                                console.error("Error updating quantity:", updateError);
                                alert("Error updating quantity");
                            }
                        });
                    }
                } else {
                    // Nếu sản phẩm không tồn tại, tiến hành thêm sản phẩm mới
                    var formData = new FormData($('#addProductForm')[0]);

                    $.ajax({
                        url: '/save-new-product',
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        data: formData,
                        success: function (response) {
                            alert("Product added successfully");
                            loadProducts();
                            $('#addProductModal').modal('hide');
                            $('#addProductForm')[0].reset();
                        },
                        error: function (error) {
                            console.error("Error adding product:", error);
                            alert("Error adding product");
                        }
                    });
                }
            },
            error: function (xhr) {
                console.error("Error checking product existence:", xhr);
                alert("Error checking product existence");
            }
        });
    }
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
            $('#editProductCost').val(product.cost);
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
        processData: false,
        contentType: false,
        data: formData,
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
            data: { id: productId },
            success: function (response) {
                alert(response.message);
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
    if (columnIndex !== 0 && columnIndex !== 4 && columnIndex !== 5 && columnIndex !== 7) {
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
