package com.example.testproject.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    // DB 에 정의한 필드들을 그대로
    @Id // primary key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement 설정
    private Long id;
    @NotNull
    @Size(min = 2, max=30 ,message = "제목은 2자이상 30자 이하입니다")
    private String title;
    private String content;

    // 게시글에서 다른 사용자 정보를 가져오기
    // 닉네임이나 누가 조회했나

    @ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    // name : 어떤 컬럼과 유저테이블이 연결이 될지 name안에 적어준다 board 테이블에 user_id
    // ref : 사용자테이블에 어떤 컬럼과 연결되어있는지 pk 값은 생략해도됨
    private User user;
    
}
