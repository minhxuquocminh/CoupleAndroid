package com.example.couple.Model.Time.Cycle;

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

    public Branch(int position) {
        this.position = position == Const.EMPTY_VALUE ? position : position % 12;
        this.name = position == Const.EMPTY_VALUE ? "" : TimeInfo.EARTHLY_BRANCHES.get(position % 12);
    }

    private List<Integer> getCompatibleBranches() {
        List<Integer> results = new ArrayList<>();
        for (int i = 4; i < 12; i += 4) {
            results.add((position + i) % 12);
        }
        return results;
    }

    private List<Integer> getIncompatibleBranches() {
        List<Integer> results = new ArrayList<>();
        for (int i = 3; i < 12; i += 3) {
            results.add((position + i) % 12);
        }
        return results;
    }

    public String getStatus(int couple, int currentYear) {
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

    // chỉ lấy từ năm 1900 -> 1999
    public List<YearCycle> getYearCycles() {
        List<YearCycle> results = new ArrayList<>();
        for (int i = position; i <= 99; i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle);
        }
        return results;
    }

    public List<Integer> getIntYearCycles() {
        List<Integer> results = new ArrayList<>();
        for (int i = position; i <= 99; i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle.getCoupleInt());
        }
        return results;
    }

    public List<YearCycle> getYearCycles(int currentYear) {
        List<YearCycle> results = new ArrayList<>();
        for (int i = position; i <= 100 + (currentYear % 100); i += 12) {
            YearCycle yearCycle = new YearCycle(i);
            results.add(yearCycle);
        }
        return results;
    }

    public List<YearCycle> getCompatibleYearCycles(int currentYear) {
        List<YearCycle> results = new ArrayList<>();
        List<Integer> compatibles = new ArrayList<>();
        compatibles.add(position);
        compatibles.addAll(getCompatibleBranches());
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
        List<YearCycle> results = new ArrayList<>();
        List<Integer> incompatibles = new ArrayList<>();
        incompatibles.add(position);
        incompatibles.addAll(getIncompatibleBranches());
        Collections.sort(incompatibles);
        for (int branch : incompatibles) {
            for (int i = branch; i <= 100 + (currentYear % 100); i += 12) {
                YearCycle yearCycle = new YearCycle(i);
                results.add(yearCycle);
            }
        }
        return results;
    }

    public static List<Branch> getYearBranchList(int couple, int currentYear) {
        List<Branch> branchList = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            int custom = i * 100 + couple;
            if (i == 1 && couple > currentYear % 100) break;
            branchList.add(new Branch(custom % 12));
        }
        return branchList;
    }

    public int getDistance(Branch branch) {
        int distance = branch.getPosition() - position;
        if (Math.abs(distance) < 6) return distance;
        if (distance < 0) return 12 + distance;
        return -(12 - distance);
    }

    public Branch plusDays(int numberOfDays) {
        int new_index = position + numberOfDays < 0 ?
                12 * (Math.abs(numberOfDays / 12) + 1) + position + numberOfDays : (position + numberOfDays) % 60;
        return new Branch(new_index);
    }

    public String show() {
        return name + " (" + position + ")";
    }

    public static Branch getEmpty() {
        return new Branch(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return position == Const.EMPTY_VALUE || name.equals("");
    }
}
