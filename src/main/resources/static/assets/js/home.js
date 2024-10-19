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
                                <div class="card text-center">
                                    <img src="${product.productImageUrl}" alt="${product.productName}" class="card-img-top" style="cursor: pointer;">
                                    <div class="card-body">
                                        <h5 class="card-title mb-0">${product.productName}</h5>
                                        <p class="card-text text-danger mb-0">${product.price} đ</p>
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

    $(document).on('click', '.card', function() {
        const productId = $(this).closest('.product-item').data('product-id');
        window.location.href = `/productDetail?id=${productId}`;
    });
});
