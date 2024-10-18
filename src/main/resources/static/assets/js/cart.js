$(document).ready(function () {
    fetchCartItems();
});

function fetchCartItems() {
    fetch('/cart-items')
        .then(response => response.json())
        .then(data => {
            cartItems = data;
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
    var cartItemsHTML = cartItems.map(function (item) {
        return `
            <div class="cart-item">
                <input type="checkbox" ${item.selected ? 'checked' : ''} onchange="toggleSelect(${item.id})">
                <div class="cart-item-image">
                    <img src="${item.image}" alt="${item.name}">
                </div>
                <div class="cart-item-details">
                    <h4>${item.name}</h4>
                    <p>
                        <span class="price">${item.productPrice.toLocaleString()}₫</span>
                    </p>
                </div>
                <div class="quantity">
                    <button class="btn btn-secondary" onclick="decreaseQuantity(${item.productId})">-</button>
                    <input type="number" value="${item.productStock}" min="1" id="quantity-${item.productId}" readonly>
                    <button class="btn btn-secondary" onclick="increaseQuantity(${item.productId})">+</button>
                </div>
                <button class="btn btn-danger" onclick="removeItem(${item.productId})">
                    <i class="fa fa-trash"></i>
                </button>
            </div>
        `;
    }).join('');

    document.getElementById('cart-items').innerHTML = cartItemsHTML;
    calculateTotal();
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

// Hàm xóa sản phẩm
function removeItem(itemId) {
    cartItems = cartItems.filter(function (item) {
        return item.productId !== itemId;
    });

    renderCartItems();
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