accMin([H|T],A,Min):- 
    H < A,
    accMax(T,H,Min).

accMax([H|T],A,Min):- 
    H >= A,
    accMax(T,A,Min).

accMax([],A,A).
