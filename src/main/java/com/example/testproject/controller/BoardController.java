package com.example.testproject.controller;

import com.example.testproject.model.Board;
import com.example.testproject.repository.BoardRepository;
import com.example.testproject.service.BoardService;
import com.example.testproject.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
        // 게시판 호출할때 데이터 값을 넘겨주고 싶다 파라미터에 Model 추가
        //List<Board> boards = boardRepository.findAll(); // 데이터 다 가져오기
        //Page<Board> boards = boardRepository.findAll(pageable); // 총 건수가 바뀜 -->/board/list?page=0&size=10
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        // http://localhost:9090/board/list?searchText=안녕하세요
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber()+4);
        //boards.getPageable().getPageNumber(); // 현재 페이지 가져올수있음
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        //boards.getTotalElements();
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    // 파라미터를 전달을 받아야하기 때문에 파라미터 하나 추가
    public String form(Model model, @RequestParam(required = false)Long id){
        if(id == null){
            // 조회된 값이 없으면 새로운 board 클래스를 생성해서 넘김
            model.addAttribute("board",new Board());
        }else{
            // 값이 있으면
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board,bindingResult);
        // 어노테이션으로 지정한 조건에 맞지 않으면
        if(bindingResult.hasErrors()){
            return "/board/form";
        }
        // user의 키값을 참조해서 user_id가 담김
        // 사용자 정보는 인증정보를 이용해서 담아야한다.
        //  Authentication authentication 인증정보가 알아서 담겨옴
        // Authentication a = SecurityContextHolder.getContext().getAuthentication().getName()
        String username = authentication.getName();
        //board.setUser(user);
        boardService.save(username,board);
        //boardRepository.save(board);
        // 값을 뿌려주지 않고 이동을 하기 때문에 redirect를 이용해야함 그때 위에 /getMapping list
        return "redirect:/board/list";
    }
}
