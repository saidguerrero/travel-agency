package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.AccountRequest;
import com.devas.travel.agency.application.dto.request.UserRequest;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.UserService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//    @PostMapping("/login")
//    public ResponseEntity<Response> getUserByLogin(@RequestBody UserRequest request) {
//        return userService.findByUserLogin(request).fold(
//                ControllerUtils::getResponseError,
//                ControllerUtils::getResponseSuccessOk
//        );
//    }

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
}
