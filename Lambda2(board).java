public class LambdaFunctionHandler implements RequestHandler<Object, String> {
	static AmazonDynamoDB dynamoDb;
	static DynamoDBMapper mapper; 
	 public static void init() {
			
			if (dynamoDb == null) {
				dynamoDb = AmazonDynamoDBClientBuilder
							.standard()
							//.withCredentials(credentialsProvider)
							.withRegion(Regions.US_EAST_2)
							.build();		
				mapper = new DynamoDBMapper(dynamoDb);
			}
	 }
	 public LambdaFunctionHandler() {
		 init();
	 }
	   
    

  
    @Override
    public String handleRequest(Object o, Context context) {
      context.getLogger().log(o.toString());
      String str = o.toString();
      String[] array = str.split("=");
      String courseId = array[array.length-1].substring(0,array[array.length-1].length()-1);
      context.getLogger().log(courseId);
      Board newBoard = new Board();
		newBoard.setCourseId(courseId);
		newBoard.setBoardId(courseId);
		mapper.save(newBoard);
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(courseId));
		
		DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression<Course>()
				.withIndexName("courseid")
				.withConsistentRead(false)
				.withKeyConditionExpression("courseid = :v1")
				.withExpressionAttributeValues(eav);
		
		List<Course> result = mapper.query(Course.class, queryExpression);
		if(result.size() == 0) return null;
		Course c = result.get(0);
		c.setBoardId(courseId);
		mapper.save(c);
        return "finished";
        }
    }
