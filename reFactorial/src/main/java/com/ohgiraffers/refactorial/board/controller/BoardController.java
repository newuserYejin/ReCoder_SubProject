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

        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");

        // post_id
        // 일단 조회 갯수

        BoardDTO board = new BoardDTO();
        board.setPostTitle(title);
        board.setPostContent(content);
        board.setPostCreationDate(LocalDateTime.now());
        board.setEmpId(user.getEmpId());
        board.setPostModificationDate(LocalDateTime.now());
        board.setCategoryCode(category);

        System.out.println("글쓴이 = " + user);

        boardService.post(board);

        return "redirect:/user/freeBoard";
    }

}
