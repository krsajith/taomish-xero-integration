package com.taomish.xero.service;

import com.taomish.xero.dto.InvoiceDTO;
import org.springframework.stereotype.Service;

@Service
public class XeroInvoiceService {
  public InvoiceDTO submitInvoice(InvoiceDTO invoice) {
    return invoice;
  }
}
