package com.taomish.xero.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceDTO extends AbstractBaseEntityDTO {
    private List<InvoiceLineItemDTO> invoiceLineItemList;
    private List<InvoiceLineItemDTO> advanceInvoiceLineItemList;
    private String tradeId ="";
    private String invoiceNumber="";
    private String company="";
    private String commodity="";
    private String settlementAmount="";
    private double amount=0.0;
    private String currency="";
    private double fxRate=0.0;
    private LocalDateTime invoiceDate;
    private LocalDateTime postingDate;
    private LocalDateTime traderApprovalDate;
    private LocalDateTime paymentConfirmedDate;
    private LocalDateTime financeApprovedDate;
    private String paymentAmount="";
    private String paymentRefNumber="";
    private LocalDateTime paymentDate;
    private String counterpartyRefNumber="";
    private String purpose="";
    private LocalDateTime paymentDueDate;
    private String contractNumber="";
    private String deliveryTerms="";
    private String limitsOrTolerance="";
    private String portOfLoading="";
    private String portOfDischarging="";
    private String termsOfPayment="";
    private String shipment="";
    private double quantity=0.0;
    private String uom="";
    private double unitPrice=0.0;
    private String counterparty="";
    private String bankName="";
    private String bankAddress="";
    private String accountNumber="";
    private String swiftCode="";
    private String remitTo="";
    private String invoiceGeneratedBy = "";
    private String invoiceApprovedBy = ""; //only trader
    private String paymentConfirmedBy = "";
    private String financeApprovedBy = ""; //only finance controller
    private String financialAmountType;
    private String status;
    private byte[] invoiceDocument;
    private String invoiceDocumentUrl="";
    private String attachmentName="";
    private String companyAddress="";
    private String counterpartyAddress="";
    private String invoiceType;
    private String total="";
    private String totalInWords="";
    private String adjustmentAmount="";
    private String blNumber="";
    private LocalDateTime blDate;
    private LocalDateTime tradeDate;
    private LocalDateTime deliveryEndDate;
    private String vesselName="";
    private String traderName="";
    private String incoterm="";
    private String adjustmentDescription="";
    private String notifyPartyName="";
    private String notifyPartyAddress="";

}
