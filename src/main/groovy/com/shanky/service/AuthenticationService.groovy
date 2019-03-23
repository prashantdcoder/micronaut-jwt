package com.shanky.service

import com.shanky.VO.UserVO
import com.shanky.constants.RoleConstants
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.token.jwt.generator.AccessRefreshTokenGenerator
import io.micronaut.security.token.jwt.render.AccessRefreshToken

import javax.inject.Inject
import javax.inject.Singleton
import java.security.Principal

@Singleton
class AuthenticationService {

    @Inject
    AccessRefreshTokenGenerator accessRefreshTokenGenerator

    Map index() {
        return [status: true, message: "Welcome to jwt authentication"]
    }

    UserVO login(String username, String password, String role) {
        UserDetails userDetails = new UserDetails(username, [role == "ADMIN" ? RoleConstants.ADMIN : RoleConstants.USER])
        Optional<AccessRefreshToken> accessRefreshToken = accessRefreshTokenGenerator.generate(userDetails)
        UserVO userVO = new UserVO(
                username: username,
                accessToken: accessRefreshToken.get().accessToken,
                refreshToken: accessRefreshToken.get().refreshToken,
                roles: userDetails.roles
        )
        return userVO
    }

    Map verify(Principal principal) {
        return ['username': principal.getName()]
    }
}
