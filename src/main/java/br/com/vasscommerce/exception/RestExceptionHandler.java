package br.com.vasscommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Erros 400 sempre no mesmo JSON (ApiError), vindo de qualquer controller. */
@RestControllerAdvice
public class RestExceptionHandler {

    // @Valid falhou no corpo (ProdutoCreateRequest)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.CampoInvalido> erros = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(e -> new ApiError.CampoInvalido(e.getField(), e.getDefaultMessage()))
                .toList();
        return badRequest("Dados inválidos", req, erros);
    }

    // JSON malformado ou com tipo errado (ex.: valorUnitario: "abc")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> jsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return badRequest("JSON malformado ou com tipos inválidos", req, List.of());
    }

    // id ou filtro inválido na URL (ex.: /produto/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> parametroInvalido(MethodArgumentTypeMismatchException ex,
                                                      HttpServletRequest req) {
        return badRequest("Parâmetro '" + ex.getName() + "' com valor inválido", req, List.of());
    }

    private ResponseEntity<ApiError> badRequest(String mensagem, HttpServletRequest req,
                                                List<ApiError.CampoInvalido> erros) {
        ApiError corpo = new ApiError(Instant.now(), 400, "Bad Request", mensagem, req.getRequestURI(), erros);
        return ResponseEntity.badRequest().body(corpo);
    }
}
