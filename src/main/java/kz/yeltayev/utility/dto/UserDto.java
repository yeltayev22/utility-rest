package kz.yeltayev.utility.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String login;
    private String type;
    private List<AccessDto> accesses;
}
