package com.root.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppointmentDto {
    private long id;
    private int years;
    private int months;
    private int days;
    private String occasion;
    private long invited_count;
    private boolean cancelled;
    private String tags;
}
