package com.example.couple.View.PredictionBridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.Model.Support.NumberArray;
import com.example.couple.R;
import com.example.couple.ViewModel.PredictionBridge.WeeklyPredictionBridgeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class WeeklyPredictionBridgeActivity extends AppCompatActivity implements WeeklyPredictionBridgeView {
    CheckBox cboSelectAll;
    CheckBox cboT2;
    CheckBox cboT3;
    CheckBox cboT4;
    CheckBox cboT5;
    CheckBox cboT6;
    CheckBox cboT7;
    CheckBox cboCN;
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

    WeeklyPredictionBridgeViewModel viewModel;
    boolean isAdd = true;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_prediction_bridge);

        cboSelectAll = findViewById(R.id.cboSelectAll);
        cboT2 = findViewById(R.id.cboT2);
        cboT3 = findViewById(R.id.cboT3);
        cboT4 = findViewById(R.id.cboT4);
        cboT5 = findViewById(R.id.cboT5);
        cboT6 = findViewById(R.id.cboT6);
        cboT7 = findViewById(R.id.cboT7);
        cboCN = findViewById(R.id.cboCN);
        edtName = findViewById(R.id.edtName);
        edtRuns = findViewById(R.id.edtRuns);
        edtLosts = findViewById(R.id.edtLosts);
        edtSets = findViewById(R.id.edtSets);
        edtTriads = findViewById(R.id.edtTriads);
        edtWinningTime = findViewById(R.id.edtRunningTime);
        edtSums = findViewById(R.id.edtSums);
        edtTouchs = findViewById(R.id.edtTouchs);
        edtHeads = findViewById(R.id.edtHeads);
        edtTails = findViewById(R.id.edtTails);
        edtAdds = findViewById(R.id.edtAdds);
        edtRemoves = findViewById(R.id.edtRemoves);
        edtInfo = findViewById(R.id.edtInfo);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        viewModel = new WeeklyPredictionBridgeViewModel(this, this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION", -1);
        if (position != -1) {
            isAdd = false;
            Prediction pb = (Prediction) intent.getSerializableExtra("PB");
            id = pb.getId();
            edtName.setText(pb.getName());
            edtName.setSelection(edtName.length());
            setCheckbox(pb.getTime().trim());
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

        cboSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (int i = 1; i <= 7; i++) {
                        setCheckedCbo(i);
                    }
                } else {
                    cboClearCheck();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String time = getStringCheckedCbo();
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
                boolean checkEmpty = name.isEmpty() || time.isEmpty() || (sets.isEmpty()
                        && sums.isEmpty() && touchs.isEmpty() && heads.isEmpty()
                        && tails.isEmpty() && adds.isEmpty());
                if (checkEmpty) {
                    Toast.makeText(WeeklyPredictionBridgeActivity.this,
                            "Vui lòng nhập đầy đủ các trường cần thiết !", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(WeeklyPredictionBridgeActivity.this,
                                "Vui lòng nhập đúng định dạng của các số !", Toast.LENGTH_SHORT).show();
                    } else {
                        int runsInt = runs.isEmpty() ? -1 : Integer.parseInt(runs);
                        int lostsInt = losts.isEmpty() ? -1 : Integer.parseInt(losts);
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
                        FirebaseBase firebaseBase = new FirebaseBase("weekly");
                        String newId = isAdd ? firebaseBase.getKey() : id;
                        Prediction pb = new Prediction(newId, name, time, winningTime,
                                runsInt, lostsInt, triadsStr, numberArray, info, dateBase, 1);
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
        return !data.trim().isEmpty() && numbers.isEmpty();
    }

    private void setCheckbox(String checkboxList) {
        if (checkboxList.isEmpty()) return;
        if (checkboxList.equals("all")) {
            cboSelectAll.setChecked(true);
            for (int i = 1; i <= 7; i++) {
                setCheckedCbo(i);
            }
        } else {
            String[] arr = checkboxList.split("-");

            cboClearCheck();
            for (String ptu : arr) {
                int stt = getDayOfWeek(ptu);
                setCheckedCbo(stt);
            }
        }
    }

    private String getStringCheckedCbo() {
        String rs = "";
        int count = 0;
        if (cboT2.isChecked()) {
            rs += "T2-";
            count++;
        }
        if (cboT3.isChecked()) {
            rs += "T3-";
            count++;
        }
        if (cboT4.isChecked()) {
            rs += "T4-";
            count++;
        }
        if (cboT5.isChecked()) {
            rs += "T5-";
            count++;
        }
        if (cboT6.isChecked()) {
            rs += "T6-";
            count++;
        }
        if (cboT7.isChecked()) {
            rs += "T7-";
            count++;
        }
        if (cboCN.isChecked()) {
            rs += "CN-";
            count++;
        }
        if (!rs.isEmpty()) {
            rs = rs.substring(0, rs.length() - 1);
        }
        return count == 7 ? "all" : rs;
    }

    private int getDayOfWeek(String day) {
        return (day.trim().charAt(1) == 'N') ? 1 : Integer.parseInt(day.trim().charAt(1) + "");
    }

    private void cboClearCheck() {
        cboCN.setChecked(false);
        cboT2.setChecked(false);
        cboT3.setChecked(false);
        cboT4.setChecked(false);
        cboT5.setChecked(false);
        cboT6.setChecked(false);
        cboT7.setChecked(false);
    }

    private void setCheckedCbo(int number) {
        if (number == 1) {
            cboCN.setChecked(true);
        }
        if (number == 2) {
            cboT2.setChecked(true);
        }
        if (number == 3) {
            cboT3.setChecked(true);
        }
        if (number == 4) {
            cboT4.setChecked(true);
        }
        if (number == 5) {
            cboT5.setChecked(true);
        }
        if (number == 6) {
            cboT6.setChecked(true);
        }
        if (number == 7) {
            cboT7.setChecked(true);
        }

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePredictionBridgeSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void addPredictionBridgeSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
