# ELO-League-Management-System
<br/>
<h3>Purpose</h3>
This is a free open-source application designed to help minor E-sports leagues track leaderboard rankings and player stats. The application is capable of supporting up to 5 unique players in 1 match. It uses the ELO rating system, a popular method for calculating the relative skill level of a player.  

<h3>Specs</h3>
<h5>The App's GUI was built with the Javafx library and uses XML to store and load Player objects. Gluon Scene builder was used to construct the view layout.</h5>
<h5> Updated on 26th of October </h5>
<h3>Update- Now integrated with Google Spreadsheets
<img src='output.gif'/>
  
  
  ### Backstory
  
   if you ever organized a mutli-player competitive event, you know the head ache that comes with ranking players whenever they win/lose.  Sure, you can rank people by wins, but what if the top guy, farmed all his wins from lower ranked-players?
 
 In short, we can't just go by wins or losses as a means to rank players. We need a way to first quantify the "skill" of a player. Thankfully, ELO already solves that. With ELO, you get a rating that guages your skill based on who you've managed to beat in the past. It forces you to compete against stronger players in order to advance your own rank. 
 
 Now, there are other open-source projects that do the same but they have some signicicant shortfalls. Namely, they only implement the classical ELO system, which is designed for 1 vs 1 style games like chess.
  
  My Application is designed for up to 5 - player games. Think free for all games. And it comes integrated with google-spreadsheets-API so that players could publicly see their rank. 


