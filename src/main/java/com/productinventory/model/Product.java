package com.productinventory.model;
import java.math.BigDecimal;

public class Product
{
	private final long id;
	private final String name;
	private final String category;
	private BigDecimal price;

	//Product Constructor
	public Product(long id, String name, String category, BigDecimal price)
	{
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Price must be greater than zero");

		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
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
	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("negative or 0 price not allowed");

		this.price = price;

	}
	public String toString()
	{
		return ("Id:"+id+" Name:"+name+" Price:"+price+" Category:"+category);
	}
	// Product sorting feature in development
	// Rebase practice: master branch change

}