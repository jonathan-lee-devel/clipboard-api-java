package io.jonathanlee.supplierservice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class StatusDataContainer<T> {

  HttpStatus httpStatus;

  T data;

}
