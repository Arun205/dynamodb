package com.aws.dynamodb.repo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.aws.dynamodb.model.StudentModel;

@Repository
public class StudentRep {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentRep.class);
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insert(StudentModel student) {
		LOGGER.info("student added");
		mapper.save(student);
	}
	
	public StudentModel getStudent(String studentId, String lastName) {
		LOGGER.info("finding student");
		return mapper.load(StudentModel.class, studentId, lastName);
	}
	
	public void delete(StudentModel student) {
		mapper.delete(student);
	}
	
	public void update (StudentModel student) {
		try {
			mapper.save(student, buildDynamoDBSaveExpression(student));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("invalid data");
		}
	}
	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(StudentModel student) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("studentId", new ExpectedAttributeValue(new AttributeValue(student.getStudentId()))
		.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}

}
