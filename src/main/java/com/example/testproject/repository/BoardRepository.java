package com.example.testproject.repository;

import com.example.testproject.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    // < model class, pk 타입>

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title,String content);

    Page<Board> findByTitleContainingOrContentContaining(String title, String Content, Pageable pageable);
}
