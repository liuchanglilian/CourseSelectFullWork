package pro1.pro2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import pro1.pro2.datamodel.Announcement;
import pro1.pro2.datamodel.DynamoDBConnector;

public class AnnouncementsService {
	//static HashMap<Long,Announcement> anno_Map;
	static DynamoDBConnector dynamoDb;
	DynamoDBMapper mapper; 
	
	public AnnouncementsService() {
		System.out.println("at announcements service");
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
		//anno_Map = mapper.get
	}
	public List<Announcement> getAllAnnouncements() {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Announcement> list = mapper.scan(Announcement.class, scanExpression);
		return list;
	}

	public Announcement addAnnouncement(Announcement anno) {	
		mapper.save(anno);
		return anno;
	}
	public Announcement getAnnouncement(String boardId,String announcementId) {
		HashMap<String, AttributeValue> eav = new HashMap<>();

        eav.put(":v1",  new AttributeValue().withS(boardId));

        eav.put(":v2",  new AttributeValue().withS(announcementId));



        DynamoDBQueryExpression<Announcement> queryExpression = new DynamoDBQueryExpression<Announcement>()

                .withIndexName("boardid-announcementid-index")

                .withConsistentRead(false)

                .withKeyConditionExpression("boardid = :v1 and announcementid = :v2")

                .withExpressionAttributeValues(eav);



        List<Announcement> iList =  mapper.query(Announcement.class, queryExpression);

        if (iList.size() == 0) return null;

        return iList.get(0);
	}
	//. Deleting a professor
	public Announcement deleteAnnouncement(String boardId,String announcementId) {
		 Announcement am = getAnnouncement(boardId, announcementId);

	      mapper.delete(am, new DynamoDBDeleteExpression());

	     return am;
	}
	// Updating Professor Info
		public Announcement updateAnnouncementInformation(String boardId,String announcementId, Announcement anno) {	
			HashMap<String, AttributeValue> eav = new HashMap<>();

	        eav.put(":v1",  new AttributeValue().withS(boardId));

	        eav.put(":v2",  new AttributeValue().withS(announcementId));



	        DynamoDBQueryExpression<Announcement> queryExpression = new DynamoDBQueryExpression<Announcement>()

	                .withIndexName("boardid-announcementid-index")

	                .withConsistentRead(false)

	                .withKeyConditionExpression("boardid = :v1 and announcementid = :v2")

	                .withExpressionAttributeValues(eav);



	        List<Announcement> iList =  mapper.query(Announcement.class, queryExpression);

	        if (iList.size() == 0) mapper.save(anno);

	        else {
	        anno.setId(iList.get(0).getId());
			mapper.save(anno);
	        }
			return anno;
		}
	// Get professors in a department 
	public List<Announcement> getAnnouncementsByCourseId(String courseId) {	
		//Getting the list
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Announcement> list = mapper.scan(Announcement.class, scanExpression);

		ArrayList<Announcement> result = new ArrayList<>();
		for (Announcement anno : list) {
			if (anno.getCourseId().equals(courseId)) {
					result.add(anno);
			}
		}
		return result ;
	}
		
}

