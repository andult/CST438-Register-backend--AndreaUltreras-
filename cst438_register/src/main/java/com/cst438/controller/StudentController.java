package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cst438.domain.StudentRepository;


@RestController
public class StudentController {

		@Autowired
		StudentRepository studentRepository;
		
		@PostMapping("/admin")
		public String addStudent() {
			return "";
		}
	}


	/*
	 * create REST apis to add or change a student record.
	 * TODO: As an administrator, I can add a student to the system.  
	 *		I input the student email and name.  
	 *		The student email must not already exists in the system.
	 *TODO: As an administrator, I can put a HOLD on a student's registration.
	 *TODO: As an administrator, I can release the HOLD on a student's registration.
	*/
