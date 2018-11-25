# Anonclass


## Iteration 02 - Review & Retrospect

 * When: Nov 1 2018
 * Where: BA 2240

## Process - Reflection


#### Decisions that turned out well

* Using Json to serialize/deserialize objects in order to transmit data between clients and server. We create an interface for all of these classes so that they must implement serialize method.
* When passing data using socket, let the first line be protocol that server can read and decide which information to gather from database. Client can also read the error message to analyse following data. This makes communication clear and effective.
* Each of the group member forks their own branch, and works on the directory they are assigned to, so there won't be many conflicts occurred during implementation, which maintains a pretty good workflow and coding environment.

#### Decisions that did not turn out as well as we hoped

* Assigned only one group member to work on Android, resulting too much workload for that member and some features have to be dummy or postponed.
* Planned to build a Android app for both instructors and students. Too much burden on the front-end development.
* Tried to deploy Server and Database on AWS at the first but failed which wastes a lot of time. We could have test it locally first.


#### Planned changes


 * Move one more person to work on Android.
 * Mobile client will focus on Student and we are going to build a web client for teachers.
 * Change the constructor of objects to Factory style. It's easier to form objects from Json string read from socket, and front end usage will be more convenient.


## Product - Review

#### Goals and/or tasks that were met/completed:

* Established communication between client and server, as well as between server and database.
* Finished some features of app including log in, sign up, enroll courses, create courses.
* Finished database schema.

#### Goals and/or tasks that were planned but not met/completed:

* Server and database have not been deployed on cloud service like AWS, which means testing can only be done in local network.
* We have not tested the connection between Server and Android, which means all the requests to server on Android are simulated.

## Meeting Highlights

Going into the next iteration, our main insights are:

 * Need to link front end and back end.
 * Let more person work on front end and UI design.
 * Mobile client will focus on Student and we are going to build a web client for teachers.
 * Finish feature of attendance and file broadcasting.

