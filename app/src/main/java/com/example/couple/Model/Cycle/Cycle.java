package com.example.couple.Model.Cycle;

import com.example.couple.Custom.Const.TimeInfo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * class sexagenary cycle
 */

@Getter
public class Cycle {
    int position;
    String cycle;
    Stems stems;
    Branches branches;

    public static Cycle getEmpty() {
        return new Cycle(-1, "", Stems.getEmpty(), Branches.getEmpty());
    }

    public boolean isEmpty() {
        return stems.isEmpty() || branches.isEmpty();
    }

    private Cycle(int position, String cycle, Stems stems, Branches branches) {
        this.position = position;
        this.cycle = cycle;
        this.stems = stems;
        this.branches = branches;
    }

    public Cycle(Stems stems, Branches branches) {
        int x = -1;
        int st = stems.getPosition();
        int br = branches.getPosition();
        for (int i = 0; i < 6; i++) {
            if ((10 * i + st - br) % 12 == 0 && 10 * i + st < 60) {
                x = i;
                break;
            }
        }
        this.position = 10 * x + st;
        this.cycle = stems.getName() + " " + branches.getName();
        this.stems = stems;
        this.branches = branches;
    }

    public static Cycle getCycle(String cycleName) {
        if (cycleName.equals("")) return Cycle.getEmpty();
        String cycleArr[] = cycleName.split(" ");
        String stemsName = cycleArr[0].trim();
        String branchesName = cycleArr[1].trim();
        int stemsPos = TimeInfo.HEAVENLY_STEMS.indexOf(stemsName);
        int branchesPos = TimeInfo.EARTHLY_BRANCHES.indexOf(branchesName);
        Stems stems = new Stems(stemsPos, stemsName);
        Branches branches = new Branches(branchesPos, branchesName);
        return new Cycle(stems, branches);
    }

    public static Cycle getCycle(int position) {
        if (position < 0) return Cycle.getEmpty();
        int stemsPos = position % 10;
        int branchesPos = position % 12;
        String stemsName = TimeInfo.HEAVENLY_STEMS.get(stemsPos);
        String branchesName = TimeInfo.EARTHLY_BRANCHES.get(branchesPos);
        Stems stems = new Stems(stemsPos, stemsName);
        Branches branches = new Branches(branchesPos, branchesName);
        return new Cycle(stems, branches);
    }

    public List<YearCycle> getCompatibleYearCyclesByBranches(int startYear, int endYear) {
        List<YearCycle> results = new ArrayList<>();
        List<Branches> compatibles = new ArrayList<>();
        compatibles.add(branches);
        compatibles.addAll(branches.getCompatibleBranches());
        for (Branches branches : compatibles) {
            for (int i = startYear; i <= endYear; i++) {
                YearCycle yearCycle = new YearCycle(i);
                if (branches.getPosition() == yearCycle.getCycle().getBranches().getPosition()) {
                    results.add(yearCycle);
                }
            }
        }
        return results;
    }

    public List<YearCycle> getIncompatibleYearCyclesByBranches(int startYear, int endYear) {
        List<YearCycle> results = new ArrayList<>();
        List<Branches> incompatibles = new ArrayList<>();
        incompatibles.add(branches);
        incompatibles.addAll(branches.getIncompatibleBranches());
        for (Branches branches : incompatibles) {
            for (int i = startYear; i <= endYear; i++) {
                YearCycle yearCycle = new YearCycle(i);
                if (branches.getPosition() == yearCycle.getCycle().getBranches().getPosition()) {
                    results.add(yearCycle);
                }
            }
        }
        return results;
    }

}
