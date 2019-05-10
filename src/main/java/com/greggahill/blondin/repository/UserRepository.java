package com.greggahill.blondin.repository;

import com.greggahill.blondin.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u from User u WHERE u.userName = :user_name")
    User findUserByUserName( @Param("user_name") String user_name );
}
