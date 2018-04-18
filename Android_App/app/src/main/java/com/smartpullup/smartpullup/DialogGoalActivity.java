package com.smartpullup.smartpullup;

import android.app.Activity;
import android.app.Dialog;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogGoalActivity extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button goBtnn, cancelButton;
    private EditText goalPullUp_edit;
    public String goal;

    public DialogGoalActivity(Activity a){
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_goal);

        goBtnn = (Button) findViewById(R.id.btn_go);
        cancelButton = (Button) findViewById(R.id.btn_cancel);
        goalPullUp_edit = (EditText) findViewById(R.id.edit_goalPullUps);

        goBtnn.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:

                Bundle bundle = new Bundle();
                bundle.putString("edttext", goalPullUp_edit.getText().toString());
                // set Fragmentclass Arguments
                ExerciseFragment fragobj = new ExerciseFragment();
                fragobj.setArguments(bundle);

                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
