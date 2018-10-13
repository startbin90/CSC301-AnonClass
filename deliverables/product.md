# YOUR PRODUCT/TEAM NAME

 > _Note:_ This document is meant to evolve throughout the planning phase of your project.    
 > That is, it makes sense for you commit regularly to this file while working on the project (especially edits/additions/deletions to the _Highlights_ section).

#### Q1: What are you planning to build?

It is a teaching staff and students in-class interaction software. The main features are students attendance-taking, broadcasting of files from teachers and students real time questioning. We found several common problems in class. First, teaching staff usually don't have an effective and cheap way to record students' attendance. Second, students sometimes don't have the chances to ask questions on class or students are too shy to ask a question. Third, some teachers don't want to be interrupted by questions when they are lecturing. Fourth, teachers don't usually have a convenient tool to broadcast lecture notes to everyone in the class. The software we are planning to build solves all of these problems. Students simply use mobile client to sign up for attendance. Teaching staff will receive the sign up on teacher’s client and then teachers can broadcast today’s lecture notes to all students who have signed up. When the lecture is on going, students can enter the chat room of this class and post a question message. When the teacher finishes a topic, teacher will see all the questions displayed on teacher’s client. 
 * Short (1 - 2 min' read)
 * Start with a single sentence, high-level description of the product.
 * Be clear - Describe the problem you are solving in simple terms.
 * Be concrete. For example:
    * What are you planning to build? Is it a website, mobile app,
   browser extension, command-line app, etc.?      
    * When describing the problem/need, give concrete examples of common use cases.
 * Focus on *what* your product does, and avoid discussing *how* you're going to implement it.      
   For example: This is not the time or the place to talk about which programming language and/or framework you are planning to use.
 * **Feel free (and very much encouraged) to include useful diagrams, mock-ups and/or links**.


#### Q2: Who are your target users?

Our target users are the teaching staff and students. 
Anthony is a shy student who has a lot of questions, but never speaks in the classroom. By using this app, Anthony can ask **ANY** question he wants without getting nerverous.

Professor A is a very outgoing person, and he wants to answer students' questions immediately. With this app, he can know which part of the class confuses the students and answer them.

On the other hand, professor B doesn't like to be interrupted during the lecture, then he can refer to the questions at the end of class or whenever he feels like to answer.

 * Short (1 - 2 min' read max)
 * Be specific (e.g. )
 * Feel free (but not obligated) to use personas.        
   You can create your personas as part of this Markdown file, or add a link to an external site (for example, [Xtensio](https://xtensio.com/user-persona/)).

#### Q3: Why would your users choose your product? What are they using today to solve their problem/need?

The users now use the piazza as the Q&A tool where the students and teaching staff ask questions, answers, and explore 24/7.  Based on this feature, students discuss topics mostly about exams and assignments. However, the users still need a real time tool for them to ask questions about the lecture in class and get an instant answer to clear up the confusions, so that they can catch the instructors and get a better understanding of the lecture. For the teaching stuff, it is always hard for them to answer all the questions in class due to the time limitation. 
the benefits of the product:
⋅⋅* Improve the quality of teaching and learning
⋅⋅* Provide convenient and effective way to clear up confusions
⋅⋅* Provide the teaching staff with information: to what extent the students understand the lecture, whether the students have sufficient background knowledge and etc. These information can help the teaching staff to adjust the teaching material and then deliver a better lecture.

 * Short (1 - 2 min' read max)
 * We want you to "connect the dots" for us - Why does your product (as described in your answer to Q1) fits the needs of your users (as described in your answer to Q2)?
 * Explain the benefits of your product explicitly & clearly. For example:
    * Save users time (how much?)
    * Allow users to discover new information (which information? And, why couldn't they discover it before?)
    * Provide users with more accurate and/or informative data (what kind of data? Why is it useful to them?)


----

### Highlights

We first considered making a Bluetooth based attendance-sign-up app. However, we realized the technological difficulties with Bluetooth technology. First, Bluetooth on a mobile device only accepts maximum 6 connections at a time and second, it normally takes some time for two devices to pair successfully. These two reasons make whether this idea will work very uncertern. We also considered other alternatives to Bluetooth, such as Geolocation or even QR code sign up. However, these alternatives are not as effective as Bluetooth since students have to be close to the teacher to sign up for attendance. But if Bluetooth really doesn’t work, we have to use other alternatives to achieve attendance sign up feature. So we shifted our focus from Bluetooth based attendance-sign-up app to an in class interaction software. 
Since this software involves both students and teachers. At first we think mobile app for student client and desktop app for teacher client will be the best combination.Then, we think mobile/web for student client and desktop/web for teacher client are also doable combinations. But, most of our group members don’t have experience with web app development and we realized that learning a new language on the fly would be too time-consuming so we have decided to proceed with a mobile application as a MVP. In order to meet MVP requirement as soon as possible, we decided to build mobile client which students and teachers both can use. In the later iterators, a web/desktop equivalent may be implemented.


Specify 3 - 5 key decisions and/or insights that came up during your meetings
and/or collaborative process.

 * Short (5 min' read max)
 * Decisions can be related to the product and/or the team process.
    * Mention which alternatives you were considering.
    * Present the arguments for each alternative.
    * Explain why the option you decided on makes the most sense for your team/product/users.
 * Essentially, we want to understand how (and why) you ended up with your current product plan.
