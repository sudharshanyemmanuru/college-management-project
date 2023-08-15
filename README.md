# college-management-project
step-1: Set up the spring boot project or Download this project and import this project as maven project into your IDE.
step-2: Make sure you have added all required dependencies  for this project. You can check out the same in pom.xml file and save that file so that all dependencies are added to the class path.
step-3: set up your Backend Data base which is MYSQL in this case. To set up your DB follow the below steps.
        ->Execute the SQL Scripts on MYSQL which are present in the src/main/resourses->static->SQL-Scripts-scripts.txt.
        ->And also provide your Data base credintials in application.properties file.
step-4: build the project and run your server. and go to the browser and type http://localhost/[your server-port number]/home.
        provide your server port number in above url. by default port number is 8080 just provide this for the above url or you can also configure your own port number by adding "server.port=" configuration in your application.properties file.
        Then finally if you redirected to the login page means the application is launched successfully. and you can login to the system by providing the admin credintails
        username : admin
        password : 143.
