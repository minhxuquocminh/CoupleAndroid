package com.example.couple.View.PredictionBridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.Model.Support.NumberArray;
import com.example.couple.R;
import com.example.couple.ViewModel.PredictionBridge.MonthlyPredictionBridgeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MonthlyPredictionBridgeActivity extends AppCompatActivity implements MonthlyPredictionBridgeView {
    TextInputEditText edtStartDate;
    TextInputEditText edtEndDate;
    TextInputEditText edtName;
    TextInputEditText edtRuns;
    TextInputEditText edtLosts;
    TextInputEditText edtSets;
    TextInputEditText edtTriads;
    TextInputEditText edtWinningTime;
    TextInputEditText edtSums;
    TextInputEditText edtTouchs;
    TextInputEditText edtHeads;
    TextInputEditText edtTails;
    TextInputEditText edtAdds;
    TextInputEditText edtRemoves;
    TextInputEditText edtInfo;
    Button btnSave;
    Button btnCancel;

    MonthlyPredictionBridgeViewModel viewModel;
    boolean isAdd = true;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_prediction_bridge);

        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        edtName = findViewById(R.id.edtName);
        edtRuns = findViewById(R.id.edtRuns);
        edtLosts = findViewById(R.id.edtLosts);
        edtSets = findViewById(R.id.edtSets);
        edtTriads = findViewById(R.id.edtTriads);
        edtWinningTime = findViewById(R.id.edtWinningTime);
        edtSums = findViewById(R.id.edtSums);
        edtTouchs = findViewById(R.id.edtTouchs);
        edtHeads = findViewById(R.id.edtHeads);
        edtTails = findViewById(R.id.edtTails);
        edtAdds = findViewById(R.id.edtAdds);
        edtRemoves = findViewById(R.id.edtRemoves);
        edtInfo = findViewById(R.id.edtInfo);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        viewModel = new MonthlyPredictionBridgeViewModel(this, this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION", -1);
        if (position != -1) {
            isAdd = false;
            Prediction pb = (Prediction) intent.getSerializableExtra("PB");
            id = pb.getId();
            edtName.setText(pb.getName());
            String timeArr[] = pb.getTime().trim().split("-");
            edtStartDate.setText(timeArr[0].trim());
            edtStartDate.setSelection(edtStartDate.length());
            String endDate = timeArr[1].trim();
            edtEndDate.setText(endDate.substring(0, endDate.length() - 1));
            String runsStr = pb.getRuns() < 0 ? "" : pb.getRuns() + "";
            edtRuns.setText(runsStr);
            String lostsStr = pb.getRuns() < 0 ? "" : pb.getRuns() + "";
            edtLosts.setText(lostsStr);
            edtTriads.setText(pb.getTriads());
            edtWinningTime.setText(pb.getWinningTime());
            NumberArray numberArray = pb.getNumberArray();
            edtSets.setText(numberArray.getSets());
            edtSums.setText(numberArray.getSums());
            edtTouchs.setText(numberArray.getTouchs());
            edtHeads.setText(numberArray.getHeads());
            edtTails.setText(numberArray.getTails());
            edtAdds.setText(numberArray.getAdds());
            edtRemoves.setText(numberArray.getRemoves());
            edtInfo.setText(pb.getInfo());
        } else {
            isAdd = true;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String startDate = edtStartDate.getText().toString().trim();
                String endDate = edtEndDate.getText().toString().trim();
                String runs = edtRuns.getText().toString().trim();
                String losts = edtLosts.getText().toString().trim();
                String triads = edtTriads.getText().toString().trim();
                String winningTime = edtWinningTime.getText().toString().trim();
                String sets = edtSets.getText().toString().trim();
                String sums = edtSums.getText().toString().trim();
                String touchs = edtTouchs.getText().toString().trim();
                String heads = edtHeads.getText().toString().trim();
                String tails = edtTails.getText().toString().trim();
                String adds = edtAdds.getText().toString().trim();
                String removes = edtRemoves.getText().toString().trim();
                String info = edtInfo.getText().toString().trim();
                boolean checkEmpty = name.equals("") || startDate.equals("") || endDate.equals("")
                        || (sets.equals("") && sums.equals("") && touchs.equals("") && heads.equals("")
                        && tails.equals("") && adds.equals(""));
                if (checkEmpty) {
                    Toast.makeText(MonthlyPredictionBridgeActivity.this,
                            "Vui lòng nhập đầy đủ các trường cần thiết!", Toast.LENGTH_SHORT).show();
                } else {
                    List<Integer> triadList = NumberBase.verifyNumberArray(triads, 1);
                    List<Integer> setList = NumberBase.verifyNumberArray(sets, 2);
                    List<Integer> sumList = NumberBase.verifyNumberArray(sums, 1);
                    List<Integer> touchList = NumberBase.verifyNumberArray(touchs, 1);
                    List<Integer> headList = NumberBase.verifyNumberArray(heads, 1);
                    List<Integer> tailList = NumberBase.verifyNumberArray(tails, 1);
                    List<Integer> addList = NumberBase.verifyNumberArray(adds, 2);
                    List<Integer> removeList = NumberBase.verifyNumberArray(removes, 2);

                    boolean checkVerify = checkEmptyListFromStringHaveData(triads, triadList) ||
                            checkEmptyListFromStringHaveData(sets, setList) ||
                            checkEmptyListFromStringHaveData(sums, sumList) ||
                            checkEmptyListFromStringHaveData(touchs, touchList) ||
                            checkEmptyListFromStringHaveData(heads, headList) ||
                            checkEmptyListFromStringHaveData(tails, tailList) ||
                            checkEmptyListFromStringHaveData(adds, addList) ||
                            checkEmptyListFromStringHaveData(removes, removeList);
                    if (checkVerify) {
                        Toast.makeText(MonthlyPredictionBridgeActivity.this,
                                "Vui lòng nhập đúng định dạng của các số !", Toast.LENGTH_SHORT).show();
                    } else {
                        int runsInt = runs.equals("") ? -1 : Integer.parseInt(runs);
                        int lostsInt = losts.equals("") ? -1 : Integer.parseInt(losts);
                        String setsStr = NumberBase.showNumbers(setList, " ");
                        String sumsStr = NumberBase.showNumbers(sumList, " ");
                        String touchsStr = NumberBase.showNumbers(touchList, " ");
                        String headsStr = NumberBase.showNumbers(headList, " ");
                        String tailsStr = NumberBase.showNumbers(tailList, " ");
                        String addsStr = NumberBase.showNumbers(addList, " ");
                        String removesStr = NumberBase.showNumbers(removeList, " ");
                        String triadsStr = NumberBase.showNumbers(triadList, " ");
                        NumberArray numberArray = new NumberArray(setsStr, sumsStr,
                                touchsStr, headsStr, tailsStr, addsStr, removesStr);
                        DateBase dateBase = DateBase.getCurrentDate();
                        FirebaseBase firebaseBase = new FirebaseBase("monthly");
                        String newId = isAdd ? firebaseBase.getKey() : id;
                        Prediction pb = new Prediction(newId, name,
                                startDate + " - " + endDate, winningTime, runsInt,
                                lostsInt, triadsStr, numberArray, info, dateBase, 2);
                        if (isAdd) {
                            viewModel.addPredictionBridge(pb);
                        } else {
                            viewModel.updatePredictionBridge(pb, id);
                        }
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean checkEmptyListFromStringHaveData(String data, List<Integer> numbers) {
        return !data.trim().equals("") && numbers.isEmpty();
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddPredictionBridgeSuccess() {
        finish();
    }

    @Override
    public void UpdatePredictionBridgeSuccess() {
        finish();
    }

}
