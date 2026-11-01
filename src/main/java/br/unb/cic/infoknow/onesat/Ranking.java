package br.unb.cic.infoknow.onesat;

import java.util.HashMap;
import java.util.HashSet;

public class Ranking {

    private HashMap<String, Integer> order;
    private String andOperator;
    private String negativeOperator;

    public Ranking(String input, String andOperator, String negativeOperator) {
        this(AlphabetExtractor.createOrdering(input, andOperator, negativeOperator), andOperator, negativeOperator);
    }

    public Ranking(HashSet<String> alphabet, String andOperator, String negativeOperator) {
        this.order = createOrdering(alphabet);
        this.andOperator = andOperator;
        this.negativeOperator = negativeOperator;
    }

    public Integer getOrder(String clause) {
        Literal literal = new Literal(clause, this.andOperator, this.negativeOperator);
        if (literal.isEmpty()) {
            return null;
        }

        return this.order.computeIfAbsent(
            literal.getAtom(), 
            key -> order.size()
        );
    }
    
    private HashMap<String, Integer> createOrdering(HashSet<String> alphabet) {
        int order = 0;
        HashMap<String, Integer> orders = new HashMap<>();
        for (String clause : alphabet) {
            if (!clause.isEmpty()) {
                orders.put(clause, order++);
            }
        }
        return orders;
    }
}
