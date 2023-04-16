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

Before discussing the laboratory implementation itself let me present the variant I was working on: `Vn` = {S, A, B, C, D}, `Vt` = {a, b} and `P` = {S -> aB | bA, A -> B | b | aD | AS | bAAB | e, B -> b | bS, C -> AB, D -> BB}

As it was already mentioned in the `Theory` section, in order to perform CFG simplisifcation and transform it into the CNF we have to go throgh the four symbol elimination processes. Fo this purpose I created an interface `GrammarSimplification` inside the `grammar/simplification` folder which includes the four elimination methods respectively.

```
public interface GrammarSimplification {
    
    void eliminateE_Productions (Map<String, List<String>> productions);

    void eliminateUnitProductions (Map<String, List<String>> productions);

    void eliminateNonProductiveSymbols (Grammar g);

    void eliminateInaccesibleSymbols (Grammar g);
}
```

This interface is implemented by the `NormalForm` class which also includes general method for Chomsky normalization and several private helper methods. Let me start discussing the most important ones. 

1. E-elimination method works mainly on defining nullable symbols and eliminating them from the corresponding producitons. If a symbol has an e-production or derives into a nullable symbol then the algorithm finds all the productions which include such a symbol. Then usinng binary numbers it computes all possible combinations of that symbol inside of the found production and adds them into the production list. For instance, in my variant I have `A -> e` and `A -> bAAB` therefore new productions for the symbol A will be bAB and bB. The algorithm stops when there are no nullables remainning in the grammar. There is also a special set containing visited symbols in order to avoid loops in epsilon elimination.

```
@Override
public void eliminateE_Productions (Map<String, List<String>> productions) {
    Queue<String> eSymbols = new LinkedList<>();
    Set<String> visitedSymbols = new HashSet<>();

    analyzeNullables(eSymbols, productions, visitedSymbols);

    while (!eSymbols.isEmpty()) {
        String eSymbol = eSymbols.poll();
        visitedSymbols.add(eSymbol);
        long counter;
        ...
```

The method `analyzeNullables` actually performs the operation of finding nullables at each iteration.

```
private void analyzeNullables (Queue<String> nullables, Map<String, List<String>> productions, Set<String> visited) {
    for (String symbol : productions.keySet()) {
        for (String production : productions.get(symbol)) {
            if ((production.compareTo("e") == 0 || containsOnlyNullables(nullables, productions, symbol))
                && !visited.contains(symbol)) {
                nullables.add(symbol);
                productions.get(symbol).remove(production);
                break;
            }
        }
    }
}
```

And the following piece of code describes how the combination of terminals inside of a production are being created. The binary number of length equal to the number of nullable in the production represents each distinct combination.

```
...
counter = production.chars().filter(ch -> ch == eSymbol.charAt(0)).count();
                        
if (counter > 1) {
    for (int i = 1; i < Math.pow(2, counter) - 1; i++) {
        String combination = Integer.toBinaryString(i);
        int diff = (int) counter - combination.length();

        if (diff != 0) {
            for (int j = 0; j < diff; j++) {
                combination = "0" + combination;
            }
        }

        StringBuilder sb = new StringBuilder();
        int index = 0;
        int k = 0;    

        while (k < production.length()) {
            if (production.charAt(k) == eSymbol.charAt(0)) {
                if (combination.charAt(index) == '1') {
                    sb.append(production.charAt(k));
                }

                index++;
            } else if (production.charAt(k) != eSymbol.charAt(0)) {
                sb.append(production.charAt(k));
            }
            k++;
        }
        ...
```

2. The process of eliminating unit productions works similarly in a loop. It continues iterating while thre are still unit productions remaining in grammar. When they are found, they are immediately removed and replaced with productions which contained the non-terminal from the right side of the production found.

```
@Override
public void eliminateUnitProductions (Map<String, List<String>> productions) {
    int unitMarker = 1;

    while (unitMarker != 0) {
        unitMarker = 0;

        for (String symbol : productions.keySet()) {
            Set<String> lineUnits = productions.get(symbol)
                                                .stream()
                                                .filter(production -> production.length() == 1 && Character.isUpperCase(production.charAt(0)))
                                                .collect(Collectors.toSet());
            if (!lineUnits.isEmpty()) {
                productions.get(symbol).removeIf(production -> production.length() == 1 && Character.isUpperCase(production.charAt(0)));
                unitMarker++;

                for (String unit : lineUnits) {
                    for (String prod : productions.get(unit)) {
                        if (!productions.get(symbol).contains(prod)) {
                            productions.get(symbol).add(prod);
                        }
                        ...
```

3. Further on, if we have to remove non-productive symbols from our grammar we have to find all of the productive symbols at first. It is important to mention that the symbols are considered to be productive not only when when they derive in a terminal but also if they derive in a productive symbol. For that purpose it is necessary to keep track of all the productive symbols currently found in grammar (a special method `isProductive()` is responsible for checking the symbols for productiveness).

```
@Override
public void eliminateNonProductiveSymbols (Grammar g) {
    Map<String, List<String>> productions = g.getProductions();
    Set<String> productiveSymbols = new HashSet<>();

    for (String symbol : productions.keySet()) {
        for (String production : productions.get(symbol)) {
            if ((production.length() == 1 && Character.isLowerCase(production.charAt(0)))
                || isProductive(production, productiveSymbols)) {
                productiveSymbols.add(symbol);
                break;
            }
        }
    }
    ...
```

After that we can find the difference of two sets: set of all non-terminal symbols and set of all productive symbols; and voila, we will obtain the set of non-productive symbols which have to be removed from the grammar together with the productions containing them.

```
    ...
    Set<String> nonTerminals = new HashSet<>(g.getNonTerminals());
    nonTerminals.removeAll(productiveSymbols);
    Set<String> nonProductiveSymbols = nonTerminals;

    for (String symbol : nonProductiveSymbols) {
        productions.remove(symbol);

        for (List<String> prods : productions.values()) {
            prods.removeIf(production -> production.contains(symbol));
        }
    }
}
```

4. The last step in preparing our grammar for Chomsky normalization consists in removing inaccesible symbols which means eliminating symbols which are not included in the right side of at least one production. The rule is simple and may seem primitive but works for the given grammars and does not require creating derivation graphs which show the connection between all non-terminals of the grammar.

```
@Override
public void eliminateInaccesibleSymbols (Grammar g) {
    Map<String, List<String>> productions = g.getProductions();
    Set<String> inaccesibleSymbols = new HashSet<>();
    byte checker;

    for (String symbol : productions.keySet()) {
        checker = 0;

        for (List<String> production : productions.values()) {
            for (String result : production) {
                if (result.contains(symbol)) {
                    checker += 1;
                    break;
                }
            }
        }

        if (checker == 0  &&  !symbol.contains("S")) {
            inaccesibleSymbols.add(symbol);
        }
    }

    for (String inSymbol : inaccesibleSymbols) {
        productions.remove(inSymbol);
        g.getNonTerminals().removeIf(nonTerm -> nonTerm.compareTo(inSymbol) == 0);
    }
}
```

## Conclusions / Screenshots / Results


## References
https://www.javatpoint.com/automata-chomskys-normal-form

https://www.javatpoint.com/automata-simplification-of-cfg

https://junit.org/junit4/javadoc/4.8/overview-summary.html
