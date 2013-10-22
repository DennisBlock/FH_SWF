accRev([], A,A).
accRev([H|T], A, R):- accRev(T, [H|A],R).

rev(L,R):- accRev(L, [], R).

palindrome(X) :- rev(X,X).

%%%   SEND
%%% + MORE
%%% =MONEY

%%%   GAUSS
%%% + RIESE
%%% =EUKLID