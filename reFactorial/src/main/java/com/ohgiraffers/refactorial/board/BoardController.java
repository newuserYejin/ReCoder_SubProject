package com.ohgiraffers.refactorial.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/freeBoard")
    public String freeBoard() {
        return "board/freeBoard";
    }

    @GetMapping("/document")
    public String document() {
        return "board/document";
    }

    @GetMapping("/vote")
    public String vote() {
        return "board/vote";
    }

    @GetMapping("/event")
    public String event() {
        return "board/event";
    }

    @GetMapping("/freeBoardRegist")
    public String freeBoardRegist() {
        return "board/freeBoardRegist";
    }

}
