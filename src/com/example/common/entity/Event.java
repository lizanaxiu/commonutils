package com.example.common.entity;

import java.util.List;

/**
 * 事件类 列表加载事件
 * 
 * @author liz
 * 
 */
public class Event {
		private List<Item> items;

		public Event(List<Item> items) {
			this.items = items;
		}
		public List<Item> getItems() {
			return items;
		}

}
