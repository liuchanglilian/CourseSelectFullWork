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

import pro1.pro2.datamodel.Professor;
import pro1.pro2.service.ProfessorsService;

@Path("professors")
public class ProfessorsResource {

	ProfessorsService profService = new ProfessorsService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getProfessorsByDeparment(
			@QueryParam("department") String department			) {
		
		if (department == null) {
			return profService.getAllProfessors();
		}
		return profService.getProfessorsByDepartment(department);
		
	}
	
	// ... webapi/professor/1 
	@GET
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor getProfessor(@PathParam("professorId") String professorId) {
		System.out.println("long is"+professorId);
		return profService.getProfessor(professorId);
	}
	
	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") String professorId) {
		return profService.deleteProfessor(professorId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
			return profService.addProfessor(prof);
	}
	
	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") String professorId, 
			Professor prof) {
		return profService.updateProfessorInformation(professorId, prof);
	}
	
 }
