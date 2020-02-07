package com.example.gorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextInputEditText entradaDoValor;
    TextView porcentagem;
    SeekBar barra;
    TextView valorTotal;
    TextView valorGorjeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializando variáveis do Layout
        //Nessa Linha de código houve um erro de cast automático para textInputEditText, por isso fiz manualmente
        entradaDoValor  = (TextInputEditText)findViewById(R.id.textInputEntrada);
        porcentagem     = findViewById(R.id.textViewPercent);
        barra           = findViewById(R.id.seekBar);
        valorGorjeta     = findViewById(R.id.textViewValorGorjeta);
        valorTotal      = findViewById(R.id.textViewValorTotal);

        //Listeners
        listenerEntradaDoValor();
        listenerBarraDePorcetagem();


    }
    //Adicionando listner do SeekBar
    private void listenerBarraDePorcetagem() {
        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                porcentagem.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saidaDeDados();
            }
        });
    }

    //Permite que o calculo seja feito automáticamente após alteração do valor de entrada
    public void listenerEntradaDoValor() {
        entradaDoValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saidaDeDados();
            }
        });
    }

    public void saidaDeDados() {
        double valorDaGorjeta;
        double total;

        //Criando um novo formato para números decimais
        DecimalFormat saida = new DecimalFormat("0.00");


        valorDaGorjeta = calcularPorcentagem(barra.getProgress(), valorDeEntrada());
        total = valorDeEntrada()+valorDaGorjeta;

        valorGorjeta.setText("R$ " + saida.format(valorDaGorjeta));
        valorTotal.setText("R$ " + saida.format(total));
    }

    //Calcula a porcentagem em cima do valor colocado pelo usuário
    private double calcularPorcentagem(double porcentagem, double valor) {
        if(porcentagem != 0 || valor != 0) {
            porcentagem /= 100;

            return valor * porcentagem;
        }
        return 0;
    }

    // Captura valor de entrada e convertendo para tipo Double
    private double valorDeEntrada() {
        String valor = entradaDoValor.getText()
                                     .toString();

        //Verificando se String do campo de entrada está vazio ou com caracteres impossíveis de serem convertidos para double, caso o valor seja um caracter inválido um Toast será exibido
        if(valor.length() != 0) {
            switch(valor){
                case "":
                case ".":
                case ",":
                    Toast.makeText(
                            getApplicationContext(),
                            "Digite um valor Válido",
                            Toast.LENGTH_SHORT
                    ).show();
                    break;
                default:
                    return Double.parseDouble(valor);
            }
        }
        else if (barra.getProgress() != 0){
            //Exibe um Toast pedindo para que seja digitado um valor de comanda caso a barra de porcentagem não seja zero, se fosse não teria necessidade de pedir um valor a ser calculado
            Toast.makeText(getApplicationContext(),"Digite o valor da comanda", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    /*private double converterParaDouble(String texto) {
        try {
            return Double.parseDouble(texto);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Digite o valor da comanda", Toast.LENGTH_SHORT).show();
        }
    }*/
}