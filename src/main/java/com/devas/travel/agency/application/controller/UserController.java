package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.AccountRequest;
import com.devas.travel.agency.application.dto.request.ResetPasswordRequest;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.UserService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devas.travel.agency.infrastructure.constants.Constants.SALES_PERSON_ID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Users", description = "Users API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Response> creteAccount(@RequestBody AccountRequest request) {
        return userService.createAccount(request).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<Response> getUserByUsername(@PathVariable String username) {
        return userService.findByUserLogin(username).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }

    @GetMapping("/salesPerson")
    public ResponseEntity<Response> getSalesPerson() {
        return userService.getUserByRole(List.of(SALES_PERSON_ID)).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }

    @PostMapping("/prepareResetPassword")
    public ResponseEntity<Response> prepareResetPassword(@Param("email") String email ) {
        log.info("prepare reset password - starting...");
        return userService.prepareForResetPassword(email).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordRequest request) {
        log.info("reset password - starting...");
        return userService.resetPassword(request).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);
    }


    @PostMapping("/updateUserPwd")
    public ResponseEntity<Response> updateUser(@RequestBody ResetPasswordRequest usersRequest) {
        log.info("Actualizar contraseña de usuario...");
        return userService.updateUserPassword(usersRequest)
                .fold(
                        ControllerUtils::getResponseError,
                        ControllerUtils::getResponseSuccessOk
                );
    }

    @GetMapping("/usersByEmail/{email}")
    public ResponseEntity<Response> getUsersByEmail(@PathVariable String email) {
        return userService.getUsersByEmail(email).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }
}
