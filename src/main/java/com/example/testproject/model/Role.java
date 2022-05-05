package com.example.testproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    // DB 에 정의한 필드들을 그대로
    @Id // primary key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement 설정
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles") // user 클래스에있는 컬럼이름 private Set<Role> roles;
    @JsonIgnore
    private List<User> users;
}
