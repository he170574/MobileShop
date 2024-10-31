$(document).ready(function() {
    function loadAllProducts() {
        $.ajax({
            url: '/get-product', // Đường dẫn đến endpoint
            method: 'GET',
            success: function(response) {
                if (response && response.data && Array.isArray(response.data)) {
                    $('#product-list').empty(); // Xóa nội dung cũ

                    // Duyệt qua từng sản phẩm và thêm vào danh sách
                    response.data.forEach(function(product) {
                        $('#product-list').append(`
                            <div class="col-6 col-md-4 col-lg-2 mb-4 product-item" data-product-id="${product.productId}">
                                <div class="card text-center h-100 d-flex flex-column justify-content-between">
                                    <img onclick="viewProductDetail(${product.productId})" src="${product.productImageUrl}" alt="${product.productName}" class="card-img-top" style="cursor: pointer;">
                                    <div class="card-body d-flex flex-column justify-content-between">
                                        <h5 onclick="viewProductDetail(${product.productId})" class="card-title mb-2 text-truncate" style="height: 3em; line-height: 1.5em;">${product.productName}</h5>
                                        <p onclick="viewProductDetail(${product.productId})" class="card-text text-danger mb-2">${product.price} đ</p>
                                        <button class="btn btn-primary w-100 mt-auto" onclick="addToCart(${product.productId})">Add to Cart</button>
                                    </div>
                                </div>
                            </div>
                        `);
                    });


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

    loadAllProducts();

    // $(document).on('click', '.card', function() {
    //     const productId = $(this).closest('.product-item').data('product-id');
    //     window.location.href = `/productDetail?id=${productId}`;
    // });
});
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
