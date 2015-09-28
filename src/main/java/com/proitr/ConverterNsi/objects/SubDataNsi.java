package com.proitr.ConverterNsi.objects;

public class SubDataNsi
{
	private int product;
	private int prognoz;
	private int parent;

	public SubDataNsi(int product, int prognoz, int parent)
	{
		this.product = product;
		this.prognoz = prognoz;
		this.parent = parent;
	}
	public int getProduct() {
		return this.product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getPrognoz() {
		return this.prognoz;
	}
	public void setPrognoz(int prognoz) {
		this.prognoz = prognoz;
	}
	public int getParent() {
		return this.parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
}
