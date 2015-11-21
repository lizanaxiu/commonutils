package com.example.common.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * eventbus事件对应的处理item
 * @author liz
 *
 */
public class Item {
	public String id;
	public String content;
	public static List<Item> ITEM = new ArrayList<Item>();
	static {
		addItem(new Item("1", "item1"));
		addItem(new Item("2", "item2"));
		addItem(new Item("3", "item3"));
		addItem(new Item("4", "item4"));
		addItem(new Item("5", "item5"));
		addItem(new Item("6", "item6"));
	}

	public static void addItem(Item item) {
		ITEM.add(item);
	}

	public Item(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public String toString() {
		return content;
	}
}
