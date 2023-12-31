package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.request.AccountRequest;
import com.devas.travel.agency.application.dto.request.ResetPasswordRequest;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.UserResponse;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Either<Error, UserResponse> findByUserLogin(String userName);

    Either<Error, List<UserResponse>> getUserByRole(List<Integer> rolesId);

    Either<Error, UserResponse> createAccount(AccountRequest request);

    Either<Error, UserResponse> getById(int userId);

    Either<Error, String> prepareForResetPassword(String email);

    Either<Error, String> resetPassword(ResetPasswordRequest request);

    Either<Error, String> updateUserPassword(ResetPasswordRequest request);

    Either<Error, String> updateUserMail(ResetPasswordRequest request);

    Either<Error, List<UserResponse>> getUsersByEmail(String email);

}
