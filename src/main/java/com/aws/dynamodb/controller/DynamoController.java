package com.aws.dynamodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aws.dynamodb.model.StudentModel;
import com.aws.dynamodb.repo.StudentRep;

@RestController
@RequestMapping("/student")
public class DynamoController {
	
	@Autowired
	private StudentRep studentRep;
	
	@PostMapping
	public String insert(@RequestBody StudentModel student) {
		studentRep.insert(student);
		return "student inserted";
	}
	
	@GetMapping
	public ResponseEntity<StudentModel> get(@RequestParam String studentId, @RequestParam String lastName) {
		StudentModel student =  studentRep.getStudent(studentId, lastName);
		return new ResponseEntity<StudentModel>(student, HttpStatus.OK);
	}
	
	@PutMapping
	public String update(@RequestBody StudentModel student) {
		studentRep.update(student);
		return "student updated";
	}
	
	@DeleteMapping
	public String delete(@RequestParam String studentId, @RequestParam String lastName) {
		StudentModel student = new StudentModel();
		student.setStudentId(studentId);
		student.setLastName(lastName);
		studentRep.delete(student);
		return "student deleted";
	}

}
