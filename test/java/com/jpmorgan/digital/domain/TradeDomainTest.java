package com.jpmorgan.digital.domain;

import org.apache.http.client.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Aman on 25-01-2017.
 */
public class TradeDomainTest {

    TradeDomain tradeDomain = new TradeDomain();

    @Test (expected = IllegalArgumentException.class)
    public void verifySetTimestampWhenProvidedWithFutureTimestamp(){
        ZonedDateTime time = ZonedDateTime.now();
        time = time.plusMinutes(5);
        tradeDomain.setTimestamp(time);
    }

    @Test
    public void verifySetTimestampWhenProvidedWithFutureTimestampSuccess(){
        ZonedDateTime time = ZonedDateTime.now();
        tradeDomain.setTimestamp(time);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifySetTimestampWhenProvidedWithWrongDate(){
        Date date = new Date();
        date = new Date(date.getTime()+50000);
        tradeDomain.setTimestamp(date);
    }

    @Test
    public void verifySetTimestampWhenProvidedWithCorrectDate(){
        Date date = new Date();
        tradeDomain.setTimestamp(date);
        long expectedTime = date.getTime();
        Assert.assertEquals(expectedTime, tradeDomain.getTimestamp().toInstant().toEpochMilli());
    }

    @Test
    public void verifySetTimestampWithCorrectZonedDate(){
        Date date = new Date();
        tradeDomain.setTimestamp(date, "GMT");
        long expectedTime = date.getTime();
        Assert.assertEquals(expectedTime,tradeDomain.getTimestamp().toInstant().toEpochMilli());
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifySetQuantityWhenNegative(){
        tradeDomain.setQuantity(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifySetQuantityWhenZero(){
        tradeDomain.setQuantity(0);
    }

    @Test
    public void verifySetQuantitySuccess(){
        tradeDomain.setQuantity(10);
        Assert.assertEquals(10,tradeDomain.getQuantity());
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifySetPriceWhenNegative(){
        tradeDomain.setPrice(-1.0);
    }

    @Test
    public void verifySetPriceSuccess(){
        tradeDomain.setPrice(4.33);
        Assert.assertEquals(4.33,tradeDomain.getPrice(),0.0);
    }

}
