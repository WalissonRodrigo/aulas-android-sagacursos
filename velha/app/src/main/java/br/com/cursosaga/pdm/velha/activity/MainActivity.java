package br.com.cursosaga.pdm.velha.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import br.com.cursosaga.pdm.velha.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img11, img12, img13, img21, img22, img23, img31, img32, img33;
    private TextView txtVitoria, txtDerrota, txtEmpate, txtNomeJogador;
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
    private static int vitoria, derrota, empate = 0;
    private Runnable JogaIA;
    private String playerName;
    private int playerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerName = getIntent().getStringExtra("name");
        playerOption = getIntent().getIntExtra("option", R.drawable.xis_velha);
        setContentView(R.layout.activity_main);
        instanciarObjetos();
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
            listaJogadas.remove((Integer) valor);
            if (playerOption == XIS)
                img.setImageResource(XIS);
            else
                img.setImageResource(CIRCULO);
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
        if (jogador == 1) {
            vitoria++;
            Toast.makeText(getApplicationContext(), "JOGADOR " + playerName.toUpperCase() + " GANHOU!", Toast.LENGTH_LONG).show();
        } else {
            derrota++;
            Toast.makeText(getApplicationContext(), "JOGADOR " + playerName.toUpperCase() + " PERDEU!", Toast.LENGTH_LONG).show();
        }
        atualizaPlacar();
        btnTryAgain.setVisibility(View.VISIBLE);
    }

    private boolean checkWin(ArrayList<Integer> jogado) {
        // For usando : é o mesmo que ForEach em outras linguagens
        for (int[] jogada : optionsWin) {
            if (jogado.contains(jogada[0]) && jogado.contains(jogada[1]) && jogado.contains(jogada[2])) {
                winner = true;
            }
        }
        return winner;
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
            empate++;
            atualizaPlacar();
            Toast.makeText(getApplicationContext(), "DEU VELHA! JOGUE NOVAMENTE...", Toast.LENGTH_LONG).show();
        }
    }

    private void atualizaPlacar(){
        txtVitoria.setText("Vitórias: " + vitoria);
        txtDerrota.setText("Derrotas: " + derrota);
        txtEmpate.setText("Empates: " + empate);
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
                                listaJogadas.remove((Integer) value);
                                jogadasMachine.add(value);
                                switch (value) {
                                    case 11:
                                        if (playerOption == XIS)
                                            img11.setImageResource(CIRCULO);
                                        else
                                            img11.setImageResource(XIS);
                                        break;
                                    case 12:
                                        if (playerOption == XIS)
                                            img12.setImageResource(CIRCULO);
                                        else
                                            img12.setImageResource(XIS);
                                        break;
                                    case 13:
                                        if (playerOption == XIS)
                                            img13.setImageResource(CIRCULO);
                                        else
                                            img13.setImageResource(XIS);
                                        break;
                                    case 21:
                                        if (playerOption == XIS)
                                            img21.setImageResource(CIRCULO);
                                        else
                                            img21.setImageResource(XIS);
                                        break;
                                    case 22:
                                        if (playerOption == XIS)
                                            img22.setImageResource(CIRCULO);
                                        else
                                            img22.setImageResource(XIS);
                                        break;
                                    case 23:
                                        if (playerOption == XIS)
                                            img23.setImageResource(CIRCULO);
                                        else
                                            img23.setImageResource(XIS);
                                        break;
                                    case 31:
                                        if (playerOption == XIS)
                                            img31.setImageResource(CIRCULO);
                                        else
                                            img31.setImageResource(XIS);
                                        break;
                                    case 32:
                                        if (playerOption == XIS)
                                            img32.setImageResource(CIRCULO);
                                        else
                                            img32.setImageResource(XIS);
                                        break;
                                    case 33:
                                        if (playerOption == XIS)
                                            img33.setImageResource(CIRCULO);
                                        else
                                            img33.setImageResource(XIS);
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

    private void instanciarObjetos() {
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

        txtNomeJogador = findViewById(R.id.txtName);
        txtNomeJogador.setText(playerName);

        txtVitoria = findViewById(R.id.txtVitorias);
        txtVitoria.setText("Vitórias: " + vitoria);

        txtDerrota = findViewById(R.id.txtDerrotas);
        txtDerrota.setText("Derrotas: " + derrota);

        txtEmpate = findViewById(R.id.txtEmpates);
        txtEmpate.setText("Empates: " + empate);
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
