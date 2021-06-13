package com.marketgad.testapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private Button m9Button;
    private Button m8Button;
    private Button m7Button;
    private Button m6Button;
    private Button m5Button;
    private Button m4Button;
    private Button m3Button;
    private Button m2Button;
    private Button m1Button;
    private Button m0Button;
    private Button equalsButton;
    private Button plusButton;
    private Button buttonClear;
    private Button multiplyButton;
    private Button buttonCart;
    Button addprod;
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    FloatingActionButton fab;
    TextView totalAmount, idcount;
    private TextView mNumberTextView;
    private static String stringNum = "0";
    private static int strToInt;
    private static int firstNum;
    private static String lastOperPressed = "None";
    private int Serialno=1;
    private ArrayList<CartViewActivity>cartitemList;
    private AdapterCartItem adapterCartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberTextView = (TextView) findViewById(R.id.calculated_text_view);
        mNumberTextView.setText(stringNum);
        addprod = findViewById(R.id.addprod);
        imageView = findViewById(R.id.imageview);
        fab = findViewById(R.id.fab);
        totalAmount.findViewById(R.id.totalAmount);
        idcount.findViewById(R.id.count);
        m9Button = (Button) findViewById(R.id.button9);
        numUpdate(m9Button, "9");
        m8Button = (Button) findViewById(R.id.button8);
        numUpdate(m8Button, "8");
        m7Button = (Button) findViewById(R.id.button7);
        numUpdate(m7Button, "7");
        m6Button = (Button) findViewById(R.id.button6);
        numUpdate(m6Button, "6");
        m5Button = (Button) findViewById(R.id.button5);
        numUpdate(m5Button, "5");
        m4Button = (Button) findViewById(R.id.button4);
        numUpdate(m4Button, "4");
        m3Button = (Button) findViewById(R.id.button3);
        numUpdate(m3Button, "3");
        m2Button = (Button) findViewById(R.id.button2);
        numUpdate(m2Button, "2");
        m1Button = (Button) findViewById(R.id.button1);
        numUpdate(m1Button, "1");
        m0Button = (Button) findViewById(R.id.button0);
        numUpdate(m0Button, "0");
        equalsButton = (Button) findViewById(R.id.buttonEquals);
        plusButton = (Button) findViewById(R.id.buttonAdd);
        operUpdate(plusButton, "addition");
        buttonClear = (Button) findViewById(R.id.buttonClear);
        operUpdate(buttonClear, "subtraction");
        multiplyButton = (Button) findViewById(R.id.buttonMultiply);
        operUpdate(multiplyButton, "multiplication");
        buttonCart = findViewById(R.id.buttonCart);
        operUpdate(buttonCart, "division");
        equalsButton = (Button) findViewById(R.id.buttonEquals);
        equalsUpdate(equalsButton);



        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calc = mNumberTextView.getText().toString();
                String tot = totalAmount.getText().toString();
                Double last = Double.parseDouble(tot)+Double.parseDouble(calc);
                totalAmount.setText(String.valueOf(last));


                showCartDialogue();
                cartitemList = new ArrayList<>();

                Serialno = Serialno+1;
                idcount.setText(String.valueOf(Serialno));

                CartViewActivity modelcartitem = new CartViewActivity(""+Serialno,""+calc);
                cartitemList.add(modelcartitem);
            }
        });
        adapterCartItem = new AdapterCartItem(this,cartitemList);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    101);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ImageTakeIntent.resolveActivity(getPackageManager()) != null) ;
                {
                    startActivityForResult(ImageTakeIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String finalamount = totalAmount.getText().toString();
                String calcamount = mNumberTextView.getText().toString();
                String countstring = idcount.getText().toString();

                Intent cartActivity = new Intent(MainActivity.this, CartActivity.class);
                cartActivity.putExtra("keyamo", finalamount);
                cartActivity.putExtra("keycalc", calcamount);
                cartActivity.putExtra("keycount", countstring);
                startActivity(cartActivity);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberTextView.setText("0");
            }
        });
    }

    private void showCartDialogue() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_cart2,null);
        RecyclerView cartitems = view.findViewById(R.id.cartitems);
        TextView totalAmountinvoice = view.findViewById(R.id.totalAmountinvoice);
        TextView numberOfItems = view.findViewById(R.id.numberOfItems);
        TextView totalAmount = view.findViewById(R.id.totalAmount);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

    }


    public void numUpdate(final Button buttonName, final String num) {
        buttonName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lastOperPressed == "add" || lastOperPressed == "subtract" || lastOperPressed == "multiply" || lastOperPressed == "divide") {
                    if (firstNum == Integer.parseInt(mNumberTextView.getText().toString())) {
                        mNumberTextView.setText("");
                    }
                }
                if (lastOperPressed == "Equals") {
                    mNumberTextView.setText("");
                    firstNum = 0;
                    lastOperPressed = "None";
                }
                if (mNumberTextView.getText().toString() == "Can't Divide by 0!") {
                    mNumberTextView.setText("0");
                }
                stringNum = mNumberTextView.getText().toString();
                stringNum = stringNum + num;
                strToInt = Integer.parseInt(stringNum);
                stringNum = Integer.toString(strToInt);
                mNumberTextView.setText(stringNum);
                if (lastOperPressed == "divide" & strToInt == 0) {
                    mNumberTextView.setText("Can't Divide by 0!");
                    firstNum = 0;
                    lastOperPressed = "None";
                }
                Log.d(TAG, Integer.toString(firstNum));
            }
        });
    }

    public void operUpdate(final Button buttonName, final String opName) {
        buttonName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lastOperPressed == "add") {
                    firstNum = firstNum + strToInt;
                    mNumberTextView.setText(Integer.toString(firstNum));
                } else if (lastOperPressed == "subtract") {
                    firstNum = firstNum - strToInt;
                    mNumberTextView.setText(Integer.toString(firstNum));
                } else if (lastOperPressed == "multiply") {
                    firstNum = firstNum * strToInt;
                    mNumberTextView.setText(Integer.toString(firstNum));
                } else if (lastOperPressed == "divide") {
                    firstNum = firstNum / strToInt;
                    mNumberTextView.setText(Integer.toString(firstNum));
                } else if (lastOperPressed == "None" || lastOperPressed == "Equals") {
                    firstNum = Integer.parseInt(mNumberTextView.getText().toString());
                }
                if (opName == "addition") {
                    lastOperPressed = "add";
                } else if (opName == "subtraction") {
                    lastOperPressed = "subtract";
                } else if (opName == "multiplication") {
                    lastOperPressed = "multiply";
                } else if (opName == "division") {
                    lastOperPressed = "divide";
                }
            }
        });
    }

    public void equalsUpdate(final Button buttonName) {
        buttonName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lastOperPressed == "add") {
                    mNumberTextView.setText(Integer.toString(firstNum + strToInt));
                } else if (lastOperPressed == "subtract") {
                    mNumberTextView.setText(Integer.toString(firstNum - strToInt));
                } else if (lastOperPressed == "multiply") {
                    mNumberTextView.setText(Integer.toString(firstNum * strToInt));
                } else if (lastOperPressed == "divide") {
                    if (strToInt != 0) {
                        mNumberTextView.setText(Integer.toString(firstNum / strToInt));
                    } else {
                        mNumberTextView.setText("Can't Divide by 0!");
                    }
                }
                lastOperPressed = "Equals";
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) ;
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}