package io.jonathanlee.supplierservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Supplier {

  @Id
  @Field("_id")
  private ObjectId objectId;

  private String id;

  private String name;

}
