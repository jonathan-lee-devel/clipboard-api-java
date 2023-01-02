package io.jonathanlee.supplierservice.service;

import io.jonathanlee.supplierservice.dto.OrderDto;
import io.jonathanlee.supplierservice.dto.StatusDataContainer;
import java.util.Collection;

public interface OrderService {

  StatusDataContainer<Collection<OrderDto>> getAllOrders(final String requestingUserUsername);

  StatusDataContainer<OrderDto> getOrderById(final String requestingUserUsername,
      final String orderId);

  StatusDataContainer<OrderDto> createOrder(final String requestingUserUsername,
      final OrderDto orderDto);

  StatusDataContainer<OrderDto> updateOrderById(
      final String requestingUserUsername,
      final String orderId,
      final OrderDto orderDto
  );

  StatusDataContainer<Void> deleteOrderById(final String requestingUsername, final String orderId);

}
