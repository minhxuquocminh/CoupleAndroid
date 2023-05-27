package com.example.couple.Custom.Old.Display;

import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public class ShowLottery {

    public static String showResults(List<Lottery> lotteries) {
        String show="";
        for (Lottery lottery : lotteries) {
            show+="------------XSMB "+ lottery.getDateBase()+"/"+ lottery.getDateBase().getMonth()+"/"+
                    lottery.getDateBase().getYear()+"------------\n";
            List<String> resultSet =lottery.getLottery();
            show+="             "+resultSet.get(0)+"             \n";
            show+="             "+resultSet.get(1)+"             \n";
            show+="       "+resultSet.get(2)+"       "+resultSet.get(3)+"       \n";
            show+="    "+resultSet.get(4)+"    "+resultSet.get(5)+"    "+resultSet.get(6)+"    \n";
            show+="    "+resultSet.get(7)+"    "+resultSet.get(8)+"    "+resultSet.get(9)+"    \n";
            show+="   "+resultSet.get(10)+"   "+resultSet.get(11)+"   "+resultSet.get(12)+"   "+resultSet.get(13)+"   \n";
            show+="     "+resultSet.get(14)+"    "+resultSet.get(15)+"    "+resultSet.get(16)+"    \n";
            show+="     "+resultSet.get(17)+"    "+resultSet.get(18)+"    "+resultSet.get(19)+"    \n";
            show+="      "+resultSet.get(20)+"    "+resultSet.get(21)+"     "+resultSet.get(22)+"    \n";
            show+="     "+resultSet.get(23)+"    "+resultSet.get(24)+"    "+resultSet.get(25)+"    "+resultSet.get(26)+"     \n";
        }
        return show;
    }

}
