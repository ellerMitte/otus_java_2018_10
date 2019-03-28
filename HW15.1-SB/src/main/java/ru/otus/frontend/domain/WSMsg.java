package ru.otus.frontend.domain;

import lombok.*;
import ru.otus.domain.User;

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
