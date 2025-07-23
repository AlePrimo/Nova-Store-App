package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Invoice Service", description = "Operaciones relacionadas con facturación")
public interface InvoiceService {

    InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto);
    InvoiceResponseDTO update(Long id, InvoiceRequestDTO dto);
    InvoiceResponseDTO getInvoiceById(Long id);

    Page<InvoiceResponseDTO> getAllInvoices(Pageable pageable);

    void deleteInvoice(Long id);
}
