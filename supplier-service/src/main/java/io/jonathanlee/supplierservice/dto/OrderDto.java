package io.jonathanlee.supplierservice.dto;

import lombok.Data;

@Data
public class OrderDto {

  private String id;

  private String creatorId;

  private String details;

}
