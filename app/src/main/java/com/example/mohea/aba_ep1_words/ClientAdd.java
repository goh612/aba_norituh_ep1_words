package com.example.mohea.aba_ep1_words;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.listener.ClientListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.regex.Pattern;

public class ClientAdd {

    Context context;

    AlertDialog dialog;

    EditText etRegName,etRegAge;

    RadioButton rbRegMale,rbRegFemale;

    Button btnRegRegister,btnRegBack;

    ClientListener clientListener;

    public ClientAdd(Context context, ClientListener clientListener) {
        this.context = context;
        this.clientListener = clientListener;
    }

    public void clientAddDialogShow(){
        View view = LayoutInflater.from(context).inflate(R.layout.register_dialog,null);
        onBindAndListener(view);

        dialog = new AlertDialog.Builder(context).create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);
        dialog.setView(view);
        dialog.show();
    }

    public void startRegister(){

        String name = etRegName.getText().toString();
        String age = etRegAge.getText().toString();
        String gender = null;

        if(rbRegMale.isChecked()){
            gender = "m";
        }else if(rbRegFemale.isChecked()){
            gender = "f";
        }

        int intAge = Integer.parseInt(age);

        if(name==""){
            Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(age==""){
            Toast.makeText(context, "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(gender==null){
            Toast.makeText(context, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
        }else if(name.length()>10){
            Toast.makeText(context, "이름은 최대 10자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show();
        }else if(intAge>200){
            Toast.makeText(context, "나이가 기네스 기록보다 많습니다.", Toast.LENGTH_SHORT).show();
        }else{

            ClientSet clientSet = new ClientSet();
            clientSet.setName(name);
            clientSet.setAge(Integer.parseInt(age));
            clientSet.setGender(gender);

            boolean result = new SQLiteHelper(context).addClient(clientSet);

            if(result){
                dialog.cancel();
                clientListener.clientComplete();

            }else{
                Toast.makeText(context, "등록 실패...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    View.OnClickListener regClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_reg_register:
                    startRegister();
                    break;
                case R.id.btn_reg_back:
                    dialog.cancel();
                    break;

            }
        }
    };

    public void onBindAndListener(View view){
        etRegName = view.findViewById(R.id.et_reg_name);
        etRegAge = view.findViewById(R.id.et_reg_age);

        rbRegMale = view.findViewById(R.id.rb_reg_male);
        rbRegFemale = view.findViewById(R.id.rb_reg_female);

        btnRegRegister = view.findViewById(R.id.btn_reg_register);
        btnRegBack = view.findViewById(R.id.btn_reg_back);

        btnRegRegister.setOnClickListener(regClickListener);
        btnRegBack.setOnClickListener(regClickListener);
    }

}
