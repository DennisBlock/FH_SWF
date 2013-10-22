scalarMult(_, [], []).
scalarMult(X, [H|Th], [R|Tr]):- R is X * H, scalarMult(X, Th, Tr).