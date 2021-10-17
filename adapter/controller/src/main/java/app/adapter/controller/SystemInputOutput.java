package app.adapter.controller;

import java.util.Scanner;

public class SystemInputOutput implements ISystemInputOutput {
    private Scanner input;

    public SystemInputOutput(){
        input = new Scanner(System.in);
    }

    public void output(String message){
        System.out.println(message);
    }

    public String input(){
        return input.nextLine();
    }
}
