package com.jpmorgan.digital.helper;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aman on 22-01-2017.
 */
public class ConfigLoaderTest {

    ConfigLoader configLoader = new ConfigLoader();

    @Test
    public void verifyLoadConfigWhenPathNull(){
        configLoader.loadConfig(null);
        Assert.assertEquals(0,configLoader.getAllSection().size());
    }

    @Test
    public void verifyLoadConfigWhenPathEmpty(){
        configLoader.loadConfig("");
        Assert.assertEquals(0,configLoader.getAllSection().size());
    }

    @Test
    public void verifyLoadConfigWhenPathInvalid(){
        configLoader.loadConfig("configData.xml");
        Assert.assertEquals(0,configLoader.getAllSection().size());
    }

    @Test
    public void verifyLoadConfigWhenPathValid(){
        configLoader.loadConfig("/configStockExchange.xml");
        Assert.assertEquals(5,configLoader.getAllSection().size());
        Assert.assertEquals(true,configLoader.getAllSection().contains("TEA"));
        Assert.assertEquals("Common",configLoader.getConfiguration("TEA","TYPE"));

    }


}
