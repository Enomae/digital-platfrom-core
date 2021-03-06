package com.dvfu.digital_platform_core.service.impl;

import com.dvfu.digital_platform_core.constants.Project2ProductMode;
import com.dvfu.digital_platform_core.dao.Product;
import com.dvfu.digital_platform_core.dao.Project;
import com.dvfu.digital_platform_core.constants.ProjectProgress;
import com.dvfu.digital_platform_core.repository.ProductRepository;
import com.dvfu.digital_platform_core.repository.ProjectRepository;
import com.dvfu.digital_platform_core.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProductRepository productRepository;

    ProjectServiceImpl(ProjectRepository projectRepository, ProductRepository productRepository) {
        this.projectRepository = projectRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Project> findAll(Long id) {
        return projectRepository.findAllByOwner_Id(id);
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.getOne(id);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project insert(Project project) {
        setInProgressStatus(project);
        return projectRepository.save(project);
    }

    @Override
    public void delete(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project update(Long id, Project project) {
        Project projectFromDB = findById(id);

        BeanUtils.copyProperties(project, projectFromDB, "id");

        if(!checkCrowdfundingDone(projectFromDB))
            setInProgressStatus(projectFromDB);

        return projectRepository.save(projectFromDB);
    }

    @Override
    public boolean checkOwner(Long spnaId, Project project) {
        Long ownerId = project.getOwner().getId();
        return ownerId.equals(spnaId);
    }

    @Override
    public boolean checkCrowdfundingDone(Project project) {
        return project.getCurrentFinancing() >= project.getTotalFinancing();
    }

    @Override
    public void setInProgressStatus(Project project) {
        project.setProjectProgress(ProjectProgress.IN_PROGRESS);
    }

    @Override
    public void setCrowdfundingDoneStatus(Project project) {
        project.setProjectProgress(ProjectProgress.CROWDFUNDING_DONE);
    }

    @Override
    public void setFinishedStatus(Project project) {
        project.setProjectProgress(ProjectProgress.FINISHED);
    }

    @Override
    public void donationsIncrement(Project project) {
        project.setDonationAmount(project.getDonationAmount() + 1);

        update(project.getId(), project);
    }

    @Override
    public void addMoney(Project project, Double money) {
        project.setCurrentFinancing(project.getCurrentFinancing() + money);

        if(checkCrowdfundingDone(project)) {
            setCrowdfundingDoneStatus(project);

            if(project.getProject2ProductMode().equals(Project2ProductMode.AUTO)) {
                setFinishedStatus(project);
                transferProjectToProduct(project);
            }
        }

        update(project.getId(), project);
    }

    @Override
    public void transferProjectToProduct(Project project) {
        Product newProduct = new Product();
        newProduct.setOriginalTitle(project.getTitle());

        productRepository.save(newProduct);
    }
}
