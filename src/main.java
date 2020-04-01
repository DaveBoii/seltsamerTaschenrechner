import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    static TextHandle handle;
    static String[] xSrech;
    static String rechnung;
    static float zamre = 0;
    static float interim;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        rechnung = sc.nextLine();
        sc.close();

        handle = new TextHandle(rechnung);

        // <dave> check if the calculation is valid
        main.validation();

        // <dave> get the char Array into a String Array
        // main.charBackToString();
        xSrech = charBackToString(handle);
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
            if (cs && calcstuff.indexOf(handle.get(i)) != -1) {
                main.invalid();
            }
            if (ms && calcstuff.indexOf(handle.get(i)) != -1) {
                main.invalid();
            }
            if (cs && mathsigns.indexOf(handle.get(i)) != -1) {
                main.invalid();
            }
            if (ms && mathsigns.indexOf(handle.get(i)) != -1) {
                main.invalid();
            }
            if (calcstuff.indexOf(handle.get(i)) != -1) {
                if (i != 0 && i != last) {
                    cs = true;
                } else {
                    main.invalid();
                }
            } else {
                cs = false;
            }
            if (mathsigns.indexOf(handle.get(i)) != -1) {
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

    public static String[] charBackToString(TextHandle inputHandle) {
        String math = "+-*/";
        String clingopen = "(";
        String clingclose = ")";

        List<String> list = new ArrayList<>();
        String current = "";

        for (int i = 0; i < inputHandle.lenght; i++) {

            // <dave> if a char is cling
            if (clingopen.indexOf(inputHandle.get(i)) != -1) {
                i++;
                while (clingclose.indexOf(inputHandle.get(i)) == -1) {
                    current = current + inputHandle.get(i);
                    i++;
                }
                list.add(current);
                current = "";
                i++;
            }

            if (math.indexOf(inputHandle.get(i)) != -1) {
                list.add(current);
                list.add("" + inputHandle.get(i));
                current = "";
            } else {
                current = current + inputHandle.get(i);
            }
        }

        list.add(current);
        String[] output = new String[list.size()];
        list.toArray(output);
        return output;
    }

    public static void wholeRech() {
        String minus = "-";
        String plus = "+";
        String div = "/";
        String mult = "*";

        for (int i = 0; i < xSrech.length; i++) {
            if (i == 0) {
                zamre = Float.parseFloat(xSrech[i]);
                i++;
            } else {
                if (plus.contains(xSrech[i - 1])) {
                    interim = 0;
                    i = main.checkForPoint(i);
                    if (interim == 0) {
                        zamre = zamre + Float.parseFloat(xSrech[i]);
                    } else {
                        zamre = zamre + interim;
                        if (i == xSrech.length - 1) {
                            break;
                        }
                    }
                }

                if (minus.contains(xSrech[i - 1])) {
                    interim = 0;
                    i = main.checkForPoint(i);
                    if (interim == 0) {
                        zamre = zamre - Float.parseFloat(xSrech[i]);
                    } else {
                        zamre = zamre - interim;
                        if (i == xSrech.length - 1) {
                            break;
                        }
                    }
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

    public static int checkForPoint(int i) {
        String div = "/";
        String mult = "*";
        interim = Float.parseFloat(xSrech[i]);

        // <dave> check in loop, if there is any * or / calculation, which needs to be done, before continue with +/-
        for (int j = i + 2; j != xSrech.length; j++) {
            if (xSrech[i + 1].equals("*") || xSrech[i + 1].equals("/")) {
                i = i + 2;
                if (mult.contains(xSrech[i - 1])) {
                    interim = interim * Float.parseFloat(xSrech[i]);
                }

                if (div.contains(xSrech[i - 1])) {
                    interim = interim / Float.parseFloat(xSrech[i]);
                }
            }
        }
        return i;
    }
}
