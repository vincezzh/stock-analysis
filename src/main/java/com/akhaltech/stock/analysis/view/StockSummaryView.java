package com.akhaltech.stock.analysis.view;

import java.util.HashMap;
import java.util.Map;

public class StockSummaryView {
    private String sourceFrom;
    private String symbol;
    private String tip;
    private Double marketCap = 0d; //市值
    private Double peInLast12Months = 0d; //市盈率 (最近 12 個月)
    private Double epsInLast12Months = 0d; //每股盈利 (最近 12 個月)
    private Map<String, StockView> stockMap = new HashMap<>();

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPeInLast12Months() {
        return peInLast12Months;
    }

    public void setPeInLast12Months(Double peInLast12Months) {
        this.peInLast12Months = peInLast12Months;
    }

    public Double getEpsInLast12Months() {
        return epsInLast12Months;
    }

    public void setEpsInLast12Months(Double epsInLast12Months) {
        this.epsInLast12Months = epsInLast12Months;
    }

    public Map<String, StockView> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<String, StockView> stockMap) {
        this.stockMap = stockMap;
    }
}
