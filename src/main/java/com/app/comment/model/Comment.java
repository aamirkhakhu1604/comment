package com.app.comment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String body;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> replies;

    @Column
    private Integer likes;

    @Column
    private Integer dislikes;

    @OneToMany(mappedBy = "comment")
    private Set<UserLikedComments> userLikedComments;

}

