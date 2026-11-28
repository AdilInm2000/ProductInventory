package com.productinventory.service;
import com.productinventory.model.Product;
import com.productinventory.repository.ProductRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;



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

	public void updateProductPrice(long id, BigDecimal newPrice) throws SQLException
	{
		if(!repository.updateProductPrice(id, newPrice)) throw new IllegalArgumentException("Product doesn't exist");
	}

	public List<Product> getProductsByCategory(String category) throws SQLException
	{
		return repository.getProductsByCategory(category);
	}
	public List<Product> getProductsCheaperThan(BigDecimal maxPrice) throws SQLException
	{
		return repository.getProductsCheaperThan(maxPrice);
	}
	public List<Product> getProductsByName(String keyword) throws SQLException
	{
		if (keyword == null || keyword.isBlank()) throw new IllegalArgumentException("Empty keyword");

		return repository.getProductsByName(keyword);
	}

}