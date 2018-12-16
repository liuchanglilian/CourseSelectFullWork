package pro1.pro2.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pro1.pro2.datamodel.Course;
import pro1.pro2.datamodel.Registrar;
import pro1.pro2.datamodel.Student;
import pro1.pro2.service.RegisterService;
import pro1.pro2.service.RegistrarOfferingService;

@Path("registerOffering")
public class RegisterOfferingResource {
	
	RegistrarOfferingService rs = new RegistrarOfferingService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Registrar> getCoursesByDeparment() {
		
			return rs.getAllRegistrars();
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Registrar adRegistrar(Registrar r) {
    	 return rs.addRegisterOffering(r);
    	 
     }
}



