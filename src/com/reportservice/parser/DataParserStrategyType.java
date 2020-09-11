package com.reportservice.parser;

import java.util.HashMap;
import java.util.Map;

import com.reportservice.util.DataType;

public class DataParserStrategyType {
	
	static Map<DataType, DataParserStrategy> strategyMap = new HashMap<>();
	
	static {
		strategyMap.put(DataType.CLIENT_DATA, new ClientDataParserStrategy());
		strategyMap.put(DataType.SALESMAN_DATA, new SalesmanDataParserStrategy());
		strategyMap.put(DataType.SALE_DATA, new SaleDataParserStrategy());
	}
	
	public static DataParserStrategy get(DataType key) {
		return strategyMap.get(key);
	}
}
