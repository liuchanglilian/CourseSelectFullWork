public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {

    @Override
    public String handleRequest(DynamodbEvent event, Context context) {
       
        for (DynamodbStreamRecord record : event.getRecords()) {
           context.getLogger().log("recordID"+record.getEventID());
            context.getLogger().log("Event name:"+record.getEventName());
            context.getLogger().log("db:"+record.getDynamodb().toString());
        	context.getLogger().log("hello chang");
        	 Map<String,AttributeValue> map = record.getDynamodb().getNewImage();
	         AttributeValue rosters =map.get("rosters");
	         AttributeValue boardId =map.get("boardId");
	         
	         AttributeValue topicArn =map.get("topicArn");
	         AttributeValue id = map.get("id");
	         AttributeValue department = map.get("department");
             AttributeValue courseId  = map.get("courseid");
	         List<AttributeValue> list = rosters.getL();
	         context.getLogger().log("I am here");
	         if(boardId == null && list.size() == 0) {
	        	 context.getLogger().log("empty now!!!");
	        	 HttpClient httpclient = HttpClients.createDefault();
	        	 HttpPost httppost = new HttpPost("https://5gd8fm8o0g.execute-api.us-east-2.amazonaws.com/alpha");

	        	 Map<String,Object> jsonValues = new HashMap<>();
	        	 if(department!=null) {
	        		 context.getLogger().log("department is not null");
	        		 context.getLogger().log(department.getS());
	        	 }
	        	 if(courseId!=null) {
	        		 context.getLogger().log("courseID is not null");
	        		 context.getLogger().log(courseId.getS());
	        	 }
	        	 jsonValues.put("input","{\"department\" : \""+department.getS()+"\",\"courseId\":\""+
	        	 courseId.getS()+"\"}");
	        	 jsonValues.put("name","MyExecution"+id.getS());
	        	 jsonValues.put("stateMachineArn", "arn:aws:states:us-east-2:149605556680:stateMachine:Choicestate");
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
	         }else {
	        	 if(boardId!=null) {
	        		 context.getLogger().log("boardId is not null");
	        		 context.getLogger().log(boardId.getS());
	        	 }
	        	 if(rosters!=null) {
	        		 context.getLogger().log("rosters is not null");
	        		 context.getLogger().log(rosters.getS());
        	 }
	        	 if(topicArn !=null) {
	        		 context.getLogger().log("topicArn  is not null");
	        		 context.getLogger().log(topicArn.getS());
	        	 }
	         }
	         
        }
        return "OldCourse";
    }
}

