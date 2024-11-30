package com.cg.casestudy.services;

import com.cg.casestudy.models.user.Relationship;

import java.util.List;

public interface RelationshipService {

    void addFriend(Relationship relationship);

    void removeFriend(Relationship relationship);

//    int countMutualFriends(Long userId, Long friendId);

    int countFriends(Long userId);

    int countPendingFriends(Long userId);

    List<Relationship> findAllFriends(Long userId);

    void acceptFriend(Long relationshipId);
}
