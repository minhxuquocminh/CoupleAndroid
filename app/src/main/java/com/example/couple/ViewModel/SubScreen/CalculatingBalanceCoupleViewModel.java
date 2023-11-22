package com.example.couple.ViewModel.SubScreen;

import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleView;

import java.util.List;

public class CalculatingBalanceCoupleViewModel {
    CalculatingBalanceCoupleView viewCalculating;

    public CalculatingBalanceCoupleViewModel(CalculatingBalanceCoupleView viewCalculating) {
        this.viewCalculating = viewCalculating;
    }

    public void CalculateBalance2D(String firstNumber, String secondNumber) {
        if (firstNumber.length() == 0) {
            viewCalculating.ShowError("Bạn chưa nhập số thứ nhất!");
        } else if (secondNumber.length() == 0) {
            viewCalculating.ShowError("Bạn chưa nhập số thứ hai!");
        } else if (Integer.parseInt(firstNumber) < 0 || Integer.parseInt(firstNumber) > 99) {
            viewCalculating.ShowError("Giới hạn của số thứ nhất là 0 - 99.");
        } else if (Integer.parseInt(secondNumber) < 0 || Integer.parseInt(secondNumber) > 99) {
            viewCalculating.ShowError("Giới hạn của số thứ hai là 0 - 99.");
        } else {
            int first = Integer.parseInt(firstNumber);
            int second = Integer.parseInt(secondNumber);
            BCouple BCouple1 = new BCouple(first / 10, first % 10);
            BCouple BCouple2 = new BCouple(second / 10, second % 10);

            List<BCouple> balanceBCouples = BCoupleBridgeHandler.GetBalanceCouples(BCouple1, BCouple2);
            int count = 0;
            String result = "Các bộ số tính toán được: \n";
            for (BCouple cp : balanceBCouples) {
                count++;
                result += "Bộ " + count + " : " + cp.showDot() + "\n";
            }
            result += "{" + BCouple1.plus(BCouple2) + "," + BCouple1.sub(BCouple2) + "}\n";
            viewCalculating.ShowResult(result);
        }
    }

}
