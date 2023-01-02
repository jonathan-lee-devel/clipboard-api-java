package io.jonathanlee.supplierservice.repository;

import io.jonathanlee.supplierservice.model.Supplier;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, ObjectId> {

}
