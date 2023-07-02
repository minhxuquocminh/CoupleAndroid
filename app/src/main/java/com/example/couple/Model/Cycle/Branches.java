package com.example.couple.Model.Cycle;

import com.example.couple.Custom.Const.TimeInfo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * class earthly branches
 */

@Getter
public class Branches {
    int position;
    String name;

    public Branches(int position, String name) {
        this.position = position % 12;
        this.name = name;
    }

    public List<Branches> getCompatibleBranches() {
        List<Branches> results = new ArrayList<>();
        for (int i = 4; i < 12; i += 4) {
            int new_position = (position + i) % 12;
            Branches branches = new Branches(new_position, TimeInfo.EARTHLY_BRANCHES.get(new_position));
            results.add(branches);
        }
        return results;
    }

    public List<Branches> getIncompatibleBranches() {
        List<Branches> results = new ArrayList<>();
        for (int i = 3; i < 12; i += 3) {
            int new_position = (position + i) % 12;
            Branches branches = new Branches(new_position, TimeInfo.EARTHLY_BRANCHES.get(new_position));
            results.add(branches);
        }
        return results;
    }

    public String showCompatibleBranches() {
        List<Branches> compatibles = getCompatibleBranches();
        String show = "";
        for (Branches branches : compatibles) {
            show += branches.getPosition() % 10 + " ";
        }
        return show.trim();
    }

    public String showIncompatibleBranches() {
        List<Branches> incompatibles = getIncompatibleBranches();
        String show = "";
        for (Branches branches : incompatibles) {
            show += branches.getPosition() % 10 + " ";
        }
        return show.trim();
    }

    public static Branches getEmpty() {
        return new Branches(-1, "");
    }

    public boolean isEmpty() {
        return position == -1 || name.equals("");
    }
}
