package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {

    private int postId;                         // 게시물 ID (post_id)
    private String postTitle;                    // 게시물 제목 (post_title)
    private String postContent;                  // 게시물 내용 (post_content)
    private LocalDateTime postCreationDate;      // 게시물 작성일 (post_creationDate)
    private LocalDateTime postModificationDate;  // 게시물 수정일 (post_modificationDate)
    private String empId;                        // 작성자 ID (emp_id)
    private int categoryCode;
}
