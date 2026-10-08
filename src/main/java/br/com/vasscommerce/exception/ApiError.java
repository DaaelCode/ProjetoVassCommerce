package br.com.vasscommerce.exception;

import java.time.Instant;
import java.util.List;

/** Formato único do JSON de erro 400. */
public record ApiError(Instant timestamp, int status, String error, String message, String path,
                       List<CampoInvalido> erros) {

    public record CampoInvalido(String campo, String mensagem) {
    }
}
