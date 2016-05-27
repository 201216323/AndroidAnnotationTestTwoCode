package com.example.androidannotationtesttwo.bean;

import java.io.Serializable;

/**
 * 频道项
 */
public class ChannelItem implements Serializable {
	/**
	 * 
	 */
	// private static final long serialVersionUID = -6465237897027410019L;
	/**
	 * Integer类型的id
	 */
	public Integer id;
	/**
	 * 字符串类型name
	 */
	public String name;
	/**
	 * Integer类型的orderId
	 */
	public Integer orderId;
	/**
	 * Integer类型的selected
	 */
	public Integer selected;

	public ChannelItem() {
	}

	/**
	 * 
	 * @param id   Integer类型的id
	 * @param name  字符串类型name
	 * @param orderId Integer类型的orderId
	 * @param selected Integer类型的selected
	 */
	public ChannelItem(int id, String name, int orderId, int selected) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public int getId() {
		return this.id.intValue();
	}

	public String getName() {
		return this.name;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(int paramInt) {
		this.id = Integer.valueOf(paramInt);
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	@Override
	public String toString() { // ChannelItem [id=ddd, name=dddd, selected=ffff]
		return "ChannelItem [id=" + this.id + ", name=" + this.name
				+ ", selected=" + this.selected + "]";
	}
}
