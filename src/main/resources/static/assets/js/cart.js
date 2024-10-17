
function fetchCartItems() {
    // Giả sử bạn có một API để lấy sản phẩm
    fetch('/api/cart-items')
        .then(response => response.json())
        .then(data => {
            cartItems = data; // Gán dữ liệu sản phẩm vào biến cartItems
            renderCartItems(); // Render lại giỏ hàng
        })
        .catch(error => console.error('Error fetching cart items:', error));
}

// Hàm tính tổng tiền của các sản phẩm được tick
function calculateTotal() {
    var total = 0;
    cartItems.forEach(function (item) {
        if (item.selected) {
            total += item.price * item.quantity;
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
                        <span class="price">${item.price.toLocaleString()}₫</span>
                        <span class="original-price">${item.originalPrice.toLocaleString()}₫</span>
                    </p>
                    <p class="promotion">
                        <i class="fa fa-gift icon"></i>Chương trình khuyến mãi: ${item.promotion}
                    </p>
                    <div class="warranty-section">
                        <i class="fa fa-shield icon"></i>Bảo vệ: ${item.warranty} <span class="choose-warranty">Chọn gói ></span>
                    </div>
                </div>
                <div class="quantity">
                    <button class="btn btn-secondary" onclick="decreaseQuantity(${item.id})">-</button>
                    <input type="number" value="${item.quantity}" min="1" id="quantity-${item.id}" readonly>
                    <button class="btn btn-secondary" onclick="increaseQuantity(${item.id})">+</button>
                </div>
                <button class="btn btn-danger" onclick="removeItem(${item.id})">
                    <i class="fa fa-trash"></i>
                </button>
            </div>
        `;
    }).join('');

    document.getElementById('cart-items').innerHTML = cartItemsHTML;
    calculateTotal(); // Tính tổng tiền sau khi render lại
}

// Hàm tăng số lượng sản phẩm
function increaseQuantity(itemId) {
    var item = cartItems.find(function (item) {
        return item.id === itemId;
    });

    if (item) {
        item.quantity++;
        renderCartItems();
    }
}

// Hàm giảm số lượng sản phẩm
function decreaseQuantity(itemId) {
    var item = cartItems.find(function (item) {
        return item.id === itemId;
    });

    if (item && item.quantity > 1) {
        item.quantity--;
        renderCartItems();
    }
}

// Hàm xóa sản phẩm
function removeItem(itemId) {
    cartItems = cartItems.filter(function (item) {
        return item.id !== itemId;
    });

    renderCartItems();
}

// Hàm bật/tắt chọn sản phẩm
function toggleSelect(itemId) {
    var item = cartItems.find(function (item) {
        return item.id === itemId;
    });

    if (item) {
        item.selected = !item.selected; // Đảo trạng thái của selected
        calculateTotal(); // Tính tổng tiền lại
    }
}

// Khởi tạo giỏ hàng
renderCartItems();
