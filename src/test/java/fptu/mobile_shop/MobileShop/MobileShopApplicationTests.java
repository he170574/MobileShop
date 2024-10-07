package fptu.mobile_shop.MobileShop;

import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MobileShopApplicationTests {

	@Autowired
	ProductRepository productRepository;
	@Test
	void contextLoads() {
		List<Product> products = productRepository.findAll();
		System.out.println(products.toArray());
	}

}
