package br.unb.cic.infoknow.onesat;

public record Formula(Integer positive, Integer negative) {

    public static Formula empty() {
        return new Formula(0, 0);
    }

    public boolean isSatisfiable() {
        return (positive & negative) == 0;
    }

    public boolean isConjunctionSatisfiable(Formula other) {
        return ((this.positive & other.negative) + (this.negative & other.positive)) == 0;
    }

    public boolean entails(Formula other) {
        Integer newPositive = this.positive & other.positive;
        Integer newNegative = this.negative & other.negative;
        return other.positive.equals(newPositive) && other.negative.equals(newNegative);
    }

    public Formula conjunction(Formula other) {
        return new Formula(this.positive | other.positive, this.negative | other.negative);
    }
}
