# Quizzy App

## Common Info
Single activity quiz application for practicing in MVVM, Room, DataBinding and NavGraphs.

### UI
App has several screens
- Quiz List. Grid **RecyclerView**, that shows added quizzes with some preview info.
- Add Quiz. Few **EditText** and **Button** objects. **RecyclerView** for added **Question** list.
- Edit Quiz. Same elements and logic as Add Quiz.
- Add Question. Few **EditText** and **Button** objects. **RecyclerView** for added **Answers** list.
- Quiz Passing. Simple screen containing **TextView** for question text, **RecyclerView** for answers list and Quiz ProgressBar.

### Room
Entities: **Quiz**, **Question**, **Answer**.
Single Dao: **QuizDao**.

## Future plans
1. Add user profile info
2. Add Firebase Auth support
3. Implement Firebase Storage and caching logic
4. Add BottomNavMenu for User Profile and other Users Quizzes.