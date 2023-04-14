# Chomsky Normal Form

### Course: Formal Languages & Finite Automata
### Author: Bajenov Sevastian

----

## Theory

`CNF` stands for `Chomsky normal form`. A `CFG`(context free grammar) is in `CNF`(Chomsky normal form) if all production rules satisfy one of the following conditions:
  1. Start symbol generating ε. For example, `A → ε`.
  2. A non-terminal generating two non-terminals. For example, `S → AB`.
  3. A non-terminal generating a terminal. For example, `S → a`.

The productions of type `S → ε` are called `ε productions`. These type of productions can only be removed from those grammars that do not generate ε.
  1. First find out all nullable non-terminal variable which derives ε.
  2. For each production `A → a`, construct all production `A → x`, where x is obtained from `a` by removing one or more non-terminal from the first step.
  3. Now combine the result of step 2 with the original production and remove ε productions.

The `unit productions` are the productions in which one non-terminal gives another non-terminal. Use the following steps to remove unit production:
  1. To remove `X → Y`, add production `X → a` to the grammar rule whenever `Y → a` occurs in the grammar.
  2. Now delete `X → Y` from the grammar.
  3. Repeat step 1 and step 2 until all unit productions are removed.

A symbol can be `useless` if it does not appear on the right-hand side of the production rule and does not take part in the derivation of any string. 
That symbol is known as a useless symbol. Similarly, a variable can be useless if it does not take part in the derivation of any string. That variable is known
as a useless variable. This definition combines the notions of `non-productive` and `inaccessible` symbols which will be processed in this laboratory work.


## Objectives:

1. Learn about Chomsky Normal Form (CNF).
2. Get familiar with the approaches of normalizing a grammar.
3. Implement a method for normalizing an input grammar by the rules of CNF.

    1. The implementation needs to be encapsulated in a method with an appropriate signature (also ideally in an appropriate class/type).
   
    2. The implemented functionality needs executed and tested.

    3. A BONUS point will be given for the student who will have unit tests that validate the functionality of the project.

    4. Also, another BONUS point would be given if the student will make the aforementioned function to accept any grammar, not only the one from the student's variant.


## Implementation description


## Conclusions / Screenshots / Results


## References
https://www.javatpoint.com/automata-chomskys-normal-form

https://www.javatpoint.com/automata-simplification-of-cfg

https://junit.org/junit4/javadoc/4.8/overview-summary.html
