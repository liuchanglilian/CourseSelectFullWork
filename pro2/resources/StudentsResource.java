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

import pro1.pro2.datamodel.Student;
import pro1.pro2.service.StudentsService;

@Path("students")
public class StudentsResource {
	StudentsService stuService = new StudentsService();
	
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getAllStudents(@QueryParam("department") String department) {
		if(department == null) {
			return stuService.getAllStudents();
		}
		return stuService.getStudentsByDepartment(department);
	}
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") String id) {
		System.out.println("long is"+id);
		return stuService.getStudent(id);
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") String id) {
		return stuService.deleteStudent(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addStudent(Student stu) {
			return stuService.addStudent(stu);
	}
	
	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student updateStudent(@PathParam("studentId") String id, 
			Student stu) {
		System.out.println(stu.getFirstName());
		return stuService.updateStudentInformation(id, stu);
	}
	
}
