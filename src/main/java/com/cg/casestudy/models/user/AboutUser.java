package com.cg.casestudy.models.user;

import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "about_user")
public class AboutUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "about_user_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;


    @OneToMany(mappedBy = "userImage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "userOne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Relationship> userOneRelationships;

    @OneToMany(mappedBy = "userTwo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Relationship> userTwoRelationship;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

}
