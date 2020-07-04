package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.Role;
import com.pepit.compareTout.entity.User;
import com.pepit.compareTout.entity.UserWithProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    @Query("SELECT new com.pepit.compareTout.entity.UserWithProduct(u, count(p)) from User u left join Product p on p.user = u INNER JOIN u.roles r WHERE r  = ?1 GROUP BY u")
    Collection<UserWithProduct> findAllByRolesEqualsWithProductCount(Role role);
}