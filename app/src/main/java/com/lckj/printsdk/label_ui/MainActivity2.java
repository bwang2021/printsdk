package com.lckj.printsdk.label_ui;

import android.bld.print.configuration.PrintConfig;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lc_print_sdk.PrintUtil;
import com.lckj.printsdk.R;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements PrintUtil.PrinterBinderListener {

    Button btnAdd;
    PrintUtil printUtil;
    private int mBarcode1DType;
    boolean isNull = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        printUtil = PrintUtil.getInstance(this);
        try {
            printUtil = PrintUtil.getInstance(this);
            printUtil.setPrintEventListener(this);
            printUtil.printEnableMark(true);
        } catch (NullPointerException e) {
            isNull = true;
            Log.e("TAG", "onCreate: ===============>" + e.getMessage());
        }

        mBarcode1DType=PrintConfig.BarCodeType.TOP_TYPE_CODE128;



        btnAdd = findViewById(R.id.btn_add);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBarcode("123456789123456");
            }
        });

    }

    @Override
    public void onPrintCallback(int i) {
        if (PrintConfig.IErrorCode.ERROR_NO_ERROR == i) {
            Log.e("TAG", "onPrintCallback: ===============>");
        }
    }

    @Override
    public void onVersion(String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printUtil.removePrintListener(this);
    }

    public void printBarcode(String barContent) {
        try {
            int distance = 1000;

            printUtil.setFeedPaperSpace(distance);
            printUtil.setUnwindPaperLen(60);
            printUtil.printBarcode(PrintConfig.Align.ALIGN_CENTER, 100, barContent, mBarcode1DType, PrintConfig.HRIPosition.POSITION_BELOW);
            printUtil.start();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "printBarcode: ===============>"+e.getMessage());
        }
    }
}