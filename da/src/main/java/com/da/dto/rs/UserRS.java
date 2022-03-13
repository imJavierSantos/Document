package com.da.dto.rs;

import lombok.Data;

import java.util.List;

@Data
public class UserRS {

    private Integer number;

    private List<String> userEmail;

}
