package pro1.pro2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import pro1.pro2.datamodel.DynamoDBConnector;
import pro1.pro2.datamodel.Professor;

public class ProfessorsService {
	
	static DynamoDBConnector dynamoDb;
	DynamoDBMapper mapper; 
	
	public ProfessorsService() {
		dynamoDb = new DynamoDBConnector();
		dynamoDb.init();
		mapper = new DynamoDBMapper(dynamoDb.getClient());
	}
	
	// Getting a list of all professor 
	// GET "..webapi/professors"
	public List<Professor> getAllProfessors() {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Professor> list = mapper.scan(Professor.class, scanExpression);
		return list;
	}

	
	public Professor addProfessor(Professor prof) {	
		mapper.save(prof);
		return prof;
	}
	
	// Getting One Professor
	public Professor getProfessor(String professorId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(professorId));
		
		DynamoDBQueryExpression<Professor> queryExpression = new DynamoDBQueryExpression<Professor>()
				.withIndexName("professorid")
				.withConsistentRead(false)
				.withKeyConditionExpression("professorid = :v1")
				.withExpressionAttributeValues(eav);
		List<Professor> result = mapper.query(Professor.class, queryExpression);
		if(result.size()==0) return null;
		return result.get(0);
	}
	
	// Deleting a professor
	public Professor deleteProfessor(String professorId) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(professorId));
		
		DynamoDBQueryExpression<Professor> queryExpression = new DynamoDBQueryExpression<Professor>()
				.withIndexName("professorid")
				.withConsistentRead(false)
				.withKeyConditionExpression("professorid = :v1")
				.withExpressionAttributeValues(eav);
		List<Professor> result = mapper.query(Professor.class, queryExpression);
		if(result.size()==0) return null;
		mapper.delete(result.get(0));
		return result.get(0);
	}
	
	// Updating Professor Info
	public Professor updateProfessorInformation(String professorId, Professor prof) {	
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(professorId));
		
		DynamoDBQueryExpression<Professor> queryExpression = new DynamoDBQueryExpression<Professor>()
				.withIndexName("professorid")
				.withConsistentRead(false)
				.withKeyConditionExpression("professorid = :v1")
				.withExpressionAttributeValues(eav);
		List<Professor> result = mapper.query(Professor.class, queryExpression);
		if(result.size()==0) return null;
		prof.setId(result.get(0).getId());
		mapper.save(prof);
		return prof;
	}
	
	// Get professors in a department 
	public List<Professor> getProfessorsByDepartment(String department) {	
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Professor> list = mapper.scan(Professor.class, scanExpression);

		ArrayList<Professor> result = new ArrayList<>();
		for (Professor prof : list) {
			if (prof.getDepartment().equals(department)) {
					result.add(prof);
			}
		}
		return result ;
	}
	
}
