# Capstone-Project
The final project of Udacity Android Developer Nanodegree.



GitHub Username: HRankit

----------



# QuizTime Description

  

Create, Share and Test your knowledge by attempting quizzes created by other users. Showcase your knowledge by creating quizzes.

  

# Intended User

  

Intended for everyone, from students to friends and family for taking a trivia quiz.

  

# Features

  

List the main features of your app. For example:

-   Saves Quiz for offline use
    
-   Create own Quiz
    
-   Preview the Quiz
    
-   Publish the Quiz
    
-   Share the Quiz
    
-   Attempt the Quiz multiple time till they are thorough
    

  

# User Interface Mocks

## Screen 1

![](https://lh4.googleusercontent.com/t28algTiGW8fzDt6QJzmSfRKJmzAhIv5Jpf4EYAzHFIFNuvZoS8gYX5Xz-wju-ieqMrU_vCci5t8u5ku1q-bdhEj6d2Sq43qUmXfOg904dErvglCF195jra79hl3_uxsIFagDy_P)

The main landing screen of the app. It shows two tabs Trending and Saved. Below tabs it displays the list of quizzes which are currently trending. A Floating Action Button is also seen which is used to create a quiz. More on it in SCREEN 5.

  
  

## Screen 2

![](https://lh6.googleusercontent.com/uojfnTJ4dhkKo2xIPptlaxZjMMYN6XOk1lwHs0Ty-2fv-NjSc_BQD8egOx6HCe0O2e2JTU2kh3CaO924QrpXZZzXoTUK7Qj8LK9we8cB0ny5tmTNxviG2Yg8mECfCtPJUJYFuC3t)

When clicked on any of the quiz on SCREEN 1 it shows the quiz. The quiz gets saved to room database automatically and is then available under Saved tab of Screen 1 The tab items on top display the number of questions in the quiz. When you click on that number, the respective question is shown. When clicked on the radio button, it changes color. Green if correct, Red if wrong. The tab also changes color according to the answer. More on it in Screen 3.

  

## Screen 3

![](https://lh5.googleusercontent.com/tTqcoRpnwbcoZDoRLNGVCSE6zqPBAcrGMm_f1ucHhNE0UbzREu5LRu41_0DJC9A55c1DcxmOkXyzr863ICMRmGqPxlBXeVkJvIMd3CAtsEnmV1ph6YnNVJIod3vSyI4Wfngq3aUf)

When Clicked on Any of the option, if the option is correct,the radio button and tab with question number turns green.

  

## Screen 4

![](https://lh5.googleusercontent.com/K7a8nbVOv-yeK6ynG2kzk7qHb_ACXXW8ClmTuVGCy8Za103vU7ht3JPbbyR98MY1dfDy7gNeoA6qCUv-tWawWd9DMHNDc8fxAUwAUxj6OWm_FoCeWI3UkZjjRUF4mcxYuKpWRI6g)

The last tab shows the Results. Note the color change of the tab which displays question number.

  
  
  

## Screen 5

![](https://lh5.googleusercontent.com/dyuFx9001ZnzTxtCoHYjVUJ9ZynaMMDri-qYd3067g7cYR2XuaWIFTcTI6zKCyr2fRKoFx_ia4CPtNrCHxzKmD3qVJ6_sqdJhL1PiFACa46I51gyYWSAsnSXlCoqIjf7SM-cIiVh)

When the FAB is pressed on Screen 1, it animates to this page. This page is used to publish the quiz. After entering all the details press Add another, the Bottom Tab changes text and title of the quiz is shown.

  

## Screen 6

![](https://lh3.googleusercontent.com/4UBLa0g5-QbUbUwR7pKN-DDvFrFA5pbUrpiLWodbS_1BP0NU67Hr5-_qhS5MnM2xfSpsZc7V5vJmwfFiVKJ5jvwnih0dzaGb7F4IdtLcGKi9m7I1f-RJZNgkMtigid6OICv5ZanM)

When the Bottom sheet is pulled up, it displays the current quiz. These cards are swipped to be removed. When the user is satisfied, using the menu option, the quiz gets published and a dialog to share the quiz is shown. When share is pressed, normal text with link is shared.

  
  
  
  
  

## Screen 7

![](https://lh4.googleusercontent.com/RSDvWPjCz7IVwGdwvzsKwERIpfXq6NXiBayowpYzOI6zObI8GzK1HrQl7stuzIxJPV7YhQ-XW76XbaQ4Oni6bku6Ia_XkEhlXapm240fSuHhUqa6Vt7x6aZ6tErhtEAkYwVhd4t_)

Widget must show the saved quizzes along with the author and image of the author.

  
  
  
  
  
  
  
  
  

# Key Considerations

  

### How will your app handle data persistence?

  

On device data persistence will be handled by Room Database.

  

### Describe any edge or corner cases in the UX.

  

-   There is no edit quiz option when the user tries to add a quiz. The user will have to remove the quiz and re-enter all the details.
    
-   Unstable or missed network connection: the application must not crash in that cases
    
-   Device orientation change: the application must handle all long-running operations correctly considering possible configuration changes
    
-   UI freezes: the application must not use the main thread for any resource consuming operations
    

  

### Describe any libraries you’ll be using and share your reasoning for including them.

  

1.  Picasso - 2.71828 - For image loading and caching,
    
2.  Retrofit - 2.4.0 - For network queries
    
3.  Firebase Auth - 16.0.5 - For Authentication
    
4.  Firebase Dynamic Links - 16.1.3 - For sharing links
    
5.  Android Architecture Components(Room, Livedata, Viewmodel, Paging, etc)
    
6.  Gradle - gradle-4.6
    
7.  Gradle Android Build tools - com.android.tools.build:gradle:-3.2.1
    
8.  Androidx.appcompat:appcompat:-1.0.2
    
9.  Androidx.constraintlayout:constraintlayout:-1.1.3
    
10.  Androidx.recyclerview:recyclerview:-1.0.0
    
11.  Androidx.legacy:legacy-support-v4:-1.0.0
    
12.  Androidx.cardview:cardview:-1.0.0
    
13.  Androidx.room-2.0.0-rc01
    
14.  com.google.android.material:material:1.1.0-alpha01
    

  

Android Studio 3.2.1

  
  

### Describe how you will implement Google Play Services or other external services.

  

Link the app to firebase via android studio, tools -> Firebase -> Connect to firebase.

  
  
  
  
  

### How you will support accessibility?

  

By adding contentDescription in all the relevant places to help the user navigate through the app. Using Android lint to check for any missing content description. Along with it proper color use with enough contrast to make them visible.

  
  
  

### How resources will be stored in the project including colors, strings, and themes?

  

Colors will be stored in colors.xml

Strings will be stored in strings.xml

Themes will be stored in styles.xml

  
  

### How the application implements one or more of the following SyncAdapter/JobDispacter or IntentService or AsyncTask for backend communication?

  

IntentService will be used for sending the Auth data to the server.

  
  
  
  

----------

  

The Android application will be written solely in the Java Programming Language.

  
  
  
  
  
  
  
  
  
  
  

# Next Steps: Required Tasks

  

This is the section where you can take the main features of your app (declared above) and break them down into tangible technical tasks that you can complete one at a time until you have a finished app.

  

## Task 1: Project Setup

  

Create a new Project in Android Studio with an empty activity called Main Activity

  

Create 3 fragments:

-   Trending - to display trending quizzes
    
-   Saved - to display saved quizzes
    

  

In main activity add the two fragments to the tab. Create a view pager adapter and link it to show the two tabs.

  
  

## Task 2: Implement UI for Each Activity and Fragment

  

List the subtasks. For example:

-   Build UI for MainActivity with viewpager and tablayout
    
-   Build UI for trending fragment and model
    
-   Build UI for saved fragment and model
    
-   Build UI for individual item to be displayed inside the recyclerview of these two fragments.
    

  

## Task 3: Code the Adapter for trending and saved recyclerview.

  

Implement Paging Library from AAC and link it to the trending fragment recyclerview.

Implement Room Database to show all the saved quizzes.

  

Make sure to start a new activity when any of the item in the recyclerview is clicked passing the quiz-id as intent.

  
  
  
  
  

## Task 4: Create a new activity called quiz-activity.

  

Implement deep-link as this will be the activity which gets displayed by Dynamic Links.

  

Once created complete the below steps:

-   Create fragment Quiz Fragment - Create a viewpager to display the quiz, along with change the color of the tabs which are shown in screen 3 and screen 4, This fragment fetches the quiz from the network.
    
-   Page Fragment - which actually displays the individual quiz data in the viewpager. This fragment is attached to the viewpager of the Quiz Fragment,
    
-   Complete the UI for the activity and both the fragment.
    

  

## Task 5: Firebase Auth

  

Use firebase authentication to get the user profile and image and send it to server. Add all the relevant methods to the MainActivity for this. When the fab is clicked if the user is logged in grant him/her access to the Insert Quiz Section else ask for authentication/signup. Send the data got from firebase auth to the server. IntentService will be used to send the Auth data to the server.

  
  

## Task 6: Insert Quiz

  

Create a fragment which will be used to publish the quiz. It should contain a Bottom Sheet for previewing the quiz before publishing. This fragment send the quiz to the server via network. A spinner must be used to select the correct answer. This fragment also handles the dynamic link generation, shortening and sharing to others.

  

## Task 7: Create Widget

  

Create a widget which should show all the saved quiz in gridview and must mention the name and author of the quiz.

  
  
  
  
  
  

## REST Server Endpoints

  

BASE URL = [https://comsec.co.in](https://comsec.co.in/quiz)

  

FETCH ALL QUIZ (GET REQUEST)

BASE_URL + /quiz/

Accepted/Required Parameters: page

  

----------

  
  

FETCH ONE QUIZ (GET REQUEST)

BASE_URL + /quiz/

Accepted/Required Parameters: t

  
  

----------

  
  

SEND QUIZ (POST REQUEST)

BASE_URL + /quiz/index2.php

Accepted/Required Parameters: title

  
  

----------

  

SEND USER DATA (POST REQUEST)

BASE_URL + /quiz/loguser.php

Accepted/Required Parameters: name, email, photourl, emailVerified, uid

  
  

----------
