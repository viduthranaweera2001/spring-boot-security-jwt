package lk.ranaweera.api.controller.request;

import lombok.Data;

@Data
public class UserAuthRequestDTO {

    private String username;
    private String password;
    private String name;
    private Long role;
}
