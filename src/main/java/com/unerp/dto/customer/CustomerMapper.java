package com.unerp.dto.customer;

import com.unerp.domain.customer.Customer;

public final class CustomerMapper {

  private CustomerMapper() {}

    public static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getAddress()
        );
    }
}
