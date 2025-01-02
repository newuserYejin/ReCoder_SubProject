package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardLikeDTO {

    private String postId;    // 게시물 ID
    private String empId;     // 사용자 ID
    private boolean likeStatus; // 좋아요 상태 (true: 좋아요, false: 좋아요 취소)

}
