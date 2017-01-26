package com.jpmorgan.digital.domain;

import com.jpmorgan.digital.service.impl.StockServiceImpl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * TradeDomain is the Domain Class that will be required to pass all the objects to
 * TradeService.
 *
 * The following data can be stored in the DomainObject:
 *
 * <ul>
 *     <li>Timestamp of Trade</li>
 *     <li>Quantity of Trade</li>
 *     <li>Trade Sell / Buy Scenario</li>
 *     <li>Price of Trade</li>
 * </ul>
 *
 *  @author Aman Chhabra
 *  @since 1.0
 */
public class TradeDomain extends StockServiceImpl {

    /**
     * Indicator values possible for a trade
     */
    public enum Indicator {
        BUY,
        SELL
    }

    /**
     *  Timestamp of the trade
     */
    private ZonedDateTime timestamp;

    /**
     * Quanitity of the trade
     */
    private int quantity;

    /**
     * Buy or Sell Indicator of the trade
     */
    private Indicator indicator = Indicator.BUY;

    /**
     * Price of the trade
     */
    private double price;

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#timestamp} for more information
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#getTimestamp} for more information
     */
    public void setTimestamp(ZonedDateTime timestamp)throws IllegalArgumentException{
        if(verifyNotFutureTimestamp(timestamp)) {
                this.timestamp = timestamp;
        } else {
            throw new IllegalArgumentException("Trade can not be of future timestamp");
        }
    }

    /**
     * Function to store timestamp provided in Data format
     *
     * To support earlier JAVA versions
     *
     * @param timestamp Timestamp in java.util.Date format
     *
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#timestamp} for more information
     */
    public void setTimestamp(Date timestamp) {
        setTimestamp(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC")));
    }

    /**
     * Function to store timestamp provided in Data format and Zone
     *
     * To support earlier JAVA versions
     *
     * @param timestamp Timestamp in java.util.Date format
     * @param currentZone Current zone in String format like GMT/UTC
     *
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#timestamp} for more information
     */
    public void setTimestamp(Date timestamp,String currentZone) {
        setTimestamp(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of(currentZone)));
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#quantity} for more information
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#quantity} for more information
     */
    public void setQuantity(int quantity) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity can not be zero or negative");
        }
        this.quantity = quantity;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#indicator} for more information
     */
    public Indicator getIndicator() {
        return indicator;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#indicator} for more information
     */
    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#price} for more information
     */
    public double getPrice() {
        return price;
    }

    /**
     * Check {@link com.jpmorgan.digital.domain.TradeDomain#price} for more information
     */
    public void setPrice(double price) {
        if(price < 0){
            throw new IllegalArgumentException("Trade price can not be negative");
        }
        this.price = price;
    }

    /**
     * Function to verify if the provided timestamp is not a future one
     * @param time - Timestamp
     * @return boolean True - If Past or current time , False - If future time
     */
    private boolean verifyNotFutureTimestamp(ZonedDateTime time){
        ChronoUnit unit = ChronoUnit.SECONDS;
        long diff = unit.between(time,ZonedDateTime.now());
        return diff>=0;
    }
}
