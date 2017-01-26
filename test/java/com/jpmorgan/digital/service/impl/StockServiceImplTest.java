package com.jpmorgan.digital.service.impl;

import com.jpmorgan.digital.domain.TradeDomain;
import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.dto.TradeDTO;
import com.jpmorgan.digital.model.StockModel;
import com.jpmorgan.digital.service.StockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.ZonedDateTime;

/**
 * Tests to verify various scenario related to different JSONs, invalid JSONs or no response from server
 *
 * @author Aman Chhabra
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StockModel.class)
public class StockServiceImplTest {

    private StockService stockServiceInstance,stockServiceWithMockStockModel;
    private StockModel stockModelMock;

    @Before
    public void init() throws Exception {
        stockServiceInstance = new StockServiceImpl();
        stockModelMock = Mockito.mock(StockModel.class);
        PowerMockito.mockStatic(StockModel.class);
        PowerMockito.when(StockModel.getModelInstance()).thenReturn(stockModelMock);
        stockServiceWithMockStockModel = new StockServiceImpl();
    }

    private StockDTO prepareSampleStock(){
        StockDTO stockDTO = new StockDTO();
        stockDTO.setFixedDividend(2);
        stockDTO.setLastDividend(0);
        stockDTO.setParValue(100);
        stockDTO.setStockSymbol("GIN");
        stockDTO.setStockType("Preferred");
        return stockDTO;
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
        time.minusSeconds(299);
        tradeAfterThresholdTime.setTimestamp(time);
        time.minusSeconds(1);
        tradeOnThresholdTime.setTimestamp(time);
        time.minusSeconds(1);
        tradeBeforeThresholdTime.setTimestamp(time);
        StockDTO stockDTO = new StockDTO();
        stockDTO.addTrade(tradeBeforeThresholdTime);
        stockDTO.addTrade(tradeOnThresholdTime);
        stockDTO.addTrade(tradeAfterThresholdTime);
        return stockDTO;
    }

    private TradeDomain prepareSampleTrade(){
        TradeDomain trade = new TradeDomain();
        trade.setTimestamp(ZonedDateTime.now());
        trade.setPrice(8.0);
        trade.setIndicator(TradeDomain.Indicator.BUY);
        trade.setQuantity(2);
        return trade;
    }

    private TradeDTO getExpectedTrade(TradeDomain tradeDomain){
        TradeDTO trade = new TradeDTO();
        trade.setTimestamp(tradeDomain.getTimestamp());
        trade.setPrice(8.0);
        trade.setIndicator("BUY");
        trade.setQuantity(2);
        return trade;
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateDividendYieldWhenStockSymbolIsNull() throws IllegalArgumentException{
        double dividendYield = stockServiceInstance.calculateDividendYield(null,0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateDividendYieldWhenStockSymbolIsEmpty() throws IllegalArgumentException{
        double dividendYield = stockServiceInstance.calculateDividendYield("",0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateDividendYieldWhenStockSymbolIsInvalid() throws IllegalArgumentException{
        double dividendYield = stockServiceInstance.calculateDividendYield("AMA",0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateDividendYieldWhenPriceIsNegative() throws IllegalArgumentException{
        double dividendYield = stockServiceInstance.calculateDividendYield("GIN",-10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateDividendYieldWhenPriceIsZero() throws IllegalArgumentException{
        double dividendYield = stockServiceInstance.calculateDividendYield("GIN",0);
    }

    @Test
    public void calculateDividendYieldForPreferredStock(){
        double dividendYield = stockServiceInstance.calculateDividendYield("GIN",10);
        Assert.assertEquals(20.0,dividendYield,0);
    }

    @Test
    public void calculateDividendYieldForCommonStock(){
        double dividendYield = stockServiceInstance.calculateDividendYield("ALE",6);
        Assert.assertEquals(3.83,dividendYield,0.0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculatePERatioWhenStockSymbolIsNull() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio(null,0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculatePERatioWhenStockSymbolIsEmpty() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio("",0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculatePERatioWhenStockSymbolIsInvalid() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio("AMA",0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculatePERatioWhenPriceIsNegative() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio("GIN",-10);
    }

    @Test
    public void calculatePERatioWhenPriceIsZero() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio("GIN",0);
        Assert.assertEquals(0.0,pERatio,0);
   }

    @Test
    public void calculatePERatio() throws IllegalArgumentException{
        double pERatio = stockServiceInstance.calculatePERatio("GIN",10);
        Assert.assertEquals(1.25,pERatio,0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculatePERatioWhenDividendIsZero() throws IllegalArgumentException{
        Mockito.when(stockModelMock.getStock("GIN")).thenReturn(prepareSampleStock());
        double pERatio = stockServiceWithMockStockModel.calculatePERatio("GIN",10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void recordTradeWhenStockSymbolIsNull() throws IllegalArgumentException{
        stockServiceInstance.recordTrade(null,prepareSampleTrade());
    }

    @Test(expected=IllegalArgumentException.class)
    public void recordTradeWhenStockSymbolIsEmpty() throws IllegalArgumentException{
        stockServiceInstance.recordTrade("",prepareSampleTrade());
    }

    @Test(expected=IllegalArgumentException.class)
    public void recordTradeWhenStockSymbolIsInvalid() throws IllegalArgumentException{
        stockServiceInstance.recordTrade("AMA",prepareSampleTrade());
    }

    @Test(expected=IllegalArgumentException.class)
    public void recordTradeWhenTradeIsNull() throws IllegalArgumentException{
        stockServiceInstance.recordTrade("GIN",null);
    }

    @Test
    public void recordTradeSuccessfully() throws IllegalArgumentException{
        StockDTO stock = new StockDTO();
        Mockito.when(stockModelMock.getStock("GIN")).thenReturn(stock);
        TradeDomain sampleTrade = prepareSampleTrade();
        stockServiceWithMockStockModel.recordTrade("GIN",sampleTrade);
        TradeDTO trade[] = stock.getAllTrades();
        TradeDTO expectedTrade = getExpectedTrade(sampleTrade);
        Assert.assertEquals(expectedTrade.getIndicator(),trade[0].getIndicator());
        Assert.assertEquals(expectedTrade.getPrice(),trade[0].getPrice(),0.0);
        Assert.assertEquals(expectedTrade.getQuantity(),trade[0].getQuantity());
        Assert.assertEquals(expectedTrade.getTimestamp(),trade[0].getTimestamp());

    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateVolWeightedPriceWhenStockSymbolIsNull() throws IllegalArgumentException{
        double volWeightPrice = stockServiceInstance.calculateVolWeightedPrice(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateVolWeightedPriceWhenStockSymbolIsEmpty() throws IllegalArgumentException{
        double volWeightPrice = stockServiceInstance.calculateVolWeightedPrice("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void calculateVolWeightedPriceWhenStockSymbolIsNotValid() throws IllegalArgumentException{
        double volWeightPrice = stockServiceInstance.calculateVolWeightedPrice("AMA");
    }

    @Test
    public void calculateVolWeightedPriceSuccessFully(){
        StockDTO stock = prepareSampleStockWithMultipleTrades();
        Mockito.when(stockModelMock.getStock("GIN")).thenReturn(stock);
        double volWeightPrice = stockServiceWithMockStockModel.calculateVolWeightedPrice("GIN");
        Assert.assertEquals(10.0,volWeightPrice,0.0);
    }

    @Test
    public void calculateGBCEAllShareIndexWithNoStock(){
        Mockito.when(stockModelMock.getAllStocks()).thenReturn(new StockDTO[0]);
        double gbceAllShareIndex = stockServiceWithMockStockModel.calculateGBCEAllShareIndex();
        Assert.assertEquals(0.0,gbceAllShareIndex,0.0);
    }

    @Test
    public void calculateGBCEAllShareIndexWithSomeStocks(){
        StockDTO stockDTOArray[] = new StockDTO[2];
        stockDTOArray[0]=prepareSampleStockWithMultipleTrades();
        stockDTOArray[1]=prepareSampleStockWithMultipleTrades();
        Mockito.when(stockModelMock.getAllStocks()).thenReturn(stockDTOArray);
        double gbceAllShareIndex = stockServiceWithMockStockModel.calculateGBCEAllShareIndex();
        Assert.assertEquals(4.47,gbceAllShareIndex,0.0);
    }

}
