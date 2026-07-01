package com.unerp.repository.customer;

import com.unerp.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerWriteRepository extends JpaRepository<Customer, Integer> {}