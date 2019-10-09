package br.com.cursosaga.pmd.combustivel.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.cursosaga.pmd.combustivel.R;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText alcool;
    private TextInputEditText gasolina;
    private TextView resultado;
    private String resultadoCalculo = "Resultado do Cálculo";
    private String[] msgLoading = {"P", "r", "o", "c", "e", "s", "s", "a", "n", "d", "o", ".", ".", "."};
    private static String msgInterpolation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alcool = findViewById(R.id.txtAlcool);
        gasolina = findViewById(R.id.txtGasolina);
        resultado = findViewById(R.id.txtResultado);
        resultado.setText(resultadoCalculo);
    }

    /**
     * Method for Calculate the best option for consumption in fuel using values on TextInputEditText's
     *
     * @param view
     */
    public void calcular(View view) {
        if (!validaInputs()) {
            return;
        }
        float alcool = Float.parseFloat(this.alcool.getText().toString());
        float gasolina = Float.parseFloat(this.gasolina.getText().toString());
        if (alcool / gasolina <= 0.7) {
            resultadoCalculo = "Álcool compensa mais!";
        } else {
            resultadoCalculo = "Gasolina compensa mais!";
        }
        resultado.setText(resultadoCalculo);
        //GerarResultado();
    }

    /**
     * Método para Validação dos Inputs evitando calcular divisões por zero ou números negátivos assim como campos vazios.
     *
     * @return
     */
    private boolean validaInputs() {
        boolean response = true;
        if (response) {
            //Validação de campo alcool quando vazio
            if (alcool.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "NÃO FOI POSSÍVEL CALCULAR! O CAMPO ALCOOL ESTÁ VÁZIO.",
                        Toast.LENGTH_LONG).show();
                response = false;
            }
            //Validação de Campos alcool quando contém Zero no input ou valor negativo
            else if (Double.parseDouble(alcool.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "NÃO FOI POSSÍVEL CALCULAR! O CAMPO ALCOOL ESTÁ COM VALOR MENOR OU IGUAL A ZERO", Toast.LENGTH_LONG).show();
                response = false;
            }
        }
        //verifica se já não existe validações com erro
        if (response) {
            //Validação de campo gasolina quando vazio
            if (gasolina.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "NÃO FOI POSSÍVEL CALCULAR! O CAMPO GASOLINA ESTÁ VAZIO.", Toast.LENGTH_LONG).show();
                response = false;
            }
            //Validação de Campo gasolina quando contém Zero no input ou valor negativo
            else if (Double.parseDouble(gasolina.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "NÃO FOI POSSÍVEL CALCULAR! O CAMPO GASOLINA ESTÁ COM VALOR MENOR OU IGUAL A ZERO", Toast.LENGTH_LONG).show();
                response = false;
            }
        }
        //Reset Texto no TextView Resultado para evitar apresentar menssagem errada ou desatualizada.
        if (!response)
            resultado.setText("Resultado do Cálculo");

        //retorno do método já com a resposta TRUE caso tenha dado tudo certo ou FALSE caso alguma validação tenha falhado.
        return response;
    }

    private void GerarResultado() {
        Thread res = new Thread() {
            @Override
            public void run() {
                try {
                    msgInterpolation = "";
                    for (int c = 0; c < msgLoading.length; c++) {
                        Thread.sleep(500 / msgLoading.length);
                        msgInterpolation += msgLoading[c];
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultado.setText(msgInterpolation);
                            }
                        });
                    }
                    Thread.sleep(250);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultado.setText(resultadoCalculo);
                        }
                    });
                } catch (InterruptedException ie) {
                    Log.e("LogErroApp", ie.getMessage());
                }
            }
        };
        res.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Salva os valores das variáveis no bundle
        super.onSaveInstanceState(outState);
        outState.putString("alcool", alcool.getText().toString());
        outState.putString("gasolina", gasolina.getText().toString());
        outState.putString("resultadoCalculo", resultadoCalculo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //recupera do bundle os valores das variáveis
        super.onSaveInstanceState(savedInstanceState);
        alcool.setText(savedInstanceState.getString("alcool"));
        gasolina.setText(savedInstanceState.getString("gasolina"));
        resultadoCalculo = savedInstanceState.getString("resultadoCalculo");
        resultado.setText(resultadoCalculo);
    }
}
