package com.leavelive.demo.repository;

import com.leavelive.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //@Repository가 없어도 IoC됨. JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
