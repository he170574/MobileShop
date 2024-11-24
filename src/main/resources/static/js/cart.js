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
//             return `
//                     <div class="cart-item">
// <!--                        <input type="checkbox" checked onchange="toggleSelect(${item.id})">-->
//                         <div class="cart-item-image">
//                             <img style="width: 50px; height:  80px" src="${item.image}" alt="${item.productName}">
//                         </div>
//                         <div class="cart-item-details">
//                             <h4>${item.productName}</h4>
//                             <p>
//                                 <span class="price">${item.productPrice.toLocaleString()}₫</span>
//                             </p>
//
//                         </div>
//                         <div class="quantity">
//                             <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity - 1}')">-</button>
//                             <input type="number" value="${item.quantity}"
//                             onchange="handleQuantityChange(event, '${item.productId}')" min="1" id="quantity-${item.id}">
//                             <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity + 1}')">+</button>
//                         </div>
//                         <button class="btn btn-danger" onclick="updateItem('${item.productId}','0')"> Delete
//                             <i class="fa fa-trash"></i>
//                         </button>
//                     </div>
//                 `;
            return `
            <div class="cart-item" style="display: flex; align-items: center; gap: 20px; padding: 10px 0; border-bottom: 1px solid #ccc;">
                <div class="cart-item-image" >
                    <img src="${item.image}" alt="${item.productName}">
                </div>
                <div class="cart-item-details" style="flex: 1;">
                    <h4 style="margin: 0;">${item.productName}</h4>
                    <p style="margin: 0;">
                        <span class="price">${item.productPrice.toLocaleString()}₫</span>
                    </p>
                </div>
                <div class="quantity" style="display: flex; align-items: center; gap: 5px;">
                    <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity - 1}')">-</button>
                    <input type="number" value="${item.quantity}" onchange="handleQuantityChange(event, '${item.productId}')"  id="quantity-${item.id}" min="1"
                    style="width: 50px; text-align: center;">
                    <button class="btn btn-secondary" onclick="updateItem('${item.productId}','${item.quantity + 1}')">+</button>
                </div>
                <button class="btn btn-danger" onclick="updateItem('${item.productId}','0')">Delete <i class="fa fa-trash"></i></button>
            </div>
            `
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
                // Xóa sản phẩm khỏi giao diện

            } else {
                Swal.fire({
                    title: "Cart update failed",
                    icon: "warning",
                    text: "An error occurred while updating the product. Please try again.",
                    confirmButtonText: "OK",
                });
                console.log('2')
            }
            console.log('3', response)
        },
        error: function(error) {
            console.error("Error removing item from cart:", error);
            Swal.fire({
                title: "Error updating cart",
                icon: "error",
                text: "An error occurred while updating the product. Please try again.",
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
                <h5 class="card-title">QR Code Payment</h5>
                <p class="card-text text-danger">Use your banking app or e-wallet to scan the QR code below.</p>

                <!-- Order Details Section -->
                <div class="order-details mb-3 text-start">
                    <p><strong>Order Code:</strong> ${orderId}</p>
                    <p><strong>Total Amount:</strong> ${totalAmount}</p>
                </div>

                <!-- QR Code Placeholder -->
                <div class="d-flex justify-content-center mb-3">
                    <img src="https://img.vietqr.io/image/${bankId}-${soTaiKhoan}-compact2.jpg?amount=${totalAmount}&addInfo=${noiDung}&accountName=${accountName}" alt="QR Code" class="img-fluid" style="max-width: 150px;">
                </div>

                <button class="btn btn-success w-100" onclick="confirmOrder()">Confirm Payment</button>
            </div>
            <div class="card-footer text-muted">
                Thank you for shopping with us!
            </div>
        </div>
    `;
    //https://vietqr.io/danh-sach-api/link-tao-ma-nhanh/
    // Insert the payment HTML into the containerCart
    document.getElementById('containerCart').innerHTML = paymentHTML;
}

function confirmOrder() {
    Swal.fire({
        title: "Order has been confirmed\n",
        icon: "success",
        text: "Cảm ơn bạn đã đặt hàng. Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.",
        confirmButtonText: "OK",
    });
}



// Khởi tạo giỏ hàng
renderCartItems();