package com.tracking.bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Hananideen on 20/12/2015.
 */
public class FindBusActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etStart, etEnd;
    private Button btnFind;
    private ImageView ivBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findbus);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnFind = (Button) findViewById(R.id.btnFindBus);
        ivBus = (ImageView) findViewById(R.id.ivBus);
        ivBus.setVisibility(View.INVISIBLE);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivBus.setVisibility(View.VISIBLE);
            }
        });
    }
}
