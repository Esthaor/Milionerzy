package com.grzegorzm.wpam.milionerzy.logic;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;

public class GameSingleton {
    private static GameSingleton instance;
    private Question lastQuestion;

    private GameSingleton() {
    }

    public static GameSingleton getInstance() {
        if (instance == null)
            instance = new GameSingleton();
        return instance;
    }
}
