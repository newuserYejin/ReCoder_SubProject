package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentLikesDTO {

    private int likeId;         // 좋아요 고유 ID
    private String empId;       // 유저 ID
    private int commentId;      // 댓글 고유 ID
}