package com.root.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long>{
	
	 List<UserTable> findAllByReservationBetween(Date to, Date to2);

	List<UserTable> findByReservationBetween(Date newdate, Date newdate1);
}
