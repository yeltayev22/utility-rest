package kz.yeltayev.utility.entity;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
