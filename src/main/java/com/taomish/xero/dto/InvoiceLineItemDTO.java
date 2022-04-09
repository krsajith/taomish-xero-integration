package com.taomish.xero.dto;



import java.time.LocalDateTime;

public class InvoiceLineItemDTO extends AbstractBaseEntityDTO {
    private String plannedObligationId;
    private String invoiceNumber; //1
    private String cashflowId;
    private String tradeId;
    private String cashflowType;
    private double costValue;
    private String currency;
    private double quantity;
    private String uom;
    private double settlementAmount;
    private String narration;
    private double tradePrice;
    private Boolean tradeType;
    private LocalDateTime tradeDate;
    private String commodity;
    private LocalDateTime deliveryStartDate;
    private LocalDateTime deliveryEndDate;
    private String incoterm;
    private String broker;
    private String priceType;
    private String profitcenter;
    private String settlementCurrency;
    private String tradePriceCurrency;
    private LocalDateTime paymentDueDate;
    private String financialAmountType;
    private String quantityStatus;
    private String priceStatus;
    private String counterparty;
    private String fxType;
    private String allocationId;
    private String priceCurrency;
    private String priceCurrencyUom;
    private String quantityUom;
    private String company;
    private String stage;
    private String brand;
    private String grade;
    private String origin;
    private String season;
    private String costName;
    private String costGroup;
    private boolean advancePayment = false;
    private boolean paidInvoice = false;

    public String getPlannedObligationId() {
        return plannedObligationId;
    }

    public void setPlannedObligationId(String plannedObligationId) {
        this.plannedObligationId = plannedObligationId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCashflowId() {
        return cashflowId;
    }

    public void setCashflowId(String cashflowId) {
        this.cashflowId = cashflowId;
    }

    public String getCashflowType() {
        return cashflowType;
    }

    public void setCashflowType(String cashflowType) {
        this.cashflowType = cashflowType;
    }

    public double getCostValue() {
        return costValue;
    }

    public void setCostValue(double costValue) {
        this.costValue = costValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public Boolean getTradeType() {
        return tradeType;
    }

    public void setTradeType(Boolean tradeType) {
        this.tradeType = tradeType;
    }

    public LocalDateTime getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDateTime tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public LocalDateTime getDeliveryStartDate() {
        return deliveryStartDate;
    }

    public void setDeliveryStartDate(LocalDateTime deliveryStartDate) {
        this.deliveryStartDate = deliveryStartDate;
    }

    public LocalDateTime getDeliveryEndDate() {
        return deliveryEndDate;
    }

    public void setDeliveryEndDate(LocalDateTime deliveryEndDate) {
        this.deliveryEndDate = deliveryEndDate;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getProfitcenter() {
        return profitcenter;
    }

    public void setProfitcenter(String profitcenter) {
        this.profitcenter = profitcenter;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public String getTradePriceCurrency() {
        return tradePriceCurrency;
    }

    public void setTradePriceCurrency(String tradePriceCurrency) {
        this.tradePriceCurrency = tradePriceCurrency;
    }

    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getFinancialAmountType() {
        return financialAmountType;
    }

    public void setFinancialAmountType(String financialAmountType) {
        this.financialAmountType = financialAmountType;
    }

    public String getQuantityStatus() {
        return quantityStatus;
    }

    public void setQuantityStatus(String quantityStatus) {
        this.quantityStatus = quantityStatus;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getFxType() {
        return fxType;
    }

    public void setFxType(String fxType) {
        this.fxType = fxType;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getPriceCurrencyUom() {
        return priceCurrencyUom;
    }

    public void setPriceCurrencyUom(String priceCurrencyUom) {
        this.priceCurrencyUom = priceCurrencyUom;
    }

    public String getQuantityUom() {
        return quantityUom;
    }

    public void setQuantityUom(String quantityUom) {
        this.quantityUom = quantityUom;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostGroup() {
        return costGroup;
    }

    public void setCostGroup(String costGroup) {
        this.costGroup = costGroup;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public boolean isAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(boolean advancePayment) {
        this.advancePayment = advancePayment;
    }

    public boolean isPaidInvoice() {
        return paidInvoice;
    }

    public void setPaidInvoice(boolean paidInvoice) {
        this.paidInvoice = paidInvoice;
    }
}
