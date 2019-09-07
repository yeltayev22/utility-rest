package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.dto.InvoiceDto;
import kz.yeltayev.utility.model.request.InvoiceRequest;
import kz.yeltayev.utility.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public List<InvoiceDto> fetchServices() {
        return invoiceService.fetchInvoices();
    }

    @GetMapping("/invoices/{counterNumber}")
    public ResponseEntity<InvoiceDto> fetchInvoiceByCounterNumber(@PathVariable(value = "counterNumber") Long counterNumber) throws ResourceNotFoundException {
        InvoiceDto invoiceDto = invoiceService.fetchInvoiceByCounterNumber(counterNumber);
        return ResponseEntity.ok().body(invoiceDto);
    }

    @PutMapping("/invoices/update")
    public ResponseEntity<InvoiceDto> updateService(@Valid @RequestBody InvoiceRequest invoiceRequest) throws ResourceNotFoundException {
        InvoiceDto updatedInvoice = invoiceService.updateInvoice(invoiceRequest);
        return ResponseEntity.ok(updatedInvoice);
    }
}
