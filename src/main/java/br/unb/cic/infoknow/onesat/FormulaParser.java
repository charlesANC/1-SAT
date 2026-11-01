package br.unb.cic.infoknow.onesat;

import java.util.HashSet;

public class FormulaParser {

    public static Formula parse(String formula, String andOperator, String negativeOperator) {
        Ranking ranking = new Ranking(formula, andOperator, negativeOperator);
        return parse(formula, ranking, andOperator, negativeOperator);
    }

    public static Formula parse(String formula, Ranking ranking, String andOperator, String negativeOperator) {
        HashSet<Literal> literals = LiteralParser.parse(formula, andOperator, negativeOperator);
        return parse(literals, ranking);
    }

    public static Formula parse(HashSet<Literal> literals, Ranking ranking) {
        Integer positive = 0;
        Integer negative = 0;

        PlaceMaskFunction placeMaskFunction = new PlaceMaskFunction(ranking);

        for (Literal literal : literals) {
            Integer place = placeMaskFunction.apply(literal.getAtom());
            if (literal.isNegated()) {
                negative += place;
            } else {
                positive += place;
            }
        }
        return new Formula(positive, negative);
    }

}
