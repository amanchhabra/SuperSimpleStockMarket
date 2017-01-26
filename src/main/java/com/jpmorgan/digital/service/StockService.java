package com.jpmorgan.digital.service;

import com.jpmorgan.digital.domain.TradeDomain;

/**
 * StockService is the Service Interface that provide various operations that can be implemented on a stock
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
public interface StockService {

    /**
     *  To calculate the Dividend Yield of provided Stock instance
     *
     *  @param stockSymbol Stock symbol for which dividend yield needs to be calculated
     *  @param price Price of stock
     *
     * @return Dividend Yield
     * @since 1.0
     */
    public double calculateDividendYield(String stockSymbol, double price) throws IllegalArgumentException;

    /**
     *  To calculate the P/E Ratio of provided Stock instance
     *
     *  @param stockSymbol Stock symbol for which P/E ratio needs to be calculated
     *  @param price Price of the stock
     *
     * @return P/E Ratio
     * @since 1.0
     */
    public double calculatePERatio(String stockSymbol, double price) throws IllegalArgumentException;

    /**
     *  To record trade for provided stock
     *
     *  @param stockSymbol Stock symbol for which trade needs to be stored
     *  @param tradeData Trade Data that needs to be stored
     *
     * @since 1.0
     */
    public void recordTrade(String stockSymbol, TradeDomain tradeData) throws IllegalArgumentException;

    /**
     *  To calculate the volume of provided Stock instance based on trades in past 5 minutes
     *
     *  @param stockSymbol Stock for which volume weighted stock price need to be calculated
     *
     * @return Dividend Yield
     * @since 1.0
     */
    public double calculateVolWeightedPrice(String stockSymbol) throws IllegalArgumentException;

    /**
     * To calculate the GBCE All Share Index for all the stocks
     *
     * @return GBCE All Share Index
     * @since 1.0
     */
    public double calculateGBCEAllShareIndex();

}
