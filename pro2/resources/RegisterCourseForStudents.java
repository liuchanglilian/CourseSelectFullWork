package pro1.pro2.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pro1.pro2.datamodel.Course;
import pro1.pro2.datamodel.Student;
import pro1.pro2.service.RegisterService;
import pro1.pro2.service.StudentsService;

@Path("student")
public class RegisterCourseForStudents {
	RegisterService rgService = new RegisterService();
	@POST
	@Path("/{studentId}/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student RegisterCourse(List<Course> courses,@PathParam("studentId") String id) {
		System.out.println("resourse part student id:"+id);
			return rgService.addCourses(courses,id);
	}
}
