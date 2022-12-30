package io.jonathanlee.registrationservice.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@AllArgsConstructor
public class Token implements Serializable {

  @Serial
  private static final long serialVersionUID = 2405172041950251807L;

  @Id
  @Field("_id")
  private ObjectId objectId;

  private String id;

  private String value;

  private Instant expiryDate;

}
