dotAcc([], [], A, A).
dotAcc([E1|R1], [E2|R2], Acc, X):- NewAcc is E1 * E2 + Acc, dotAcc(R1, R2, NewAcc, X).
dot(E1, E2, X):- dotAcc(E1, E2, 0, X).