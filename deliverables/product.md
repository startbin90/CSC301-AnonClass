# AnonClass


#### Q1: What are you planning to build?

We plan to build a teaching staff and students in-class interaction software. The main features of this application are students attendance sign-up, lecture notes broadcasting and students in class online questioning. The reason why we build this application is that we found several common problems in class. First, teaching staff usually don't have an effective and cheap way to record students' attendance. Second, students sometimes don't have the chances to ask questions on class or students are too shy to ask a question. Third, some teachers don't want to be interrupted by questions when they are lecturing. Fourth, teachers don't usually have a convenient tool to broadcast lecture notes to everyone in the class. This software (AnonClass) solves all of these problems. Students simply use mobile client to sign up for attendance. Teaching staff will receive the sign up on the teacher’s client and then teachers can broadcast today’s lecture notes to all students who have signed up. When the lecture is on going, students can enter the chat room of this class and post a question message when they have a question. Questions will pop up on teacher's client and teachers can choose to see this questions whenever he/she likes.


#### Q2: Who are your target users?

Our target users are the teaching staff and students. 
Elliot is a shy student who has a lot of questions, but never speaks in the classroom. Everytime he comes up with a question at class, he has to wait for the class to end and post this question on Piazza. With this app, Elliot can get the answer immediately in class.

Professor IhaveAnswers is a very outgoing person, and he wants to answer students' questions immediately. With this app, he is able to know which part of the class confuses the students and answer them.

On the other hand, Professor NoMoreQuestions doesn't like to be interrupted during the lecture, then he can refer to the questions at the end of class or whenever he would like to answer.

#### Q3: Why would your users choose your product? What are they using today to solve their problem/need?

* Users(students and teachers) now use the Piazza as the Q&A tool where the students and teaching staff ask and answer questions. However, this is not a real time Q&A service and teachers will not check questions on Piazza in the lecture. This application aims at in class quick message Q&A. Its convenience is not provided by currently used tools like Piazza.
* Users(students and teachers) now use iClicker as a attendance sign-up tool and in-class quiz tool. However, iClicker is not free. With AnonClass, students only have to download an app on their phones and can save that 10 bucks for some snacks.
* Users(students and teachers) now use Quercus as a tool to post lecture notes. However, posting a file to a website can be very cumbersome as well as for students to find and download files from websites. Teachers can even broadcast codes or notes typed in class when the lecture is on-going because the steps to broadcasting a file on our app is so simple.
* Overall benefits of the product:
  1. Improve the quality of teaching and learning
  2. Provide convenient and effective way to clear up confusions
  3. Provide the teaching staff with information: to what extent the students understand the lecture, whether the students have sufficient background knowledge and etc. These information can help the teaching staff to adjust the teaching material and then deliver a better lecture.
  4. Provide the attendance information to the teaching stuff.
----

### Highlights

* We first considered making a Bluetooth based attendance-sign-up app. However, we realized some technological difficulties with Bluetooth technology. First, Bluetooth on a mobile device only accepts maximum 6 connections at a time and second, it normally takes some time for two devices to pair successfully. These two reasons make whether this idea will work very uncertern. We also considered other alternatives to Bluetooth, such as Geolocation or even QR code sign up. However, these alternatives are not as effective as Bluetooth since students have to be close to the teacher to sign up for attendance. But if Bluetooth really doesn’t work, we have to use other alternatives to achieve attendance sign up feature. So we shifted our focus from Bluetooth based attendance sign-up app to an in class interaction software. 
* Since this software involves both students and teachers. At first we think mobile app for student client and desktop app for teacher client will be the best combination. Then we think mobile/web for student client and desktop/web for teacher client are also doable combinations. But, most of our group members don’t have experience with web app development and we realized that learning a new language on the fly would be too time-consuming so we have decided to proceed with a mobile application as a MVP. In order to meet MVP requirement as soon as possible, we decided to build mobile client which students and teachers both can use. In the later iterators, a web/desktop equivalent may be implemented.
