package br.com.cursosaga.pmd.sort;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static TextView mainTextSort;
    private static Button mainButton;
    //
    private Random sort;
    private Thread runSort;
    private static int limit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //Recupera um elemento R.id.nome_do_elemento para um objeto java do mesmo tipo
        mainTextSort = (TextView) findViewById(R.id.mainTextView);
        mainButton = findViewById(R.id.mainButton);

        SeekBar objetoTeste = findViewById(R.id.seekBar);

        objetoTeste.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Mudamos o valor do SeekBar para " + progress, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Clicamos na barra para mudar seu valor", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Soltamos o click e mudamos o valor", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton sortButton = findViewById(R.id.mainFloatingActionButton);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText();
            }
        });
    }

    private void updateText() {
        sort = new Random();
        int counter = sort.nextInt(99);
        mainTextSort.setText(String.valueOf(counter));
    }

    public void Sort(View view) {
        //instanciou a nova thread
        runSort = new Thread() {
            @Override
            public void run() {//sobrecarga da função run na classe Thread
                limit = 0; //atribuiu valor para limite
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainButton.setEnabled(false);
                        mainButton.setVisibility(View.INVISIBLE);
                    }
                });
                boolean isRunning = true;
                try {
                    while (isRunning != false) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateText();
                                limit++;
                            }
                        });
                        if (limit > 9)
                            isRunning = false;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainTextSort.setText(R.string.main_stopped);
                        }
                    });
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainTextSort.setText(R.string.main_not_running);
                            mainButton.setEnabled(true);
                            mainButton.setVisibility(View.VISIBLE);
                        }
                    });
                    Thread.sleep(250);
                } catch (InterruptedException ie) {
                    Log.e("SORTE", "Erro de Thread | " + ie.getMessage(), ie);
                }
            }
        };
        //Dá inicio a execução da thread
        runSort.start();
    }
}
