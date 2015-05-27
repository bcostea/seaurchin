# seaurchin
Web survey tool used for proactive technical support, in romanian, spring 2, hibernate 3, acegi, velocity

This is a tool built back in 2006.
Tool name, integrations and any other details were stripped.

Changes:
 - migrated from Oracle to HSQLDB - so it can run standalone
 - replaced Servlet auth/Tomcat config with Acegi, maintaing version compatibility with the Spring/Hibernate versions from that time (2006-2007) - so there's no need to configure Tomcat

Code is mostly Romanian.

Tested on Tomcat7 64bit vanilla and it works.

## How to Run

Run in embedded tomcat7 with mvn tomcat7:run-war.

Or deploy to tomcat and run it in server.

