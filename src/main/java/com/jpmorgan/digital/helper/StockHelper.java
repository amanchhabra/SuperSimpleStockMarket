package com.jpmorgan.digital.helper;

import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.dto.TradeDTO;
import org.apache.log4j.Logger;

import java.lang.IllegalArgumentException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * StockHelper is a helper class to implement the business rules for StockServiceImpl
 *
 *  @author Aman Chhabra
 *  @since 1.0
 */
public class StockHelper {

    private final static Logger logger = Logger.getLogger(StockHelper.class);

    /**
     * Function to implement Dividend Yield for Stock Type common
     * @param dividend Last Dividend of the stock
     * @param price Price of the trade
     * @return dividendYield Calculated Dividend Yield
     * @throws IllegalArgumentException When price is zero
     * @since 1.0
     */
    public static double calculateDividendYieldForCommon(int dividend, double price) throws IllegalArgumentException{
        if(price == 0.0) {
            logger.error("calculateDividendYieldForCommon :: Dividend Yield can not be calculated when price is 0");
            throw new IllegalArgumentException("Dividend Yield can not be calculated when price is 0");
        }
        double dividendYield = dividend/price;
        dividendYield = getFormattedDouble(dividendYield);
        if(logger.isInfoEnabled()){
            logger.info("Dividend yield-"+dividendYield+" calculated for Common for Dividend-" + dividend+"  and price-"+price);
        }
        return dividendYield;
    }

    /**
     * Function to implement Dividend Yield for Stock Type Preferred
     * @param fixedDividend  Fixed dividend of the stock
     * @param parValue Par Value of the stock
     * @param price  Price of the trade
     * @return dividendYield Calculated Dividend Yield
     * @throws IllegalArgumentException When price is 0
     * @since 1.0
     */
    public static double calculateDividendYieldForPreferred(int fixedDividend, int parValue, double price) throws IllegalArgumentException{
        if(price == 0.0) {
            logger.error("calculateDividendYieldForPreferred :: Dividend Yield can not be calculated when price is 0");
            throw new IllegalArgumentException("Dividend Yield can not be calculated when price is 0");
        }
        double dividendYield = (fixedDividend*parValue)/price;
        dividendYield = getFormattedDouble(dividendYield);
        if(logger.isInfoEnabled()){
            logger.info("Dividend yield-"+dividendYield+" calculated for Preferred for Fixed Dividend-" + fixedDividend+" ParValue-"+parValue+"  and price-"+price);
        }
        return dividendYield;
    }

    /**
     * Function to calculate P/E Ratio
     * @param dividend  Last dividend of the stock
     * @param price Price of the trade
     * @return peRatio Calculated P/E ratio
     * @throws IllegalArgumentException When last dividend is zero
     * @since 1.0
     */
    public static double calculatePERatio(int dividend, double price) throws IllegalArgumentException{
        if(dividend == 0) {
            logger.error("calculatePERatio :: P/E Ratio can not be calculated when dividend is 0");
            throw new IllegalArgumentException("P/E Ratio can not be calculated when dividend is 0");
        }
        double pERatio = price/dividend;
        pERatio = getFormattedDouble(pERatio);
        if(logger.isInfoEnabled()){
            logger.info("P/E Ratio -"+pERatio+" calculated for Dividend-" + dividend+"  and price-"+price);
        }
        return pERatio;
    }

    /**
     * Function to calculate Volume Weighted price of a stock for all the trades in last 5 minutes
     *
     * @param stock Given stock for which Volume Weighted price needs to be calculated
     * @return volWeightPrice Calculated Volume Weighted price
     * @since 1.0
     */
    public static double calculateVolWeightPrice(StockDTO stock){
        double volWeightPrice = 0.0;
        TradeDTO[] trades = stock.getAllTrades();
        int noOfTrades = trades.length;
        int quantityTotal = 0;
        double quantityPriceTotal = 0.0;
        ZonedDateTime currentTime = ZonedDateTime.now();
        while(noOfTrades-->0) {
            if(getTimeDifferenceInSeconds(trades[noOfTrades].getTimestamp(),currentTime)<300) {
                quantityTotal = trades[noOfTrades].getQuantity();
                quantityPriceTotal = trades[noOfTrades].getQuantity()*trades[noOfTrades].getPrice();
            }
        }
        if(quantityTotal != 0) {
            volWeightPrice = quantityPriceTotal/quantityTotal;
        }
        volWeightPrice = getFormattedDouble(volWeightPrice);
        if(logger.isInfoEnabled()){
            logger.info("Volume Weighted Price-"+volWeightPrice+"calculated for trades of last 5 minutes for Stock -"+stock.getStockSymbol());
        }
        return volWeightPrice;
    }

    /**
     * Function to calculate GBCE All Share Index
     * @param stocks Stock for which Index needs to be calculated
     * @return gbceAllShareIndex - Calculated GBCE All Share Index
     * @since 1.0
     */
    public static double calculateGBCEAllShareIndex(StockDTO[] stocks) {
        int noOfStocks = stocks.length;
        double volWeightPriceTotal = 0;
        while(noOfStocks-->0){
            volWeightPriceTotal += calculateVolWeightPrice(stocks[noOfStocks]);
        }
        double gbceAllShareIndex = Math.pow(volWeightPriceTotal, 1.0 / stocks.length);
        gbceAllShareIndex = StockHelper.getFormattedDouble(gbceAllShareIndex);
        if(logger.isInfoEnabled()){
            logger.info("GBCE All Shared Index -"+gbceAllShareIndex+" calculated for total "+stocks.length+" stocks");
        }
        return gbceAllShareIndex;
    }

    /**
     * Function to round off Double to two decimals
     * @param value - Value which needs to be rounded off
     * @return roundedOffVal double
     * @since 1.0
     */
    public static double getFormattedDouble(double value){
        double roundedOffVal = Math.round(value * 100);
        roundedOffVal = roundedOffVal/100;
        return roundedOffVal;
    }

    /**
     * Function to calculate difference in seconds between two timestamps
     * @param earlyTime - Early Timestamp
     * @param latestTime - Latest Timestamp
     * @return diff long
     * @since 1.0
     */
    private static long getTimeDifferenceInSeconds(ZonedDateTime earlyTime, ZonedDateTime latestTime) {
        ChronoUnit unit = ChronoUnit.SECONDS;
        return unit.between(earlyTime,latestTime);
    }

}
