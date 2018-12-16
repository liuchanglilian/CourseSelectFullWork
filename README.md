# CourseSelectFullWork
The Whole work integrates AWS Step Functions and DynamoDB and AWS Lambda Functions.
Every time a new course is created.
If it is checked to be a new one then a AWS state machine will be triggered.
In the state machine, it will first check if the department of the course is "Seminars":
     1.If it is, nothing will be down, the state machine will end.
     2.If it isn't. A record in Registrar table will be created and continue the workflow. Besides, a board item will be created for the corresponding new course and the "boardId" part in the new course item will be set to be the boardId of the new board.
Here's an image of a drag racer in action:

![Alt text](/relative/path/to/img.jpg?raw=true "Optional Title")

Move along.
I also did the bonus part to create a separate Registrar Service, that exposes a  “…./registerOffering” POST URL. In this way, every time a new item was inserted into the Registrar table, a service in  Elastic beanstalk will take on the responsibility to insert into the "Registrar" table in DynamoDB(instead of directly insert into the dynamoDB).
