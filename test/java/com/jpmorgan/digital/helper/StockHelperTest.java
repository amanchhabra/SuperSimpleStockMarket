package com.jpmorgan.digital.helper;

import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.dto.TradeDTO;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Aman on 22-01-2017.
 */
public class StockHelperTest {

    @Test (expected = IllegalArgumentException.class)
    public void verifyCalculateDividendYieldForCommonWhenPriceIsZero(){
        double dividendYield = StockHelper.calculateDividendYieldForCommon(10,0);
    }

    @Test
    public void verifyCalculateDividendYieldForCommonSuccess(){
        double dividendYield = StockHelper.calculateDividendYieldForCommon(10,2);
        Assert.assertEquals(5.0,dividendYield,0.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyCalculateDividendYieldForPreferredWhenPriceIsZero(){
        double dividendYield = StockHelper.calculateDividendYieldForPreferred(10, 5, 0);
    }

    @Test
    public void verifyCalculateDividendYieldForPreferredSuccess(){
        double dividendYield = StockHelper.calculateDividendYieldForPreferred(10, 5, 2);
        Assert.assertEquals(25.0,dividendYield,0.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyCalculatePERatioWhenDividendIsZero(){
        double peRatio = StockHelper.calculatePERatio(0, 5);
    }

    @Test
    public void verifyCalculatePERatioSuccess(){
        double peRatio = StockHelper.calculatePERatio(10, 5);
        Assert.assertEquals(0.5,peRatio,0.0);
    }

    @Test
    public void verifyGetFormattedDoubleSuccess(){
        double formattedDouble = StockHelper.getFormattedDouble(4.33245);
        Assert.assertEquals(4.33,formattedDouble,0.0);
    }

    private StockDTO prepareSampleStockWithOldTrades(){
        TradeDTO tradeBeforeThresholdTime = new TradeDTO();
        TradeDTO tradeOnThresholdTime = new TradeDTO();
        TradeDTO tradeAfterThresholdTime = new TradeDTO();
        tradeBeforeThresholdTime.setQuantity(2);
        tradeOnThresholdTime.setQuantity(4);
        tradeAfterThresholdTime.setQuantity(3);
        tradeBeforeThresholdTime.setPrice(10.0);
        tradeOnThresholdTime.setPrice(10.0);
        tradeAfterThresholdTime.setPrice(10.0);
        ZonedDateTime time = ZonedDateTime.now();
        time = time.minusSeconds(310);
        tradeAfterThresholdTime.setTimestamp(time);
        time = time.minusSeconds(1);
        tradeOnThresholdTime.setTimestamp(time);
        time = time.minusSeconds(1);
        tradeBeforeThresholdTime.setTimestamp(time);
        StockDTO stockDTO = new StockDTO();
        stockDTO.addTrade(tradeBeforeThresholdTime);
        stockDTO.addTrade(tradeOnThresholdTime);
        stockDTO.addTrade(tradeAfterThresholdTime);
        return stockDTO;
    }

    @Test
    public void verifyCalculateVolWeightPriceWhenNoEligibleTrade(){
        double volWeightPrice = StockHelper.calculateVolWeightPrice(prepareSampleStockWithOldTrades());
        Assert.assertEquals(0.0,volWeightPrice,0.0);
    }

    private StockDTO prepareSampleStockWithMultipleTrades(){
        TradeDTO tradeBeforeThresholdTime = new TradeDTO();
        TradeDTO tradeOnThresholdTime = new TradeDTO();
        TradeDTO tradeAfterThresholdTime = new TradeDTO();
        tradeBeforeThresholdTime.setQuantity(2);
        tradeOnThresholdTime.setQuantity(4);
        tradeAfterThresholdTime.setQuantity(3);
        tradeBeforeThresholdTime.setPrice(10.0);
        tradeOnThresholdTime.setPrice(10.0);
        tradeAfterThresholdTime.setPrice(10.0);
        ZonedDateTime time = ZonedDateTime.now();
        time = time.minusSeconds(299);
        tradeAfterThresholdTime.setTimestamp(time);
        time = time.minusSeconds(1);
        tradeOnThresholdTime.setTimestamp(time);
        time = time.minusSeconds(1);
        tradeBeforeThresholdTime.setTimestamp(time);
        StockDTO stockDTO = new StockDTO();
        stockDTO.addTrade(tradeBeforeThresholdTime);
        stockDTO.addTrade(tradeOnThresholdTime);
        stockDTO.addTrade(tradeAfterThresholdTime);
        return stockDTO;
    }

    @Test
    public void verifyCalculateVolWeightPriceSuccess(){
        double volWeightPrice = StockHelper.calculateVolWeightPrice(prepareSampleStockWithMultipleTrades());
        Assert.assertEquals(10.0,volWeightPrice,0.0);
    }
}
