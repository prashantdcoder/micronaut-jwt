package com.shanky.controller

import com.shanky.constants.RoleConstants
import com.shanky.service.AuthenticationService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured

import javax.inject.Inject
import java.security.Principal

@Secured("isAnonymous()")
@Controller("/public")
class PublicController {


    @Inject
    AuthenticationService authenticationService

    @Post(value = "/index", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.APPLICATION_JSON)
    HttpResponse index() {
        return HttpResponse.ok(authenticationService.index())
    }

    @Post(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.APPLICATION_JSON)
    HttpResponse authentication(String username, String password, String role) {
        return HttpResponse.ok(authenticationService.login(username, password, role))
    }

    @Secured([RoleConstants.ADMIN])
    @Post(value = "/verify", consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.APPLICATION_JSON)
    HttpResponse verifyToken(Principal principal) {
        return HttpResponse.ok(authenticationService.verify(principal))
    }
}
