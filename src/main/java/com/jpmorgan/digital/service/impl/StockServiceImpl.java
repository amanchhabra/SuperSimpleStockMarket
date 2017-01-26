package com.jpmorgan.digital.service.impl;

import com.jpmorgan.digital.domain.TradeDomain;
import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.dto.TradeDTO;
import com.jpmorgan.digital.helper.StockHelper;
import com.jpmorgan.digital.model.StockModel;
import com.jpmorgan.digital.service.StockService;
import org.apache.log4j.Logger;

/**
 * StockServiceImpl is the implementation of StockService class that provide definition of
 * various operations that can be implemented on a stock
 *
 * The following operations can be executed on a Stock Instance:
 *
 * <ul>
 *     <li>Calculate Dividend Yield</li>
 *     <li>Calculate P/E Ration</li>
 *     <li>Record Trade</li>
 *     <li>Calculate volume weighted stock price based on trades in past 5 minutes</li>
 * </ul>
 *
 *  @author Aman Chhabra
 *  @since 1.0
 */
public class StockServiceImpl extends StockHelper implements StockService{

    private final static Logger logger = Logger.getLogger(StockServiceImpl.class);

    private StockModel stockModel = StockModel.getModelInstance();

    private static String stockTypeCommon = "Common";

    private static String stockTypePreferred = "Preferred";

    /**
     * To calculate the Dividend Yield of provided Stock instance
     *
     * @param stockSymbol Stock symbol for which dividend yield needs to be calculated
     * @param price       Price of stock
     * @return Dividend Yield
     * @since 1.0
     */
    @Override
    public double calculateDividendYield(String stockSymbol, double price) throws IllegalArgumentException{
        double dividendYield = 0;
        if(price < 0) {
            logger.error("calculateDividendYield :: Price can not be negative");
            throw new IllegalArgumentException("Price can not be negative");
        }
        if(stockSymbol == null || stockSymbol.isEmpty()) {
            logger.error("calculateDividendYield :: Stock Symbol can not be empty");
            throw new IllegalArgumentException("Stock Symbol can not be empty");
        }
        StockDTO stock = stockModel.getStock(stockSymbol);
        if(stockTypeCommon.equalsIgnoreCase(stock.getStockType())) {
            dividendYield = StockHelper.calculateDividendYieldForCommon(stock.getLastDividend(),price);
        } else if (stockTypePreferred.equalsIgnoreCase(stock.getStockType())) {
            dividendYield = StockHelper.calculateDividendYieldForPreferred(stock.getFixedDividend(),stock.getParValue(),price);
        }
        return dividendYield;
    }

    /**
     * To calculate the P/E Ratio of provided Stock instance
     *
     * @param stockSymbol Stock symbol for which P/E ratio needs to be calculated
     * @param price       Price of the stock
     * @return P/E Ratio
     * @since 1.0
     */
    @Override
    public double calculatePERatio(String stockSymbol, double price) throws IllegalArgumentException{
        double pERatio;
        if(price < 0) {
            logger.error("calculatePERatio :: Price can not be negative");
            throw new IllegalArgumentException("Price can not be negative");
        }
        if(stockSymbol == null || stockSymbol.isEmpty()) {
            logger.error("calculatePERatio :: Stock Symbol can not be empty");
            throw new IllegalArgumentException("Stock Symbol can not be empty");
        }
        StockDTO stock = stockModel.getStock(stockSymbol);
        pERatio = StockHelper.calculatePERatio(stock.getLastDividend(),price);
        return pERatio;
    }

    /**
     * To record trade for provided stock
     *
     * @param stockSymbol Stock symbol for which trade needs to be stored
     * @param tradeData   Trade Data that needs to be stored
     * @since 1.0
     */
    @Override
    public void recordTrade(String stockSymbol, TradeDomain tradeData) throws IllegalArgumentException{
        if(stockSymbol == null || stockSymbol.isEmpty()) {
            logger.error("recordTrade :: Stock Symbol can not be empty");
            throw new IllegalArgumentException("Stock Symbol can not be empty");
        }
        if(tradeData == null){
            logger.error("recordTrade :: Trade can not be null");
            throw new IllegalArgumentException("Trade can not be null");
        }
        StockDTO stock = stockModel.getStock(stockSymbol);
        TradeDTO trade = new TradeDTO();
        trade.setIndicator(tradeData.getIndicator().toString());
        trade.setPrice(tradeData.getPrice());
        trade.setQuantity(tradeData.getQuantity());
        trade.setTimestamp(tradeData.getTimestamp());
        stock.addTrade(trade);
    }

    /**
     * To calculate the volume of provided Stock instance based on trades in past 5 minutes
     *
     * @param stockSymbol Stock for which volume weighted stock price need to be calculated
     * @return Dividend Yield
     * @since 1.0
     */
    @Override
    public double calculateVolWeightedPrice(String stockSymbol) throws IllegalArgumentException{
        if(stockSymbol == null || stockSymbol.isEmpty()) {
            logger.error("calculateVolWeightedPrice :: Stock Symbol can not be empty");
            throw new IllegalArgumentException("Stock Symbol can not be empty");
        }
        double volWeightedPrice = 0.0;
        StockDTO stock = stockModel.getStock(stockSymbol);
        volWeightedPrice = StockHelper.calculateVolWeightPrice(stock);
        return volWeightedPrice;
    }

    /**
     * To calculate the GBCE All Share Index for all the stocks
     *
     * @return GBCE All Share Index
     * @since 1.0
     */
    @Override
    public double calculateGBCEAllShareIndex() {
        double gbceAllShareIndex = 0.0;
        StockDTO stocks[] = stockModel.getAllStocks();
        gbceAllShareIndex = StockHelper.calculateGBCEAllShareIndex(stocks);
        return gbceAllShareIndex;
    }

}
