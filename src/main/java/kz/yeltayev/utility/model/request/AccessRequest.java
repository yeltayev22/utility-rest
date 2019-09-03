package kz.yeltayev.utility.model.request;

import lombok.Data;

@Data
public class AccessRequest {
    private Long userId;
    private Long serviceId;
}
