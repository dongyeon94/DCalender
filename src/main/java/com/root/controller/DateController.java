package com.root.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.root.domain.NameTag;
import com.root.domain.UserTable;
import com.root.domain.UserRepository;

@Controller
public class DateController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/reservation")
	@ResponseBody
	public HashMap<String, ArrayList<String>> reservation(String data) throws ParseException {
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		if( data != null) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = transFormat.parse(data.replace(".","-"));		
			List<UserTable> times = userRepo.findAllByReservationBetween(date, date);

			ArrayList<String> dateList = new ArrayList<String>();
			ArrayList<String> appointmentList = new ArrayList<String>();
			for(UserTable users : times) {
				dateList.add(users.getReservation().toString());
				appointmentList.add(users.getAppointment());				
			}
			result.put("date", dateList);
			result.put("appointment", appointmentList);
			return result;
		}
		return null;
	}
	
	@PostMapping("/reservation")
	public String insertReservation(String reservation, String appointment, String name) {
		System.out.println("============================");
		System.out.println(reservation);
		System.out.println(appointment);
		System.out.println(name);
		System.out.println("============================");
		
		return "redirect:/";
	}
	
	
	@GetMapping("/t1")
	@ResponseBody
	public String t1() {
		UserTable u1 = userRepo.findAll().get(0);
		System.out.println(u1.getReservation());
		
		
		return "sdfsdf";
	}
	
	@GetMapping("/t2")
	@ResponseBody
	public String t2() {
		UserTable u2 = new UserTable();
		u2.setAppointment("Asdfasdf");
		u2.setReservation(new Date(System.currentTimeMillis()));
		userRepo.save(u2);
		return "sdfsdf";
	}

}
