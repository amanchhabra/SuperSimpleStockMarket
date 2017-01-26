package com.jpmorgan.digital.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Class to store various information related to Trade
 *
 * @author Aman Chhabra
 * @since 1.0
 */
public class TradeDTO {

    /**
     * Indicator values possible for a trade
     * <ul>
     *     <li>BUY</li>
     *     <li>SELL</li>
     * </ul>
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
     * Quantity of the trade
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
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#timestamp} for more information
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#timestamp} for more information
     */
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#quantity} for more information
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#quantity} for more information
     */
    public void setQuantity(int quantity) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity can not be zero or negative");
        }

        this.quantity = quantity;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#indicator} for more information
     */
    public String getIndicator() {
        return indicator.toString();
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#indicator} for more information
     */
    public void setIndicator(String indicator) {
        this.indicator = Indicator.valueOf(indicator);
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#price} for more information
     */
    public double getPrice() {
        return price;
    }

    /**
     * Check {@link com.jpmorgan.digital.dto.TradeDTO#price} for more information
     */
    public void setPrice(double price) throws IllegalArgumentException{
        if(price < 0){
            throw new IllegalArgumentException("Trade price can not be negative");
        }
        this.price = price;
    }
}
