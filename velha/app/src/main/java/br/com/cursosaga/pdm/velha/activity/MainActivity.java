package br.com.cursosaga.pdm.velha.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import br.com.cursosaga.pdm.velha.R;
import br.com.cursosaga.pdm.velha.model.Game;
import br.com.cursosaga.pdm.velha.model.Player;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img11, img12, img13, img21, img22, img23, img31, img32, img33, btnTryAgain;
    private TextView txtVictory, txtFail, txtDraw, txtPlayerName;
    private static final int CIRCULO = R.drawable.circulo_velha;
    private static final int XIS = R.drawable.xis_velha;
    private static int lastId;
    private Player player1;
    private Player player2;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
        instantiateObjects();
        clearBoard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img11:
                game.userPlays(11, img11);
                break;
            case R.id.img12:
                game.userPlays(12, img12);
                break;
            case R.id.img13:
                game.userPlays(13, img13);
                break;
            case R.id.img21:
                game.userPlays(21, img21);
                break;
            case R.id.img22:
                game.userPlays(22, img22);
                break;
            case R.id.img23:
                game.userPlays(23, img23);
                break;
            case R.id.img31:
                game.userPlays(31, img31);
                break;
            case R.id.img32:
                game.userPlays(32, img32);
                break;
            case R.id.img33:
                game.userPlays(33, img33);
                break;
            case R.id.btnTryAgain:
                startGame();
                clearBoard();
                break;
            default:
                break;
        }
        if (game.canPlay) {
            if (game.getCurrentPlayer().getId() == 0)
                IAPlay();
            game.changeTurn();
            if (game.winner == true)
                btnTryAgain.setVisibility(View.VISIBLE);
            if (game.playedList.isEmpty() && game.winner == false)
                btnTryAgain.setVisibility(View.VISIBLE);
            updateScoreboard();
        }
    }

    private void IAPlay() {
        Player player = game.getCurrentPlayer();
        int value = player.getPlayerMovesList().get(player.getPlayerMovesList().size() - 1);
        game.playedList.remove((Integer) value);
        switch (value) {
            case 11:
                if (game.machine.getChoise() == XIS)
                    img11.setImageResource(XIS);
                else
                    img11.setImageResource(CIRCULO);
                break;
            case 12:
                if (game.machine.getChoise() == XIS)
                    img12.setImageResource(XIS);
                else
                    img12.setImageResource(CIRCULO);
                break;
            case 13:
                if (game.machine.getChoise() == XIS)
                    img13.setImageResource(XIS);
                else
                    img13.setImageResource(CIRCULO);
                break;
            case 21:
                if (game.machine.getChoise() == XIS)
                    img21.setImageResource(XIS);
                else
                    img21.setImageResource(CIRCULO);
                break;
            case 22:
                if (game.machine.getChoise() == XIS)
                    img22.setImageResource(XIS);
                else
                    img22.setImageResource(CIRCULO);
                break;
            case 23:
                if (game.machine.getChoise() == XIS)
                    img23.setImageResource(XIS);
                else
                    img23.setImageResource(CIRCULO);
                break;
            case 31:
                if (game.machine.getChoise() == XIS)
                    img31.setImageResource(XIS);
                else
                    img31.setImageResource(CIRCULO);
                break;
            case 32:
                if (game.machine.getChoise() == XIS)
                    img32.setImageResource(XIS);
                else
                    img32.setImageResource(CIRCULO);
                break;
            case 33:
                if (game.machine.getChoise() == XIS)
                    img33.setImageResource(XIS);
                else
                    img33.setImageResource(CIRCULO);
                break;
            default:
                break;
        }
        game.validateWinner();
    }

    private void updateScoreboard() {
        Player player = game.getCurrentPlayer();
        txtPlayerName.setText(player.getName());
        txtVictory.setText(getResources().getString(R.string.message_victory) + " " + player.getVictory());
        txtFail.setText(getResources().getString(R.string.message_fail) + " " + player.getFail());
        txtDraw.setText(getResources().getString(R.string.message_draw) + " " + player.getDraw());
    }

    private void instantiateObjects() {
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

        txtPlayerName = findViewById(R.id.txtName);
        txtPlayerName.setText(game.getCurrentPlayer().getName());

        txtVictory = findViewById(R.id.txtVictories);
        txtVictory.setText(getResources().getString(R.string.message_victory) + game.getCurrentPlayer().getVictory());

        txtFail = findViewById(R.id.txtFails);
        txtFail.setText(getResources().getString(R.string.message_fail) + game.getCurrentPlayer().getFail());

        txtDraw = findViewById(R.id.txtDraws);
        txtDraw.setText(getResources().getString(R.string.message_draw) + game.getCurrentPlayer().getDraw());
    }

    private void clearBoard() {
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

    private void startGame() {
        if (player1 == null)
            player1 = (Player) getIntent().getSerializableExtra("player1");
        if (player2 == null)
            player2 = (Player) getIntent().getSerializableExtra("player2");
        if (game == null) {
            if (player2 == null) {
                game = new Game(getApplicationContext(), player1);
                player1.setTurn(1);
            } else
                game = new Game(getApplicationContext(), player1, player2);
        }
        game.initializePlayedList();
    }


    @Override
    protected void onStop() {
        player1 = new Player();
        game = new Game(getApplicationContext(), player1);
        super.onStop();
    }

    @Override
    protected void onStart() {
        Bundle savedIntance = getIntent().getBundleExtra("savedState");
        if (savedIntance != null) {
            player1 = (Player) savedIntance.getSerializable("player1");
            player2 = (Player) savedIntance.getSerializable("player2");
            game = (Game) savedIntance.getSerializable("game");
        }
        super.onStart();
        //Toast.makeText(getApplicationContext(), getResources().getString(R.string.display_prefix_player_name) + player.getName() + "\n" + getResources().getString(R.string.message_option) + player.getChoise() + "\n" + getResources().getString(R.string.message_fail) + victory + "\n" + getResources().getString(R.string.message_fail) + fail, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        Bundle outState = new Bundle();
        outState.putSerializable("player1", player1);
        outState.putSerializable("player2", player2);
        outState.putSerializable("game", game);
        getIntent().putExtra("savedState", outState);
        super.onPause();
    }
}
