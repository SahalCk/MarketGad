package com.marketgad.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

public class ShareActivity extends AppCompatActivity {

    EditText phone;
    TextView message,numberOfItems,totalAmount;
    FloatingActionButton whatsapp, sms, line;
    String phonestring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        phone = findViewById(R.id.phone);
        whatsapp.findViewById(R.id.whatsapp);
        sms.findViewById(R.id.sms);
        line.findViewById(R.id.line);
        message.findViewById(R.id.message);

        String totalAmountinvoicestring = getIntent().getStringExtra("keyamo");
        String countString = getIntent().getStringExtra("keycount");


        totalAmount.setText(totalAmountinvoicestring);
        numberOfItems.setText(countString);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ShareActivity.this,new String[]{
                        Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS},PackageManager.PERMISSION_GRANTED);

                phonestring = ("+91"+phone.getText().toString());
                message.setText("You have purchased items worth ₹"+totalAmount+"from general Store.please find below the pdf to proceed with your payments click on the below link");
                String messagestring = message.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phonestring,null,messagestring,null,null);
            }
        });



        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phonestring = ("91"+phone.getText().toString());
                String messagestring = message.getText().toString();
                message.setText("You have purchased items worth ₹"+totalAmount+"from general Store.please find below the pdf to proceed with your payments click on the below link");

                if (phonestring.isEmpty()) {
                    Toast.makeText(ShareActivity.this, "Please enter the mobile Number", Toast.LENGTH_SHORT).show();
                } else {

                    if (isWhatsappInstalled()){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone="+phonestring+
                                "&text"+messagestring));
                        startActivity(i);
                        phone.setText("");
                    }
                    else {
                        Toast.makeText(ShareActivity.this, "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonestring = ("+91"+phone.getText().toString());
                message.setText("You have purchased items worth ₹"+totalAmount+"from general Store.please find below the pdf to proceed with your payments click on the below link");
                String messagestring = message.getText().toString();
                if (phonestring.isEmpty()) {
                    Toast.makeText(ShareActivity.this, "Please enter the mobile Number", Toast.LENGTH_SHORT).show();
                } else {

                    if (isLineInstalled()){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.line.com/send/?phone="+phonestring+
                                "&text"+messagestring));
                        startActivity(i);
                        phone.setText("");
                    }
                    else {
                        Toast.makeText(ShareActivity.this, "Line is not installed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }



    private boolean isWhatsappInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        return  whatsappInstalled;

    }
    private boolean isLineInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean LineInstalled;

        try {
            packageManager.getPackageInfo("com.line", PackageManager.GET_ACTIVITIES);
            LineInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            LineInstalled = false;
        }
        return  LineInstalled;

    }

}