package com.agrosense.backend.service;

import com.agrosense.backend.dto.WeatherAlert;
import java.util.List;

public interface WeatherAlertService {
    List<WeatherAlert> getAlertsForDistrict(Long districtId);
}
