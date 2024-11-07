package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.FeedbackFilterRequest;
import fptu.mobile_shop.MobileShop.dto.ProductCommentDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.service.FeedbackService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getListFeedback(@Validated FeedbackFilterRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        Page<ProductCommentDTO> response = feedbackService.getPageFeedback(request);
        responseDTO.setData(response.getContent());
        if (response.getContent().size() != 0) {
            responseDTO.setCurrentPage(response.getNumber() + 1);
        }
        responseDTO.setTotalPages(response.getTotalPages());
        responseDTO.setMessage("Success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/feedback-manage")
    public ResponseEntity<ResponseDTO> getListFeedbackManage(@Validated FeedbackFilterRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        var response = feedbackService.getListFeedbackManage(request);
        responseDTO.setData(response.getContent());
        if (response.getContent().size() != 0) {
            responseDTO.setCurrentPage(response.getNumber() + 1);
        }
        responseDTO.setTotalPages(response.getTotalPages());
        responseDTO.setMessage("Success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/{commentId}/detail")
    public ResponseEntity<ResponseDTO> getFeedbackDetail(@PathVariable("commentId") @NotNull() Integer commentId) {
        ResponseDTO responseDTO = new ResponseDTO();
        var response = feedbackService.getFeedbackDetail(commentId);
        responseDTO.setData(response);
        responseDTO.setMessage("Success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> creatProductComment(@Validated @RequestBody ProductCommentDTO request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            var response = feedbackService.creatProductComment(request);
            responseDTO.setData(response);
            responseDTO.setMessage("Success");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDTO> updateProductComment(
            @PathVariable("commentId") @NotNull() Integer commentId,
            @Validated @RequestBody ProductCommentDTO request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            var response = feedbackService.updateProductComment(commentId, request);
            responseDTO.setData(response);
            responseDTO.setMessage("Success");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO> deleteProductComment(@PathVariable("commentId") @NotNull() Integer commentId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            feedbackService.deleteProductComment(commentId);
            responseDTO.setMessage("Success");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

}
