package com.dvfu.digital_platform_core.service;

import com.dvfu.digital_platform_core.dao.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll(Long id);

    List<Project> findAll();

    Project findById(Long id);

    Project insert(Project project);

    void delete(Long projectId);

    Project update(Long id, Project project);

    boolean checkOwner(Long spnaId, Project project);

    boolean checkCrowdfundingDone(Project project);

    void setCrowdfundingDoneStatus(Project project);

    void setInProgressStatus(Project project);

    void setFinishedStatus(Project project);



    void donationsIncrement(Project project);

    void addMoney(Project project, Double money);

    void transferProjectToProduct(Project project);


}
