package org.domain.workshop.session;

import org.domain.workshop.entity.Item;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.hibernate.validator.Range;
import org.jboss.seam.annotations.Name;



@Name("lineaPedido")
public class LineaPedido {
	
	private Item item ;
	private boolean selected;
	private int quantity;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	//@NotNull
	//@Range(min=0, max=100, message="La cantidad solo puede ser entre 0 y 100")
	//@Pattern(regex="[1-9][0-9]", message="Ingrese valor númerico")
	public int getQuantity() {
		return quantity;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + quantity;
		result = prime * result + (selected ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		LineaPedido other = (LineaPedido) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (quantity != other.quantity)
			return false;
		if (selected != other.selected)
			return false;
		return true;
	} 
	
	
	
}
