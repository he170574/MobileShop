$(document).ready(function () {
    loadProvinces();

    // Initialize Select2 on the dropdowns
    $('#province, #district, #ward').select2({
        placeholder: 'Chọn...',
        allowClear: true,
        width: '100%' // Ensures the dropdown matches the input width
    });

    // Load provinces
    function loadProvinces() {
        $.getJSON('/provinces', function (data) {
            const provinceSelect = $('#province');
            provinceSelect.empty().append(new Option('Tỉnh/Thành phố (*)', ''));
            data.forEach(function (province) {
                provinceSelect.append(new Option(province.name, province.id));
            });
        }).fail(function () {
            console.error('Error loading provinces');
        });
    }

    // Load districts based on selected province
    $('#province').on('change', function () {
        const provinceId = $(this).val();
        if (provinceId) {
            loadDistricts(provinceId);
        } else {
            $('#district').empty().append(new Option('Quận/Huyện (*)', ''));
            $('#ward').empty().append(new Option('Phường/Xã (*)', ''));
        }
    });

    function loadDistricts(provinceId) {
        $.getJSON(`/provinces/${provinceId}/districts`, function (data) {
            const districtSelect = $('#district');
            districtSelect.empty().append(new Option('Quận/Huyện (*)', ''));
            data.forEach(function (district) {
                districtSelect.append(new Option(district.name, district.id));
            });
        }).fail(function () {
            console.error('Error loading districts');
        });
    }

    // Load wards based on selected district
    $('#district').on('change', function () {
        const districtId = $(this).val();
        if (districtId) {
            loadWards(districtId);
        } else {
            $('#ward').empty().append(new Option('Phường/Xã (*)', ''));
        }
    });

    function loadWards(districtId) {
        $.getJSON(`/districts/${districtId}/wards`, function (data) {
            const wardSelect = $('#ward');
            wardSelect.empty().append(new Option('Phường/Xã (*)', ''));
            data.forEach(function (ward) {
                wardSelect.append(new Option(ward.name, ward.id));
            });
        }).fail(function () {
            console.error('Error loading wards');
        });
    }

    // Toggle delivery details based on selection
    $('input[name="delivery"]').on('change', function () {
        if ($('#homeDelivery').is(':checked')) {
            $('#homeDeliveryDetails').show();
        } else {
            $('#homeDeliveryDetails').hide();
        }
    });

    // Initialize the delivery details visibility
    if ($('#homeDelivery').is(':checked')) {
        $('#homeDeliveryDetails').show();
    } else {
        $('#homeDeliveryDetails').hide();
    }

});
