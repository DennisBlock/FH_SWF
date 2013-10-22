addone2([], []).
addone2([H|Th], [X|Tx]):- H =:= X-1, addone2(Th, Tx).
 