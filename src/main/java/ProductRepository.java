import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductRepository
{
	private final DatabaseConnection databaseConnection;

	public ProductRepository(DatabaseConnection databaseConnection)
	{
		this.databaseConnection = databaseConnection;
	}


	public void saveProduct(Product product) throws SQLException
	{
		String sql = "INSERT INTO products (id, name, category, price) VALUES (?, ?, ?, ?)";
		try (Connection connection = databaseConnection.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setLong(1, product.getId());
				statement.setString(2, product.getName());
				statement.setString(3, product.getCategory());
				statement.setDouble(4, product.getPrice());
				statement.executeUpdate();
			}
		   
		}
		catch(SQLException e)
		{
			if("23505".equals(e.getSQLState()))
			{
				throw new DuplicateProductException("A product with this id already exists", e);
			}
			throw e;
		}
	}

	public List<Product> getAllProducts() throws SQLException
	{
	    String sql = "Select * From Products";
	    List<Product> products = new ArrayList<>();

		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				try(ResultSet resultSet = statement.executeQuery())
				{
					while (resultSet.next())
					{
						long id = resultSet.getLong("id");
						String name = resultSet.getString("name");
						String category = resultSet.getString("category");
						double price = resultSet.getDouble("price");
						products.add(new Product(id, name, category, price));
					}
				}
			}
		}
		return products;
	}

	public Product getProductById(long id) throws SQLException
	{
	    String sql = "Select * From Products where id = ?";
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setLong(1, id);
				try(ResultSet resultSet = statement.executeQuery())
				{
					if(resultSet.next())
					{
						String name = resultSet.getString("name");
						String category = resultSet.getString("category");
						double price = resultSet.getDouble("price");
						return (new Product(id, name, category, price));
					}
				}
			}
		}
		return null;
	}

	public boolean updateProductPrice(long id, double newPrice) throws SQLException
	{
	    String sql = "update products set price = ? where id = ?";
	    try (Connection connection = databaseConnection.getConnection()) {
	    	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    		statement.setDouble(1, newPrice);
	    		statement.setLong(2, id);
	    		int rowsUpdated = statement.executeUpdate();
	    		return rowsUpdated > 0;
	    	}
	    }
	}

	public boolean deleteProduct(long id) throws SQLException
	{
		String sql = "delete from products where id = ?";
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
		String sql = "select count(*) as count from products";
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
		String sql = "select * from products where category = ?";
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
						long id  = resultSet.getLong("id");
						String name = resultSet.getString("name");
						String productCategory = resultSet.getString("category");
						double price = resultSet.getDouble("price");
						products.add(new Product(id, name, productCategory, price));
					}
				}
			}
		}
		return products;
	}

	public List<Product> getProductsCheaperThan(double maxPrice) throws SQLException
	{
		String sql = "SELECT * FROM products WHERE price < ?";
		List<Product> products = new ArrayList<>();
		try (Connection connection = databaseConnection.getConnection())
		{
			try (PreparedStatement statement = connection.prepareStatement(sql))
			{
				statement.setDouble(1, maxPrice);
				try(ResultSet resultSet = statement.executeQuery())
				{
					while(resultSet.next())
					{
						long id  = resultSet.getLong("id");
						String name = resultSet.getString("name");
						String category = resultSet.getString("category");
						double price = resultSet.getDouble("price");
						products.add(new Product(id, name, category, price));
					}
				}
			}
		}
		return products;
	}

	public List<Product> getProductsByName(String keyword) throws SQLException
	{
		String sql = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?);";
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
						long id  = resultSet.getLong("id");
						String name = resultSet.getString("name");
						String category = resultSet.getString("category");
						double price = resultSet.getDouble("price");
						products.add(new Product(id, name, category, price));
					}
				}
			}
		}
		return products;

	}

}