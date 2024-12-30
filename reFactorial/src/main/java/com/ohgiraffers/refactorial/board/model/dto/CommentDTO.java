package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private String commentContent;               // 댓글 내용
    private int commentId;                       // 댓글고유
    private String postId;                       // 게시물 ID
    private String empId;                        // 댓글 작성 사원번호
    private String empName;                      // 댓글 작성 사원이름
    private LocalDateTime commentCreationDate;   // 댓글 등록 시간
    private int likeCount;                       // 좋아요 카운트
    private boolean isMyLike;                    // 본인 투표 여부
    private String loginUserEmpId;               // 로그인한 유저의 empID

}