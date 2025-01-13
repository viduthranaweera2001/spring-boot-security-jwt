package lk.ranaweera.api.controller.request;

import lombok.Data;

@Data
public class UserAuthRequestDTO {

    private String name;
    private String username;
    private String password;
    private String email;
    private Long role;
}
