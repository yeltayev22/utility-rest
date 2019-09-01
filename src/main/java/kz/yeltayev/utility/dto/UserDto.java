package kz.yeltayev.utility.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String login;
    private String type;
    private String owner;
    private String password;
    private List<AccessDto> accesses;
}
