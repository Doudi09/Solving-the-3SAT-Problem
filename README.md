Solving the 3SAT problem using meta-heuristic methods

Overview :

The SAT satisfiability problem is a decision problem which is the first demonstrated as an NP-Complete problem. 
The SAT is defined by a set of variables and clauses. 
Such that a clause is a disjunction of literals, and a literal is defined by a variable or its negation.
To say that the SAT is satisfiable there must exist at least one instantiation of the variables such that the conjunction of the clauses is true.

In this project, we are going to use three meta-heuristic methods, we start with genetic algorithms, the PSO method (particle swarm optimization) and the BSO method (Bee Swarm optimization).

Data :

Benchmark files are instances of SAT problems written in files of the form CNF (conjunctive normal form in English), the file begins with lines that describe the file, 
clause number and clause size. 
After the instance description, each line represents a clause, and each clause is represented by the sequence of coded variables, where a variable is coded by an integer between –m and -1 for the negation of the variable and between 1 and m otherwise (where m is the total number of variables), if the variable does not exist in the clause, it will not be represented and each clause ends with a zero.
These files will be used in the implementation and test part of the algorithms developed.

The benchmark files can be found at : src/rsc


GUI :

A GUI has been developped with JavaFX, the file can be found at : src/App
