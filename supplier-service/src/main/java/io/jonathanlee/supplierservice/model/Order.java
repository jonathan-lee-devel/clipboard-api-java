package io.jonathanlee.supplierservice.model;

import io.jonathanlee.registrationservice.model.ApplicationUser;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Order {

  @Id
  @Field("_id")
  private ObjectId objectId;

  private String id;

  @DBRef
  private Supplier supplier;

  @DBRef
  private ApplicationUser creator;

  private String details;

}
