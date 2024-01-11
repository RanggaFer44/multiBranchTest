package org.example;

import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Main menu = new Main();


        System.out.println("choose option :\n (1) input new team \n " +
                "(2) input Score \n (3) delete team \n (4) winner ");
        String opt = sc.nextLine();

        menu.menu(opt);
    }


    public void menu (String menu) throws SQLException {
        Scanner sc = new Scanner(System.in);
        inputTeamScore its = new inputTeamScore();
        inputTeamScore.SoccerScore ts = new inputTeamScore.SoccerScore();
        while ((menu.equals("1")) || (menu.equals("2")) || (menu.equals("3")) || (menu.equals("4")) || menu != null){
            switch (menu){
                case "1" ->  {
                    System.out.println("Number : \n Team Name : ");
                    int in1 = Integer.parseInt(sc.nextLine());
                    String in2 = sc.nextLine();
                    its.inputTeam(in1, in2);
                    System.out.println("team input successfully");
                    break;
                }
                case "2" -> {
                    System.out.println(" team1: \n team2: \n score1:  \n score2: ");
                    String in1 = sc.nextLine();
                    String in2 = sc.nextLine();
                    int in3 = Integer.parseInt(sc.next());
                    int in4 = Integer.parseInt(sc.next());
                    ts.inputScore(in1, in2, in3, in4);
                    break;
                }
                case "3" -> {
                    System.out.println(" delete Team : ");
                    String in1 = sc.nextLine();
                    its.deleteTeam(in1);
                    break;
                }
                case "4" -> {
                    System.out.println(" Winner :  ");
                    its.winner();
                    break;
                }
                default -> {

                }

            }


            System.out.println(" option not available \n continue? y/n");
            String cn = sc.nextLine();
            if (cn.equals("y")){
                System.out.println("choose option :\n (1) input new team \n " +
                        "(2) input Score \n (3) delete team \n (4) show winner ");
                menu = sc.nextLine();
            } else if (cn.equals("n")){
                System.out.println("terminated");
                return;
            } else {
                System.out.println(" option not available");
                System.out.println("choose option :\n (1) input new team \n " +
                        "(2) input Score \n (3) delete team \n (4) show winner ");
                menu = sc.nextLine();
            }

        }
    }



}


