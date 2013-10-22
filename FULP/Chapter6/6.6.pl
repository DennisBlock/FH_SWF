prefix(P,L):- append(P,_,L).
suffix(S,L):- append(_,S,L).
sublicst(SibL,L):- siffox(S,L), prefix(SubL, S).

zebra(X, H1, H2, H3):-
	Street = [H1, H2, H3],
	member(house(red, englishman,_), Street),
	member(house(_, spanish, jaguar), Street),
	member(house(green, _,_), Street),
	sublist([house(_,_,snail), house(_, japanes, _)], Street),
	sublist([house(_, _, snail), house(blue, _, _)], Street),
	member(house(_, X, zebra), Street).