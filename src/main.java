import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class main {

    static char[] xCrechnung = new char[100];
    static String[] xSrech = new String[100];
    static String rechnung;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        rechnung = sc.nextLine();

        xCrechnung = rechnung.toCharArray();

        // <dave> check if the calculation is valid
        main.validation();

        // <dave> get the char Array into a String Array
        main.charBackToString();

        // <dave> start calculating
        main.smash();
    }

    public static void validation() {
        int last = rechnung.length() - 1;

        // <dave> declare all invalid chars
        String allinvalid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZöäüÖÄÜ#~!§$&?}][{}°<>|;]";
        char[] xCalphabet = allinvalid.toCharArray();

        // <dave> declare numbers, calcstuff and math signs
        String numbers = "1234567890";
        String calcstuff = ",.";
        String mathsigns = "+-*/";
        boolean cs = false;
        boolean ms = false;
        boolean msaa = false;

        // <dave> check if invalid chars are involved
        for (int i = 0; i < xCalphabet.length; i++) {
            if (rechnung.indexOf(xCalphabet[i]) != -1) {
                main.invalid();
            }
        }

        // <dave> check if there is any incorrect syntax
        for (int i = 0; i < rechnung.length(); i++) {
            if (cs && calcstuff.indexOf(xCrechnung[i]) != -1) {
                main.invalid();
            }
            if (ms && calcstuff.indexOf(xCrechnung[i]) != -1) {
                main.invalid();
            }
            if (cs && mathsigns.indexOf(xCrechnung[i]) != -1) {
                main.invalid();
            }
            if (ms && mathsigns.indexOf(xCrechnung[i]) != -1) {
                main.invalid();
            }
            if (calcstuff.indexOf(xCrechnung[i]) != -1) {
                if (i != 0 && i != last) {
                    cs = true;
                } else {
                    main.invalid();
                }
            } else {
                cs = false;
            }
            if (mathsigns.indexOf(xCrechnung[i]) != -1) {
                // <dave> let the program know a math sign exists
                if (!msaa) {
                    msaa = true;
                }
                if (i != 0 && i != last) {
                    ms = true;
                } else {
                    main.invalid();
                }
            } else {
                ms = false;
            }
        }
        // <dave> check if there is a mathsign at all
        if (!msaa) {
            main.invalid();
        }

        System.out.println("Jo, {" + rechnung + "} scheint ne valide Rechnung zu sein.");

    }

    public static void smash() {
        // <dave> calculation String into char array
        String numbers = "1234567890";
        String calcstuff = ",.";
        String rech = "";
        String plus = "+";
        String min = "-";
        String mult = "*";
        String div = "/";

        // <dave> if its only plus und minus, go the easy way
        if (!rechnung.contains(mult) && !rechnung.contains(div)) {
            main.plusmine();
        } else {
            main.wholeRech();
        }
    }

    public static void invalid() {
        System.out.println("Invalid");
        System.exit(1);
    }

    public static void plusmine() {
        String minus = "-";
        String plus = "+";
        float zamre = 0;

        for (int i = 0; xSrech[i] != null; i++) {
            if (i == 0) {
                zamre = Float.parseFloat(xSrech[i]);
                i++;
            } else {
                if (plus.contains(xSrech[i - 1])) {
                    zamre = zamre + Float.parseFloat(xSrech[i]);
                } else {
                    zamre = zamre - Float.parseFloat(xSrech[i]);
                }
                i++;
            }
        }
        int j = (int) zamre;
        if (zamre > 0) {
            if (j < zamre) {
                System.out.println("Result: " + zamre);
            } else {
                System.out.println("Result: " + j);
            }
        } else {
            if (j > zamre) {
                System.out.println("Result: " + zamre);
            } else {
                System.out.println("Result: " + j);
            }
        }
    }

    public static void charBackToString() {
        String math = "+-*/";
        String clingopen = "(";
        String clingclose = ")";
        int count = 0;

        for (int i = 0; i < rechnung.length(); i++) {

            // <dave> if a char is cling
            if (clingopen.indexOf(xCrechnung[i]) != -1) {
                i++;
                while (clingclose.indexOf(xCrechnung[i]) == -1) {
                    if (xSrech[count] == null) {
                        xSrech[count] = "";
                    }
                    xSrech[count] = xSrech[count] + xCrechnung[i];
                    i++;
                }
                count++;
                xSrech[count] = "";
                i++;
            }

            if (math.indexOf(xCrechnung[i]) != -1) {
                count++;
                xSrech[count] = "" + xCrechnung[i];
                count++;
                xSrech[count] = "";
            } else {
                if (xSrech[count] == null) {
                    xSrech[count] = "";
                }
                xSrech[count] = xSrech[count] + xCrechnung[i];
            }
        }
    }

    public static void wholeRech() {
        String minus = "-";
        String plus = "+";
        String div = "/";
        String mult = "*";
        float zamre = 0;

        for (int i = 0; xSrech[i] != null; i++) {
            if (i == 0) {
                zamre = Float.parseFloat(xSrech[i]);
                i++;
            } else {
                if (plus.contains(xSrech[i - 1])) {
                    zamre = zamre + Float.parseFloat(xSrech[i]);
                }

                if (minus.contains(xSrech[i - 1])) {
                    zamre = zamre - Float.parseFloat(xSrech[i]);
                }

                if (mult.contains(xSrech[i - 1])) {
                    zamre = zamre * Float.parseFloat(xSrech[i]);
                }

                if (div.contains(xSrech[i - 1])) {
                    zamre = zamre / Float.parseFloat(xSrech[i]);
                }
                i++;
            }
        }

        int j = (int) zamre;
        if (zamre > 0) {
            if (j < zamre) {
                System.out.println("Result: " + zamre);
            } else {
                System.out.println("Result: " + j);
            }
        } else {
            if (j > zamre) {
                System.out.println("Result: " + zamre);
            } else {
                System.out.println("Result: " + j);
            }
        }

    }
}
