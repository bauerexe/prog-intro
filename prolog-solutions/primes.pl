prime(N) :- N > 1, \+ has_divisor(N, 2).

composite(N) :- has_divisor(N, 2).

has_divisor(N, D) :-
    D * D =< N, (0 is N mod D; has_divisor(N, D + 1)).

prime_divisors(N, Divisors) :-
    (var(N) ->
        multiply_divisors(Divisors, 1, N)
    ;
        find_prime_divisors(N, 2, Divisors)
    ), !.

multiply_divisors([], N, N).
multiply_divisors([H|T], M, N) :-
    M1 is M * H,
    multiply_divisors(T, M1, N).

find_prime_divisors(1, _, []).
find_prime_divisors(N, D, Divisors) :-
    N > 1,
    (0 is N mod D
    ->  N1 is N // D,
        Divisors = [D|RestDivisors],
        find_prime_divisors(N1, D, RestDivisors)
    ;   D2 is D + 1,
        find_prime_divisors(N, D2, Divisors)).

nth_prime(N, P) :-
    (var(N) ->
        prime(P),
        nth_prime_count(1, N, 2, P)
    ;
        nth_prime_rec(1, N, 2, P)
    ), !.

nth_prime_rec(N, N, P, P) :- prime(P), !.
nth_prime_rec(N0, N, P0, P) :-
    P1 is P0 + 1,
    N0 < N,
    (prime(P1) ->
        N1 is N0 + 1,
        nth_prime_rec(N1, N, P1, P)
    ;
        nth_prime_rec(N0, N, P1, P)).

nth_prime_count(N, N, P, P) :- prime(P).
nth_prime_count(N0, N, P0, P) :-
    P1 is P0 + 1,
    (prime(P1) ->
        N1 is N0 + 1,
        nth_prime_count(N1, N, P1, P)
    ;
        nth_prime_count(N0, N, P1, P)).