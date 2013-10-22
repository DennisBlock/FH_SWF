lastrec([E], E).
lastrec([_|T], E) :- lastrec(T,E).

lastrev(L, E) :- rev(L, [E|_]).