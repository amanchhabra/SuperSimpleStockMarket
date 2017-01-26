# Super Simple Stock Market


![JAVA8](http://www.softys.ru/files/news_photos/news3171.png)

A Stock Market module that provides various stock and trade operations. The operations and their details are below:

    1. Calculation of Dividend Yield for a given Stock
    2. Calculation of P/E Ratio for a given Stock
    3. Recording a trade with below details for a given Stock
        a. Time of Trade
        b. Price of Trade
        c. Quanitity of Trade
        d. Buy or Sell Scenario
     4. Calculation of Volume Weighted Stock Price for trades in last 5 minutes
     5. Calculation of GBCE All Share Index for all the Stocks
     
     
 Stocks supported by this module are as follows:
 
     1. TEA
     2. POP
     3. ALE
     4. GIN
     5. GOE


[More Information...](https://github.com/amanchhabra/SuperSimpleStockMarket/docs/index.html)

## Installing and Runing the server

### Standalone

     1. Clone the repository
     2. Maven installation is a prerequisite 
     3. Install all dependencies in pom.xml
     4. Run the server as a normal JAVA application

### Using as a JAR in another project

     1. Clone the repository
     2. Maven installation is a prerequisite 
     3. Run "mvn compile assembly:single" command
     4. Use the JAR exported in the target folder
     
## Configuration

### Stocks Configuration

You can add , update or remove supporting Stocks of the module by amending the configStockExchange.xml in resource folder

### Logging Configuration

You can also change the logging level and name by ameding the log4j.properties file.

## Logs

All the logs will be stored in SuperSimpleStockApplication.log file in the root folder

## Service Functions

___________________________________________________________________________________________________________________
               Method and Description                                                                              |
-------------------------------------------------------------------------------------------------------------------|
double	                    calculateDividendYield(java.lang.String stockSymbol, double price)                     |
                            To calculate the Dividend Yield of provided Stock instance                             |
-------------------------------------------------------------------------------------------------------------------|
double	                    calculateGBCEAllShareIndex()                                                           |
                            To calculate the GBCE All Share Index for all the stocks                               | 
-------------------------------------------------------------------------------------------------------------------|
double	                    calculatePERatio(java.lang.String stockSymbol, double price)                           |
                            To calculate the P/E Ratio of provided Stock instance                                  |
-------------------------------------------------------------------------------------------------------------------|
double	                    calculateVolWeightedPrice(java.lang.String stockSymbol)                                |
                            To calculate the volume of provided Stock instance based on trades in past 5 minutes   |
-------------------------------------------------------------------------------------------------------------------|
void                        recordTrade(java.lang.String stockSymbol, TradeDomain tradeData)                       |
                            To record trade for provided stock                                                     |
-------------------------------------------------------------------------------------------------------------------|
## Class Diagram

 ![Class Diagram](/docs/design/ClassDiagram.png)
 
## Troubleshooting

Application is simple and easy to troubleshoot. You can enable debug logs or start as a stand alone application in debug. Also, 60 Test cases are written to ensure the application is always intact and any breaking change can easily be rectified.

## License
MIT

