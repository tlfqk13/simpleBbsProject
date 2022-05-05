package com.example.testproject.repository;

import com.example.testproject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    // findBy.컬럼이름 컬럼이름에 일치하는 사용자 데이터 가져옴

    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();
    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")  // ?1 = 첫번째 파라미터라는 의미
    List<User> findByUsernameQuery(String username);

    @Query(value = "select * from User u where u.username like %?1%",nativeQuery = true)  // nativeQuery 는 정통 쿼리 select * 가능
    List<User> findByUsernameNativeQuery(String username);

}
