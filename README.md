# hotelbookingProblem

Questions itself in hotelbookingProblem.pdf, some tips from the recuiter:

Please ensure you include Unit Tests and be careful not make these 2 mistakes in your solution:
 
•                     Hotel class was not immutable.
•                     addBooking function was not thread safe
 
Also please try and make sure your Concurrency is your best effort. The below is what a previous candidate said about his:
 
“I forgot to use ConcurentHashMap instead of HashMap ( for the bookings) which will provide me with quite fast reads and also provide lock on write without locking the object”
