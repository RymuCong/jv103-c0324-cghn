package com.cg.casestudy.models.post;

import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.user.AboutUser;
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
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Column(name="title", columnDefinition = "TEXT")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id", referencedColumnName = "image_id")
    private Image image;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName = "about_user_id")
    private AboutUser createdBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Liked> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}