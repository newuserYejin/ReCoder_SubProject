package com.ohgiraffers.refactorial.board.controller;

import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.service.ApprovalService;
import com.ohgiraffers.refactorial.board.model.dto.*;
import com.ohgiraffers.refactorial.board.service.BoardService;
import com.ohgiraffers.refactorial.fileUploade.model.dto.UploadFileDTO;
import com.ohgiraffers.refactorial.fileUploade.model.service.UploadFileService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UploadFileService uploadService;

    @Autowired
    public BoardController(BoardService boardService, UploadFileService uploadService) {
        this.boardService = boardService;
        this.uploadService = uploadService;
    }

    // 게시물 전체조회
    @GetMapping("list") // url로 이동
    public String list(@RequestParam int categoryCode, Model model, HttpSession session) {

        List<BoardDTO> postList = boardService.postList(categoryCode);      // 전체조회 기능

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");   // 로그인한 유저 정보를 가져옴

        model.addAttribute("postList", postList);    // 템플릿에 값 전달
        model.addAttribute("categoryCode", categoryCode);   // 카테고리코드를 게시물 등록페이지로 이동시키기 위한 셋팅
        model.addAttribute("currentCategory", categoryCode);    // 게시판 사이드바에 값 전달

        return "/board/list";   // html 페이지로 이동
    }

    // 게시물 등록 페이지로 이동
    @GetMapping("freeBoardRegist")
    public String freeBoardRegist(@RequestParam int categoryCode, Model model) {

        model.addAttribute("categoryCode", categoryCode);
        model.addAttribute("currentCategory", categoryCode);    // 게시판 사이드바에 값 전달


        return "/board/freeBoardRegist";
    }


    // 게시물 등록
    @PostMapping("freeBoardRegist")
    public String boardPost(@RequestParam String title,
                            @RequestParam String content,
                            @RequestParam int categoryCode,
                            @RequestParam String option1,
                            @RequestParam String option2,
                            @RequestParam(required = false) String option3,
                            @RequestParam(required = false) String option4,
                            @RequestParam(required = false) String option5,
                            @RequestParam(required = false) List<MultipartFile> postFileList,
                            Model model, HttpSession session) throws IOException {

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴
        String boardId = "BO" + String.format("%05d", (int) (Math.random() * 100000));     // 5자리 랜덤 문자열 생성(게시물 ID)

        // 게시물의 정보
        BoardDTO board = new BoardDTO();        // BoardDTO 객체에 밑에있는 값을 담음
        board.setPostId(boardId);               // 게시물 번호
        board.setPostTitle(title);              // 게시물 제목
        board.setPostContent(content);          // 게시물 내용
        board.setPostCreationDate(LocalDateTime.now()); // 게시물 등록 시간
        board.setEmpId(user.getEmpId());        // 작성자 사원번호
        board.setPostModificationDate(LocalDateTime.now()); // 게시물 수정 시간
        board.setCategoryCode(categoryCode);    // 게시물 카테고리 코드

        // 카테고리가 4(투표게시판)일 때
        if (categoryCode == 4) {

            List<String> options = new ArrayList<>();

            if (option1 != null && !option1.isEmpty()) {
                options.add(option1);
            }
            if (option2 != null && !option2.isEmpty()) {
                options.add(option2);
            }
            if (option3 != null && !option3.isEmpty()) {
                options.add(option3);
            }
            if (option4 != null && !option4.isEmpty()) {
                options.add(option4);
            }
            if (option5 != null && !option5.isEmpty()) {
                options.add(option5);
            }

//            System.out.println(options); // 값을 받은걸 리스트 형태로 출력

            // 반복문돌려서 1개씩 만들기 (게시물 ID, 항목 이름)
            for (String option : options) {
                VoteItemDTO optionDTO = new VoteItemDTO();
                optionDTO.setPostId(boardId);   // 게시물 ID
                optionDTO.setItemTitle(option);   // 항목 이름
//                System.out.println("optionDTO의 값: " + optionDTO);

                boardService.optionResult(optionDTO);   // 투표 항목 DTO로 전달
            }

        }

        if (categoryCode == 3){

            System.out.println("postFileList = " + postFileList);

            if (!postFileList.isEmpty()) {
                board.setPostfile(postFileList);
                board.setAttachment(1);

                uploadService.upLoadFile(postFileList,boardId);
            }
        }

        boardService.post(board);   // 게시물 등록 기능

        return "redirect:/board/list?categoryCode=" + categoryCode; // 내 API를 호출
    }

    // 게시물 상세페이지 / 투표 항목
    @GetMapping("postDetail")
    public String postDetail(@RequestParam String postId,
                             @RequestParam int categoryCode,
                             Model model, HttpSession session) {

        // 로그인한 유저 정보를 가져옴
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        // 게시물 상세
        BoardDTO postDetail = boardService.postDetail(postId);
        // 댓글 리스트 가져옴
        List<CommentDTO> comment = boardService.commentView(postId);
        // 항목 리스트 가져옴
        List<VoteItemDTO> voteItemList = boardService.itemView(postId);
        // 투표한사람이면 보여주는 항목리스트
        List<VoteResultDTO> resultInquiryList = boardService.voteComplete(postId, user.getEmpId());
        // 투표 TotalList
        List<VoteTotalDTO> voteTotalList = boardService.getVoteResults(postId);
        System.out.println("voteTotalList = " + voteTotalList);

        // 초기 투표 수 0으로 세팅
        voteItemList.stream().forEach(item->{
            item.setTotalCount(0);
        });


        // ItemId가 일치할 경우 총 투표 수 세팅
        for(int i=0; i<voteItemList.size(); i++){
            for(int j=0; j<voteTotalList.size(); j++){
                if(voteItemList.get(i).getItemId() == voteTotalList.get(j).getItemId()){
                    voteItemList.get(i).setTotalCount(voteTotalList.get(j).getVoteCount());
                }
            }
        }

        // 본인이 투표한 사람일 경우 체크
        for(int i=0; i<resultInquiryList.size(); i++){
            for(int j=0; j<voteItemList.size(); j++){
                if(resultInquiryList.get(i).getEmpId().equals(user.getEmpId()) && resultInquiryList.get(i).getItemId() == voteItemList.get(j).getItemId()){
                    voteItemList.get(j).setOwnVoted(true);
                    // 로그인한 사용자의 투표 여부
                    model.addAttribute("votedByPost", true);
                    //
                }
            }
        }

        // 첨부파일
        if (categoryCode == 3){
            if (postDetail.getAttachment() == 1){
                List<UploadFileDTO> uploadFileList = uploadService.findFileByMappingId(postId);

                if (!uploadFileList.isEmpty()){
                    model.addAttribute("attachmentFileList",uploadFileList);
                }
            }
        }

        model.addAttribute("postDetail", postDetail);   // 상세페이지
        model.addAttribute("commentView", comment);     // 댓글 조회
        model.addAttribute("user", user);   // user정보 postDetail에 전달(게시물 수정,삭제 권한)
        model.addAttribute("voteView", voteItemList);   // 항목 리스트
        model.addAttribute("currentCategory", categoryCode);    // 게시판 사이드바에 값 전달
//        model.addAttribute("voteTotal", voteTotalList);     // 투표 총계

//        if (resultInquiry != null && resultInquiry.size() > 0) {
//            model.addAttribute("voteComplete", resultInquiry);   // 투표한사람이면 보여주는 항목리스트
//        }


        return "/board/postDetail";
    }

    // 투표 결과를 DB에 저장
    @PostMapping("vote")
    public String voteResult(@RequestParam List<Integer> voteIdList,
                             @RequestParam int categoryCode,
                             @RequestParam String postId,
                             HttpSession session,
                             RedirectAttributes rttr) {


        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴
        List<VoteResultDTO> voteItemList = new ArrayList<>();   // 항목 1개씩 담음

        // 반복문을 돌려 항목을 리스트에 저장
        for(int i = 0; i < voteIdList.size(); i++){
            voteItemList.add(new VoteResultDTO(null, user.getEmpId(), voteIdList.get(i), postId));
        }

        boardService.voteResult(voteItemList);    // 투표결과 DB 저장

//        List<VoteResultDTO> voteSelectResult = boardService.getVoteResults(postId);    // 투표결과 List 형태로 가져옴

//        System.out.println("voteSelectResult = " + voteSelectResult);


        // 결과 데이터를 모델에 추가하여 HTML(화면) 전달  @@@ 리다이렉트 플래시 사용 @@@
//        rttr.addFlashAttribute("voteResults", voteSelectResult);

        return "redirect:/board/postDetail?postId=" + postId + "&categoryCode=" + categoryCode;    // redirect 를 사용하는 이유 - postDetail에 있는 데이터 사용을 위해!
    }

    // 투표 결과 조회(12/22)
//    @GetMapping("/voteResult")
//    public String getVoteResults(@RequestParam("postId") String postId, Model model, HttpSession session) {
//
//        // 로그인한 유저 정보를 가져옴
//        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");
//        // 투표 결과 가져오기
//        List<VoteResultDTO> voteResults = boardService.getVoteResults(postId);
//        List<VoteItemDTO> userVote = boardService.getUserVote(postId, user.getEmpId());
//
//        model.addAttribute("voteResults", voteResults);
//        model.addAttribute("userVote", userVote);
//
//        return "voteResultPage";
//    }


    // 게시물 삭제
    @GetMapping("postDelete")
    public String deletePost() {
        return "/board/postDelete";
    }

    @PostMapping("postDelete")
    public String postDelete(@RequestParam String postId, @RequestParam int categoryCode) {

        // 삭제 기능
        boardService.postDelete(postId);

        return "redirect:/board/list?categoryCode=" + categoryCode;
    }

    // 게시물 수정
    @GetMapping("postUpdate")
    public String postUpdate(@RequestParam String postId, Model model) {

        BoardDTO postDetail = boardService.postDetail(postId);

        model.addAttribute("modify", postDetail);

        return "/board/postUpdate";
    }
    @PostMapping("postUpdate")
    public String updatePost(@ModelAttribute BoardDTO board) {

        board.setPostModificationDate(LocalDateTime.now()); // 게시물 수정 시간
        boardService.updatePost(board);



//        return "redirect:/board/postDetail?postId=" + board.getPostId();    // 상세페이지 머무르기
        return "redirect:/board/list?categoryCode=" + board.getCategoryCode();  // 게시판 이동

    }

    // 댓글 등록
    @PostMapping("comment")
    public String comment(@RequestParam String comment,
                          @RequestParam String postId,
                          @RequestParam int categoryCode,
                          HttpSession session, Model model) {

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴

        CommentDTO commentDetail = new CommentDTO();

        LocalDateTime commentTime = LocalDateTime.now();

        commentDetail.setCommentContent(comment);
        commentDetail.setPostId(postId);
        commentDetail.setEmpId(user.getEmpId());
        commentDetail.setCommentCreationDate(commentTime);

        model.addAttribute("comment", comment);

        boardService.comment(commentDetail);

        return "redirect:/board/postDetail?categoryCode=" + categoryCode + "&postId=" + postId;
    }

    // 댓글 삭제
    @GetMapping("commentDelete")
    @ResponseBody   // 화면 이동이 아닌 데이터만 넘겨주기 위해 사용
    public List<CommentDTO> commentDelete(@RequestParam int commentId, @RequestParam String postId) {

        // 댓글을 삭제하는 기능
        boardService.commentDelete(commentId);

        // 화면에 댓글 리스트 형태로 가져옴
        return boardService.commentView(postId);

    }

}
