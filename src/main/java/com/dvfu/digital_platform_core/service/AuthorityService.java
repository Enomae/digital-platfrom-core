package com.dvfu.digital_platform_core.service;

import com.dvfu.digital_platform_core.dao.Authority;
import com.dvfu.digital_platform_core.constants.UserRoleName;

import java.util.List;

public interface AuthorityService {

    public List<Authority> findById(Long id);

    public List<Authority> findByAuthority(UserRoleName authority);

}