package fptu.mobile_shop.MobileShop.dto.jsonDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@lombok.Data
@Getter
@Setter
public class DataPayment {
    private int page;
    private int pageSize;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalRecords;
    private List<RecordPayment> records;

}
