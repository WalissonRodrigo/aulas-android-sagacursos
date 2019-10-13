package br.com.cursosaga.pdm.velha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import br.com.cursosaga.pdm.velha.R;

public class PlayerChoiseActivity extends AppCompatActivity {

    private Button btStartGame;
    private RadioGroup rgOption;
    private TextInputEditText txtName;
    private ImageView imgChoised;
    private int playerOption = 0;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_choise);
        //instanciação dos elementos recuperando a instância da tela pelo id na classe R
        btStartGame = findViewById(R.id.btStartGame);
        rgOption = findViewById(R.id.rgOption);
        btStartGame = findViewById(R.id.btStartGame);
        imgChoised = findViewById(R.id.imgChoised);
        txtName = findViewById(R.id.playerChoiseNamePlayer);
        //criação dos eventos de click para o botão e alteração de radio button quando marcado a escolha
        btStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicar a nova activity passando os Parametros
                playerName = txtName.getText().toString();
                if (!playerName.isEmpty() || playerOption > 0) {
                    Intent intensao = new Intent(getApplicationContext(), MainActivity.class);
                    intensao.putExtra("name", playerName);
                    intensao.putExtra("option", playerOption);
                    startActivity(intensao);
                } else {
                    Toast.makeText(getApplicationContext(), "O nome ou a opção não foi preenchido!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        rgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCIRCULO:
                        playerOption = R.drawable.circulo_velha;
                        break;
                    case R.id.rbXIS:
                        playerOption = R.drawable.xis_velha;
                        break;
                    default:
                        break;
                }
                imgChoised.setImageResource(playerOption);
            }
        });
    }
}
