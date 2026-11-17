import java.util.Optional;
import java.util.HashMap; 
import java.util.Map; 
import java.util.List;
import java.util.stream.Collectors; 



public class InventoryManager
{
	private Map<Long,Product> inventory;

	public InventoryManager()
	{
		inventory = new HashMap<>();
	}

	public void addProduct(Product product)
	{
		if (product == null) throw new IllegalArgumentException("Product cannot be null");
		if(inventory.containsKey(product.getId())) throw new IllegalArgumentException("Product with same id already exists");
		inventory.put(product.getId(), product);
	}

	/*public Product getProduct(long id)
	{
		if(inventory.containsKey(id))
		{
			return inventory.get(id);
		}
		else return null;
	}*/
	public Optional<Product> getProduct(long id)
	{
    	return Optional.ofNullable(inventory.get(id));
	}

	public void removeProduct(long id)
	{
		if(inventory.remove(id) == null) throw new IllegalArgumentException("Product doesn't exist");
	}

	public void displayAllProducts()
	{
		if(inventory.isEmpty())
			System.out.println("Empty Inventory");
		else
		{
			for(Product p : inventory.values())
			{
				System.out.println(p);
			}
		}
	}
	public int getProductCount()
	{
		return inventory.size();
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
	public void updateProductPrice(long id, double newPrice)
	{
		Optional<Product> p = this.getProduct(id);
		p.ifPresentOrElse(
		    product -> product.setPrice(newPrice),
		    () -> { throw new IllegalArgumentException("Product doesn't exist"); }
		);
	}

	public List<Product> getProductsByCategory(String category)
	{
		List<Product> productsList = inventory.values().stream()
		.filter(p -> p.getCategory().equals(category))
		.collect(Collectors.toList());
		return productsList;
	}
	public List<Product> getProductsCheaperThan(double maxPrice)
	{
		List<Product> productsList = inventory.values().stream()
		.filter(p -> p.getPrice()<maxPrice)
		.collect(Collectors.toList());
		return productsList;
	}
	public List<Product> getProductsByName(String keyword)
	{
		if (keyword == null || keyword.isBlank()) throw new IllegalArgumentException("Empty keyword");

		return inventory.values().stream()
		.filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
		.collect(Collectors.toList());
	}

}