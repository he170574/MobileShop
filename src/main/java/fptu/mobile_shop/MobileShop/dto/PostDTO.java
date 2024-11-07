package fptu.mobile_shop.MobileShop.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @NotNull(message = "Title cannot be null")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotNull(message = "Content cannot be null")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;

    @NotNull(message = "Brief info cannot be null")
    @Size(min = 10, max = 200, message = "Brief info must be between 10 and 200 characters")
    private String briefInfo;
    private MultipartFile thumbnail; // Đường dẫn của thumbnail
    private Long categoryPostID; // ID of the category (instead of category name)

    private Long authorID; // ID of the author (instead of full author info)
}
