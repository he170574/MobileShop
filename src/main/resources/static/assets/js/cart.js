$(document).ready(function () {
    loadCartItems();

    // Load giỏ hàng
    function loadCartItems() {
        $.ajax({
            url: '/view-cart',
            method: 'GET',
            success: function (cart) {
                displayCartItems(cart.items);
                updateCartTotal(cart);
            },
            error: function (error) {
                console.error('Error fetching cart:', error);
            }
        });
    }

    // Hiển thị sản phẩm trong giỏ hàng
    function displayCartItems(items) {
        const cartItemsContainer = $('#cart-items');
        cartItemsContainer.empty(); // Clear old items

        items.forEach(function (item) {
            const itemRow = `
                <tr>
                    <td><img src="${item.product.productImage}" alt="${item.product.productName}"> ${item.product.productName}</td>
                    <td>${item.product.price} VND</td>
                    <td><input type="number" min="1" value="${item.quantity}" class="quantity-input" data-id="${item.id}"></td>
                    <td>${(item.product.price * item.quantity).toFixed(2)} VND</td>
                    <td>
                        <button class="btn btn-danger btn-remove" data-id="${item.id}">Xóa</button>
                    </td>
                </tr>
            `;
            cartItemsContainer.append(itemRow);
        });

        // Cập nhật số lượng khi người dùng thay đổi input
        $('.quantity-input').on('change', function () {
            const cartItemId = $(this).data('id');
            const newQuantity = $(this).val();
            updateCartItem(cartItemId, newQuantity);
        });

        // Xóa sản phẩm khi nhấn nút Xóa
        $('.btn-remove').on('click', function () {
            const cartItemId = $(this).data('id');
            removeCartItem(cartItemId);
        });
    }

    // Cập nhật tổng tiền giỏ hàng
    function updateCartTotal(cart) {
        const total = cart.items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
        $('#cart-total').text(total.toFixed(2));
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    function updateCartItem(cartItemId, newQuantity) {
        $.ajax({
            url: '/update-quantity',
            method: 'POST',
            data: {
                cartItemId: cartItemId,
                quantity: newQuantity
            },
            success: function (cart) {
                loadCartItems(); // Reload giỏ hàng sau khi cập nhật
            },
            error: function (error) {
                console.error('Error updating cart item:', error);
            }
        });
    }

    // Xóa sản phẩm khỏi giỏ hàng
    function removeCartItem(cartItemId) {
        $.ajax({
            url: '/remove-cart',
            method: 'POST',
            data: {cartItemId: cartItemId},
            success: function (cart) {
                loadCartItems(); // Reload giỏ hàng sau khi xóa
            },
            error: function (error) {
                console.error('Error removing cart item:', error);
            }
        });
    }

    // Thanh toán
    $('#checkout-btn').on('click', function () {
        alert('Chức năng thanh toán đang được phát triển.');
    });
});
