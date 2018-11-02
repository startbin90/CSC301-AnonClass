# Anonclass

 > _Note:_ This document is meant to be written during (or shortly after) your review meeting, which should happen fairly close to the due date.      
 >      
 > _Suggestion:_ Have your review meeting a day or two before the due date. This way you will have some time to go over (and edit) this document, and all team members should have a chance to make their contribution.


## Iteration 02 - Review & Retrospect

 * When: Nov 1 2018
 * Where: BA 2240

## Process - Reflection

(Optional) Short introduction

#### Decisions that turned out well

List process-related (i.e. team organization) decisions that, in retrospect, turned out to be successful.

* Let all objects that required transmission through socket implement one interface that contains that serialize/deserialize method. So we can convert between object and Json string conveniently.
* When passing data using socket, let the first line be protocol that server can read and decide which information to gather from database. Client can also read the error message to analyse following data. This makes communication clear and effective.
* Each of the group member fork their own branch, and work on the directory they assigned to, so no much conflict occurred during implementation, which maintains a pretty good workflow and coding environment.

#### Decisions that did not turn out as well as we hoped

* Assign only one group member to build front end, resulting that the app cannot display features as we designed.
* Use Android app for both instructors and students. Too much burden on the front-end development.


#### Planned changes

List any process-related changes you are planning to make (if there are any)

 * Move one or two more people to work on front end of app, since we have finished most part of back end for now, and need more hands to implement front end.
 * Change the constructor of objects to Factory style. It's easier to form objects from Json string read from socket, and front end usage will be more convenient.


## Product - Review

#### Goals and/or tasks that were met/completed:

* Work out communication between client and server, as well as between server and database.
* Finish some features of app including log in, sign up, enroll courses, create courses.
* Finish database schema.

#### Goals and/or tasks that were planned but not met/completed:

* Didn't finish features such as open discussions, review and answer posted questions. One of the reasons is that front end is firstly focusing on other features such as log in, so the coding is delayed. Also, back end met difficulties during implementation of a consistent 'chatroom' of questions.

## Meeting Highlights

Going into the next iteration, our main insights are:

 * Need to link front end and back end.
 * Let more person work on front end and UI design.
 * Finish feature of attendance.

