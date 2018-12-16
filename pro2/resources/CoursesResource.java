package pro1.pro2.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pro1.pro2.datamodel.Course;
import pro1.pro2.service.CoursesService;

@Path("courses")
public class CoursesResource {
	CoursesService courService = new CoursesService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getCoursesByDeparment(@QueryParam("department") String department) {
		if(department == null) {
			return courService.getAllCourses();
		}
		return courService.getCoursesByDepartment(department);
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Professor> getProfessorsByDeparment(
//			@QueryParam("department") String department			) {
//		
//		if (department == null) {
//			return profService.getAllProfessors();
//		}
//		return profService.getProfessorsByDepartment(department);
//		
//	}
	
//	//getCoursesByDepartment(String department)
//	@GET
//	@Path("/{department}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Course> getCourse(@PathParam("department") String department) {
//		System.out.println("department is"+department);
//		return courService.getCoursesByDepartment(department);
//	}
	@GET
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("courseId") String id) {
		System.out.println("long is"+id);
		return courService.getCourse(id);
	}
	
	@DELETE
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseId") String id) {
		return courService.deleteCourse(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course addCourse(Course cour) {
			return courService.addCourse(cour);
	}
	
	@PUT
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("courseId") String id, 
			Course cour) {
		return courService.updateCourseInformation(id, cour);
	}
	

}

