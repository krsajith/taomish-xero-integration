package com.taomish.xero;

import com.taomish.xero.dto.InvoiceDTO;
import com.taomish.xero.service.XeroInvoiceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XeroInvoiceController {

  private final XeroInvoiceService xeroInvoiceService;

  public XeroInvoiceController(XeroInvoiceService xeroInvoiceService) {
    this.xeroInvoiceService = xeroInvoiceService;
  }

  @PostMapping("/submit-invoice")
	public InvoiceDTO submitInvoice(@RequestBody InvoiceDTO invoice) {
		return xeroInvoiceService.submitInvoice(invoice);
	}
}
