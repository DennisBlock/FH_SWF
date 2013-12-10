groundterm(T) :- nonvar(T), atomic(T), !.

groundterm(T) :- nonvar(T),
functor(T,_,A),
groundterm(A,T).

groundterm(N,T) :- N > 0, arg(N, T, Arg), groundterm(Arg), N1 is N - 1, groundterm(N1, T).
groundterm(0,_). 


%%% args !?!?
args(T,Args) :- nonvar(T),
functor(T,_,A),
acc_args(A,T,[],Args).

acc_args(N,T,Acc,Args):- N > 0, arg(N,T,Arg), N1 is N - 1, NewAcc = [Arg|Acc], acc_args(N1, T, NewAcc, Args).
acc_args(0,-,Args,Args).