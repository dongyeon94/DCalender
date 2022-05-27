package com.root.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.root.dto.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.root.domain.NameTag;
import com.root.domain.UserTable;
import com.root.domain.UserRepository;

@Controller
@RequiredArgsConstructor
public class DateController {

	private final UserRepository userRepo;
	
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


	@PostMapping("/search")
	@ResponseBody
	public List<AppointmentDto> searchAppointment(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newdate) {
		Calendar scal = Calendar.getInstance();
		scal.setTime(newdate);
		scal.set(Calendar.DATE,1);
		Date sta = scal.getTime();

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(newdate);
		ecal.set(Calendar.DATE,ecal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date ens = ecal.getTime();

		List<UserTable> userTableList = userRepo.findByReservationBetween(sta, ens);
		List<AppointmentDto> appointmentDtos = new ArrayList<>();
		for(UserTable userTable: userTableList) {
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(userTable.getReservation());

			AppointmentDto appointmentDto = new AppointmentDto();
			appointmentDto.setYears(calDate.get(Calendar.YEAR));
			appointmentDto.setMonths(calDate.get(Calendar.MONTH));
			appointmentDto.setDays(calDate.get(Calendar.DATE));
			appointmentDto.setOccasion(userTable.getAppointment());
			appointmentDto.setInvited_count(userTable.getId());
			appointmentDto.setTags(userTable.getTags().toString());
			appointmentDto.setCancelled(false);

			appointmentDtos.add(appointmentDto);
		}
		System.out.println("gogogo");
		return appointmentDtos;
	}
}
