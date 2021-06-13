package com.marketgad.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public  TextView totalAmountinvoice,numberOfItems,totalAmount;
    FloatingActionButton fab;
    int pagewidth = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        totalAmountinvoice = findViewById(R.id.totalAmountinvoice);
        numberOfItems = findViewById(R.id.numberOfItems);
        fab = findViewById(R.id.fab);

        String totalAmountinvoicestring = getIntent().getStringExtra("keyamo");
        String totalamountstring = getIntent().getStringExtra("keyamo");
        String countString = getIntent().getStringExtra("keycount");

        totalAmountinvoice.setText(totalAmountinvoicestring);
        totalAmount.setText(totalamountstring);
        numberOfItems.setText(countString);


        createPDF();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String finalamount = totalAmount.getText().toString();
                String countstring = numberOfItems.getText().toString();

                Intent ShareActivity= new Intent(CartActivity.this, com.marketgad.testapp.ShareActivity.class);
                ShareActivity.putExtra("keyamo", finalamount);
                ShareActivity.putExtra("keycount", countstring);
                startActivity(ShareActivity);
            }
        });
    }


    private void createPDF() {

        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo myPageinfo1 = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page myPAge1 = myPdfDocument.startPage(myPageinfo1);
        Canvas canvas = myPAge1.getCanvas();

        title.setTextAlign(Paint.Align.CENTER);
        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        title.setTextSize(70);
        canvas.drawText("Invoice",pagewidth/2,270,title);

        myPaint.setTextSize(40f);



        myPdfDocument.finishPage(myPAge1);

        File file = new File(Environment.getExternalStorageDirectory(),"/Invoice.pdf");
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        myPdfDocument.close();
    }
}