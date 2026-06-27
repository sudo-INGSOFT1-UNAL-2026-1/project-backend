package com.unerp.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SupplierCreateRequest (

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    String name,

    @NotBlank(message = "El numero de telefono del proveedor es obligatorio")
    String phone,

    @NotBlank(message = "El correo del proveedor es obligatorio")
    @Email(message = "El correo del proveedor no es valido")
    String email
){

}
