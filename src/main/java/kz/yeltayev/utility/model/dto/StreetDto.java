package kz.yeltayev.utility.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class StreetDto {
    private Long id;
    private String streetName;
    private List<AccountDto> accounts;
}
