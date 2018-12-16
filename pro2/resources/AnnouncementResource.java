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

import pro1.pro2.datamodel.Announcement;
import pro1.pro2.service.AnnouncementsService;
import javax.ws.rs.core.MediaType;


@Path("announcements")
public class AnnouncementResource {
	AnnouncementsService annoService = new AnnouncementsService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Announcement> getAnnouncementByDeparment(@QueryParam("course")String courseId) {
		if(courseId == null) {
			return annoService.getAllAnnouncements();
		}
		return annoService.getAnnouncementsByCourseId(courseId);
	}
	
	@GET
	@Path("/{courseId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement getAnnouncement(@PathParam("courseId") String courseId,
										@PathParam("announcementId") String announcementId) {
		return annoService.getAnnouncement(courseId,announcementId);
	}
	
	@DELETE
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement deleteAnnouncement(@PathParam("boardId") String boardId,
			@PathParam("announcementId") String announcementId) {
		return annoService.deleteAnnouncement(boardId,announcementId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement addAnnouncement(Announcement anno) {
			return annoService.addAnnouncement(anno);
	}
	
	@PUT
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Announcement updateAnnouncement(@PathParam("announcementId") String announcementId, 
			@PathParam("boardId") String boardId,
			Announcement anno) {
		return annoService.updateAnnouncementInformation(boardId, announcementId,anno);
	}
	

}



