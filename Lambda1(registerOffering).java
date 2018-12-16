//Third
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
		  String firstPart = array[2];
		  String[] array3 = firstPart.split(",");
		  String courseId = array3[0];
		  context.getLogger().log("courseId:"+courseId);
		  String middle = array[1];
		  context.getLogger().log(middle);
		  String[] array2 = middle.split(",");
		  String Departmnt = array2[0];
		  context.getLogger().log(Departmnt);
		  HttpClient httpclient = HttpClients.createDefault();
     	  HttpPost httppost = new HttpPost("http://lilianassignment4.us-east-2.elasticbeanstalk.com/webapi/registerOffering");
     	  Map<String,Object> jsonValues = new HashMap<>();
	      jsonValues.put("department",Departmnt);
	      jsonValues.put("offeringId",courseId);
	      jsonValues.put("offeringType","Course");
	      jsonValues.put("registrarId",courseId);
     	  JSONObject json = new JSONObject(jsonValues);
    	  StringEntity entity = new StringEntity(json.toString(), "UTF8");
    	  entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    	  httppost.setEntity(entity);
    	  context.getLogger().log(json.toString());
    	  try {
    		  HttpResponse response = httpclient.execute(httppost);
    		  context.getLogger().log(response.toString());
    	  }catch(Exception e) {
    		  context.getLogger().log("failure");
    	 }
    	
    	 context.getLogger().log("succeed");
    	 return "NewCourse";
		    }
		}