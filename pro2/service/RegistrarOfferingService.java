package pro1.pro2.service;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import pro1.pro2.datamodel.Course;
import pro1.pro2.datamodel.DynamoDBConnector;
import pro1.pro2.datamodel.Professor;
import pro1.pro2.datamodel.Registrar;

public class RegistrarOfferingService {
	static DynamoDBConnector dynamoDb;
	DynamoDBMapper mapper; 
	
	public RegistrarOfferingService() {
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}
	
	public Registrar addRegisterOffering(Registrar registrar) {	
		mapper.save(registrar);
		return registrar;
	}
	
	public List<Registrar> getAllRegistrars(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Registrar> list = mapper.scan(Registrar.class, scanExpression);
		return list;
	}
}
