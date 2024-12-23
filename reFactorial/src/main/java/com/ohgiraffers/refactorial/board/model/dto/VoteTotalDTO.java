package com.ohgiraffers.refactorial.board.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteTotalDTO {

    private int itemId;
    private int voteCount;

}
