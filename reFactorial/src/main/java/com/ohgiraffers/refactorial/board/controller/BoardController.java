package com.ohgiraffers.refactorial.board.controller;

import com.ohgiraffers.refactorial.approval.service.ApprovalService;
import com.ohgiraffers.refactorial.board.model.dto.BoardDTO;
import com.ohgiraffers.refactorial.board.service.BoardService;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("user/freeBoard")
    public String freeBoard() {
        return "board/freeBoard";
    }

    @GetMapping("user/document")
    public String document() {
        return "board/document";
    }

    @GetMapping("user/vote")
    public String vote() {
        return "board/vote";
    }

    @GetMapping("user/event")
    public String event() {
        return "board/event";
    }

    @GetMapping("user/freeBoardRegist")
    public String freeBoardRegist() { return "board/freeBoardRegist"; }

    @PostMapping("user/freeBoardRegist")
    public String boardPost(@RequestParam String title, @RequestParam String content, @RequestParam int category,
                            Model model, HttpSession session) {

        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴

        BoardDTO board = new BoardDTO();        // BoardDTO 객체에 밑에있는 값을 담음
        board.setPostId(board.getPostId());     // 게시물 번호
        board.setPostTitle(title);              // 게시물 제목
        board.setPostContent(content);          // 게시물 내용
        board.setPostCreationDate(LocalDateTime.now()); // 게시물 등록 시간
        board.setEmpId(user.getEmpId());        // 작성자 사원번호
        board.setPostModificationDate(LocalDateTime.now()); // 게시물 수정 시간
        board.setCategoryCode(category);        // 게시물 카테고리 코드

//        System.out.println("글쓴이 = " + user);

        boardService.post(board);

        return "redirect:/user/freeBoard";
    }

}
