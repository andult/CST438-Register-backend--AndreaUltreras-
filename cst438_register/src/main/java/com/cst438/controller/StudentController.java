package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {
		// student.status
		// = 0  ok to register
		// != 0 hold on registration.  student.status may have reason for hold.
		

	@Autowired
	StudentRepository studentRepository;	//connects student table
	
	//ADDS A STUDENT--------------------------------------------------------------------------
	@PostMapping("/student")
	public StudentDTO addStudent(@RequestBody StudentDTO s) {
		//when dealing with json you need to use a DTO to handle it
		//this is saying you need a json entry as input and then the user decides what info to enter
		Student student = studentRepository.findByEmail(s.email);	
		
		if (student == null) {
			Student addStudent = new Student(); 
			addStudent.setName(s.name);
			addStudent.setEmail(s.email);
			addStudent.setStatusCode(0);
			addStudent.setStatus(null);
			Student newStudent = studentRepository.save(addStudent); //how you save it
			s.student_id = newStudent.getStudent_id();
		}
		else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already exists or error was encountered.");
		}
		return s;
	}

	//ADDS A HOLD-----------------------------------------------------------------------------
	@PutMapping("/student/hold/{id}")   //  /student/3?status=2  <-what THEY need to type in
	public void addHold(@PathVariable("id") int id , @RequestParam("status") int code) {
		//pathvariable is what element they are searching for
		//requestparam is what they are changing
		Student student = studentRepository.findById(id).get(); //have to add .get()	
		
		if (student != null) {
			student.setStatusCode(code);
			studentRepository.save(student);
		}
		else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "error");
		}
	}
	
	//REMOVES A HOLD--------------------------------------------------------------------------
	@PutMapping("/student/nohold/{id}")		//will this automatically change the status? <-yes
	public void removeHold(@PathVariable("id") int id) {

		Student student = studentRepository.findById(id).get(); //have to add .get()
		
		if (student != null) {
			student.setStatusCode(0);
			studentRepository.save(student);
		}
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
