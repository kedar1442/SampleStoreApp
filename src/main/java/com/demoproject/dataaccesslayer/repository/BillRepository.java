package com.demoproject.dataaccesslayer.repository;

import org.springframework.data.repository.CrudRepository;
import com.demoproject.dataaccesslayer.entity.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
