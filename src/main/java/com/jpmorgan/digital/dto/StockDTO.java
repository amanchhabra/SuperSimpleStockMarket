package com.jpmorgan.digital.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store various information related to Stock
 *
 * @author Aman Chhabra
 * @since 1.0
 */
public class StockDTO {

    /**
     * Symbol of Stock
     */
    private String stockSymbol;

    /**
     * Type of stock
     * <ul>
     *     <li>Common</li>
     *     <li>Preferred</li>
     * </ul>
     */
    private String stockType;

    /**
     * Last dividend of stock
     */
    private int lastDividend;

    /**
     * Fixed dividend of stock
     */
    private int fixedDividend;

    /**
     * Par Value of stock
     */
    private int parValue;

    /**
     * List of all the trades for this stock
     */
    private List<TradeDTO> tradeList;

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#stockSymbol} for more information
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#stockSymbol} for more information
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#stockType} for more information
     */
    public String getStockType() {
        return stockType;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#stockType} for more information
     */
    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#lastDividend} for more information
     */
    public int getLastDividend() {
        return lastDividend;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#lastDividend} for more information
     */
    public void setLastDividend(int lastDividend) {
        this.lastDividend = lastDividend;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#fixedDividend} for more information
     */
    public int getFixedDividend() {
        return fixedDividend;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#fixedDividend} for more information
     */
    public void setFixedDividend(int fixedDividend) {
        if(fixedDividend < 0){
            throw new IllegalArgumentException("Fixed dividend can not be negative");
        }
        this.fixedDividend = fixedDividend;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#parValue} for more information
     */
    public int getParValue() {
        return parValue;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#parValue} for more information
     */
    public void setParValue(int parValue) {
        if(parValue < 0){
            throw new IllegalArgumentException("Par value can not be negative");
        }
        this.parValue = parValue;
    }

    /**
     * Function to add trade for this stock
     *
     * Check {@link com.jpmorgan.digital.dto.StockDTO#tradeList} for more information
     */
    public void addTrade(TradeDTO tradeDTO){
        if(tradeList == null) {
            tradeList = new ArrayList<TradeDTO>();
        }
        tradeList.add(tradeDTO);
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.StockDTO#tradeList} for more information
     */
    public TradeDTO[] getAllTrades(){
        return tradeList.toArray(new TradeDTO[0]);
    }
}
