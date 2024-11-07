package fptu.mobile_shop.MobileShop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryService {
    private Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvshotakm",
                "api_key", "581814954497878",
                "api_secret", "pinCXTOZHV2ZT45X0Q2rGga1FNo"));
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("temp-", multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }

        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        tempFile.delete();

        String url = (String) uploadResult.get("url");
        String secureUrl = (String) uploadResult.get("secure_url");

        return secureUrl;
    }
}
