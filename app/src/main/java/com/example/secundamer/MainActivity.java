package com.example.secundamer;

import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class MainActivity extends AppCompatActivity //активность наследует методы от этого класса
{
    private TextView textViewTimer;
    private int seconds = 0;
    private boolean isRunning = false;
    public boolean wasRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// используется для востановления состояния
        super.onCreate(savedInstanceState); //onCreate , onDestroy связаны с созданием и уничтожением активности
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);// находим текстовое поле по id
        if (savedInstanceState != null) {//проверка если метод не пуст значит считываем значения по ключу
            seconds = savedInstanceState.getInt("seconds"); // для чтения Bundle
            isRunning = savedInstanceState.getBoolean("isRunning"); // для чтения Bundle
            wasRunning = savedInstanceState.getBoolean("wasRunning"); // для чтения Bundle
        }
        runTimer();// запуск метода
    }

    @Override
    protected void onResume() { //получение и потеря фокуса активности
        super.onResume();

    }

    @Override
    protected void onPause() {//получение и потеря фокуса активности
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//позволяет сохранить своё состояние перед уничтожением
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);  //добавления значения в Bundle
        outState.putBoolean("isRunning", isRunning);//добавления значения в Bundle
        outState.putBoolean("wasRunning", wasRunning);//добавления значения в Bundle
    }
    public void onClickStartTimer(View view) {
        isRunning = true;// таимер запускается
    }

    public void onClickPauseTimer(View view) {
        isRunning = false;//таймер остановлен и добавлять сек не нужно
    }

    public void onClickResetTimer(View view) {
        isRunning = false;// таимер сброшен и добавлять сек не нужно
        seconds = 0;
    }


    public void runTimer() {
        final Handler handler = new Handler(); //используется для планирования выполнения или передачи кода другому потоку
        handler.post(new Runnable() { //указывает что нужно сделать в первую очередь
            @Override
            public void run() {
                int hours = seconds / 3600; // потому-что в 1мин 60 секунд, а в 1ч 60мин 60*60 = 3600
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                textViewTimer.setText(time);
                if (isRunning) {// проверка переменной и если значение перем труе
                    seconds++; //то добавляется по одной секунде
                }
                handler.postDelayed(this, 1000);//вызыв того же самого какой код нужно запустить в первую очередь только с задержкой милисекундах
            }
        });


    }
}
//только главный поток может обновлять пользовательский интерфеис