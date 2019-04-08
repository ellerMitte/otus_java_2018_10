package ru.otus.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WSMsg {
    private String method;
    private User user;
    private List<User> users;
}
