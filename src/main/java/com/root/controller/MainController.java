package com.root.controller;

import com.root.domain.UserRepository;
import com.root.domain.UserTable;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@GetMapping("/")
	public String mains() {
		return"index";
	}



	private final UserRepository userRepository;
	@PostMapping("/test")
	@ResponseBody
	public String sres() {
		UserTable userTable = new UserTable();
		userTable.setAppointment("asdfas");
		userTable.setReservation(new Date());
		userRepository.save(userTable);
		return "200";
	}

	@PostMapping("/tts")
	@ResponseBody
	public String test(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newdate) {
		System.out.println("==========================================");
		System.out.println(newdate);
		List<UserTable> userTableList = userRepository.findByReservationBetween(newdate, newdate);
		for(UserTable ob: userTableList) {
			System.out.println(ob.getReservation());
			System.out.println(ob.getAppointment());
		}
		System.out.println("==========================================");
		return "200";
	}

}
