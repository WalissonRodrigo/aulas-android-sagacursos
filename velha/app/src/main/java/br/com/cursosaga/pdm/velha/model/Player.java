package br.com.cursosaga.pdm.velha.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.cursosaga.pdm.velha.R;

public class Player implements Serializable {
    private String name;
    private int choise, turn, id;
    private int victory, fail, draw = 0;
    private ArrayList<Integer> playerMovesList;
    private boolean winner = false;

    public String getName() {
        return name;
    }

    public void setName(String playerName) {
        this.name = playerName;
    }

    public int getChoise() {
        return choise;
    }

    public void setChoise(int choise) {
        if (choise == R.drawable.circulo_velha || choise == R.drawable.xis_velha)
            this.choise = choise;
        else
            this.choise = -1;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getPlayerMovesList() {
        return playerMovesList;
    }

    public void addItemPlayerMovesList(int item) {
        if (this.playerMovesList == null)
            playerMovesList = new ArrayList<Integer>();
        playerMovesList.add(item);
    }

    public void clearPlayerMovesList() {
        playerMovesList = new ArrayList<Integer>();
        playerMovesList.clear();
        winner = false;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getVictory() {
        return victory;
    }

    public void addVictory() {
        this.victory++;
    }

    public int getFail() {
        return fail;
    }

    public void addFail() {
        this.fail++;
    }

    public int getDraw() {
        return draw;
    }

    public void addDraw() {
        this.draw++;
    }
}
