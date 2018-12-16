package pro1.pro2.service;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import pro1.pro2.datamodel.*;
public class RegisterService {
	StudentsService ss = new StudentsService();
	CoursesService cs = new CoursesService();
	public Student addCourses(List<Course> courses,String studentId) {
		System.out.println("student id in register service"+studentId);
		System.out.println("courses");
		for(Course course: courses) {
			System.out.println(course.getCourseId());
		}
		Student stu = ss.getStudent(studentId);
		for(Course course: courses) {
		    Course courseInDatabase = cs.getCourse(course.getCourseId());
		    if(stu.getRegisteredCourses() == null) {
		    	stu.setRegisteredCourses(new ArrayList<String>());
		    }
		    if(stu.getRegisteredCourses().size()==3) break;
		    cs.subscribe(stu,course);
			stu.getRegisteredCourses().add(course.getCourseId());
			if(courseInDatabase.getRosters() == null) {
				courseInDatabase.setRosters(new ArrayList<String>());
			}
			courseInDatabase.getRosters().add(studentId);
			cs.updateCourseInformation(course.getCourseId(), courseInDatabase);
		}
		ss.updateStudentInformation(stu.getStudentId(), stu);
		
		return stu;
	}
}
