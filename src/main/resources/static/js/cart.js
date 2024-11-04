$(document).ready(function () {
    fetchCartItems();
});

function fetchCartItems() {
    fetch('/view-cart')
        .then(response => response.json())
        .then(data => {
            cartItems = data.data;
            renderCartItems();
        })
        .catch(error => console.error('Error fetching cart items:', error));
}

// Hàm tính tổng tiền của các sản phẩm được tick
function calculateTotal() {
    var total = 0;
    cartItems.forEach(function (item) {
        if (item.selected) {
            total += item.productPrice * item.productStock;
        }
    });
    document.querySelector('.cart-total').innerText = total.toLocaleString() + '₫';
}

// Hàm để render các sản phẩm trong giỏ hàng
function renderCartItems() {
    if(cartItems){
        let  totlePriceCart = 0;
        var cartItemsHTML = cartItems.map(function (item) {
            totlePriceCart += item?.productPrice * item?.quantity;
            return `
                    <div class="cart-item">
<!--                        <input type="checkbox" checked onchange="toggleSelect(${item.id})">-->
                        <div class="cart-item-image">
                            <img src="${item.image}" alt="${item.productName}">
                        </div>
                        <div class="cart-item-details">
                            <h4>${item.productName}</h4>
                            <p>
                                <span class="price">${item.productPrice.toLocaleString()}₫</span>
                            </p>
                             
                        </div>
                        <div class="quantity">
                            <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity - 1}')">-</button>
                            <input type="number" value="${item.quantity}" min="1" id="quantity-${item.id}" readonly>
                            <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity + 1}')">+</button>
                        </div>
                        <button class="btn btn-danger" onclick="updateItem('${item.productId}','0')">
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                `;
        }).join('');
        console.log('totlePriceCart', totlePriceCart)
        document.getElementById('priceCart').innerText = totlePriceCart.toLocaleString() + ' ₫'
        // $('#totlePriceCart').empty(); // Xóa nội dung cũ
        // $('#totlePriceCart').append(`${totlePriceCart.toLocaleString()} ₫`);

        document.getElementById('cart-items').innerHTML = cartItemsHTML;

        // calculateTotal();
    }
}

// Hàm tăng số lượng sản phẩm
function increaseQuantity(itemId) {
    var item = cartItems.find(function (item) {
        return item.productId === itemId;
    });

    if (item) {
        item.productStock++;
        renderCartItems();
    }
}

// Hàm giảm số lượng sản phẩm
function decreaseQuantity(itemId) {
    var item = cartItems.find(function (item) {
        return item.productId === itemId;
    });

    if (item && item.quantity > 1) {
        item.productStock--;
        renderCartItems();
    }
}

function updateItem(productId, quantity) {
    $.ajax({
        url: '/update-quantity', // API endpoint
        method: 'POST',
        data: {
            productId: productId,
            quantity: quantity // Đặt số lượng bằng 0 để xóa sản phẩm
        },
        success: function(response) {
            if (response && response.message === 'Success') {
                Swal.fire({
                    title: "Đã xóa sản phẩm khỏi giỏ hàng",
                    icon: "success",
                    text: "Sản phẩm đã được xóa thành công khỏi giỏ hàng của bạn.",
                    confirmButtonText: "OK",
                });
                // Xóa sản phẩm khỏi giao diện
                fetchCartItems();
            } else {
                Swal.fire({
                    title: "Không thể xóa sản phẩm",
                    icon: "warning",
                    text: "Đã xảy ra lỗi khi xóa sản phẩm. Vui lòng thử lại.",
                    confirmButtonText: "OK",
                });
            }
        },
        error: function(error) {
            console.error("Error removing item from cart:", error);
            Swal.fire({
                title: "Lỗi khi xóa sản phẩm",
                icon: "error",
                text: "Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng. Vui lòng thử lại sau.",
                confirmButtonText: "OK",
            });
        }
    });
}



// Hàm bật/tắt chọn sản phẩm
function toggleSelect(itemId) {
    var item = cartItems.find(function (item) {
        return item.productId === itemId;
    });

    if (item) {
        item.selected = !item.selected; // Đảo trạng thái của selected
        calculateTotal(); // Tính tổng tiền lại
    }
}

// Khởi tạo giỏ hàng
renderCartItems();