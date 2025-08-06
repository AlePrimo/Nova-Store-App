

//package com.aleprimo.nova_store.entityServices.implementations;

//import com.aleprimo.nova_store.dto.purchaseHistory.OrderItemDTO;
//import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
//import com.aleprimo.nova_store.entityServices.PurchaseHistoryService;
//import com.aleprimo.nova_store.models.Order;
//import com.aleprimo.nova_store.models.OrderItem;
//import com.aleprimo.nova_store.models.Shipping;
//
//import com.aleprimo.nova_store.repository.OrderRepository;
//import com.aleprimo.nova_store.repository.ShippingRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
//
//    private final OrderRepository orderRepository;
//    private final ShippingRepository shippingRepository;
//
//    @Override
//    public Page<PurchaseHistoryDTO> getPurchaseHistoryByCustomerId(Long customerId, Pageable pageable) {
//        return orderRepository.findByCustomerId(customerId, pageable)
//                .map(this::mapToPurchaseHistoryDTO);
//    }
//
//    private PurchaseHistoryDTO mapToPurchaseHistoryDTO(Order order) {
//        Shipping shipping = shippingRepository.findByOrderId(order.getId()).orElse(null);
//
//        return PurchaseHistoryDTO.builder()
//                .orderId(order.getId())
//                .orderDate(order.getCreatedAt())
//                .totalAmount(order.getTotalAmount())
//                .paymentMethod(order.getPaymentMethod())
//                .orderStatus(order.getOrderStatus())
//                .items(mapOrderItems(order.getOrderItems()))
//                .shippingAddress(shipping != null ? shipping.getAddress() : null)
//                .shippingCity(shipping != null ? shipping.getCity() : null)
//                .shippingPostalCode(shipping != null ? shipping.getPostalCode() : null)
//                .shippingCountry(shipping != null ? shipping.getCountry() : null)
//                .shippingStatus(shipping != null ? shipping.getStatus() : null)
//                .shippedAt(shipping != null ? shipping.getShippedAt() : null)
//                .deliveredAt(shipping != null ? shipping.getDeliveredAt() : null)
//                .build();
//    }
//
//    private List<OrderItemDTO> mapOrderItems(List<OrderItem> items) {
//        return items.stream()
//                .map(item -> OrderItemDTO.builder()
//                        .productName(item.getProduct().getName())
//                        .quantity(item.getQuantity())
//                        .price(item.getUnitPrice())
//                        .build())
//                .collect(Collectors.toList());
//    }
//}




package com.aleprimo.nova_store.entityServices.implementations;
import com.aleprimo.nova_store.controller.mappers.PurchaseHistoryMapper;
import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
import com.aleprimo.nova_store.entityServices.PurchaseHistoryService;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final OrderDAO orderDAO;
    private final ShippingDAO shippingDAO;
    private final PurchaseHistoryMapper purchaseHistoryMapper;

    @Autowired
    public PurchaseHistoryServiceImpl(OrderDAO orderDAO, ShippingDAO shippingDAO, PurchaseHistoryMapper purchaseHistoryMapper) {
        this.orderDAO = orderDAO;
        this.shippingDAO = shippingDAO;
        this.purchaseHistoryMapper = purchaseHistoryMapper;
    }

    @Override
    public Page<PurchaseHistoryDTO> getPurchaseHistoryByCustomerId(Long customerId, Pageable pageable) {
        Page<Order> orders = orderDAO.findByCustomerId(customerId, pageable);
        List<PurchaseHistoryDTO> history = orders.getContent().stream().map(order -> {
            Shipping shipping = shippingDAO.findByOrderId(order.getId()).orElse(null);
            return purchaseHistoryMapper.toDTO(order, shipping);
        }).toList();

        return new PageImpl<>(history, pageable, orders.getTotalElements());
    }
}