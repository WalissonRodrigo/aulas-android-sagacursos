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
import br.com.cursosaga.pdm.velha.model.Player;

public class PlayerChoiseActivity extends AppCompatActivity {

    private Button btStartGame;
    private RadioGroup rgOption;
    private TextInputEditText txtName;
    private ImageView imgChoised;
    private Player player;
    private boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_choise);
        player = new Player();
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
                player.setName(txtName.getText().toString());
                if (!player.getName().isEmpty() && player.getChoise() > 0) {
                    player.setId(1);
                    player.setTurn(1);
                    Intent intensao = new Intent(getApplicationContext(), MainActivity.class);
                    intensao.putExtra("player1", player);
                    startActivity(intensao);
                    firstStart = false;
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.message_error_choise_player), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        rgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCIRCULO:
                        player.setChoise(R.drawable.circulo_velha);
                        break;
                    case R.id.rbXIS:
                        player.setChoise(R.drawable.xis_velha);
                        break;
                    default:
                        break;
                }
                imgChoised.setImageResource(player.getChoise());
            }
        });
    }

    @Override
    protected void onStart() {
        if (!firstStart) {
            rgOption.clearCheck();
            txtName.setText(null);
            txtName.clearFocus();
            player = new Player();
            imgChoised.setImageDrawable(null);
            firstStart = true;
        }
        super.onStart();
    }
}
