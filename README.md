# CourseSelectFullWork with bonus part
The Whole work integrates AWS Step Functions and DynamoDB and AWS Lambda Functions.
Every time a new course is created.
If it is checked to be a new one then a AWS state machine will be triggered.
In the state machine, it will first check if the department of the course is "Seminars":
     1.If it is, nothing will be down, the state machine will end.
     2.If it isn't. A record in Registrar table will be created and continue the workflow. Besides, a board item will be created for the corresponding new course and the "boardId" part in the new course item will be set to be the boardId of the new board.
Here's an image of a drag racer in action:

![Alt text](/statemachine.png)

Move along.
I also did the bonus part to create a separate Registrar Service, that exposes a  “…./registerOffering” POST URL. In this way, every time a new item was inserted into the Registrar table, a service in  Elastic beanstalk will take on the responsibility to insert into the "Registrar" table in DynamoDB(instead of directly insert into the dynamoDB).

## Domain: http://lilianassignment4.us-east-2.elasticbeanstalk.com
How to use it?
### 1. Check for courses in database
GET ->../webapi/courses
Here we can see the courses in database currently.

If we try to insert a course with the courseId that already exists-- say"CS570". Then nothing will happend.
### 2. Try to insert a new course
POST -> ../webapi/courses
put in the body:

      {
         "courseId": "DS999",
         "department": "Dummy department",
         "professorId": "123",
         "taId": "123"
       }
### 3.Check for the registrar table
GET -> ../webapi/registerOffering
We can get the results of all the registrars and the corresponding regsteroffering has the OfferingId the same as the coresponding course's courseId

### 4.Check for the board item created for it
  #### A. First We need to know the boardId of the created board item.
  GET -> ../webapi/courses/{"the courseId of the created course like DS999"}
  then the boardId will be included
  ![Alt text](/pic2.png)
  (I set the boardId to be the same as the created course as they are corresponding)
  #### B. Second, We can check for the board with this boardId
  GET -> ../webapi/boards/{"the boardId we get like DS999"}
### 5. What happens if the department of the course is "Seminars"?
   Nothing happens
   After POST -> ../webapi/courses
        put in the body:

      {
         "courseId": "DS998",
         "department": "Seminars",
         "professorId": "123",
         "taId": "123"
       }
    if we have a check of the boards or registrars, we will found that there are no such items.
#### 6. About the BONUS Part:
  I wrote the Registrar Service "../registerOffering" and deploy it to elastic beanstalk.
  The lambda function calls this API and send HTTP POST request to it.
  I also put part of the code in the lambda function here.
  ![Alt text](/pic3.png)
