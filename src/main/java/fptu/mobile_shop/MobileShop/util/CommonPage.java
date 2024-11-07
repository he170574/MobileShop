package fptu.mobile_shop.MobileShop.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class CommonPage {
    public static Pageable pageWithSort(Integer pageNum, Integer pageSize, Sort sort) {
        int offset = Optional.of(pageNum).map(p -> {
            if (p > 0) return p - 1;
            else return 0;
        }).orElse(0);
        return PageRequest.of(offset, pageSize, sort);
    }
}
