function validateForm(event, element) {
    let isValid = true;

    // Clear previous errors
    $(".error").text("");
    $(".text-danger").text("");

    // Validate first name
    let firstName = $('#firstName').val().trim();
    if (firstName === "") {
        $("#firstNameError").text("Tên là bắt buộc.");
        isValid = false;
    }

    // Validate last name
    let lastName = $('#lastName').val().trim();
    if (lastName === "") {
        $("#lastNameError").text("Họ là bắt buộc.");
        isValid = false;
    }

    // Validate phone number
    let phoneNumber = $('#phoneNumber').val().trim();
    let phonePattern = /^0\d{9}$/;
    if (phoneNumber === "") {
        $("#phoneNumberError").text("Số điện thoại là bắt buộc.");
        isValid = false;
    } else if (!phonePattern.test(phoneNumber)) {
        $("#phoneNumberError").text("Số điện thoại phải dài 10 chữ số và bắt đầu bằng số 0.");
        isValid = false;
    }

    // Validate email
    let email = $('#email').val().trim();
    let emailPattern = /^[a-zA-Z0-9._-]+@gmail\.com$/;
    if (email === "") {
        $("#emailError").text("Địa chỉ email là bắt buộc.");
        isValid = false;
    } else if (!emailPattern.test(email)) {
        $("#emailError").text("Email phải là địa chỉ Gmail hợp lệ (example@gmail.com).");
        isValid = false;
    }

    // Validate address
    let address1 = $('#address1').val().trim();
    if (address1 === "") {
        $("#address1Error").text("Địa chỉ là bắt buộc.");
        isValid = false;
    }

    // Validate province
    let province = $('#toProvinceSelect').val();
    if (province === "") {
        $("#toProvinceSelect-error").text("Tỉnh thành là bắt buộc.");
        isValid = false;
    }

    // Validate district
    let district = $('#toDistrictSelect').val();
    if (district === "") {
        $("#toDistrictSelect-error").text("Quận/huyện là bắt buộc.");
        isValid = false;
    }

    // Validate ward
    let ward = $('#toWardSelect').val();
    if (ward === "") {
        $("#toWardSelect-error").text("Phường là bắt buộc.");
        isValid = false;
    }

    // Prevent form submission if validation failed
    if (!isValid) {
        console.log('Biểu mẫu không hợp lệ, không thể nộp.'); // Debugging statement
        event.preventDefault();
        // Re-enable the submit button in case of validation failure
        $("#proceedToPayment").prop('disabled', false);
    } else {
        console.log('Biểu mẫu hợp lệ, đang nộp...'); // Debugging statement
        // Disable the submit button only when the form is valid and is being submitted
        $("#proceedToPayment").prop('disabled', true);
        element.submit();
    }
}