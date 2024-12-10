package com.ohgiraffers.refactorial.board.model.dao;

import com.ohgiraffers.refactorial.board.model.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void post(BoardDTO board);

    void boardPost(BoardDTO board);

    List<BoardDTO> postList();

    BoardDTO postDetail(String title);

    void postDelete(int postId);

}
