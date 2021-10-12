package usecase.sort.comparator;

import domain.entity.Ratable;

import java.util.Comparator;

public class RatingComparator implements Comparator<Ratable> {
    @Override
    public int compare(Ratable o1, Ratable o2) {
        float r1 = o1.getAvgRating();
        float r2 = o2.getAvgRating();

        if(r1<r2){return -1;} else if(r1 == r2){return 0;}

        return 1;
    }
}
