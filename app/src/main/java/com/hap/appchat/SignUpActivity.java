package com.hap.appchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hap.appchat.Model.Users;
import com.hap.appchat.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // setContentView(R.layout.activity_sign_up);

        // getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Acount");
        progressDialog.setMessage("We' re creating your acount");


        binding.btnSignUp.setOnClickListener(this::onClick);


        binding.dacoTaiKhoan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClick(View v) {
//        if (binding.eEmail.getText().toString().isEmpty() || binding.ePassWord.getText().toString().isEmpty()
//                || binding.eUserName.getText().toString().isEmpty()
//                ||binding.eConfirmPassWord.getText().toString().isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Thieu thong tin dang ky", Toast.LENGTH_LONG).show();
//
//        }
        if (binding.eUserName.getText().toString().isEmpty()) {
            binding.eUserName.setError("Email không được để trống");
            return;
        }
        if (binding.eEmail.getText().toString().isEmpty()) {
            binding.eEmail.setError("Email không được để trống");
            return;
        }
        if (binding.ePassWord.getText().toString().isEmpty()||binding.ePassWord.getText().length()<6) {
            binding.ePassWord.setError("mật khẩu phải nhiều hơn 6 ký tự ");
            return;
        }
        if (binding.eConfirmPassWord.getText().toString().isEmpty()) {
            binding.eConfirmPassWord.setError("confirm không được để trống");
            return;
        }
        if (!binding.ePassWord.getText().toString().equals(binding.eConfirmPassWord.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_LONG).show();

        } else {

            progressDialog.show();
            auth.createUserWithEmailAndPassword(
                    binding.eEmail.getText().toString(),
                    binding.ePassWord.getText().toString()).
                    addOnCompleteListener(this::onComplete);
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void onComplete(Task<AuthResult> task) {
        progressDialog.dismiss();
        if (task.isSuccessful()) {
            Users user = new Users(binding.eUserName.getText().toString(), binding.eEmail.getText().toString(), binding.ePassWord.getText().toString());


            String id = task.getResult().getUser().getUid();
            database.getReference().child("Users").child(id).setValue(user);

            Toast.makeText(SignUpActivity.this, "thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(SignUpActivity.this, "Thất bại!! Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
        }

    }
}