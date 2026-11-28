package com.productinventory.repository;
import com.productinventory.model.Product;
import com.productinventory.config.DatabaseConnection;
import com.productinventory.exception.DuplicateProductException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ProductRepository
{
	private final DatabaseConnection databaseConnection;

	public ProductRepository(DatabaseConnection databaseConnection)
	{
		this.databaseConnection = Objects.requireNonNull(databaseConnection, "DatabaseConnection cannot be null");
	}


	public void saveProduct(Product product) throws SQLException
	{
		String sql = "INSERT INTO PRODUCTS (id, name, category, price) VALUES (?, ?, ?, ?)";
		try (Connection connection = databaseConnection.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setLong(1, product.getId());
				statement.setString(2, product.getName());
				statement.setString(3, product.getCategory());
				statement.setBigDecimal(4, product.getPrice());
				statement.executeUpdate();
			}

		}
		catch(SQLException e)
		{
			if("23505".equals(e.getSQLState()))
			{
				throw new DuplicateProductException("A product with id already exists", e);
			}
			throw e;
		}
	}

	public List<Product> getAllProducts() throws SQLException
	{
	    String sql = "SELECT * FROM PRODUCTS";
	    List<Product> products = new ArrayList<>();

		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				try(ResultSet resultSet = statement.executeQuery())
				{
					while (resultSet.next())
					{
						products.add(mapRow(resultSet));
					}
				}
			}
		}
		return products;
	}

	public Product getProductById(long id) throws SQLException
	{
	    String sql = "SELECT * FROM PRODUCTS WHERE id = ?";
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setLong(1, id);
				try(ResultSet resultSet = statement.executeQuery())
				{
					if(resultSet.next())
					{
						return mapRow(resultSet);
					}
				}
			}
		}
		return null;
	}

	public boolean updateProductPrice(long id, BigDecimal newPrice) throws SQLException
	{
	    String sql = "UPDATE PRODUCTS SET price = ? WHERE id = ?";
	    try (Connection connection = databaseConnection.getConnection()) {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setBigDecimal(1, newPrice);
			statement.setLong(2, id);
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		}
	    }
	}

	public boolean deleteProduct(long id) throws SQLException
	{
		String sql = "DELETE FROM PRODUCTS WHERE id = ?";
		try (Connection connection = databaseConnection.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setLong(1, id);
				int rowsAffected = statement.executeUpdate();
				return rowsAffected > 0;
			}
		}
	}

	public int getProductCount() throws SQLException
	{
		String sql = "SELECT COUNT(*) as count FROM PRODUCTS";
		int count = 0;
		try (Connection connection = databaseConnection.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try(ResultSet resultSet = statement.executeQuery())
				{
					if(resultSet.next())
					{
						count = resultSet.getInt("count");
					}

				}
			}
		}
		return count;
	}

	public List<Product> getProductsByCategory(String category) throws SQLException
	{
		String sql = "SELECT * FROM PRODUCTS WHERE category = ?";
		List<Product> products = new ArrayList<>();
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setString(1, category);
				try(ResultSet resultSet = statement.executeQuery())
				{
					while(resultSet.next())
					{
						products.add(mapRow(resultSet));
					}
				}
			}
		}
		return products;
	}

	public List<Product> getProductsCheaperThan(BigDecimal maxPrice) throws SQLException
	{
		String sql = "SELECT * FROM PRODUCTS WHERE price < ?";
		List<Product> products = new ArrayList<>();
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setBigDecimal(1, maxPrice);
				try(ResultSet resultSet = statement.executeQuery())
				{
					while(resultSet.next())
					{
						products.add(mapRow(resultSet));
					}
				}
			}
		}
		return products;
	}

	public List<Product> getProductsByName(String keyword) throws SQLException
	{
		String sql = "SELECT * FROM PRODUCTS WHERE LOWER(name) LIKE LOWER(?);";
		List<Product> products = new ArrayList<>();
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setString(1, "%" + keyword + "%");
				try(ResultSet resultSet = statement.executeQuery())
				{
					while(resultSet.next())
					{
						products.add(mapRow(resultSet));
					}
				}
			}
		}
		return products;

	}
	private Product mapRow(ResultSet resultSet) throws SQLException
	{
		long id  = resultSet.getLong("id");
		String name = resultSet.getString("name");
		String category = resultSet.getString("category");
		BigDecimal price = resultSet.getBigDecimal("price");

		return new Product(id, name, category, price);

	}

}