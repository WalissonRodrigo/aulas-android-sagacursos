package br.com.cursosaga.pdm.velha.model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import br.com.cursosaga.pdm.velha.R;
import br.com.cursosaga.pdm.velha.activity.MainActivity;

public class Game implements Serializable {

    private static final int CIRCULO = R.drawable.circulo_velha;
    private static final int XIS = R.drawable.xis_velha;
    private final static int[] options = {11, 12, 13, 21, 22, 23, 31, 32, 33};
    private static int[][] optionsWin = {{11, 12, 13}, {21, 22, 23}, {31, 32, 33}, {11, 22, 33}, {13, 22, 31}, {11, 21, 31}, {12, 22, 32}, {13, 23, 33}};
    public static ArrayList<Integer> playedList;
    public static boolean winner;
    public static boolean canPlay;
    public Player player1;
    public Player player2;
    public Player machine;
    private Context context;

    public Game(Context context, Player player1) {
        winner = false;
        initializePlayedList();
        this.player1 = player1;
        if (player2 == null) {
            machine = new Player();
            machine.setName("Machine");
            if (player1.getChoise() == XIS)
                machine.setChoise(CIRCULO);
            else
                machine.setChoise(XIS);
            machine.setTurn(2);
            machine.setId(0);
        }
        this.context = context;
    }

    public Game(Context context, Player player1, Player player2) {
        initializePlayedList();
        this.player1 = player1;
        this.player2 = player2;
        this.context = context;
    }

    public void initializePlayedList() {
        playedList = new ArrayList<Integer>();
        playedList.clear();
        for (int x : options) {
            playedList.add(x);
        }
        if (player1 != null) {
            player1.clearPlayerMovesList();
        }
        if (player2 != null) {
            player2.clearPlayerMovesList();
        }
        if (machine != null) {
            machine.clearPlayerMovesList();
        }
        if (player2 == null && machine != null) {
            player1.setTurn(1);
            machine.setTurn(2);
        }
        winner = false;
        canPlay = true;
    }

    public void userPlays(int value, ImageView img) {
        if (!playedList.isEmpty() && winner == false && playedList.contains(value)) {
            Player player = getCurrentPlayer();
            if (player.getId() > 0 && playedList.contains(value) && player.isWinner() == false) {
                playedList.remove((Integer) value);
                player.addItemPlayerMovesList(value);
                if (player.getChoise() == XIS)
                    img.setImageResource(XIS);
                else
                    img.setImageResource(CIRCULO);
                validateWinner();
            }
            if (machine != null && player.isWinner() == false && winner == false) {
                player1.setTurn(2);
                MachinePlay();
            }
            canPlay = true;
            if (playedList.isEmpty() && winner == false) {
                if (player1.isWinner() == false)
                    player1.addDraw();
                if (player2 != null) {
                    if (player2.isWinner() == false)
                        player2.addDraw();
                }
                if (machine != null) {
                    if (machine.isWinner() == false)
                        machine.addDraw();
                }
                Toast.makeText(context.getApplicationContext(), context.getResources().getText(R.string.message_draw_to_user), Toast.LENGTH_LONG).show();
            }
        } else {
            canPlay = false;
        }
    }

    private void MachinePlay() {
        if ((playedList.size() > 0 || !playedList.isEmpty()) && machine.isWinner() == false) {
            int value = playedList.get(new Random().nextInt(playedList.size()));
            if (playedList.contains(value)) {
                playedList.remove((Integer) value);
                machine.addItemPlayerMovesList(value);
                validateWinner();
            }
        }
    }

    public Player getCurrentPlayer() {
        Player player;
        if (player1.getTurn() == 1)
            player = player1;
        else if (player2 != null && player2.getTurn() == 1)
            player = player2;
        else
            player = machine;

        return player;
    }

    private void winner() {
        //atualiza os status de ganahador, perdedor e empates do usuário
        Player player = getCurrentPlayer();
        if (player.isWinner() == true) {
            player.addVictory();
            Toast.makeText(context.getApplicationContext(), context.getResources().getText(R.string.display_prefix_player_name) + " " + player.getName().toUpperCase() + " " + context.getResources().getText(R.string.message_player_winner), Toast.LENGTH_LONG).show();
        }
        if (player1 != null) {
            if (player1.isWinner() == false)
                player1.addFail();
        }
        if (player2 != null) {
            if (player2.isWinner() == false)
                player2.addFail();
        }
        if (machine != null) {
            if (machine.isWinner() == false)
                machine.addFail();
        }

    }

    private boolean checkWin(ArrayList<Integer> played) {
        // For usando : é o mesmo que ForEach em outras linguagens
        for (int[] play : optionsWin) {
            if (played.contains(play[0]) && played.contains(play[1]) && played.contains(play[2])) {
                getCurrentPlayer().setWinner(true);
                winner = true;
                return winner;
            }
        }
        return winner;
    }

    public void changeTurn() {
        Player player = getCurrentPlayer();
        if (player.getId() == 0) {
            player1.setTurn(1);
            machine.setTurn(2);
        }
        if (player.getId() == 1) {
            player1.setTurn(2);
            if (player2 != null)
                player2.setTurn(1);
            if (machine != null)
                machine.setTurn(1);
        }
        if (player.getId() == 2) {
            player1.setTurn(1);
            player2.setTurn(2);
        }
    }

    public void validateWinner() {
        Player player = getCurrentPlayer();
        if (player.getPlayerMovesList().size() >= 3) {
            if (checkWin(player.getPlayerMovesList())) {
                winner();
            }
        }
    }
}
