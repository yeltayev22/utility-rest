package kz.yeltayev.utility.entity;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
