package com.akhaltech.stock.analysis.view;

public class StockView {
    private String title;
    private Double totalRevenue = 0d; //總收益
    private Double netIncome = 0d; //淨收益
    private Double researchDevelopment = 0d; //研究發展
    private Double totalCash = 0d; //總現金
    private Double totalCurrentAssets = 0d; //目前總資產
    private Double totalAssets = 0d; //總資產
    private Double totalCurrentLiabilities = 0d; //目前總負債
    private Double totalLiabilities = 0d; //總負債
    private Double totalStockholdersEquity = 0d; //股東權益總額

    //流动比率 = 目前總資產 / 目前總負債 （很好>2，一般1-2，不好<1）
    public Double getCurrentRatio() {
        try {
            return totalCurrentAssets / totalCurrentLiabilities;
        }catch (Exception e) {
            return 0d;
        }
    }

    //负债比率 = 總負債 / 總資產 (50%-60%比较好)
    public Double getDebtRatio() {
        try {
            return totalLiabilities / totalAssets;
        }catch (Exception e) {
            return 0d;
        }
    }

    //销货报酬率 = 淨收益 / 總收益
    public Double getRos() {
        try {
            return netIncome / totalRevenue;
        }catch (Exception e) {
            return 0d;
        }
    }

    //股东权益报酬率 = 淨收益 / 股東權益總額
    public Double getRoe() {
        try {
            return netIncome / totalStockholdersEquity;
        }catch (Exception e) {
            return 0d;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Double getResearchDevelopment() {
        return researchDevelopment;
    }

    public void setResearchDevelopment(Double researchDevelopment) {
        this.researchDevelopment = researchDevelopment;
    }

    public Double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Double totalCash) {
        this.totalCash = totalCash;
    }

    public Double getTotalCurrentAssets() {
        return totalCurrentAssets;
    }

    public void setTotalCurrentAssets(Double totalCurrentAssets) {
        this.totalCurrentAssets = totalCurrentAssets;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getTotalCurrentLiabilities() {
        return totalCurrentLiabilities;
    }

    public void setTotalCurrentLiabilities(Double totalCurrentLiabilities) {
        this.totalCurrentLiabilities = totalCurrentLiabilities;
    }

    public Double getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Double getTotalStockholdersEquity() {
        return totalStockholdersEquity;
    }

    public void setTotalStockholdersEquity(Double totalStockholdersEquity) {
        this.totalStockholdersEquity = totalStockholdersEquity;
    }
}
