package com.dvfu.digital_platform_core.service.impl;

import com.dvfu.digital_platform_core.constants.TourProgress;
import com.dvfu.digital_platform_core.dao.TourCreateNotification;
import com.dvfu.digital_platform_core.repository.TourProductRepository;
import com.dvfu.digital_platform_core.repository.TourRepository;
import com.dvfu.digital_platform_core.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final TourProductRepository tourProductRepository;

    NotificationServiceImpl(TourProductRepository tourProductRepository) {
        this.tourProductRepository = tourProductRepository;
    }

    @Override
    public List<TourCreateNotification> sendNotification(Long ownerId) {
        return tourProductRepository.findAllTourProductByTourProgressAndProduct_Owner_Id(TourProgress.IN_PROGRESS, ownerId);
    }
}
