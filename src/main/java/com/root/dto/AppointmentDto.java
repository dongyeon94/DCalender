package com.root.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppointmentDto {
    private int years;
    private int months;
    private int days;
    private String occasion;
    private String invited_count;
    private boolean cancelled;
}
