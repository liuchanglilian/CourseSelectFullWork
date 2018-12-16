package pro1.pro2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeResult;

import pro1.pro2.datamodel.Course;
import pro1.pro2.datamodel.DynamoDBConnector;
import pro1.pro2.datamodel.SNSConnector;
import pro1.pro2.datamodel.Student;

public class CoursesService{

	static DynamoDBConnector dynamoDb;
	static SNSConnector sns;
	DynamoDBMapper mapper; 
	
	public CoursesService() {
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
		sns = new SNSConnector();
		sns.init();
	}
	public List<Course> getAllCourses() {	
		//Getting the list
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Course> list = mapper.scan(Course.class, scanExpression);
		return list;
	}
	public Course addCourse(Course cour) {	
		if(getCourse(cour.getCourseId()) == null) {
		CreateTopicResult result = sns.getClient().createTopic(cour.getCourseId());
		cour.setTopicArn(result.getTopicArn());
		cour.setRosters(new ArrayList<String>());
		mapper.save(cour);
		return cour;
		}else return null;
	}
	public void subscribe(Student student,Course cs){
		SubscribeResult sbrs = this.sns.getClient().subscribe(cs.getTopicArn(),"email", student.getEmailId());
		
	}
	public Course getCourse(String courseId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(courseId));
		
		DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
				.withIndexName("courseid")
				.withConsistentRead(false)
				.withKeyConditionExpression("courseid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Course> result = mapper.query(Course.class, queryExpression);
		if(result.size() == 0) return null;
		return result.get(0);
	}
	// Deleting a professor
	public Course deleteCourse(String courseId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(courseId));
		
		DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
				.withIndexName("courseid")
				.withConsistentRead(false)
				.withKeyConditionExpression("courseid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Course> result = mapper.query(Course.class, queryExpression);
		if(result.size() == 0) return null;
		mapper.delete(result.get(0));
		return result.get(0);
	}
	// Updating Professor Info
		public Course updateCourseInformation(String courseId, Course course) {	
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
			eav.put(":v1", new AttributeValue().withS(courseId));
			
			DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
					.withIndexName("courseid")
					.withConsistentRead(false)
					.withKeyConditionExpression("courseid = :v1")
					.withExpressionAttributeValues(eav);
			
			List<Course> result = mapper.query(Course.class, queryExpression);
			if(result.size() == 0) return null;
			course.setId(result.get(0).getId());
			mapper.save(course);
			return course;
		}
	// Get professors in a department 
	public List<Course> getCoursesByDepartment(String department) {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Course> list = mapper.scan(Course.class, scanExpression);

		ArrayList<Course> result = new ArrayList<>();
		for (Course course : list) {
			if (course.getDepartment().equals(department)) {
					result.add(course);
			}
		}
		return result ;
	}
		
}