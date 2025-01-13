package lk.ranaweera.api.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthRequestDTO {

    private String name;
    private String username;
    private String password;
    private String email;
    private List<Long> role;
}
