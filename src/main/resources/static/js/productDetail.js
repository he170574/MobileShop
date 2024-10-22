$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    if (productId) {
        $.ajax({
            url: '/get-product-detail',
            method: 'GET',
            data: { id: productId },
            success: function(product) {
                $('#product-name').text(product.productName);
                $('#product-image').attr('src', product.productImageUrl);
                $('#product-price').text(product.price + ' đ');
                $('#product-description').text(product.productDetails);
            },
            error: function(error) {
                console.error("Error fetching product details:", error);
                alert('Unable to fetch product details. Please try again later.');
            }
        });
    } else {
        alert('Product ID is missing.');
    }
});
