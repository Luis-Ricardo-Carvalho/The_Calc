package br.edu.ifsuldeminas.mch.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "calc";

    private TextView textViewResultado;
    private TextView textViewUltimaExpressao;

    private String expressao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);

        configurarBotoes();
    }

    private void configurarBotoes() {

        int[] numeros = {
                R.id.buttonZeroID, R.id.buttonUmID, R.id.buttonDoisID,
                R.id.buttonTresID, R.id.buttonQuatroID, R.id.buttonCincoID,
                R.id.buttonSeisID, R.id.buttonSeteID, R.id.buttonOitoID,
                R.id.buttonNoveID
        };

        for (int id : numeros) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> adicionarExpressao(btn.getText().toString()));
        }

        findViewById(R.id.buttonSomaID).setOnClickListener(v -> adicionarExpressao("+"));
        findViewById(R.id.buttonSubtracaoID).setOnClickListener(v -> adicionarExpressao("-"));
        findViewById(R.id.buttonMultiplicacaoID).setOnClickListener(v -> adicionarExpressao("*"));
        findViewById(R.id.buttonDivisaoID).setOnClickListener(v -> adicionarExpressao("/"));
        findViewById(R.id.buttonVirgulaID).setOnClickListener(v -> adicionarExpressao("."));
        findViewById(R.id.buttonPorcentoID).setOnClickListener(v -> calcularPorcentagem());
        findViewById(R.id.buttonIgualID).setOnClickListener(v -> calcularResultado());
        findViewById(R.id.buttonResetID).setOnClickListener(v -> resetar());
        findViewById(R.id.buttonDeleteID).setOnClickListener(v -> deletarUltimo());
    }

    private void adicionarExpressao(String valor) {
        expressao += valor;
        textViewResultado.setText(expressao);
    }

    private void calcularResultado() {
        try {
            Calculable calc = new ExpressionBuilder(expressao).build();
            double resultado = calc.calculate();

            textViewUltimaExpressao.setText(expressao);
            textViewResultado.setText(String.valueOf(resultado));

            expressao = String.valueOf(resultado);

        } catch (Exception e) {
            Log.e(TAG, "Erro: " + e.getMessage());
            textViewResultado.setText("Erro");
        }
    }

    private void resetar() {
        expressao = "";
        textViewResultado.setText("0");
        textViewUltimaExpressao.setText("");
    }

    private void deletarUltimo() {
        if (!expressao.isEmpty()) {
            expressao = expressao.substring(0, expressao.length() - 1);
            textViewResultado.setText(expressao.isEmpty() ? "0" : expressao);
        }
    }

    private void calcularPorcentagem() {
        try {
            double valor = Double.parseDouble(expressao);
            double resultado = valor / 100;

            textViewUltimaExpressao.setText(expressao + "%");
            textViewResultado.setText(String.valueOf(resultado));

            expressao = String.valueOf(resultado);

        } catch (Exception e) {
            textViewResultado.setText("Erro");
        }
    }
}