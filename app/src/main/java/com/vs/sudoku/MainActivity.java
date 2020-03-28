package com.vs.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity{

    private Button[][] bord = new Button[9][9];
    private Button[] num = new Button[9];
    public static int I,J;
    private Button btncheck;
    private Button btnclear;
    private Button btnundo;
    private Button btnsubmit;

    int max = 8;
    int min = 0;
    int digitMax = 9;
    int digitMin = 1;
    int easyMin = 36;
    int easyMax = 49;
    int mediumMin = 32;
    int mediumMax = 35;
    int hardMin = 22;
    int hardMax = 27;

    public int[][] grid = new int[9][9];
    public int[][] array = new int[9][9];
    private String[] generatedXY = new String[81];
    private int n = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncheck = findViewById(R.id.btncheck);
        btnclear = findViewById(R.id.btnclear);
        btnundo = findViewById(R.id.btnundo);
        btnsubmit = findViewById(R.id.btnsubmit);

        for (int x = 0; x<9; x++){
            int j = x + 1;
            String buttonID = "btn" + j;
            int findID = getResources().getIdentifier(buttonID,"id",getPackageName());
            num[x] = findViewById(findID);
            final int finalI1 = x;
            num[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bord[I][J].setText(num[finalI1].getText().toString());
                }
            });
        }
        for (int i = 0; i<9; i++){
            for (int j = 0; j<9; j++){
                String buttonID = "btn" + i + j;
                int findID = getResources().getIdentifier(buttonID,"id",getPackageName());
                bord[i][j] = findViewById(findID);
                final int finalI = i;
                final int finalJ = j;
                bord[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked(finalI,finalJ);
                    }
                });
            }
        }

        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        btnundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer();
            }
        });

        createBoard();

    }

    private void answer() {
        save();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int v;
                v = nonnull(i,j);
                if (noConflict(array,i,j,v)){
                    Toast.makeText(MainActivity.this, "abc", LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "def", LENGTH_SHORT).show();
                }
            }
        }
    }

    private int nonnull(int i, int j) {
        int v;
        if (bord[i][j].getText().toString().equals("")){
            v = 0;
        }else {
            v = Integer.parseInt(bord[i][j].getText().toString());
        }
        return v;
    }

    private void save() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!bord[i][j].getText().toString().trim().equals("")){
                    array[i][j] = Integer.parseInt(bord[i][j].getText().toString());
                }
            }
        }
    }

    private void saveBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0){
                    generatedXY = append(i,j);
                }
            }
        }
    }

    private String[] append(int i, int j) {
        generatedXY[n] = "("+i+","+j+")";
        n++;
        return generatedXY;
    }

    private void createBoard() {
        generateBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0){
                    if (bord[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.background1).getConstantState()){
                        bord[i][j].setText(String.valueOf(grid[i][j]));
                        bord[i][j].setBackgroundResource(R.drawable.background3);
                    } else if (bord[i][j].getBackground().getConstantState() == getResources().getDrawable(R.drawable.background2).getConstantState()){
                        bord[i][j].setText(String.valueOf(grid[i][j]));
                        bord[i][j].setBackgroundResource(R.drawable.background4);
                    }
                }
                else {
                    bord[i][j].setText("");
                }
            }
        }
        saveBoard();
    }

    private void generateBoard() {
        Random random = new Random();
        int row;
        int col;

        int randomNumber;
        int noOfCellsToBeGenerated;
        Intent intent = getIntent();
        String option = intent.getStringExtra("level");

        assert option != null;
        switch (option) {
            case "easy":
                noOfCellsToBeGenerated = random.nextInt((easyMax - easyMin) + 1) + easyMax;
                break;
            case "medium":
                noOfCellsToBeGenerated = random.nextInt((mediumMax - mediumMin) + 1) + mediumMax;
                break;
            case "hard":
                noOfCellsToBeGenerated = random.nextInt((hardMax - hardMin) + 1) + hardMin;
                break;
            default:
                noOfCellsToBeGenerated = random.nextInt(10);
                break;
        }

        for (int i = 1; i <= noOfCellsToBeGenerated; i++) {
            row = random.nextInt((max - min) + 1) + min;
            col = random.nextInt((max - min) + 1) + min;
            randomNumber = random.nextInt((digitMax - digitMin) + 1) + digitMin;

            if (grid[row][col] == 0 && noConflict(grid, row, col, randomNumber)) {
                grid[row][col] = randomNumber;
            } else {
                i--;
            }

        }
    }

    private void clear() {
        for (int i = 0; i<9; i++){
            for (int j = 0; j<9; j++){
                if(isEditable(i,j)){
                    bord[i][j].setText("");
                }
            }
        }
    }

    private boolean isEditable(int i, int j) {
        for (String s : generatedXY) {
            char[] m = s.toCharArray();
            char x = (char) (i + '0');
            char y = (char) (j + '0');
            Log.e("v", s);
            //Toast.makeText(MainActivity.this,String.valueOf(generatedXY.length),LENGTH_SHORT).show();
            return m[1] != x || m[3] != y;
        }
        return false;
    }

    private void clicked(final int finalI, final int finalJ) {
        if (isEditable(finalI,finalJ)){
            I = finalI;
            J = finalJ;
        }
    }
    public static boolean noConflict(int[][] array, int row, int col, int num) {

        for (int i = 0; i < 9; i++) {
            if (array[row][i] == num) {
                return false;
            }
            if (array[i][col] == num) {
                return false;
            }
        }

        int gridRow = row - (row % 3);
        int gridColumn = col - (col % 3);
        for (int p = gridRow; p < gridRow + 3; p++) {
            for (int q = gridColumn; q < gridColumn + 3; q++) {
                if (array[p][q] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
