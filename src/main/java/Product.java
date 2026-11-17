public class Product
{
	private final long id;
	private final String name;
	private final String category;
	private double price = 100;

	//Product Constructor
	public Product(long id, String name, String category, double price)
	{
		if(price>0)
		{
			this.id = id;
			this.name = name;
			this.category = category;
			this.price = price;
		}
		else throw new IllegalArgumentException("Price must be greater than zero");

	}

	//getter
	public String getName()
	{
		return name;
	}
	public long getId()
	{
		return id;
	}
	public String getCategory()
	{
		return category;
	}
	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		if(price>0)
		{
			this.price = price;
		}
		else
		{
			throw new IllegalArgumentException("negative or 0 price not allowed");
		}
	}
	public String toString()
	{
		return ("Id:"+id+" Name:"+name+" Price:"+price+" Category:"+category);
	}
	// Product sorting feature in development

}