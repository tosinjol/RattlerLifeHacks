# RattlerLifeHacks
## Table of Contents
- [Overview](#overview)
- [Product Spec](#product-spec)
- [Wireframes](#wireframes)
- [Schema](#schema)
- [Backend Demonstration](#backend--api-demonstration-gif)
- [Poster](#poster)
- [Poster Presentation](#poster-presentation)
- [Full Demo](#full-demo)

## Overview
### Description


The Rattler Life Hacks app is a comprehensive platform designed to enhance the student experience at Florida A&M University. Students can easily view and sign up for campus events and manage their schedules with a personalized calendar. The app also allows users to check their Rattler Card balance and conveniently add money to it. Additionally, it provides real-time wait times for the financial aid office and other key offices on campus, as well as study room availability and reservations at the library. Restaurant wait times, along with detailed menus for cafeteria's and restaurants on campus, help students plan their meals efficiently. The rattler Life Hacks is your all-in-one tool for staying connected and organized on campus!


### App Evaluation
- **Story:** 'Rattler Life Hacks' allows students to quickly and sufficiently control their schedules on campus. With live wait times, menus, and event check-ins, this app allows students to optimize their time and plan their day appropriately.
- **Market:** This app is marketed toward Florida Agricultual & Mechanical Universities student body. It is useful for freshmen and seniors to transfer students!
- **Habit:** This app will be helpful at all times of the day. Since the app houses a 'live' feature, it will always be up to date and ready when a student needs it.
- **Scope:** The app would start by providing live wait times for financial aid, study rooms, and campus dining, helping students manage their time more efficiently. It would then expand to include live event check-ins for easy attendance tracking and a virtual school ID for seamless access to campus services. Over time, the app could integrate with campus systems for booking services, pre-ordering meals, and scheduling appointments, with the potential to partner with broader student platforms to enhance engagement and usage.
## Product Spec
### 1. User Stories (Required and Optional)

#### Required Must-have Stories

For Rattlers LifeHacks, we identified the following "must-have" features that a user needs to be able to perform for the app to work:</p>

- [X] Get all users 
- [X] Get user Id 
- [ ] User can post their campus events
- [ ] User can create a new organization account
- [X] User can create a new student account
- [X] User can login
- [X] User can update their username
- [X] User can update profile picture
- [ ] User can search for events
- [X] User can view a feed of events
- [ ] User can tap their RattlerCard
- [ ] User can see food/financial aid wait times
- [X] User can view study room availability


#### Optional Nice-to-have Stories


- [ ] User can like events
- [ ] User can follow/unfollow campus organizations
- [ ] User can add a comment to an event
- [ ] User can tap an event flyer to view a more detailed screen with other users comments
- [ ] User can see trending events
- [ ] User can search for events by organization
- [ ] User can see notifications when an event is upcoming
- [ ] User can join their organization
- [ ] User can mark attending at an event
- [ ] User can see a list of who will be attending an event
- [ ] User can see a list of people in an organization
- [ ] User can view other organization's profiles and see their eventfeed
- [ ] User can join a financial aid queue


### 2. Screens 
<ul>
<li>Registration- User signs up or logs into their account</li>
  <ul>
    <li>Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person.    
    </li>
  </ul>
<li>RattlerCard- User can access their Rattler card and see their balances.</li>
  <ul>
  <li>Upon selecting the Rattler icon, this screen will open.</li>
  </ul>
<li>Calendar Screen- User can add their personal events, assignments, birthdays, etc. on this calendar page to organize themselves.</li>
  <ul>
    <li>Upon selecting the Calendar icon, this screen will open.</li>
  </ul>
<li>Wait times- User can view the wait times of their favorite on campus food spots as well as offices such as financial aid. They are also able to see study room availability and book study rooms in Coleman.</li>
  <ul>
    <li>Upon selecting the Clock icon, this screen will open.</li>
  </ul>
<li>Home Page- User can view all of the campuses upcoming events</li>
  <ul>
    <li>Upon scrolling below the home page, users can access this screen.</li>
  </ul>
<li>Home Page- User can view the "Welcome to the Hill Rattlers!" display.</li>
</ul>

### 3. Navigation
**Tab Navigation:**
(Appears as three dots in the corner and opens after being pressed)
- Login / profile
- Home
- Calendar
- Rattler Card
- Live Wait Times
  
## Wireframes

![handrawn_wireframe](https://github.com/user-attachments/assets/ffda628d-e892-49b0-808d-457664ddbe86)

### Digital Wireframe
![Screen Shot 2024-10-05 at 4 20 14 PM](https://github.com/user-attachments/assets/315411f5-6e2a-4399-b914-45b3ae02033e)

## Schema
# Models 
# Post

| Property | Type | Desscription |
| ----------- | ----------- | --------|
| objectId | String | unique id for registration purposes (default field)|
| author | pointer to the user | image author |
| image | File | image that organization posts |
| caption | String | image caption made by author |
| registerCount | Number | number of registrations for the post|
| createdAt | DateTime | date when post is created (default field) |
| updatedAt | DateTime | date when post is updated (default field)| 
| locationId | String | unique id for event location |
| locationName | pointer to the locationId | name of event location |

# Networking 

- Home Screen
    - (Read/GET) Events list
    - (Update/PUT) Organizations can add events 
    - (Update) Mark attendance
- Rattler Card Screen
    - (Read/GET) Rattler Card / Meal Plan information
- Wait Time Screen
    - (Read/GET) Wait time statistics
    - (Update) Queue information
- Calendar Screen
    - (Update) weekly schedule
    - (Read/GET) Existing entries
- Study Room Pop-Up
    - (Read/GET) study room availability
 
## Backend & API Demonstration (GIF)
For the Rattler Life Hacks app, we developed a User model class in IntelliJ, which represents user data and interactions with Firebase. We included a unique service key for enhanced identification, along with two core methods: getAllUsers and getUserById. These methods are structured as service and controller files, establishing the key user interactions for retrieving all users or individual user data based on ID. Through these queries to the Firebase database, the app can efficiently access and manage user information, aligning with the app's back-end functionality requirements.
![App Demo](https://github.com/Rattler-LifeHacks/RattlerLifeHacks/blob/main/giphy.gif)

## Updated GIF:
https://github.com/user-attachments/assets/bb4e7f96-0c23-4aa2-b60b-fcaf029fc606




**YouTube Link:** https://youtu.be/-o8nyYOmP0E
## Full Demo
https://github.com/user-attachments/assets/1b49ac12-44a7-4e17-8261-bac9c5451c9a









