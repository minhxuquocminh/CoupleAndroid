package com.example.couple.Model.DateTime.Date.Cycle;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * class earthly branches
 */

@Getter
public class Branch {
    int position;
    String name;

    private Branch() {
        this.position = Const.EMPTY_VALUE;
        this.name = "";
    }

    public Branch(int position) {
        if (position == Const.EMPTY_VALUE) {
            new Branch();
            return;
        }

        this.position = position % 12;
        this.name = TimeInfo.EARTHLY_BRANCHES.get(position % 12);
    }

    public static Branch getByName(String branchName) {
        if (branchName.isEmpty()) return new Branch();
        int position = TimeInfo.EARTHLY_BRANCHES.indexOf(branchName);
        if (position < 0) return Branch.getEmpty();
        return new Branch(position);
    }

    public boolean equalsBranch(Branch branch) {
        return this.position == branch.getPosition();
    }

    public static boolean isSameBranch(int firstCouple, int secondCouple, int currentYear) {
        if (secondCouple < currentYear % 100)
            return firstCouple % 12 == secondCouple % 12 || firstCouple % 12 == (secondCouple + 4) % 12;
        if (firstCouple < currentYear % 100)
            return firstCouple % 12 == secondCouple % 12 || secondCouple % 12 == (firstCouple + 4) % 12;
        return firstCouple % 12 == secondCouple % 12;
    }

    public String getStatus(int couple, int currentYear) {
        if (this.isEmpty()) return Const.EMPTY;
        List<YearCycle> compatible = getCompatibleYearCycles(currentYear);
        List<YearCycle> incompatible = getIncompatibleYearCycles(currentYear);
        String status = "";
        for (YearCycle cycle : compatible) {
            if (cycle.getYear() % 100 == couple) {
                status += "H";
            }
        }
        for (YearCycle cycle : incompatible) {
            if (cycle.getYear() % 100 == couple) {
                status += "K";
            }
        }
        return status;
    }

    public List<Integer> getTailsOfYear() {
        if (this.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = position; i <= 99; i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle.getCoupleInt());
        }
        return results;
    }

    public List<Integer> getTailsOfYear(int currentYear) {
        if (this.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = position; i <= 100 + (currentYear % 100); i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle.getCoupleInt());
        }
        return results;
    }

    public List<Integer> getReverseTailsOfYear(int currentYear) {
        if (this.isEmpty()) return new ArrayList<>();
        List<Integer> tails = getTailsOfYear(currentYear);
        List<Integer> results = new ArrayList<>();
        for (int tail : tails) {
            results.add(CoupleBase.reverse(tail));
        }
        return results;
    }

    // chỉ lấy từ năm 1900 -> 1999
    public List<YearCycle> getYearCycles() {
        if (this.isEmpty()) return new ArrayList<>();
        List<YearCycle> results = new ArrayList<>();
        for (int i = position; i <= 99; i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle);
        }
        return results;
    }

    public List<YearCycle> getYearCycles(int currentYear) {
        if (this.isEmpty()) return new ArrayList<>();
        List<YearCycle> results = new ArrayList<>();
        for (int i = position; i <= 100 + (currentYear % 100); i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle);
        }
        return results;
    }

    public List<YearCycle> getCompatibleYearCycles(int currentYear) {
        if (this.isEmpty()) return new ArrayList<>();
        List<YearCycle> results = new ArrayList<>();
        List<Integer> compatibles = new ArrayList<>();
        compatibles.add(position);
        compatibles.addAll(getCompatibleNumbers());
        Collections.sort(compatibles);
        for (int branch : compatibles) {
            for (int i = branch; i <= 100 + (currentYear % 100); i += 12) {
                YearCycle yearCycle = new YearCycle(i % 100);
                results.add(yearCycle);
            }
        }
        return results;
    }

    public List<YearCycle> getIncompatibleYearCycles(int currentYear) {
        if (this.isEmpty()) return new ArrayList<>();
        List<YearCycle> results = new ArrayList<>();
        List<Integer> incompatibles = new ArrayList<>();
        incompatibles.add(position);
        incompatibles.addAll(getIncompatibleNumbers());
        Collections.sort(incompatibles);
        for (int branch : incompatibles) {
            for (int i = branch; i <= 100 + (currentYear % 100); i += 12) {
                YearCycle yearCycle = new YearCycle(i);
                results.add(yearCycle);
            }
        }
        return results;
    }

    // yearCouple là đuôi của năm tính từ 1900
    public static List<Branch> getBranchsByYear(int yearCouple, int currentYear) {
        List<Branch> branches = new ArrayList<>();
        branches.add(new Branch(yearCouple % 12));
        if (yearCouple <= currentYear % 100) {
            branches.add(new Branch((yearCouple + 4) % 12));
        }
        return branches;
    }

    // yearCouple là đuôi của năm tính từ 1900
    public static List<Branch> getReverseBranchsByYear(int yearCouple, int currentYear) {
        return getBranchsByYear(CoupleBase.reverse(yearCouple), currentYear);
    }


    public boolean isYearBranch(int yearCouple, int currentYear) {
        if (this.isEmpty()) return false;
        if (position == yearCouple % 12) return true;
        if (yearCouple <= currentYear % 100) {
            return position == (yearCouple + 4) % 12;
        }
        return false;
    }

    public int getDistance(Branch branch) {
        if (this.isEmpty()) return Const.EMPTY_VALUE;
        int distance = branch.getPosition() - position;
        if (Math.abs(distance) < 6) return distance;
        if (distance < 0) return 12 + distance;
        return -(12 - distance);
    }

    public Branch addDays(int numberOfDays) {
        if (this.isEmpty()) return Branch.getEmpty();
        int new_index = numberOfDays % 12 + position < 0 ?
                12 + numberOfDays % 12 + position : numberOfDays % 12 + position;
        return new Branch(new_index);
    }

    public String show() {
        if (this.isEmpty()) return Const.EMPTY;
        return name + " (" + position + ")";
    }

    public static Branch getEmpty() {
        return new Branch();
    }

    public boolean isEmpty() {
        return position == Const.EMPTY_VALUE || name.isEmpty();
    }

    private List<Integer> getCompatibleNumbers() {
        if (this.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = 4; i < 12; i += 4) {
            results.add((position + i) % 12);
        }
        return results;
    }

    private List<Integer> getIncompatibleNumbers() {
        if (this.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = 3; i < 12; i += 3) {
            results.add((position + i) % 12);
        }
        return results;
    }
}
