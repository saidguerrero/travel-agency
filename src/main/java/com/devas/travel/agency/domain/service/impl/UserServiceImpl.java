package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.AccountRequest;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.UserResponse;
import com.devas.travel.agency.domain.model.SalesPersonInfo;
import com.devas.travel.agency.domain.model.User;
import com.devas.travel.agency.domain.service.SalesPersonInfoService;
import com.devas.travel.agency.domain.service.UserService;
import com.devas.travel.agency.infrastructure.adapter.repository.RoleRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.UserRepository;
import com.devas.travel.agency.infrastructure.utils.Utils;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SalesPersonInfoService salesPersonInfoService;

    @Override
    public Either<Error, UserResponse> findByUserLogin(String userName) {
        return userRepository.findByUserLoginAndActiveTrue(userName)
                .<Either<Error, UserResponse>>map(user -> Either.right(UserResponse.builder()
                        .userId(user.getUserId())
                        .login(user.getUserLogin())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .roleId(user.getRole().getRoleId())
                        .role(user.getRole().getName())
                        .currentDate(Utils.getFormatDateSpanish())
                        .build()))
                .orElseGet(() -> Either.left(Error.builder()
                        .message("User not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()));
    }

    @Override
    public Either<Error, List<UserResponse>> getUserByRole(List<Integer> rolesId) {
        var items = userRepository.findByRoleRoleIdInAndActiveTrue(rolesId);
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("User not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items.stream().map(user -> UserResponse.builder()
                .userId(user.getUserId())
                .login(user.getUserLogin())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roleId(user.getRole().getRoleId())
                .role(user.getRole().getName())
                .build()).collect(Collectors.toList()));

    }

    @Override
    public Either<Error, UserResponse> createAccount(AccountRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setActive(true);
        user.setUserLogin(request.getLogin());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        try {
            roleRepository.findByRoleId(request.getRoleId()).ifPresent(user::setRole);
            userRepository.save(user);
            if ("Vendedor".equals(user.getRole().getName())) {
                salesPersonInfoService.createSalesPersonInfo(user, request.getBranchId(), request.getCityId());

            }

        } catch (Exception e) {
            return Either.left(Error.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
        return Either.right(UserResponse.builder()
                .userId(user.getUserId())
                .login(user.getUserLogin())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build());

    }

    @Override
    public Either<Error, UserResponse> getById(int userId) {
        var user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return Either.left(Error.builder()
                    .message("User not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());
        } else {
            SalesPersonInfo salesPersonInfo = null;
            if ("Vendedor".equals(user.get().getRole().getName())) {
                salesPersonInfo = salesPersonInfoService.getInformation(userId);

            }
            return Either.right(UserResponse.builder()
                    .userId(user.get().getUserId())
                    .login(user.get().getUserLogin())
                    .fullName(user.get().getFullName())
                    .email(user.get().getEmail())
                    .roleId(user.get().getRole().getRoleId())
                    .role(user.get().getRole().getName())
                    .branchId(salesPersonInfo != null ? salesPersonInfo.getBranch().getBranchId() : 0)
                    .branch(salesPersonInfo != null ? salesPersonInfo.getBranch().getDescription() : "N/A")
                    .city(salesPersonInfo != null ? salesPersonInfo.getCity().getDescription() : "N/A")
                    .build());

        }
    }

}
