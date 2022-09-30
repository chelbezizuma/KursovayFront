package com.example.kursovayadada.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacultysAndSpecialitys {
    int id;
    String facultys;
    String specialitys;

    public FacultysAndSpecialitys() {
    }
}
