### e-RATOS (backend+compiled front-end)
e-RATOS (Embeddable Remote Automatised Teaching and Controlling System)
This is a web-based application that provides a rich extensive toolset for students' knowledge control 
via classical tests. The app can be used by medium and large educational organisations like schools, colleges or universities 
in multiple or mixed knowledge domains: health care, linguistics, natural sciences, etc. with significant loading 
like 10000+ learning sessions per day, with 1000+ simultaneous active sessions of 20-200 questions, accessed from
within any LTI 1.x-compatible [LMS](https://en.wikipedia.org/wiki/Learning_management_system) like [edX](https://github.com/edx), [Moodle](https://moodle.org/), [Sakai](https://github.com/sakaiproject/sakai), etc. 
as well as directly from within Intranet or Internet network via a browser.

#### Technologies:
- Java 8;
- Spring Boot 2.x: Spring Data, Spring Security, Spring MVC, Spring Session;
- Hibernate 5.x;
- RDBMS (MySql 5.7 or any other);
- Caffeine cache;
- React.js (sources [here](https://github.com/Popov85/ratos3-frontend))
- Maven.

#### Architecture
![e-RATOS](https://drive.google.com/uc?export=view&id=1yJV3F1zL8CetbKzlzSYBe0wcqPaqj_Tl)

#### Hardware config:
Varies depending on the scale of your organization: quantity of simultaneous users and quantity of questions.
E.g. for an organization of 10000 concurrent students, and staff of 500 people with questions base of 500000+, 
minimal requirement is the following:
- RAM 16Gb, with heap size > 8Gb;
- Single quad core CPU;

#### Features:
- Embeddable tool provider via [LTI 1.x](https://www.imsglobal.org/specs/ltiv1p0/implementation-guide);
- Unlimited test banks and huge test sets per learning session (200+ questions per scheme); 
  5 tried-and-true types of exercises (multiple choice questions, fill blank (single/multiple), matcher, sequence);
- Configurable learning schemes: exercise sequence strategies, grading strategies, batch sizes etc.;
- Controlling and educational types of learning sessions, extensive reports on sessions outcomes;
- Locale specific pre-populating the production database at first start-up, supported languages (EN, FR, RU);
- Flexible security policies (student, staff, instructor, department admin, faculty admin, organization admin);
- Turbo-start option via configurable parallel cache of questions populating at start-up;
- Multiple simultaneous learning sessions, comeback to already started session, session preservation;
- Off-heap session data storage capabilities (RDBMS, Redis, etc.): robust, clustered and JVM independent, though slower;
- Gamification.

##### LMS via LTI support
Sessions can be launched directly from within HTML pages of e-courses of Learning Management Systems. RATOS's 
security authentication mechanism is adjusted to LTI 1.x launch requests with OAuth 1.0 [One leg](http://oauthbible.com/) authorization credentials.
Authorization credentials are populated at first start-up as specified in [init.sql](https://github.com/Popov85/ratos3/blob/master/src/main/resources/script/prod/init.sql) script, 
one pair of key/secret per LMS instance. 
An example of how to configure LTI component of a tool consumer (Open edX) can be found [here](https://edx.readthedocs.io/projects/edx-partner-course-staff/en/latest/exercises_tools/lti_component.html)
##### Question types
The application is tuned to support huge questions sets per session (over 200 selected randomly out of as many as 10000+ total questions) and can operate with questions of multiple types: 
 - Classical multiple choice questions ([MCQ](https://en.wikipedia.org/wiki/Multiple_choice) with one or multiple right answers;
 - Fill blank single questions (FBSQ), when you are expected to type in some word or sentence of defined symbol quantity, language, etc;
 - Fill blank multiple questions (FBMQ), when you have multiple positions in a text to be filled in like in the previous example;
 - Matcher questions (MQ), when you need to match left and right sides of items that may contain resources too;
 - Sequence questions (SQ), when you have to put the mixed series of items (possibly with resources) in a certain order to make any sense.
##### Configurable learning schemes
Learning scheme is central entity to the application: it is the main asset as well as questions themselves and a subject to copyrights. 
Scheme consists of themes, themes may contain multiple questions of different types and difficulty levels. 
Each scheme is associated with a given sequence generating strategy (random selection of n requested out of m total questions in the database);
Currently supported <b>sequence strategies</b> are: 
 - <i>Default</i>: questions are randomised irrespective to question type and level, but observe themes order); 
 - <i>Random</i>: questions are randomised irrespective to type, level and themes;
 - <i>Types->levels</i>: questions are sequenced in order to types then levels {MCQ} {1, 2, 3} -> {FBSQ} {1, 2, 3} -> etc., theme's order is ignored.

Each scheme is associated with a given <b>grading strategy</b> that defines a numeric score and passed/not-passed verdict. 
Currently supported grading strategies are (users can create they own highly customized strategies):
 - <i>Four-point</i>: the classic 4 points grading system, acceptable scores {2, 3, 4, 5};
 - <i>Two-point</i>: the classic 2 points grading system, acceptable scores {0, 1} or {passed, not passed};
 - <i>Free-point</i>: an universal discrete grading system, acceptable scores {min, ..., max}

Each scheme is associated with a given mode and settings (users can create they own highly customized modes and settings 
to satisfy the learning process needs). <b>Mode</b> specifies multiple behavioral options: 
 - helps (whether or not this scheme allows user to get helps), 
 - skips (whether or not this scheme allows user to skip certain questions (to appear later in the same session)), 
 - pyramid (whether or not incorrectly answered question will appear later in the same session),
 - right answers (whether or not this scheme allows user to see the right answers after each response),
 - preservation (whether or not this scheme allows user to preserve his session to database to continue later),
 - complaints (whether or not this scheme allows user to complain about questions),
 - stars (whether or not this scheme allows user to mark questions to be reviewed later).
  
<b>Settings</b> specifies multiple structural options: 
 - time limit per question, 
 - questions per sheet, 
 - how many days to store detailed session results,
 - what results to display (percent, grade, passed or not, etc.),
 - levels coefficients, etc.
 
 Each scheme is associated with its creator (staff) and department it belongs to.

#### Controlling and educational sessions
There are two main algorithms for sessions: controlling and educational.
 - controlling (best fit for knowledge control). You always know when session is going to be finished.
 - educational (comes in play when you turn on skips or pyramid options). You never know when the session is going to be finished 
 as questions can float to the end of the queue each time a skip is requested or an incorrect answer was provided.
 
 You can combine options and create custom instances of modes and settings and use them if or when needed.
 
#### Database pre-populating
 At deployment time, you can pre-populate the production database with localized entries.
 Currently only supported languages are (EN, FR, RU).
 
#### Flexible security policies
 The application supports flexible security policies by protecting resources with multiple roles:
 student, staff, instructor, department admin, faculty admin, organization admin.

#### Turbo-start
The app supports populating cache at start-up (hot start) that significantly speeds up the responsiveness.
The supported options to load cache are:
 - ALL (all schemes and their questions will be loaded on start up);
 - LATEST (only recently used schemes will be loaded, the decision is made based on 1000 recent results);
 - LARGE (only composite schemes that contain more than one theme will be loaded);
 
 Also REST API for re-loading to cache certain schemes, course's schemes or even department's schemes is provided.
 At deployment time you can specify caching options (TTL (time to live) and max cache size) depending of the scale and needs of your organization.

#### Off-heap http session
There is a possibility for off-heap session data storage (RDBMS, Redis, etc.)
Let's consider these possibilities:
 - Tomcat's http session (in heap) - the fastest but least robust solution. All sessions' data are stored in heap
  and thus can be lost in case of server reloading. 
  Recommended in highly concurrent environments with >1000 simultaneous users.
 - RDBMS - the slowest but rather robust solution. All sessions's data are stored in a database 
 and will not be lost in case of server failure. 
 Not suitable for a very concurrent environment with >200 simultaneous users. Huge delays (2-3s) between batches are very probable.
 - Redis - the most appropriate trade-off, very fast and very robust. It requires a running Redis server, though.
 Recommended in rather concurrent environments with requirement to protect sessions' data with up to 1000 simultaneous users.

#### Gamification
Gamification is to push students into a competitive environment sparkling their interest to study.
The basic aspect of gamification is gaining points for achievements. By achievements, we mean a non-trivial successful session completion.
In gamification, only controlling schemes participate (these with no skipping or pyramid capabilities). To gain any points, 
a student must successfully complete a scheme from the first attempt. Second and the following attempts are not even considered.
Points are not granted for results on eligible schemes lower than a threshold specified by gamification settings.
Depending on the percentage scored, a user gets certain points, in default configuration (1, 3, 5) for (80-85%, 86-95%, 95-100%).
These points are summarized and stored forever in the database. Also, a sum of points scored during the last week is stored separately to
calculate the week winners. Weekly winners are considered those on the top n% from the top of sorted by gained points users list. 
Weekly achievements are reset each week. If a user appeared to be a winner, his counter of weeks he has won incriminates
and depending on how many times a user was a weekly winner, changes his title. In default configuration:

 - 0 = Novice
 - 1 = Beginner
 - 2 = Smart
 - 3 = Mature
 - 5 = Professional
 - 20 = Expert
 - 100 = Genius
 
Gamification settings are specified in application.properties in ratos.game branch and can be changed at deployment time to meet your needs.

Thus, it is easier for teaching staff to judge about a student who has some title and gained points in the middle of the university study.
Overall, gamification stimulates students to better prepare for tests and encourages them to be involved deeper in learning process.

