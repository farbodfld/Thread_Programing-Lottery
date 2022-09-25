# Thread_Programing-Lottery
Server and client source code for Lottery program with thread-programing method.

In Russia, Lottery TV show is very popular. The routine of the program is that at the 
beginning of the program, after introducing the sponsor, the presenter chooses a 10-
digit number, but does not announce it to the audience. From the moment the presenter 
chooses the number, people have the opportunity to call the number of the program and 
say their guessed number. The first person to guess the number correctly is the winner!
Now you are going to simulate this TV show under the title of native startup. The details 
of the program are as follows:

* The application must have a server. At first, the server is run so that the program 
is ready to run.
* The program must have a client. These clients connect to the server and 
exchange information with it.

## Program implementation process
First, the server runs. This server must have the ability to handle different clients at the same time.

After the server is up and running, clients can connect to the server. 

Each client can choose a username for himself.

The uniqueness of this user name must be confirmed by the server and if it is duplicate, ask the client to choose another user name. After the server is up, the game starts!

## *Point of interest*:

Normally, disconnecting a client from the server does not remove it from the list of clients. For points score, whenever a client is disconnected from the server, remove it from the server's client list.

The start of the game is announced to all connected clients through The Game is on message.
Before starting the game, the server must have generated a 5-digit number (not starting with zero). 
Through the sent message, the clients will know that the game has started. After starting the game, each client can guess any number he wants and send it to the server.

This process continues until a client guesses the number correctly. After this happens, a message is sent to the clients (FOLAANI won the round, which FOLAANI is actually the username of the corresponding client), and also a special message is sent to the winning client (You won).

After a client wins, the next round begins! This round is actually no different from the previous round (the server chooses a number, announces the start of the game to the clients, receives the numbers, and announces the end of the game after a client wins). 
After each round ends, the next round begins.

## *Bonus point*:

Don't choose the number that the server chooses randomly, but take the number from the internet! The process of choosing a number from the Internet is as 
follows:

See the contents of the page https://api.github.com/users/roozbehsayadi.

You should extract the id field from this page, and select its 5 least significant digits as the server's initial number.

In the nth round, you should calculate n times the number obtained and consider the number obtained as the number of the server.

If the number of digits of the number is more than 5, it has no case.

Note that we use different GitHub users for testing, so try to code that is flexible.

It is guaranteed that the number of digits in the id number is greater than or equal to 5.
