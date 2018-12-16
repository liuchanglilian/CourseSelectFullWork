package pro1.pro2.resources;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

public class Test {
	
		static AmazonDynamoDB dynamoDB;
		
		/*
		 * Init function to make the client availabe 
		 * 		setup the resouces
		 * 		resources include credentials 
		 * 		aws region 
		 * 		build the client
		 */
		private static void init() throws Exception {
			System.out.println(Regions.US_EAST_2);
			ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			credentialsProvider.getCredentials();
			
			dynamoDB = AmazonDynamoDBClientBuilder
						.standard()
						.withCredentials(credentialsProvider)
						.withRegion(Regions.US_EAST_2)
						.build();			
		}
		//"us-east-2"
		public static void main(String[] args) throws Exception {
			init();
			String tableName = "announcement";
			// For intellij  /Users/avinav/.aws/credentials
			/*
			 * aws_access_key_id=AKIAJXS
			   aws_secret_access_key=/+AgeULQdm6mRu0plX
			 */
			GetItemRequest getItemRequest = new GetItemRequest(); 
			//  key that are you looking for:   studentId with value 123
			Map<String, AttributeValue> itemToFetch = new HashMap<>();
			itemToFetch.put("announcementid", new AttributeValue().withS("130"));
			getItemRequest.setKey(itemToFetch);
			// The table that we are looking at
			getItemRequest.setTableName(tableName);
			GetItemResult getItemResult = dynamoDB.getItem(getItemRequest);
			System.out.println("GetItemResult:"  + getItemResult);
		}
	}

