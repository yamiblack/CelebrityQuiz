# Celebrity Quiz

Feel free to use the sourcecode :recycle::grinning:

A simple Android quiz app that allows a user to select a correct answer to each question (any question can be skipped), given a hint by an image.

Build in Android Studio 3.5.3, Minimum SDK Version 22

### Libraries Used
* [OkHttp](https://github.com/codepath/android_guides/wiki/Using-OkHttp): for sending and receive HTTP-based network requests
* [Glide](https://github.com/bumptech/glide): media management
* [Gson](https://github.com/google/gson): convert Java Objects into their JSON representation and vise versa

### How to use the app

* First list item
   - First nested list item
     - Second nested list item
     
* First screen content allows user to select:
   - *Level of Difficulty*
     - For simplicity, all levels have 5 questions, just different
   - *Choose CountDown time*
     - Once quiz is started, it will only last for the amount of time selected and the app will automatically show score and solutions
* User may need to update the quiz contents, using *Update from Internet* button, if there’s an update or start quiz using locally saved content. Download *progress* is shown.
* Press *Start Quiz* to begin the quiz in new screen. NB: This button may be dependent to the update button
* In *quiz* screen, while watching the time:
   - User can select appropriate (or choose not to select) answer until completion.
   - At any point user can go to *next* or *previous* question
   - If user would like to finish or exit, press *submit*, all un-checked answers are given a score of 0.
*	In *Results* screen, 
   - All questions from selected level are shown
   - All corrected answers are marked as green, if user selects wrong answer, it will be marked red
   - Score obtained is shown on the right side of screen
   - A congratulatory image will be shown once user gets all the answers correct
*	User can then go back to First screen to start new quiz, again.

