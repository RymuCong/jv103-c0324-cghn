package com.cg.casestudy.repositories;

import com.cg.casestudy.models.user.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    @Query("SELECT r FROM Relationship r WHERE (r.userOne.id = :userId OR r.userTwo.id = :userId) AND r.status = true")
    List<Relationship> findAllFriendsByUserId(@Param("userId") Long userId);
}
