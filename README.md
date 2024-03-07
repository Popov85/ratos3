### e-RATOS (backend+compiled front-end)

[See WiKi](https://github.com/Popov85/ratos3/wiki)

e-RATOS (Embeddable Remote Automatised Teaching and Controlling System)
This is a web-based application that provides a rich extensive toolset for students' knowledge control 
via classical tests. The app can be used by medium and large educational organisations like schools, colleges or universities 
in multiple or mixed knowledge domains: health care, linguistics, natural sciences, etc. with significant loading 
like 10000+ learning sessions per day, with 1000+ simultaneous active sessions of 20-200 questions, accessed from
within any LTI 1.x-compatible [LMS](https://en.wikipedia.org/wiki/Learning_management_system) like [edX](https://github.com/edx), [Moodle](https://moodle.org/), [Sakai](https://github.com/sakaiproject/sakai), etc. 
as well as directly from within Intranet or Internet network via a browser.

#### Stack of technologies:
##### Backend:

- Java 8+;
- Spring Boot;
- Hibernate;
- RDBMS (MySql);
- Maven.

##### Frontend:
- React.js (sources [here](https://github.com/Popov85/ratos3-frontend))

#### Hardware config:
Varies depending on the scale of your organization: quantity of simultaneous users and quantity of questions.
E.g. for an organization of 10000 concurrent students, and staff of 500 people with questions base of 500000+, 
minimal requirement is the following:
- RAM 16Gb, with heap size > 8Gb;
- Single quad core CPU;

#### Most recent Docker build:
[Here](https://hub.docker.com/repository/docker/gelever85/ratos3)<br>
You need to create a Docker hub account to access.

#### TODO:
1. Correct controllers/services for all implemented frontend features, described [here](https://github.com/Popov85/ratos3-frontend#todo)

