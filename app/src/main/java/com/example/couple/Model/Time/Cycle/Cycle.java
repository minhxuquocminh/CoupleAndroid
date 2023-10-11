package com.example.couple.Model.Time.Cycle;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;

import lombok.Getter;

/**
 * class sexagenary cycle
 */

@Getter
public class Cycle {
    int position;
    String cycle;
    Stem stem;
    Branch branch;

    public static Cycle getEmpty() {
        return new Cycle(Const.EMPTY_VALUE, "", Stem.getEmpty(), Branch.getEmpty());
    }

    public boolean isEmpty() {
        return stem.isEmpty() || branch.isEmpty();
    }

    private Cycle(int position, String cycle, Stem stem, Branch branch) {
        this.position = position;
        this.cycle = cycle;
        this.stem = stem;
        this.branch = branch;
    }

    public Cycle(Stem stem, Branch branch) {
        int x = -1;
        int st = stem.getPosition();
        int br = branch.getPosition();
        for (int i = 0; i < 6; i++) {
            if ((10 * i + st - br) % 12 == 0 && 10 * i + st < 60) {
                x = i;
                break;
            }
        }
        this.position = 10 * x + st;
        this.cycle = stem.getName() + " " + branch.getName();
        this.stem = stem;
        this.branch = branch;
    }

    public static Cycle getCycle(String cycleName) {
        if (cycleName.equals("")) return Cycle.getEmpty();
        String[] cycleArr = cycleName.split(" ");
        String stemsName = cycleArr[0].trim();
        String branchesName = cycleArr[1].trim();
        int stemsPos = TimeInfo.HEAVENLY_STEMS.indexOf(stemsName);
        int branchesPos = TimeInfo.EARTHLY_BRANCHES.indexOf(branchesName);
        Stem stem = new Stem(stemsPos);
        Branch branch = new Branch(branchesPos);
        return new Cycle(stem, branch);
    }

    public static Cycle getCycle(int position) {
        if (position < 0) return Cycle.getEmpty();
        int stemsPos = position % 10;
        int branchesPos = position % 12;
        Stem stem = new Stem(stemsPos);
        Branch branch = new Branch(branchesPos);
        return new Cycle(stem, branch);
    }

}
