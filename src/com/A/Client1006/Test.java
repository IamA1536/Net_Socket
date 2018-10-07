package com.A.Client1006;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.println("Note: message@username(who you want to send message, default is all users), if you want to send it to all users, please @All");
        System.out.println("Please input user's name:");
        Scanner input = new Scanner(System.in);
        Client client = new Client(input.nextLine());
        client.ClientStart();
    }
}
