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
        totalAmount = 0;
        var cartItemsHTML = cartItems.map(function (item) {
            totalAmount += item?.productPrice * item?.quantity;
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
                            <input type="number" value="${item.quantity}" 
                            onchange="handleQuantityChange(event, '${item.productId}')" min="1" id="quantity-${item.id}">
                            <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity + 1}')">+</button>
                        </div>
                        <button class="btn btn-danger" onclick="updateItem('${item.productId}','0')"> Xoá
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                `;
        }).join('');
        document.getElementById('priceCart').innerText = totalAmount.toLocaleString() + ' ₫'
        // $('#totlePriceCart').empty(); // Xóa nội dung cũ
        // $('#totlePriceCart').append(`${totlePriceCart.toLocaleString()} ₫`);

        document.getElementById('cart-items').innerHTML = cartItemsHTML;

        // calculateTotal();
    }
}

function  handleQuantityChange(event, productId){
    if(event && event.target.value){
        updateItem(productId, event.target.value)
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
                console.log('1')
                fetchCartItems();
                Swal.fire({
                    title: "Cập nhật giỏ hàng thành công",
                    icon: "success",
                    text: "Sản phẩm đã được cập nhật thành công",
                    confirmButtonText: "OK",
                });
                // Xóa sản phẩm khỏi giao diện

            } else {
                Swal.fire({
                    title: "Cập nhật giỏ hàng thất bại",
                    icon: "warning",
                    text: "Đã xảy ra lỗi khi cập nhật sản phẩm. Vui lòng thử lại.",
                    confirmButtonText: "OK",
                });
                console.log('2')
            }
            console.log('3', response)
        },
        error: function(error) {
            console.error("Error removing item from cart:", error);
            Swal.fire({
                title: "Lỗi khi cập nhật giỏ hàng",
                icon: "error",
                text: "Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng. Vui lòng thử lại sau.",
                confirmButtonText: "OK",
            });
        }
    });
}
var orderId = "";
var totalAmount = 0;

function payment() {
    // Sample data for demonstration purposes
    if(totalAmount === 0){
        return;
    }

    // Clear the container
    document.getElementById('containerCart').innerHTML = '';
    const bankId = '970415';
    const soTaiKhoan = '113366668888';
    const noiDung = 'Thanh toan don hang mobile shop';
    const accountName = 'nhinnhin';

    // Generate the payment section HTML
    const paymentHTML = `
        <div class="card text-center mx-auto" style="max-width: 400px;">
            <div class="card-header">
                Thanh toán
            </div>
            <div class="card-body">
                <h5 class="card-title">Quét mã QR để thanh toán</h5>
                <p class="card-text text-danger">Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã QR bên dưới.</p>

                <!-- Order Details Section -->
                <div class="order-details mb-3 text-start">
                    <p><strong>Mã đơn hàng:</strong> ${orderId}</p>
                    <p><strong>Tổng số tiền:</strong> ${totalAmount}</p>
                </div>

                <!-- QR Code Placeholder -->
                <div class="d-flex justify-content-center mb-3">
                    <img src="https://img.vietqr.io/image/${bankId}-${soTaiKhoan}-compact2.jpg?amount=${totalAmount}&addInfo=${noiDung}&accountName=${accountName}" alt="QR Code" class="img-fluid" style="max-width: 150px;">
                </div>

                <button class="btn btn-success w-100" onclick="confirmOrder()">Xác nhận đơn hàng</button>
            </div>
            <div class="card-footer text-muted">
                Cảm ơn bạn đã mua sắm cùng chúng tôi!
            </div>
        </div>
    `;
    //https://vietqr.io/danh-sach-api/link-tao-ma-nhanh/
    // Insert the payment HTML into the containerCart
    document.getElementById('containerCart').innerHTML = paymentHTML;
}

function confirmOrder() {
    Swal.fire({
        title: "Đơn hàng đã được xác nhận",
        icon: "success",
        text: "Cảm ơn bạn đã đặt hàng. Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.",
        confirmButtonText: "OK",
    });
}



// Khởi tạo giỏ hàng
renderCartItems();