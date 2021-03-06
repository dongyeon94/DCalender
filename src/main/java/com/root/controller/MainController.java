package com.root.controller;

import com.root.domain.UserRepository;
import com.root.domain.UserTable;
import com.root.dto.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UserRepository userRepository;

	@GetMapping("/")
	public String mains(Model model) {
		LocalDate starts = LocalDate.of(2021, 4, 8);
		LocalDate today = LocalDate.now();

		long times = ChronoUnit.DAYS.between(starts, today);

		int plus = (int) (times % 100);
		LocalDate nextDay = today.plusDays(99 - plus);
		long nextCount = times + (99 - plus) + 1;

		model.addAttribute("daycounts","+ " + (times+1));
		model.addAttribute("nextdays", "[+ " + (nextCount) + "]  " + nextDay);

		return"index";
	}



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
	public List<AppointmentDto> searchAppointment(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newdate) {
		Calendar scal = Calendar.getInstance();
		scal.setTime(newdate);
		scal.set(Calendar.DATE,1);
		Date sta = scal.getTime();

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(newdate);
		ecal.set(Calendar.DATE,ecal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date ens = ecal.getTime();

		List<UserTable> userTableList = userRepository.findByReservationBetween(sta, ens);
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
			appointmentDto.setCancelled(false);

			appointmentDtos.add(appointmentDto);
		}
		return appointmentDtos;
	}

}
