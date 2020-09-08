package com.demoproject.dataaccesslayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demoproject.dataaccesslayer.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	public long count();

	public List<Product> findByBarCodeId(String barCodeId);

}
