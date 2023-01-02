package io.jonathanlee.supplierservice.controller;

import io.jonathanlee.supplierservice.dto.OrderDto;
import io.jonathanlee.supplierservice.dto.StatusDataContainer;
import io.jonathanlee.supplierservice.service.OrderService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  private static String getCurrentUsername() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<OrderDto>> getAllOrders() {
    final StatusDataContainer<Collection<OrderDto>> statusDataContainer
        = this.orderService.getAllOrders(getCurrentUsername());
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      value = "/{orderId}"
  )
  public ResponseEntity<OrderDto> getOrderById(@PathVariable final String orderId) {
    final StatusDataContainer<OrderDto> statusDataContainer
        = this.orderService.getOrderById(getCurrentUsername(), orderId);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrderDto> createOrder(@Validated @RequestBody final OrderDto orderDto) {
    final StatusDataContainer<OrderDto> statusDataContainer
        = this.orderService.createOrder(getCurrentUsername(), orderDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      value = "/{orderId}"
  )
  public ResponseEntity<OrderDto> updateOrderById(
      @PathVariable final String orderId,
      @Validated @RequestBody final OrderDto orderDto
  ) {
    final StatusDataContainer<OrderDto> statusDataContainer
        = this.orderService.updateOrderById(getCurrentUsername(), orderId, orderDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrderById(@PathVariable final String orderId) {
    final StatusDataContainer<Void> statusDataContainer
        = this.orderService.deleteOrderById(getCurrentUsername(), orderId);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .build();
  }

}
