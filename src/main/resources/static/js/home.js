$(document).ready(function() {
    function loadAllProducts(filters = {}) {
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

    // Initial load of all products
    loadAllProducts();

    // Category Filter
    $('.category').on('click', function() {
        const category = $(this).data('category'); // Get the selected category (apple, samsung, or xiaomi)
        loadAllProducts({ category: category });
    });

    // Price Range Filter
    const priceSlider = document.getElementById('price-slider');
    noUiSlider.create(priceSlider, {
        start: [0, 1000000],
        connect: true,
        range: { 'min': 0, 'max': 1000000 },
        tooltips: true,
        format: wNumb({ decimals: 0, thousand: ',', suffix: ' đ' })
    });

    // Toggle price dropdown
    $('#toggle-price').on('click', function() {
        $('#price-dropdown').toggle();
    });

    // Apply price filter
    $('#apply-price-filter').on('click', function() {
        const [minPrice, maxPrice] = priceSlider.noUiSlider.get();
        $('#price-dropdown').hide();
        loadAllProducts({
            minPrice: parseFloat(minPrice.replace(/ đ/g, '').replace(/,/g, '')),
            maxPrice: parseFloat(maxPrice.replace(/ đ/g, '').replace(/,/g, ''))
        });
    });

    // Search by product name
    $('#search-button').on('click', function() {
        const productName = $('#search-input').val();
        loadAllProducts({ search: productName });
    });

    // Sorting by price: high to low and low to high
    $('#sort-high-to-low').on('click', function() {
        loadAllProducts({ sort: 'desc' });
    });

    $('#sort-low-to-high').on('click', function() {
        loadAllProducts({ sort: 'asc' });
    });
});

function renderProductList(products) {
    $('#product-list').empty();
    let row;

    products.forEach((product, index) => {
        if (index % 5 === 0) {
            row = $('<div class="row mb-4"></div>');
            $('#product-list').append(row);
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
    // Define the CartItemDTO object
    const cartItemDTO = {
        productId: id,
        quantity: 1 // Adjust the quantity as necessary
    };

    $.ajax({
        url: '/add-to-cart', // API endpoint
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(cartItemDTO), // Convert the object to JSON
        success: function(response) {
            if (response && response.message == 'Success') {
                Swal.fire({
                    title: "Item Added to Cart",
                    icon: "success",
                    text: "The item has been successfully added to your cart.",
                    confirmButtonText: "OK",
                });
                $('#countItemCartHeder').empty(); // Xóa nội dung cũ
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
