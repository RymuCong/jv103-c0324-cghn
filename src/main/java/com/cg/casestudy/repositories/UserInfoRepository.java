package com.cg.casestudy.repositories;

import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findUserInfoByUser(User user);

    List<UserInfo> findByNameContains(String name);
}
