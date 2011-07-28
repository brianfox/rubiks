package com.seefoxrun.rubiks.apps.interactive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.seefoxrun.rubiks.interactive.Interpreter;
import com.seefoxrun.rubiks.interactive.Interpreter.InterpreterException;


public class TextPlayer {

	public static void main(String[] args) {
		Interpreter myPlayer = new Interpreter(2);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println(myPlayer);
			while (true) {
				System.out.println("COMMAND: [print]");
				String input = in.readLine();
				try { 
					myPlayer.interpret(input, System.out); 
				}
				catch (InterpreterException e) {
					System.err.println("Could not complete command: " + e.getMessage());
				}
			} 
		}
		catch (IOException e) {
			System.err.println("An IO error occurred: " + e.getMessage());	
		}
	}
}
