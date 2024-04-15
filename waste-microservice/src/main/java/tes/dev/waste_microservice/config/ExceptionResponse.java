package tes.dev.waste_microservice.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private Date time = new Date();
    private String message;
    private String url;

    public ExceptionResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
