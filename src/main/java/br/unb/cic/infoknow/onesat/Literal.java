package br.unb.cic.infoknow.onesat;

import java.util.HashSet;

public class Literal {
    private String atom;
    private boolean isNegated;

    public static boolean isSatisfiable(HashSet<Literal> literals){
        for (Literal literal : literals) {
            Literal neg = literal.negate();
            if (literals.contains(neg)) {
                return false;
            }
        }
        return true;
    }

    public static boolean implies(HashSet<Literal> antecedent, HashSet<Literal> consequent) {
        for (Literal literal : consequent) {
            if (!antecedent.contains(literal)) {
                return false;
            }
        }
        return true;
    }

    public String getAtom() {
        return atom;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public boolean isEmpty() {
        return this.atom.isBlank();
    }

    public Literal(String clause, String andOperator, String negativeOperator) {
        String clean = cleanAnd(clause, andOperator);
        this.isNegated = isNegated(clean, negativeOperator);
        this.atom = clean.replace(negativeOperator, "");        
    }

    private String cleanAnd(String clause, String andOperator) {
        return clause
            .replaceAll("\\s+", "")
            .replace(andOperator, "")
            .trim();
    }

    public boolean isNegated(String clause, String nevativeOperator) {
        return clause.startsWith(nevativeOperator);
    }

    public Literal negate() {
        return new Literal(atom, !isNegated);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Literal literal)) {
            return false;
        }
        return isNegated == literal.isNegated && atom.equals(literal.atom);
    }

    @Override
    public int hashCode() {
        int result = atom.hashCode();
        result = 31 * result + Boolean.hashCode(isNegated);
        return result;
    }

    private Literal(String atom, boolean isNegated) {
        this.atom = atom;
        this.isNegated = isNegated;
    }
}
