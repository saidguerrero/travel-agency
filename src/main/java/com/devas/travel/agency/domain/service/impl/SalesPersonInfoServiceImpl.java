package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.domain.model.SalesPersonInfo;
import com.devas.travel.agency.domain.model.User;
import com.devas.travel.agency.domain.service.SalesPersonInfoService;
import com.devas.travel.agency.infrastructure.adapter.repository.BranchRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.CityRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.SalesPersonInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesPersonInfoServiceImpl implements SalesPersonInfoService {

    private final BranchRepository branchRepository;

    private final CityRepository cityRepository;

    private final SalesPersonInfoRepository salesPersonInfoRepository;

    @Override
    public void createSalesPersonInfo(User user, int branchId, int cityId) {
        SalesPersonInfo salesPersonInfo = new SalesPersonInfo();
        salesPersonInfo.setBranch(branchRepository.findById(branchId).orElse(null));
        salesPersonInfo.setCity(cityRepository.findById(cityId).orElse(null));
        salesPersonInfo.setUser(user);

        salesPersonInfoRepository.save(salesPersonInfo);

    }

    @Override
    public SalesPersonInfo getInformation(int userId) {
       return salesPersonInfoRepository.findByUserUserId(userId)
               .orElseThrow(() -> new UsernameNotFoundException("El informacion del vendedor no existe"));

    }
}
