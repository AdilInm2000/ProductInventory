import java.sql.SQLException;
import java.util.Optional;
import java.util.HashMap; 
import java.util.Map; 
import java.util.List;
import java.util.stream.Collectors; 



public class InventoryManager
{
	private final ProductRepository repository;

	public InventoryManager(ProductRepository repository)
	{
		this.repository = repository;
	}

	public void addProduct(Product product) throws SQLException
	{
		if (product == null) throw new IllegalArgumentException("Product cannot be null");
		repository.saveProduct(product);		
	}

	/*public Product getProduct(long id)
	{
		if(inventory.containsKey(id))
		{
			return inventory.get(id);
		}
		else return null;
	}*/
	public Optional<Product> getProduct(long id) throws SQLException
	{
    	return Optional.ofNullable(repository.getProductById(id));
	}

	public void removeProduct(long id) throws SQLException
	{
		if(!repository.deleteProduct(id)) throw new IllegalArgumentException("Product doesn't exist");
	}

	public void displayAllProducts() throws SQLException
	{
		List<Product> products = repository.getAllProducts();
		if (products.isEmpty())
		{
			System.out.println("Empty Inventory");
		}
		else
		{
			for (Product p : products)
			{
				System.out.println(p);
			}
		}
	}
	public int getProductCount() throws SQLException
	{
		return (repository.getProductCount());
	}

	/*public void updateProductPrice(long id, double newPrice)
	{
		Optional<Product> p = this.getProduct(id);
		if(p.isPresent())
		{
			p.get().setPrice(newPrice);	
		}
		else
			throw new IllegalArgumentException("Product doesn't exist");
	}*/
	public void updateProductPrice(long id, double newPrice) throws SQLException
	{
		if(!repository.updateProductPrice(id, newPrice)) throw new IllegalArgumentException("Product doesn't exist");
	}

	public List<Product> getProductsByCategory(String category) throws SQLException
	{
		return repository.getProductsByCategory(category);
	}
	public List<Product> getProductsCheaperThan(double maxPrice) throws SQLException
	{
		return repository.getProductsCheaperThan(maxPrice);
	}
	public List<Product> getProductsByName(String keyword) throws SQLException
	{
		if (keyword == null || keyword.isBlank()) throw new IllegalArgumentException("Empty keyword");

		return repository.getProductsByName(keyword);
	}

}