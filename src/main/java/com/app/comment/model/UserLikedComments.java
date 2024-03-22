package com.app.comment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_liked_comments")
@NoArgsConstructor
@Getter
@Setter
@IdClass(UserCommentMapping.class)
public class UserLikedComments {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;
}
