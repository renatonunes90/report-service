package com.reportservice.store;

import java.util.HashMap;
import java.util.Map;

import com.reportservice.entity.BaseEntity;

public abstract class BaseStore<U, T extends BaseEntity> {

	private Map<U, T> loadedItems;
	
	public BaseStore() {
		loadedItems = new HashMap<>();
	}
	
	public void addItem(T newItem) {
		if ( newItem != null ) {
			loadedItems.put(getKeyValue(newItem), newItem);			
		}
	}

	public abstract U getKeyValue(T newItem);
	
	public void clear() {
		loadedItems.clear();
	}

	public Map<U, T> getLoadedItems(){
		return loadedItems;
	}
}
