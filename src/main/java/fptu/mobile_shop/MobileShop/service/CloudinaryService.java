package fptu.mobile_shop.MobileShop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class CloudinaryService {
    private Cloudinary cloudinary;

    public CloudinaryService() {
        // Cấu hình Cloudinary với các thông tin lấy từ tài khoản của bạn
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvshotakm",
                "api_key", "581814954497878",
                "api_secret", "pinCXTOZHV2ZT45X0Q2rGga1FNo"));
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        // Tạo file tạm thời
        File tempFile = File.createTempFile("temp-", multipartFile.getOriginalFilename());

        // Ghi dữ liệu từ MultipartFile vào file tạm
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }

        // Gọi Cloudinary để upload file
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        // Xóa file tạm sau khi upload thành công
        tempFile.delete();

        // Lấy URL của file từ kết quả trả về
        String url = (String) uploadResult.get("url");         // URL không bảo mật
        String secureUrl = (String) uploadResult.get("secure_url"); // URL bảo mật (HTTPS)

        return secureUrl;  // Trả về URL bảo mật của file vừa upload
    }
}
