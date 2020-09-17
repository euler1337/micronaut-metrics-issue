package server.example.controllers;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import server.example.ApplicationException;

@Controller("/errorResponses")
public class ErrorController {

    @Error(global = true, exception = ApplicationException.class)
    public HttpResponse<?> applicationException(HttpRequest request, ApplicationException e) {
        return HttpResponse.status(e.getStatus()).body(new ErrorResponse(e.getMessage()));
    }
}
