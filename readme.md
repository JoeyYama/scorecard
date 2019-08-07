# Android Studio Basic Golf Scorecard App


## How to Use the App

This was written to keep the scores of up to four golfers on a 18 hole golf course. 

The default par for each hole is 4. To change it, click on the ellipses and select Par. Edit the par information for each hole
that is not 4 and click on the Submit button on top.

To differentiate between golfers, click on the ellipses and select Players Initial. Starting with the first golfer, enter 
the initials of the golfers, up to 3 characters. The app will automatically capitalize the letters.

To enter the scores per hole, click on the row for the hole you wish to update. On the new page, enter in the scores. Click on the down image and then the Save button to save the scores and be taken back to the scorecard screen. The scorecard will automatically update the running total right under the golfer initials.

To clear the scores, click on the ellipses and click Reset Scorecard. The app will automatically reset the score of all holes to
zero and take you back to the original scorecard.


## General Design of the App

This app contains two Entities (ScoreEntity and ParEntity). Tables

The is app contains two Dao classes, one for each entity. Queries

There is just one AppDatabase, one Repository (used to call the AsyncTasks), and one ViewModel (separate the data from the UI).

The main activity for this app is the ScorecardActivity.

The Scorecard xml contains a RecyclerView connected by the ScorecardAdapter.

There is an UpdateActivity used to update the scores. Intent Extras are used to pass the data between the Scorecard and
Update Activities.

The Player's initials are stored in SharedPreferences. 

The ParActivity is used to modify the par for each hole. It has its own RecyclerView and Adapter.


## Next Step?

Add Handicap and calculate the net score

Scotch game?
