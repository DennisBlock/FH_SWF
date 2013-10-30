type(X, atom):- atom(X).
type(X, number):- number(X).
type(X, constant):- atomic(X).
type(X, variable):- var(X).
type(X, simple_term):- simpleterm(X).
type(X, complex_term):- complexterm(X).
type(_, term).

complexterm(X) :-
        nonvar(X),
        functor(X,_,A),
        A > 0.
        
simpleterm(X):-
	nonvar(X),
	functor(X,_,A),
	A = 0.
        
termtype(Term, Type):- type(Term, Type).

