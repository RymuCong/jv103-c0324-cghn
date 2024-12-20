package com.cg.casestudy.models.user;

import com.cg.casestudy.models.common.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    private Boolean gender;

    @Column
    private Date dob;

    @Column
    private String education;

    @Column
    private String location;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "background_id")
    private Image background;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private User user;

}
