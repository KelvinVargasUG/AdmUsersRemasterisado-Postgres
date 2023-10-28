package com.kjvargas.admusersremasterisadopostgres.Security.Entitys;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
