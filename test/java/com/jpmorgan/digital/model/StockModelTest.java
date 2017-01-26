package com.jpmorgan.digital.model;

import com.jpmorgan.digital.dto.StockDTO;
import com.jpmorgan.digital.helper.ConfigLoader;
import com.jpmorgan.digital.helper.ConfigLoaderTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aman on 22-01-2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(StockModel.class)
public class StockModelTest {

    private StockModel stockModelInstance;

    private ConfigLoader mockConfigLoader;

    @Before
    public void init(){
        mockConfigLoader = Mockito.mock(ConfigLoader.class);
        try {
            PowerMockito.whenNew(ConfigLoader.class).withNoArguments().thenReturn(mockConfigLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<String> prepareSampleInvalidSection(){
        Set<String> sectionSet = new HashSet<>();
        sectionSet.add("AMA");
        return sectionSet;
    }

    @Test
    public void verifyLoadStockWhenEmptySet() throws IllegalArgumentException{
        Set<String> sectionSet = new HashSet<>();
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(sectionSet);
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "LAST_DIVIDEND")).thenReturn("");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        Assert.assertEquals(0, stockModelInstance.getAllStocks().length);
    }

    @Test
    public void verifyLoadStockWhenLastDividendIsEmpty() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "LAST_DIVIDEND")).thenReturn("");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualLastDividend = stockModelInstance.getStock("AMA").getLastDividend();
        int defaultLastDividend = 0;
        Assert.assertEquals(defaultLastDividend, actualLastDividend);
    }

    @Test
    public void verifyLoadStockWhenLastDividendIsInvalid() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "LAST_DIVIDEND")).thenReturn("last_dividend");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualLastDividend = stockModelInstance.getStock("AMA").getLastDividend();
        int defaultLastDividend = 0;
        Assert.assertEquals(defaultLastDividend, actualLastDividend);
    }

    @Test
    public void verifyLoadStockWhenFixedDividendIsEmpty() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "FIXED_DIVIDEND")).thenReturn("");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualFixedDividend = stockModelInstance.getStock("AMA").getFixedDividend();
        int defaultFixedDividend = 0;
        Assert.assertEquals(defaultFixedDividend, actualFixedDividend);
    }

    @Test
    public void verifyLoadStockWhenFixedDividendIsInvalid() throws IllegalArgumentException {
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "FIXED_DIVIDEND")).thenReturn("fixed_dividend");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualFixedDividend = stockModelInstance.getStock("AMA").getFixedDividend();
        int defaultFixedDividend = 0;
        Assert.assertEquals(defaultFixedDividend, actualFixedDividend);
    }

    @Test
    public void verifyLoadStockWhenParValueIsEmpty() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "PAR_VALUE")).thenReturn("");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualParValue = stockModelInstance.getStock("AMA").getParValue();
        int defaultParValue = 0;
        Assert.assertEquals(defaultParValue,actualParValue);
    }

    @Test
    public void verifyLoadStockWhenParValueIsInvalid() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "PAR_VALUE")).thenReturn("par_value");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        int actualParValue = stockModelInstance.getStock("AMA").getParValue();
        int defaultParValue = 0;
        Assert.assertEquals(defaultParValue, actualParValue);
    }

    @Test
    public void verifyLoadStockWhenStockTypeValid() throws IllegalArgumentException{
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        Mockito.when(mockConfigLoader.getConfiguration("AMA", "TYPE")).thenReturn("Common");
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        String actualType = stockModelInstance.getStock("AMA").getStockType();
        String expectedType = "Common";
        Assert.assertEquals(expectedType, actualType);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyGetStockWhenStockSymbolIsNull(){
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        StockDTO stock = stockModelInstance.getStock(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyGetStockWhenStockSymbolIsEmpty(){
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        StockDTO stock = stockModelInstance.getStock("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyGetStockWhenStockSymbolIsInvalid(){
        Mockito.when(mockConfigLoader.getAllSection()).thenReturn(prepareSampleInvalidSection());
        stockModelInstance = StockModel.getModelInstance();
        stockModelInstance.init();
        StockDTO stock = stockModelInstance.getStock("GI");
    }
}
