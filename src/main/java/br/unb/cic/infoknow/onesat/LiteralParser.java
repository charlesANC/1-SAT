package br.unb.cic.infoknow.onesat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

public class LiteralParser {

    public static HashSet<Literal> parse(String formula, String andOperator, String negativeOperator) {
        return Arrays.stream(formula.split(Pattern.quote(andOperator)))
            .map(clause -> new Literal(clause, andOperator, negativeOperator))
            .filter(literal -> !literal.isEmpty())
            .collect(HashSet::new, (set, literal) -> set.add(literal), HashSet::addAll);
    }
}
