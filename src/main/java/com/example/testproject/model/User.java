package com.example.testproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // DB 에 정의한 필드들을 그대로
    @Id // primary key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement 설정
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))

    private List<Role> roles = new ArrayList<>();

    //@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = false)
    // board 클래스에 변수명을 적어준다
    // casacase.all ->
    // orphanRemoval
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
    // EAGER
    // OneToOne
    // ManyToOne

    // LAZY
    // OneToMany
    // ManyToMany

    //spring boot n+1 문제;

}
