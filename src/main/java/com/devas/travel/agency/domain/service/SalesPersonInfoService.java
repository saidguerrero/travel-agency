package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.domain.model.SalesPersonInfo;
import com.devas.travel.agency.domain.model.User;

public interface SalesPersonInfoService {

    void createSalesPersonInfo(User user, int branchId, int cityId);

    SalesPersonInfo getInformation(int userId);

}
