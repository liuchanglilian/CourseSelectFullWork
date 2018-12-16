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

import pro1.pro2.datamodel.Board;
import pro1.pro2.service.BoardService;


@Path("boards")
public class BoardResource {
	BoardService boardService = new BoardService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Board> getBoardByDeparment(@QueryParam("courseId")String courseId) {
		if(courseId == null) {
			return boardService.getAllBoards();
		}
		return boardService.getBoardsByCourseId(courseId);
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
//	//getBoardsByCourseId(long courseId)
//	@GET
//	@Path("/{courseId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Board> getBoardByCourseID(@PathParam("courseId") long courseId) {
//		
//		return boardService.getBoardsByCourseId(courseId);
//		
//	}
	
	@GET
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board getBoard(@PathParam("boardId") String boardId) {
		System.out.println("long is"+boardId);
		return boardService.getBoard(boardId);
	}
	
	@DELETE
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board deleteBoard(@PathParam("boardId") String boardId) {
		return boardService.deleteBoard(boardId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Board addBoard(Board board) {
			return boardService.addBoard(board);
	}
	
	@PUT
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Board updateBoard(@PathParam("boardId") String boardId, 
			Board board) {
		return boardService.updateBoardInformation(boardId, board);
	}

}


