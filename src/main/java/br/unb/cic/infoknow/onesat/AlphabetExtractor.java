package br.unb.cic.infoknow.onesat;

import java.util.Arrays;
import java.util.HashSet;

public class AlphabetExtractor {

    public static HashSet<String> createOrdering(String alphabet, String separator) {
        return Arrays.stream(alphabet.split(separator))
            .map(String::trim)
            .filter(clause -> !clause.isEmpty())
            .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }
    
    public static HashSet<String> createOrdering(String input, String andOperator, String negativeOperator) {
        return LiteralParser.parse(input, andOperator, negativeOperator)
                    .stream()
                    .map(Literal::getAtom)
                    .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }
}
