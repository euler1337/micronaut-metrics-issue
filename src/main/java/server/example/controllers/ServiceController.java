package server.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.reactivex.Single;
import server.example.ApplicationException;
import static io.micronaut.http.MediaType.TEXT_PLAIN;

@Controller
public class ServiceController {
    @Get
    @Produces(TEXT_PLAIN)
    public Single<HttpResponse<?>> error(@QueryValue Integer status) {
        return Single.error(new ApplicationException(HttpStatus.valueOf(status), "error"));
    }
}
