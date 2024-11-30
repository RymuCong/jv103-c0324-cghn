package com.cg.casestudy.services.impl;

import com.cg.casestudy.models.user.Relationship;
import com.cg.casestudy.repositories.RelationshipRepository;
import com.cg.casestudy.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    @Override
    public void addFriend(Relationship relationship) {
        relationship.setStatus(false);
        relationshipRepository.saveAndFlush(relationship);
    }

    @Override
    public void removeFriend(Relationship relationship) {
        if (relationship.getId() != null) {
            relationshipRepository.delete(relationship);
        } else {
            throw new IllegalArgumentException("Relationship ID is null");
        }
    }

    @Override
    public void acceptFriend(Long relationshipId) {
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid relationship ID"));
        relationship.setStatus(true);
        relationshipRepository.save(relationship);
    }

    @Override
    public int countFriends(Long userId) {
        List<Relationship> friends = relationshipRepository.findAllFriendsByUserId(userId);
        return friends.size();
    }

    @Override
    public int countPendingFriends(Long userId) {
        return 0;
    }

    @Override
    public List<Relationship> findAllFriends(Long userId) {
        return relationshipRepository.findAllFriendsByUserId(userId);
    }

    private Long getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & 0x1FFFFFFFFFFFFFL;
    }
}