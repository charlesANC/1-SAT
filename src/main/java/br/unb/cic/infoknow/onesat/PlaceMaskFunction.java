package br.unb.cic.infoknow.onesat;

public class PlaceMaskFunction {

    private Ranking ranking;

    public PlaceMaskFunction(Ranking ranking) {
        this.ranking = ranking;
    }

    public Integer apply(String clause) {
        Integer order = this.ranking.getOrder(clause);
        if (order == null) {
            throw new RuntimeException("Clause is empty: " + clause);
        }

        Double place = Math.pow(2, this.ranking.getOrder(clause));
        if (place > Integer.MAX_VALUE) {
            throw new RuntimeException("Place value exceeds Integer.MAX_VALUE");
        }
        return place.intValue();
    }
}
