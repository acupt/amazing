package com.acupt.dao;

import com.acupt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liujie on 2017/8/16.
 */
public interface UserDAO extends JpaRepository<User, Long> {
}
