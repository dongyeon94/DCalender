package com.root.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class UserTable {
	
	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reservation;

	private String appointment; 

	@Enumerated(EnumType.STRING)
	private NameTag tags;
}
