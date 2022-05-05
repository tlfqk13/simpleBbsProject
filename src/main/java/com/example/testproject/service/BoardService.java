package com.example.testproject.service;

import com.example.testproject.model.Board;
import com.example.testproject.model.User;
import com.example.testproject.repository.BoardRepository;
import com.example.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        // username을 바탕(select를 먼저해서) 으로 id를 조회
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
