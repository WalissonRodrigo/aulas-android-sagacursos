package br.com.cursosaga.pdm.velha.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import br.com.cursosaga.pdm.velha.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img11, img12, img13, img21, img22, img23, img31, img32, img33;
    private static Button btnTryAgain;
    private static final int CIRCULO = R.drawable.circulo_velha;
    private static final int XIS = R.drawable.xis_velha;
    //private static int[] randomOption = {CIRCULO, XIS};
    private static ArrayList<Integer> listaJogadas;
    private static ArrayList<Integer> jogadasPlayer;
    private static ArrayList<Integer> jogadasMachine;
    private final static int[] options = {11, 12, 13, 21, 22, 23, 31, 32, 33};
    private static int[][] optionsWin = {{11, 12, 13}, {21, 22, 23}, {31, 32, 33}, {11, 22, 33}, {13, 22, 31}, {11, 21, 31}, {12, 22, 32}, {13, 23, 33}};
    private static boolean winner = false;
    private Runnable JogaIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanciarImagens();
        limparTabuleiro();
        iniciaListaJogadas();
    }

    private void iniciaListaJogadas() {
        listaJogadas = new ArrayList<Integer>();
        listaJogadas.clear();
        for (int x : options) {
            listaJogadas.add(x);
        }
        jogadasMachine = new ArrayList<Integer>();
        jogadasMachine.clear();

        jogadasPlayer = new ArrayList<Integer>();
        jogadasPlayer.clear();
        winner = false;
    }

    private void usuarioJoga(int valor, ImageView img) {
        if (listaJogadas.contains(valor) && winner == false) {
            listaJogadas.remove(listaJogadas.indexOf(valor));
            img.setImageResource(XIS);
            jogadasPlayer.add(valor);
            validaGanhador(1);
            new Thread(IAJoga()).start();
        }
    }

    private void validaGanhador(int jogador) {
        if (jogador == 1) {
            if (jogadasPlayer.size() >= 3) {
                if (checkWin(jogadasPlayer)) {
                    vencedor(jogador);
                    return;
                }
            }
        } else {
            if (jogadasMachine.size() >= 3) {
                if (checkWin(jogadasMachine)) {
                    vencedor(jogador);
                    return;
                }
            }
        }
    }

    private void vencedor(int jogador) {
        //atualiza na tela quem foi o ganhador da vez.
        if(jogador == 1)
            Toast.makeText(getApplicationContext(), "JOGADOR 1 GANHOU!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "JOGADOR 2 GANHOU!", Toast.LENGTH_LONG).show();
        btnTryAgain.setVisibility(View.VISIBLE);
    }

    private boolean checkWin(ArrayList<Integer> jogado) {
        //boolean winner = false;
        // For usando : é o mesmo que ForEach em outras linguagens
        for (int[] jogada : optionsWin) {
            if (jogado.contains(jogada[0]) && jogado.contains(jogada[1]) && jogado.contains(jogada[2]))
            {
                //winner = true;
                this.winner = true;
            }
        }
        return this.winner;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img11:
                usuarioJoga(11, img11);
                break;
            case R.id.img12:
                usuarioJoga(12, img12);
                break;
            case R.id.img13:
                usuarioJoga(13, img13);
                break;
            case R.id.img21:
                usuarioJoga(21, img21);
                break;
            case R.id.img22:
                usuarioJoga(22, img22);
                break;
            case R.id.img23:
                usuarioJoga(23, img23);
                break;
            case R.id.img31:
                usuarioJoga(31, img31);
                break;
            case R.id.img32:
                usuarioJoga(32, img32);
                break;
            case R.id.img33:
                usuarioJoga(33, img33);
                break;
            case R.id.btnTryAgain:
                iniciaListaJogadas();
                limparTabuleiro();
                break;
            default:
                break;
        }
        if (listaJogadas.isEmpty() && winner == false) {
            btnTryAgain.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "DEU VELHA! JOGUE NOVAMENTE...", Toast.LENGTH_LONG).show();
        }
    }

    private Runnable IAJoga() {
        Runnable runIA = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((listaJogadas.size() > 0 || !listaJogadas.isEmpty()) && winner == false) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int value = listaJogadas.get(new Random().nextInt(listaJogadas.size()));
                            if (listaJogadas.contains(value)) {
                                listaJogadas.remove(listaJogadas.indexOf(value));
                                jogadasMachine.add(value);
                                switch (value) {
                                    case 11:
                                        img11.setImageResource(CIRCULO);
                                        break;
                                    case 12:
                                        img12.setImageResource(CIRCULO);
                                        break;
                                    case 13:
                                        img13.setImageResource(CIRCULO);
                                        break;
                                    case 21:
                                        img21.setImageResource(CIRCULO);
                                        break;
                                    case 22:
                                        img22.setImageResource(CIRCULO);
                                        break;
                                    case 23:
                                        img23.setImageResource(CIRCULO);
                                        break;
                                    case 31:
                                        img31.setImageResource(CIRCULO);
                                        break;
                                    case 32:
                                        img32.setImageResource(CIRCULO);
                                        break;
                                    case 33:
                                        img33.setImageResource(CIRCULO);
                                        break;
                                    default:
                                        break;
                                }
                                validaGanhador(2);
                            }
                        }
                    });
                }
            }
        };

        return runIA;
    }

    private void instanciarImagens() {
        img11 = findViewById(R.id.img11);
        img11.setOnClickListener(this);

        img12 = findViewById(R.id.img12);
        img12.setOnClickListener(this);

        img13 = findViewById(R.id.img13);
        img13.setOnClickListener(this);

        img21 = findViewById(R.id.img21);
        img21.setOnClickListener(this);

        img22 = findViewById(R.id.img22);
        img22.setOnClickListener(this);

        img23 = findViewById(R.id.img23);
        img23.setOnClickListener(this);

        img31 = findViewById(R.id.img31);
        img31.setOnClickListener(this);

        img32 = findViewById(R.id.img32);
        img32.setOnClickListener(this);

        img33 = findViewById(R.id.img33);
        img33.setOnClickListener(this);

        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(this);
    }

    private void limparTabuleiro() {
        img11.setImageDrawable(null);
        img11.setEnabled(true);
        img12.setImageDrawable(null);
        img12.setEnabled(true);
        img13.setImageDrawable(null);
        img13.setEnabled(true);
        img21.setImageDrawable(null);
        img21.setEnabled(true);
        img22.setImageDrawable(null);
        img22.setEnabled(true);
        img23.setImageDrawable(null);
        img23.setEnabled(true);
        img31.setImageDrawable(null);
        img31.setEnabled(true);
        img32.setImageDrawable(null);
        img32.setEnabled(true);
        img33.setImageDrawable(null);
        img33.setEnabled(true);
        btnTryAgain.setVisibility(View.GONE);
    }
}
