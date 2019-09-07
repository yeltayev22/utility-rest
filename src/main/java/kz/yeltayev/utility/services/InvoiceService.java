package kz.yeltayev.utility.services;

import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.dto.InvoiceDto;
import kz.yeltayev.utility.model.entity.Invoice;
import kz.yeltayev.utility.model.request.InvoiceRequest;
import kz.yeltayev.utility.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public List<InvoiceDto> fetchInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return convertToListInvoiceDto(invoices);
    }

    @Transactional
    public InvoiceDto fetchInvoiceByCounterNumber(Long counterNumber) throws ResourceNotFoundException {
        return convertToDto(invoiceRepository.findById(counterNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found for this counterNumber : " + counterNumber)));
    }

    @Transactional
    public InvoiceDto updateInvoice(InvoiceRequest invoiceRequest) throws ResourceNotFoundException {
        Invoice invoice = invoiceRepository.findById(invoiceRequest.getCounterNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found for this counterNumber : " + invoiceRequest.getCounterNumber()));

        switch (invoiceRequest.getMonthNumber()) {
            case 1:
                invoice.setMonth1(invoiceRequest.getCounterValue());
                break;
            case 2:
                invoice.setMonth2(invoiceRequest.getCounterValue());
                break;
            case 3:
                invoice.setMonth3(invoiceRequest.getCounterValue());
                break;
            case 4:
                invoice.setMonth4(invoiceRequest.getCounterValue());
                break;
            case 5:
                invoice.setMonth5(invoiceRequest.getCounterValue());
                break;
            case 6:
                invoice.setMonth6(invoiceRequest.getCounterValue());
                break;
            case 7:
                invoice.setMonth7(invoiceRequest.getCounterValue());
                break;
            case 8:
                invoice.setMonth8(invoiceRequest.getCounterValue());
                break;
            case 9:
                invoice.setMonth9(invoiceRequest.getCounterValue());
                break;
            case 10:
                invoice.setMonth10(invoiceRequest.getCounterValue());
                break;
            case 11:
                invoice.setMonth11(invoiceRequest.getCounterValue());
                break;
            case 12:
                invoice.setMonth12(invoiceRequest.getCounterValue());
                break;
        }

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return convertToDto(updatedInvoice);
    }

    private List<InvoiceDto> convertToListInvoiceDto(List<Invoice> invoices) {
        if (invoices.isEmpty())
            return new ArrayList<>();
        return invoices.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private InvoiceDto convertToDto(Invoice invoice) {
        InvoiceDto invoiceDto = modelMapper.map(invoice, InvoiceDto.class);
        return invoiceDto;
    }

}
