package io.jonathanlee.supplierservice.repository;

import io.jonathanlee.supplierservice.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {

}
