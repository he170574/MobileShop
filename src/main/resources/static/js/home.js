$(document).ready(function() {
    let priceSliderInitialized = false;

    // Hàm để tải tất cả sản phẩm với bộ lọc
    function loadAllProducts(filters = {}) {

        console.log("Filters applied:", filters);

        $.ajax({
            url: '/get-product',
            method: 'GET',
            data: filters,
            success: function(response) {
                if (response && response.data) {
                    renderProductList(response.data);
                } else {
                    console.error("Unexpected data format received:", response);
                    alert('Unexpected data format received.');
                }
            },
            error: function(error) {
                console.error("Error fetching products:", error);
                alert('Unable to load products. Please try again later.');
            }
        });
    }

    // Tải tất cả sản phẩm khi trang được tải
    loadAllProducts();

    // Toggle hiển thị dropdown bộ lọc giá và khởi tạo thanh trượt khi nhấn nút "Price"
    $('#toggle-price').on('click', function () {
        const priceDropdown = $('#price-dropdown');

        if (!priceSliderInitialized) {
            // Khởi tạo thanh trượt noUiSlider chỉ một lần
            const priceSlider = document.getElementById('price-slider');
            noUiSlider.create(priceSlider, {
                start: [0, 50000000],
                connect: true,
                range: { min: 0, max: 50000000 },
                tooltips: true,
                format: wNumb({ decimals: 0, thousand: ',', suffix: ' đ' })
            });

            // Cập nhật giá trị hiển thị khi thay đổi thanh trượt
            priceSlider.noUiSlider.on('update', function (values) {
                $('#min-price').text(values[0]);
                $('#max-price').text(values[1]);
            });

            priceSliderInitialized = true; // Đánh dấu thanh trượt đã được khởi tạo
        }

        // Toggle hiển thị dropdown
        priceDropdown.toggle();
    });

    // Áp dụng bộ lọc giá khi nhấn nút "Xem kết quả"
    $('#apply-price-filter').on('click', function () {
        const priceSlider = document.getElementById('price-slider');
        const [minPrice, maxPrice] = priceSlider.noUiSlider.get();

        $('#price-dropdown').hide();

        loadAllProducts({
            minPrice: parseFloat(minPrice.replace(/ đ/g, '').replace(/,/g, '')),
            maxPrice: parseFloat(maxPrice.replace(/ đ/g, '').replace(/,/g, ''))
        });
    });

    // Xử lý đóng bộ lọc
    $('#close-filter').on('click', function () {
        $('#price-dropdown').hide();
    });

    // Lọc sản phẩm theo danh mục
    $('.category').on('click', function() {
        const category = $(this).data('category'); // Lấy danh mục được chọn
        loadAllProducts({ category: category });
    });

    // Tìm kiếm sản phẩm theo tên
    $('#search-button').on('click', function() {
        const productName = $('#search-input').val();
        loadAllProducts({ search: productName });
    });

    // Sắp xếp sản phẩm theo giá: từ cao xuống thấp và từ thấp lên cao
    $('#sort-high-to-low').on('click', function() {
        loadAllProducts({ sort: 'desc' });
    });

    $('#sort-low-to-high').on('click', function() {
        loadAllProducts({ sort: 'asc' });
    });
});

function renderProductList(products) {
    const productListContainer = $('#product-list');
    productListContainer.empty();

    console.log("Rendering products:", products);

    if (products.length === 0) {
        productListContainer.append('<p>No products found.</p>');
        return;
    }

    let row;

    products.forEach((product, index) => {
        if (index % 5 === 0) {
            row = $('<div class="row mb-4"></div>');
            productListContainer.append(row);
        }

        row.append(`
            <div class="col-6 col-md-4 col-lg-2 mb-4 product-item" data-product-id="${product.productId}">
                <div class="card text-center h-100">
                    <img onclick="viewProductDetail(${product.productId})" src="${product.productImageUrl}" alt="${product.productName}" class="card-img-top" style="cursor: pointer;">
                    <div class="card-body">
                        <h5 class="card-title text-truncate">${product.productName}</h5>
                        <p class="card-text text-danger">${product.price} đ</p>
                        <button class="btn btn-primary" onclick="addToCart(${product.productId})">Add to Cart</button>
                    </div>
                </div>
            </div>
        `);
    });
}

function viewProductDetail(id){
    window.location.href = `/productDetail?id=${id}`;
}

function addToCart(id) {
    const cartItemDTO = {
        productId: id,
        quantity: 1
    };

    $.ajax({
        url: '/add-to-cart',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(cartItemDTO),
        success: function(response) {
            if (response && response.message == 'Success') {
                Swal.fire({
                    title: "Item Added to Cart",
                    icon: "success",
                    text: "The item has been successfully added to your cart.",
                    confirmButtonText: "OK",
                });
                $('#countItemCartHeder').empty();
                $('#countItemCartHeder').append(`(${response.data})`);
            } else {
                Swal.fire({
                    title: "Failed to Add Item",
                    icon: "warning",
                    text: "An issue occurred while adding the item. Please check your input and try again.",
                    confirmButtonText: "OK",
                });
            }
        },
        error: function(error) {
            console.error("Error adding to cart:", error);
            Swal.fire({
                title: "Add to Cart Error",
                icon: "error",
                text: "There was an error adding the item to your cart. Please try again later.",
                confirmButtonText: "OK",
            });
        }
    });
}
