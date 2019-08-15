package kz.yeltayev.utility.dto;

import lombok.Data;

@Data
public class AccessDto {
    private Long streetId;
    private String streetName;
    private Long serviceId;
    private String serviceName;
}
