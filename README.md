# `Smartschool to Trello`

### About the project 

So my school use this service called Smartschool to send mails, assign homework, reminders about an upcoming test etc.
This project forward those test and homework reminders to my Trello board.

### Why did I make this?

The problem for me is that the service doesn't have a good online agenda to show me when I have a test/homework,
so that's why I decided to make this.

### Problems I faced but found a workaround for

#### Smartschool API
The service does have an API, but those are only on request, and to request one I need to have an organisation which I don't have.<br/>
**Solution:** I was able to find a setting that forwards every test/homework reminder to my mailbox, that way I am able to use the Google Gmail API.

#### On new Gmail mail
When I want to use [users#watch()](https://developers.google.com/gmail/api/reference/rest/v1/users/watch) I will need to have a Google Pub/Sub service,
the problem with that is I will need to give my credit-card details which I obviously don't want to because what if I mess up?!.<br/>
**Solution:** I can fetch the mails every X minutes (Will have to do some maths to calculate how many quota Units I use for 1 request) it's not like my school is going to give me homework/a test without giving a proper amount of time to make it/study for it.