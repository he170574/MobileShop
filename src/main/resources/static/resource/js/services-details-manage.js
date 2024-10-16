/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Lấy danh sách các tab link trong navbar
    const tabLinks = document.querySelectorAll('.other');
    // Lấy danh sách các tab content
    const tabContents = document.querySelectorAll('.tab-pane');
    // Lặp qua từng tab link và thêm sự kiện click
    tabLinks.forEach(function(link) {
        link.addEventListener('click', function(event) {
            // Ngăn chặn hành vi mặc định của liên kết
            event.preventDefault();
            // Xóa lớp active khỏi tất cả các tab link
            tabLinks.forEach(function(link) {
                link.classList.remove('active');
            });
            // Thêm lớp active vào tab link được nhấp vào
            this.classList.add('active');
            // Lấy id của tab content tương ứng
            const target = this.getAttribute('href');
            // Xóa lớp show khỏi tất cả các tab content
            tabContents.forEach(function(content) {
                content.classList.remove('show');
                content.classList.remove('active');
            });
            // Thêm lớp show vào tab content tương ứng
            document.querySelector(target).classList.add('show');
            document.querySelector(target).classList.add('active');
        });
    });
    
    function  sendToDetailManage(serviceID){
      window.location.href = "service?event=onoff-status&id="+serviceID;
  }