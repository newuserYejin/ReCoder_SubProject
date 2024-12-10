package com.ohgiraffers.refactorial.board.service;

import com.ohgiraffers.refactorial.board.model.dao.BoardMapper;
import com.ohgiraffers.refactorial.board.model.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {

        this.boardMapper = boardMapper;
    }

    public void post(BoardDTO board) {

        boardMapper.boardPost(board);
    }

    public List<BoardDTO> postList() {

        return boardMapper.postList();
    }

    public BoardDTO postDetail(String title) {
        return boardMapper.postDetail(title);
    }

    @Transactional
    public void postDelete(int postId) {
        boardMapper.postDelete(postId);
    }
}
