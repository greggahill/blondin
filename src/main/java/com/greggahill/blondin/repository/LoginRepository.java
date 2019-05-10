package com.greggahill.blondin.repository;

import com.greggahill.blondin.model.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends CrudRepository<Login, Long> {
    @Query("SELECT l from Login l WHERE l.userName = :user_name")
    Login findUserByUserName(@Param("user_name") String user_name);
}
