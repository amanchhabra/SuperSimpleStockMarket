package com.jpmorgan.digital.helper;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ConfigLoader is a helper class to load any config in section, tag and value format and convert it as a Map
 *
 *
 * As per the implementation, config should be in seciton,tag and value format
 *
 * Tags allowed in config
 * <ul>
 *     <li>section</li>
 *     <li>tag</li>
 *     <li>value</li>
 * </ul>
 *
 * So a sample config can be as follows:
 *
 *     &lt;section&gt;
 *         Section1
 *         &lt;tag&gt;
 *             Tag1
 *             &lt;value&gt;TagValue&lt;/value&gt;
 *         &lt;/tag&gt;
 *         &lt;tag&gt;
 *             Tag2
 *             &lt;value&gt;TagValue&lt;/value&gt;
 *         &lt;/tag&gt;
 *     &lt;/section&gt;
 *
 *  @author Aman Chhabra
 *  @since 1.0
 */
public class ConfigLoader {
    private Map<String, Map> configData = new HashMap<String, Map>();

    private Map<String, String> sectionMap;

    private final static Logger logger = Logger.getLogger(ConfigLoader.class);

    /**
     * Function to load config for a given path and store it in the map
     *
     * @param path Path of the config file
     * @since 1.0
     */
    public void loadConfig(String path) {
        try {
            InputStream inputStream = null;
            if(path!=null) {
                inputStream = getClass().getResourceAsStream(path);
                if (logger.isInfoEnabled()) {
                    logger.info("Config load requested for path : " + path);
                }
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {
                    boolean section = false;
                    boolean tag = false;
                    String tagValue = "";
                    boolean value = false;

                    public void startElement(String uri, String localName, String qName,
                                             Attributes attributes) throws SAXException {

                        if (logger.isDebugEnabled()) {
                            logger.debug("Start Element Detected : " + qName);
                        }

                        if (qName.equalsIgnoreCase("SECTION")) {
                            section = true;
                        }

                        if (qName.equalsIgnoreCase("TAG")) {
                            tag = true;
                        }

                        if (qName.equalsIgnoreCase("VALUE")) {
                            value = true;
                        }

                        if (logger.isDebugEnabled()) {
                            logger.debug("Element detected as : section:" + section + " Tag:" + tag + " value:" + value);
                        }

                    }

                    public void endElement(String uri, String localName,
                                           String qName) throws SAXException {

                        if (logger.isDebugEnabled()) {
                            logger.debug("End Element detected : " + qName);
                        }

                    }

                    public void characters(char ch[], int start, int length) throws SAXException {

                        if (section) {
                            String newSection = new String(ch, start, length);
                            createNewSection(newSection);
                            if (logger.isDebugEnabled()) {
                                logger.debug("New section created " + newSection);
                            }
                            section = false;
                        }

                        if (tag) {
                            tagValue = new String(ch, start, length);
                            tag = false;
                            if (logger.isDebugEnabled()) {
                                logger.debug("New tag detected " + tagValue);
                            }
                        }

                        if (value) {
                            String tagData = new String(ch, start, length);
                            addTagValue(tagValue, tagData);
                            if (logger.isDebugEnabled()) {
                                logger.debug("New tag value created " + tagData);
                            }
                            value = false;
                        }
                    }

                };

                saxParser.parse(inputStream, handler);
            }
        } catch (Exception e) {
            logger.error("XML Parsing failed!", e);
        }
    }

    /**
     * Function to get tag value of a loaded config for given section and tag
     * @param section String value of Section
     * @param tag String value of Tag
     * @return Corresponding value
     * @throws IllegalArgumentException
     * @since 1.0
     */
    public String getConfiguration(String section, String tag) throws IllegalArgumentException {
        if (section != null && !section.isEmpty()) {
            if (tag != null && !tag.isEmpty()) {
                HashMap<String, String> sectionData = (HashMap) configData.get(section);
                if (sectionData == null) {
                    logger.error("getConfiguration :: No configuration found for the provided section");
                    throw new IllegalArgumentException("No configuration found for the provided section");
                }
                String tagValue = sectionData.get(tag);
                if (logger.isInfoEnabled()) {
                    logger.info("Get configuration requested for : Section-" + section + "  and tag-" + tag + " value provided-" + tagValue);
                }
                return tagValue;

            } else {
                logger.error("getConfiguration :: Tag can not be empty/null");
                throw new IllegalArgumentException("Tag can not be empty/null");
            }

        } else {
            logger.error("getConfiguration :: Section can not be empty/null");
            throw new IllegalArgumentException("Section can not be empty/null");
        }
    }

    /**
     * Function to create new section in the map
     * @param sectionName Name of the section
     * @since 1.0
     */
    private void createNewSection(String sectionName) {
        sectionName = sectionName.replaceAll("\\s|\n", "");
        sectionMap = new HashMap<String, String>();
        configData.put(sectionName, sectionMap);
    }

    /**
     * Function to store tag and value for the current section in the map
     * @param tag Name of the Tag
     * @param value Value of the Tag
     * @since 1.0
     */
    private void addTagValue(String tag, String value) {
        tag = tag.replaceAll("\\s|\n", "");
        value = value.replaceAll("\\s|\n", "");
        sectionMap.put(tag, value);
    }

    /**
     * Function to get all the sections of the loaded config
     * @return Set of all the sections
     * @since 1.0
     */
    public Set<String> getAllSection() {
        return configData.keySet();
    }
}