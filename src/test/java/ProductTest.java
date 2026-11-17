import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest{

	private InventoryManager manager;
	private Product product;

	@BeforeEach
	public void setUp()
	{
		manager = new InventoryManager();
		product = new Product(1,"iPhone", "Electronics", 999.0);
	}

	@Test
	public void getPriceReturnsCorrectPrice()
	{
		double price = product.getPrice();
		assertEquals(999, price, 0.001);
	}
	@Test
	public void constructorRejectsNegativePrice()
	{
		assertThrows(IllegalArgumentException.class, ()->
		{
			Product test = new Product(2,"Macbook", "Electronics", -1);
		});
	}
	@Test
	public void addProductSuccessfully()
	{
		manager.addProduct(product);

		Product foundProduct = manager.getProduct(1).orElseThrow();

		assertSame(product, foundProduct);
	}
	@Test
	public void addProductRejectsDuplicateId()
	{
		Product secondProduct = new Product(1,"Macbook", "Electronics", 1500.0);
		
		manager.addProduct(product);
		assertThrows(IllegalArgumentException.class,()->
		{
			manager.addProduct(secondProduct);
		});
	}
	@Test
	public void removeProductSuccessfully()
	{
		manager.addProduct(product);
		manager.removeProduct(1);

		// assertNull(manager.getProduct(1));
		assertTrue(manager.getProduct(1).isEmpty());
	}
	@Test
	public void updateProductPriceSuccessfully()
	{
		manager.addProduct(product);
		manager.updateProductPrice(1, 899.0);

		assertEquals(899.0,manager.getProduct(1).orElseThrow().getPrice(), 0.0001);
	}
	@Test
	public void updateProductPriceRejectsNegativePrice()
	{
		manager.addProduct(product);

		assertThrows(IllegalArgumentException.class, ()->
		{
			manager.updateProductPrice(1, -50);
		});
	}
	@Test
	public void getProductReturnsNullForUnknownId()
	{
		// assertNull(manager.getProduct(5).orElseThrow());
		assertTrue(manager.getProduct(5).isEmpty());

	}
	@Test
	public void getProductCountReturnsCorrectCount()
	{
		Product secondProduct = new Product(2,"Macbook", "Electronics", 1500.0);
		manager.addProduct(product);
		manager.addProduct(secondProduct);

		assertEquals(manager.getProductCount(), 2);
	}
	@Test
	public void removeProductThrowsExceptionForUnknownId()
	{
		assertThrows(IllegalArgumentException.class, ()->
		{
			manager.removeProduct(1);
		});
	}
	@Test
	public void addProductRejectsNull()
	{
		assertThrows(IllegalArgumentException.class, ()->
		{
			manager.addProduct(null);
		});
	}
	@Test
	public void getProductReturnsEmptyOptionalForUnknownId()
	{
		assertTrue(manager.getProduct(999).isEmpty());
	}
	@Test
	public void getProductReturnsOptionalForKnownId()
	{
		manager.addProduct(product);
		// assertFalse(manager.getProduct(1).isEmpty());
		assertSame(product, manager.getProduct(1).orElseThrow());
	}
	@Test
	public void getProductsByCategoryReturnsMatchingProducts()
	{
		manager.addProduct(product);
		manager.addProduct(new Product(2,"Macbook", "Electronics", 1400.0));
		manager.addProduct(new Product(3,"Nike", "Clothing", 150.0));

		assertEquals(2, manager.getProductsByCategory("Electronics").size());
	}
	@Test
	public void getProductsByNameIsCaseInsensitive()
	{
		manager.addProduct(product);
		manager.addProduct(new Product(2,"Macbook", "Electronics", 1400.0));

		assertEquals(1, manager.getProductsByName("MAC").size());
	}
	public void getProductsByNameRejectsBlankKeyword()
	{
		assertThrows(IllegalArgumentException.class, ()->
		{
			List<Product> productList = manager.getProductsByName("  ");
		});
	}
}