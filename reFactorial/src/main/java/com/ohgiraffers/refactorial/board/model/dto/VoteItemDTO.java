package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteItemDTO {

    private int voteItemId;
    private String postId;
    private String itemVote1;
    private String itemVote2;
    private String itemVote3;
    private String itemVote4;
    private String itemVote5;

}