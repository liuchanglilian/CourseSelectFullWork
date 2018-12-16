package pro1.pro2.datamodel;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBConnector {

	 static AmazonDynamoDB dynamoDb ;

	 public static void init() {
		if (dynamoDb == null) {
			//ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			AWSCredentialsProvider credentialsProvider = new InstanceProfileCredentialsProvider(false);
			
			credentialsProvider.getCredentials();
			
			dynamoDb = AmazonDynamoDBClientBuilder
						.standard()
						.withCredentials(credentialsProvider)
						.withRegion(Regions.US_EAST_2)
						.build();			
//		AWSCredentialsProvider credentialsProvider = new InstanceProfileCredentialsProvider();
//		
//		credentialsProvider.getCredentials();
//		
//		dynamoDb = AmazonDynamoDBClientBuilder
//					.standard()
//					.withCredentials(credentialsProvider)
//					.withRegion(Regions.US_EAST_2)
//					.build();		
//		System.out.println("I created the client");
		} 

	}
	 
	 public AmazonDynamoDB getClient() {
		 return dynamoDb;
	 }
}