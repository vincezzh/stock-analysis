package com.akhaltech.stock.analysis.spider;

import com.akhaltech.stock.analysis.view.StockSummaryView;
import com.akhaltech.stock.analysis.view.StockView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YahooFinanceSpider extends Spider {

    private final static Logger log = Logger.getLogger(YahooFinanceSpider.class);
    private final String EMPTY = "";
    private final String DOLLAR = "$";
    private final String COMMA = ",";
    private final String DOT = ".";
    private Map<String, String> letterToNumberMap = new HashMap<>();

    public YahooFinanceSpider() {
        letterToNumberMap.put("T", "000000000000");
        letterToNumberMap.put("B", "000000000");
        letterToNumberMap.put("M", "000000");
        initDriver();
    }

    public StockSummaryView startRequest(String symbol) {
        log.info("Run startRequest()");
        try {
            String url = "https://ca.finance.yahoo.com/quote/" + symbol + "?p=" + symbol;
            driver.get(url);
//            scrollUntilLoaded();

            StockSummaryView summary = new StockSummaryView();
            summary.setSourceFrom("yahoo");
            summary.setSymbol(symbol);

            WebElement quoteSummaryNode = driver.findElement(By.id("quote-summary"));
            WebElement marketCapNode = quoteSummaryNode.findElement(By.xpath(".//td[@data-test='MARKET_CAP-value']/span"));
            summary.setMarketCap(generateMarketCap(marketCapNode.getText()));
            WebElement peNode = quoteSummaryNode.findElement(By.xpath(".//td[@data-test='PE_RATIO-value']/span"));
            summary.setPeInLast12Months(getDoubleValue(peNode.getText()));
            WebElement epsNode = quoteSummaryNode.findElement(By.xpath(".//td[@data-test='EPS_RATIO-value']/span"));
            summary.setEpsInLast12Months(getDoubleValue(epsNode.getText()));

            Actions actions = new Actions(driver);
            WebElement quoteNavNode = driver.findElement(By.id("quote-nav"));
            WebElement noticeNode = quoteNavNode.findElement(By.xpath("./ul/li[@data-test='FINANCIALS']/a"));
            actions.moveToElement(noticeNode).click().perform();

            WebElement financeNode = driver.findElement(By.id("Col1-1-Financials-Proxy"));
            WebElement tipNode = financeNode.findElement(By.xpath("./section[@data-test='qsp-financial']/div[2]/span/span"));
            summary.setTip(tipNode.getText());

            WebElement incomeStatementNode = financeNode.findElement(By.xpath("./section[@data-test='qsp-financial']/div[3]/div[1]/div[1]"));
            List<WebElement> titleNodes = incomeStatementNode.findElements(By.xpath("./div[1]/div[1]/div"));
            List<WebElement> incomeStatementDetailsNodes = incomeStatementNode.findElements(By.xpath("./div[2]/div[@data-test='fin-row']"));
            for (int i = 1; i < titleNodes.size(); i++) {
                String title = titleNodes.get(i).findElement(By.xpath("./span")).getText();
                if (isValidDate(title)) {
                    if (summary.getStockMap().get(title) == null) {
                        summary.getStockMap().put(title, new StockView());
                    }

                    summary.getStockMap().get(title).setTotalRevenue(getIncomeStatmentCellValue(incomeStatementDetailsNodes, "Total Revenue", i));
                    summary.getStockMap().get(title).setNetIncome(getIncomeStatmentCellValue(incomeStatementDetailsNodes, "Net Income", i));
                    summary.getStockMap().get(title).setResearchDevelopment(getIncomeStatmentCellValue(getSubIncomeStatementDetailsNodes(incomeStatementDetailsNodes, "Operating Expenses"), "Research Development", i));
                }
            }

            return summary;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            driver.close();
        }
    }

    private List<WebElement> getSubIncomeStatementDetailsNodes(List<WebElement> nodeList, String type) {
        try {
            List<WebElement> subNodeList = new ArrayList<>();
            for(WebElement node : nodeList) {
                List<WebElement> rowNodes = node.findElements(By.xpath("./div[1]/div"));
                WebElement typeNode = rowNodes.get(0);
                if(type.equals(typeNode.findElement(By.xpath("./div[1]/span")).getText())) {
                    subNodeList = node.findElements(By.xpath("./div[2]/div[@data-test='fin-row']"));
                    break;
                }
            }
            return subNodeList;
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private Double getIncomeStatmentCellValue(List<WebElement> nodeList, String type, int index) {
        try {
            Double value = 0d;
            for(WebElement node : nodeList) {
                List<WebElement> rowNodes = node.findElements(By.xpath("./div[1]/div"));
                WebElement typeNode = rowNodes.get(0);
                if(type.equals(typeNode.findElement(By.xpath("./div[1]/span")).getText())) {
                    WebElement cellNode = rowNodes.get(index).findElement(By.xpath("./span"));
                    value = getDoubleValue(cellNode.getText());
                    break;
                }
            }
            return value;
        }catch (Exception e) {
            return 0d;
        }
    }

    private boolean isValidDate(String value) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(value);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private Double generateMarketCap(String value) {
        String convertedValue = value;
        String lastLetter = value.substring(value.length()-1).toUpperCase();
        if(letterToNumberMap.get(lastLetter) != null) {
            convertedValue = value.substring(0, value.length()-1) + letterToNumberMap.get(lastLetter);
        }
        if(convertedValue.indexOf(DOT) != -1) {
            convertedValue = convertedValue.substring(0, convertedValue.length()-3).replace(DOT, EMPTY);
        }
        return getDoubleValue(convertedValue);
    }

    private Double getDoubleValue(String value) {
        try {
            return Double.parseDouble(value.replace(DOLLAR, EMPTY).replace(COMMA, EMPTY));
        }catch (Exception e) {
            return 0d;
        }
    }

    public static void main(String[] args) throws Exception {
        YahooFinanceSpider ts = new YahooFinanceSpider();
        StockSummaryView stockSummaryView = ts.startRequest("AAPL");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(stockSummaryView));
    }

}
