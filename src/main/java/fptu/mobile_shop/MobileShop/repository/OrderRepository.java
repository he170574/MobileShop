package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.OrderListManageFilterRequest;
import fptu.mobile_shop.MobileShop.entity.Order;
import fptu.mobile_shop.MobileShop.final_attribute.STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
            select x1 from Order x1
            left join x1.account x2
            where true
            and (:#{#request.keyword} is null or CONCAT(coalesce(x1.orderCode,''), x2.fullName) like CONCAT('%',:#{#request.keyword},'%'))
            and ( :#{#request.accountId} is null or x2.accountId = :#{#request.accountId})
            and ( :#{#request.orderStatus} is null or x1.orderStatus = :#{#request.orderStatus})
            """, countQuery = """
             select COUNT(x1.id) from Order x1
                        left join x1.account x2
                        where true
                        and (:#{#request.keyword} is null or CONCAT(coalesce(x1.orderCode,''), x2.fullName) like CONCAT('%',:#{#request.keyword},'%'))
                        and ( :#{#request.accountId} is null or x2.accountId = :#{#request.accountId})
                        and ( :#{#request.orderStatus} is null or x1.orderStatus = :#{#request.orderStatus})
            """)
    Page<Order> getListOrdersManage(OrderListManageFilterRequest request, Pageable pageable);

    @Query(value = "SELECT o FROM Order o",
            countQuery = "SELECT COUNT(o.id) FROM Order o")
    Page<Order> getListAllOrder(Pageable pageable);

    @Query(value = "SELECT o FROM Order o where o.account.accountId = :accountId",
            countQuery = "SELECT COUNT(o.id) FROM Order o where o.account.accountId = :accountId")
    Page<Order> getListAllOrderUser(Pageable pageable, Integer accountId);

    @Query(value = "select distinct i from Order i where i.orderStatus = 'WAITING_DELIVERY' " +
            "and i.shippingCode is not null and i.account.accountId = :userId ")
    List<Order> getAllOrderByUserId(Long userId);

    @Query(value = "select distinct i from Order i where i.orderStatus = 'WAITING_DELIVERY' and i.shippingCode is not null")
    List<Order> getAllOrderShipping();
}
