package com.example.couple.Model.DateTime.Date.Cycle;

import com.example.couple.Custom.Const.Const;

import lombok.Getter;

/**
 * class sexagenary cycle
 */

@Getter
public class Cycle {
    int position;
    String name;
    Stem stem;
    Branch branch;

    private Cycle() {
        this.position = Const.EMPTY_VALUE;
        this.name = "";
        this.stem = Stem.getEmpty();
        this.branch = Branch.getEmpty();
    }

    public Cycle(int position) {
        if (position == Const.EMPTY_VALUE) {
            new Cycle();
            return;
        }

        this.position = position % 60;
        this.stem = new Stem(position % 10);
        this.branch = new Branch(position % 12);
        this.name = stem.getName() + " " + branch.getName();
    }

    public static Cycle getEmpty() {
        return new Cycle();
    }

    public boolean isEmpty() {
        return position == Const.EMPTY_VALUE || name.isEmpty() || stem.isEmpty() || branch.isEmpty();
    }


    public static Cycle getByName(String cycleName) {
        if (cycleName.isEmpty()) return Cycle.getEmpty();
        String[] cycleArr = cycleName.split(" ");
        String stemName = cycleArr[0].trim();
        String branchName = cycleArr[1].trim();
        Stem stem = Stem.getByName(stemName);
        Branch branch = Branch.getByName(branchName);
        return Cycle.getByStemAndBranch(stem, branch);
    }

    public static Cycle getByStemAndBranch(Stem stem, Branch branch) {
        int x = -1;
        int stemPos = stem.getPosition();
        int branchPos = branch.getPosition();
        for (int i = 0; i < 6; i++) {
            if ((10 * i + stemPos - branchPos) % 12 == 0 && 10 * i + stemPos < 60) {
                x = i;
                break;
            }
        }
        if (x == -1) return Cycle.getEmpty();
        return new Cycle(10 * x + stemPos);
    }

    public Cycle addDays(int numberOfDays) {
        if (this.isEmpty()) return Cycle.getEmpty();
        int new_index = numberOfDays % 60 + position < 0 ?
                60 + numberOfDays % 60 + position : numberOfDays % 60 + position;
        return new Cycle(new_index);
    }

    public int getCoupleInt() {
        if (this.isEmpty()) return Const.EMPTY_VALUE;
        return stem.getPosition() * 10 + branch.getPosition() % 10;
    }

}
