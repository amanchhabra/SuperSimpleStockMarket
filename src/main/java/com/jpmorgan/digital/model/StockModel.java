package com.jpmorgan.digital.model;

import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.helper.ConfigLoader;

import java.util.*;

/**
 * StockModel is a singleton class to keep all the Stocks
 *
 *  @author Aman Chhabra
 *  @since 1.0
 */
public class StockModel {

    private StockModel()    {
       init();
    }

    private static StockModel modelInstance;

    private Map<String,StockDTO> stockMap;

    private ConfigLoader configLoader;

    /**
     * Method to create and return only instance of Class
     * @return modelInstance
     * @since 1.0
     */
    public static StockModel getModelInstance(){
        if(modelInstance == null)
            modelInstance = new StockModel();
        return modelInstance;
    }

    /**
     * Function to load the initial stocks from the config
     * @since 1.0
     */
    public void init(){
        configLoader = new ConfigLoader();
        configLoader.loadConfig("/configStockExchange.xml");
        stockMap = new HashMap<String,StockDTO>();
        Set<String> stocks = configLoader.getAllSection();
        stocks.forEach(section -> loadStock(section));
    }

    /**
     * Function to create and load Stock DTO for provided stock symbol
     * @param stockSymbol Stock symbol
     * @since 1.0
     * */
    private void loadStock(String stockSymbol){
        StockDTO stockInstance = new StockDTO();
        String stockType = configLoader.getConfiguration(stockSymbol,"TYPE");
        int lastDividend = 0;
        String lastDividendString = configLoader.getConfiguration(stockSymbol, "LAST_DIVIDEND");
        lastDividend = convertToInteger(lastDividendString);
        int fixedDividend = 0;
        String fixedDividendString = configLoader.getConfiguration(stockSymbol, "FIXED_DIVIDEND");
        fixedDividend = convertToInteger(fixedDividendString);
        int parValue = 0;
        String parValueString = configLoader.getConfiguration(stockSymbol, "PAR_VALUE");
        parValue = convertToInteger(parValueString);
        stockInstance.setStockSymbol(stockSymbol);
        stockInstance.setStockType(stockType);
        stockInstance.setLastDividend(lastDividend);
        stockInstance.setFixedDividend(fixedDividend);
        stockInstance.setParValue(parValue);
        stockMap.put(stockSymbol, stockInstance);
    }

    /**
     * Function to get stock for a given Stock symbol
     * @param stockSymbol - Symbol of the stock
     * @return stockInstance StockDTO
     * @since 1.0
     */
    public StockDTO getStock(String stockSymbol) {
      StockDTO stockInstance = stockMap.get(stockSymbol);
        if(stockInstance == null) {
            throw new IllegalArgumentException("Stock symbol provided is not supported by the application");
        }
        return stockInstance;
    }

    /**
     * Function to return all the stocks
     * @return Array of StockDTO
     * @since 1.0
     */
    public StockDTO[] getAllStocks(){
        return stockMap.values().toArray(new StockDTO[0]);
    }

    /**
     * Function to convert String to integer and in case of wrong integer returns 0
     * @param dataString
     * @return
     * @since 1.0
     */
    private int convertToInteger(String dataString){
       int data = 0;
        try{
           data = Integer.parseInt(dataString);
       } catch (NumberFormatException e){

        }
        return data;
    }

}
