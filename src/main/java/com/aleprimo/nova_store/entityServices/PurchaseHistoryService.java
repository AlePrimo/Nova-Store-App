package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseHistoryService {
    Page<PurchaseHistoryDTO> getPurchaseHistoryByCustomerId(Long customerId, Pageable pageable);
}
