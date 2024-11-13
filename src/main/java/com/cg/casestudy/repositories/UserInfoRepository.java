package com.cg.casestudy.repositories;

import com.cg.casestudy.models.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
