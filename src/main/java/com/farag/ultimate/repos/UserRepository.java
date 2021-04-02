package com.farag.ultimate.repos;

import com.farag.ultimate.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user.roles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByUserName(String s);
    Long removeById(Long id);
//    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.userName = (:username)")
//    Optional<User> findByUserNameAndFetchRolesEagerly(@Param("username") String userName);

}
