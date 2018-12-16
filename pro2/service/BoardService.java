package pro1.pro2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import pro1.pro2.datamodel.Board;
import pro1.pro2.datamodel.DynamoDBConnector;

public class BoardService {
	static DynamoDBConnector dynamoDb;
	DynamoDBMapper mapper; 
	
	public BoardService() {
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}
	public List<Board> getAllBoards() {	
		//Getting the list
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Board> list = mapper.scan(Board.class, scanExpression);
		return list;
	}
//	public void addBoard(long courseId) {
//		// Next Id 
//		long nextAvailableId = board_Map.size() + 1;
//		
//		//Create a Professor Object
//		Board board = new Board(nextAvailableId,courseId);
//		board_Map.put(nextAvailableId, board);
//	}
	public Board addBoard(Board board) {	
		mapper.save(board);
		return board;
	}
	public Board getBoard(String boardId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(boardId));
		
		DynamoDBQueryExpression<Board> queryExpression = new DynamoDBQueryExpression<Board>()
				.withIndexName("boardid")
				.withConsistentRead(false)
				.withKeyConditionExpression("boardid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Board> result = mapper.query(Board.class, queryExpression);
		if(result.size() == 0) return null;
		return result.get(0);
	}
	// Deleting a professor
	public Board deleteBoard(String boardId) {
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(boardId));
		
		DynamoDBQueryExpression<Board> queryExpression = new DynamoDBQueryExpression<Board>()
				.withIndexName("boardid")
				.withConsistentRead(false)
				.withKeyConditionExpression("boardid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Board> result = mapper.query(Board.class, queryExpression);
		if(result.size()==0) return null;
		mapper.delete(result.get(0));
		return result.get(0);
	}
	// Updating Professor Info
		public Board updateBoardInformation(String boardId, Board board) {	
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
			eav.put(":v1", new AttributeValue().withS(boardId));
			
			DynamoDBQueryExpression<Board> queryExpression = new DynamoDBQueryExpression<Board>()
					.withIndexName("boardid")
					.withConsistentRead(false)
					.withKeyConditionExpression("boardid = :v1")
					.withExpressionAttributeValues(eav);
			
			List<Board> result = mapper.query(Board.class, queryExpression);
			if(result.size() == 0) return null;
			board.setId(result.get(0).getId());
			mapper.save(board);
			return board;
		}	
	// Get professors in a department 
	public List<Board> getBoardsByCourseId(String courseId) {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Board> list = mapper.scan(Board.class, scanExpression);

		ArrayList<Board> result = new ArrayList<>();
		for (Board board : list) {
			if (board.getCourseId().equals(courseId)) {
					result.add(board);
			}
		}
		return result ;
	}
		
}

