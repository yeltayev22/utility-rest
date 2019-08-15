package kz.yeltayev.utility.dto;

import lombok.Data;

import java.util.List;

@Data
public class StreetDto {
    private String streetName;
    private List<AccountDto> accounts;
}
