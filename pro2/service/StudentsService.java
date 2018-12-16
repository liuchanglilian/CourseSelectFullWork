package pro1.pro2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import pro1.pro2.datamodel.DynamoDBConnector;
import pro1.pro2.datamodel.Student;

public class StudentsService {
	static DynamoDBConnector dynamoDb;
	DynamoDBMapper mapper; 
	
	public StudentsService() {
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}
	public List<Student> getAllStudents() {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Student> list = mapper.scan(Student.class, scanExpression);
		return list;
	}
	public Student addStudent(Student stu) {	
		stu.setRegisteredCourses(new ArrayList<String>());
		mapper.save(stu);
		return stu;
	}
	public Student getStudent(String studentId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(studentId));
		
		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
				.withIndexName("studentid")
				.withConsistentRead(false)
				.withKeyConditionExpression("studentid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Student> result = mapper.query(Student.class, queryExpression);
		if(result.size() == 0) return null;
		return result.get(0);
	}
	// Deleting a professor
	public Student deleteStudent(String studentId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(studentId));
		
		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
				.withIndexName("studentid")
				.withConsistentRead(false)
				.withKeyConditionExpression("studentid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Student> result = mapper.query(Student.class, queryExpression);
		if(result.size() == 0) return null;
		mapper.delete(result.get(0));
		return result.get(0);
	}
	// Updating Professor Info
		public Student updateStudentInformation(String studentId, Student stu) {	
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
			eav.put(":v1", new AttributeValue().withS(studentId));
			
			DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
					.withIndexName("studentid")
					.withConsistentRead(false)
					.withKeyConditionExpression("studentid = :v1")
					.withExpressionAttributeValues(eav);
			
			List<Student> result = mapper.query(Student.class, queryExpression);
			if(result.size() == 0) return null;
			stu.setId(result.get(0).getId());
			mapper.save(stu);
			return stu;
		}
	// Get professors in a department 
	public List<Student> getStudentsByDepartment(String department) {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Student> list = mapper.scan(Student.class, scanExpression);

		ArrayList<Student> result = new ArrayList<>();
		for (Student stu : list) {
			if (stu.getDepartment().equals(department)) {
					result.add(stu);
			}
		}
		return result ;
	}
		
}
