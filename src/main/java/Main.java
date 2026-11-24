import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public class Main
{
	public static void main(String[] args)
	{
		String password = System.getenv("DB_PASSWORD");

    	try
    	{
    		DatabaseConnection db = new DatabaseConnection(
			"jdbc:postgresql://localhost:5432/product_inventory",
    		"product_app",
    		password);
    		ProductRepository repository = new ProductRepository(db);
    		InventoryManager manager = new InventoryManager(repository);
    		//get product by id
    		Optional<Product> found = manager.getProduct(1);
    		if (found.isPresent()) {
    		    System.out.println("Found: " + found.get());
    		} else {
    		    System.out.println("Product not found");
    		}
    		manager.displayAllProducts();
    		
    		//update price of product
    		manager.updateProductPrice(1, 799.99);

    		//get product after price change
    		Optional<Product> updatedProduct = manager.getProduct(1);
    		System.out.println(updatedProduct.isPresent()? updatedProduct.get(): "product doesn't exist");

    		//display products count
    		System.out.println("Total products: " + manager.getProductCount());

    		System.out.println("Products in Electronics:");
    		manager.getProductsByCategory("Electronics").forEach(System.out::println);

    		System.out.println("Products cheaper than 1000:");
    		manager.getProductsCheaperThan(1000).forEach(System.out::println);

    		System.out.println("Products matching 'phone':");
    		manager.getProductsByName("phone").forEach(System.out::println);

    		Product duplicate = new Product(1, "Samsung", "Electronics", 799.99);
    		manager.addProduct(duplicate);
    	}
    	catch(DuplicateProductException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	catch(SQLException e)
    	{
    		System.out.println("failed to save product" + e.getMessage());
    	}


	}
}