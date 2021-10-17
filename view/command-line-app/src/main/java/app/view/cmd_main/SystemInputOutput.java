package app.view.cmd_main;

import app.adapter.controller.GenericController;

import java.util.Scanner;

public class SystemInputOutput implements GenericController.ISystemInputOutput {
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
