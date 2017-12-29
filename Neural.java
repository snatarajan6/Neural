import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Neural {

    private static void getWeights(ArrayList<Double> w, String[] args) {
        w.add(0.0);
        for(int i= 1 ; i< 10 ; i++){
            w.add(Double.valueOf(args[i]));
        }
    }
    private static double computeUA(ArrayList<Double> w, double x1, double x2){

        double ua = 0.0;

        ua = w.get(1) + x1*w.get(2) + x2*w.get(3);

        return ua;
    }
    private static double computeUB(ArrayList<Double> w, double x1, double x2){

        double ub = 0.0;

        ub = w.get(4) + x1*w.get(5) + x2*w.get(6);
        return ub;

    }
    private static double computeUC(ArrayList<Double> w, double ua , double ub){

        double uc = 0.0;

        uc = w.get(7) + ua*w.get(8) + ub*w.get(9);

        return uc;
    }
    private static void computePardevOfWeights(ArrayList<Double> parDev_w, double parDev_ua, double parDev_ub, double parDev_uc, double va, double vb, double x1, double x2) {

        parDev_w.add(0.0);
        parDev_w.add(parDev_ua);
        parDev_w.add(x1*parDev_ua);
        parDev_w.add(x2*parDev_ua);
        parDev_w.add(parDev_ub);
        parDev_w.add(x1*parDev_ub);
        parDev_w.add(x2*parDev_ub);
        parDev_w.add(parDev_uc);
        parDev_w.add(va*parDev_uc);
        parDev_w.add(vb*parDev_uc);

    }


    public static void main(String[] args) {
        // write your code here
        int flag = Integer.valueOf(args[0]);
        ArrayList<Double> w = new ArrayList<>();
        ArrayList<Double> parDev_w = new ArrayList<>();
        ArrayList<Double> updated_w = new ArrayList<>();
        getWeights(w, args);
        double x1, x2, y, E, parDev_uc, parDev_vc, parDev_va, parDev_vb, parDev_ua = 0.0, parDev_ub = 0.0, eta, evalError = 0.0;
        double ua, ub, uc, va, vb, vc;
        if (flag == 100) {
            x1 = Double.valueOf(args[10]);
            x2 = Double.valueOf(args[11]);
            ua = computeUA(w, x1, x2);
            ub = computeUB(w, x1, x2);


            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            System.out.println(String.format("%.5f %.5f %.5f %.5f %.5f %.5f", ua, va, ub, vb, uc, vc));
        }
        if (flag == 200) {
            x1 = Double.valueOf(args[10]);
            x2 = Double.valueOf(args[11]);
            y = Double.valueOf(args[12]);
            ua = computeUA(w, x1, x2);
            ub = computeUB(w, x1, x2);
            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            E = 0.5 * Math.pow((vc - y), 2);
            parDev_vc = vc - y;
            parDev_uc = vc * (1 - vc) * parDev_vc;

            System.out.println(String.format("%.5f %.5f %.5f", E, parDev_vc, parDev_uc));
        }
        if (flag == 300) {
            x1 = Double.valueOf(args[10]);
            x2 = Double.valueOf(args[11]);
            y = Double.valueOf(args[12]);
            ua = computeUA(w, x1, x2);
            ub = computeUB(w, x1, x2);
            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            E = 0.5 * Math.pow((vc - y), 2);
            parDev_vc = vc - y;
            parDev_uc = vc * (1 - vc) * parDev_vc;

            parDev_va = w.get(8) * parDev_uc;
            parDev_vb = w.get(9) * parDev_uc;

            if (ua >= 0) {
                parDev_ua = parDev_va;
            } else if (ua < 0) {
                parDev_ua = 0.0;
            }

            if (ub >= 0) {
                parDev_ub = parDev_vb;
            } else if (ua < 0) {
                parDev_ub = 0.0;
            }

            System.out.println(String.format("%.5f %.5f %.5f %.5f", parDev_va, parDev_ua, parDev_vb, parDev_ub));
        }

        if (flag == 400) {
            x1 = Double.valueOf(args[10]);
            x2 = Double.valueOf(args[11]);
            y = Double.valueOf(args[12]);

            ua = computeUA(w, x1, x2);
            ub = computeUB(w, x1, x2);
            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            E = 0.5 * Math.pow((vc - y), 2);
            parDev_vc = vc - y;
            parDev_uc = vc * (1 - vc) * parDev_vc;

            parDev_va = w.get(8) * parDev_uc;
            parDev_vb = w.get(9) * parDev_uc;

            if (ua >= 0) {
                parDev_ua = parDev_va;
            } else if (ua < 0) {
                parDev_ua = 0.0;
            }

            if (ub >= 0) {
                parDev_ub = parDev_vb;
            } else if (ua < 0) {
                parDev_ub = 0.0;
            }

            computePardevOfWeights(parDev_w, parDev_ua, parDev_ub, parDev_uc, va, vb, x1, x2);

            for (int i = 1; i <= 9; i++) {
                System.out.print(String.format("%.5f ", parDev_w.get(i)));
            }
        }
        if (flag == 500) {
            x1 = Double.valueOf(args[10]);
            x2 = Double.valueOf(args[11]);
            y = Double.valueOf(args[12]);
            eta = Double.valueOf(args[13]);

            ua = computeUA(w, x1, x2);
            ub = computeUB(w, x1, x2);
            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            E = 0.5 * Math.pow((vc - y), 2);
            parDev_vc = vc - y;
            parDev_uc = vc * (1 - vc) * parDev_vc;

            parDev_va = w.get(8) * parDev_uc;
            parDev_vb = w.get(9) * parDev_uc;

            if (ua >= 0) {
                parDev_ua = parDev_va;
            } else if (ua < 0) {
                parDev_ua = 0.0;
            }

            if (ub >= 0) {
                parDev_ub = parDev_vb;
            } else if (ua < 0) {
                parDev_ub = 0.0;
            }
            computePardevOfWeights(parDev_w, parDev_ua, parDev_ub, parDev_uc, va, vb, x1, x2);

            updated_w.add(0.0);
            for (int i = 1; i <= 9; i++) {
                updated_w.add(w.get(i) - (eta * parDev_w.get(i)));
            }

            for (int i = 1; i <= 9; i++) {
                System.out.print(String.format("%.5f ", w.get(i)));
            }
            System.out.println(String.format("\n%.5f", E));

            for (int i = 1; i <= 9; i++) {
                System.out.print(String.format("%.5f ", updated_w.get(i)));
            }

            ua = computeUA(updated_w, x1, x2);
            ub = computeUB(updated_w, x1, x2);
            va = Math.max(ua, 0);
            vb = Math.max(ub, 0);
            uc = computeUC(updated_w, va, vb);
            vc = (1 / (1 + Math.exp(-uc)));

            E = 0.5 * Math.pow((vc - y), 2);
            System.out.println(String.format("\n%.5f", E));

        }
        if (flag == 600) {
            eta = Double.valueOf(args[10]);
            File f1 = new File("hw2_midterm_A_train.txt");
            try {
                Scanner scr = new Scanner(f1);
                while (scr.hasNextLine()) {
                    String str = scr.nextLine();
                    String strar[] = str.split(" ");
                    x1 = Double.parseDouble(strar[0]);
                    x2 = Double.parseDouble(strar[1]);
                    y = Double.parseDouble(strar[2]);

                    System.out.println(String.format("%.5f %.5f %.5f", x1, x2, y));
                    ua = computeUA(w, x1, x2);
                    ub = computeUB(w, x1, x2);
                    va = Math.max(ua, 0);
                    vb = Math.max(ub, 0);
                    uc = computeUC(w, va, vb);
                    vc = (1 / (1 + Math.exp(-uc)));

                    E = 0.5 * Math.pow((vc - y), 2);
                    parDev_vc = vc - y;
                    parDev_uc = vc * (1 - vc) * parDev_vc;

                    parDev_va = w.get(8) * parDev_uc;
                    parDev_vb = w.get(9) * parDev_uc;

                    if (ua >= 0) {
                        parDev_ua = parDev_va;
                    } else if (ua < 0) {
                        parDev_ua = 0.0;
                    }

                    if (ub >= 0) {
                        parDev_ub = parDev_vb;
                    } else if (ua < 0) {
                        parDev_ub = 0.0;
                    }
                    computePardevOfWeights(parDev_w, parDev_ua, parDev_ub, parDev_uc, va, vb, x1, x2);

                    updated_w.add(0.0);
                    for (int i = 1; i <= 9; i++) {
                        updated_w.add(w.get(i) - (eta * parDev_w.get(i)));
                    }
                    Collections.copy(w, updated_w);
                    updated_w.clear();
                    for (int i = 1; i <= 9; i++) {
                        System.out.print(String.format("%.5f ", w.get(i)));
                    }
                    System.out.println();
                    evalError = 0.0;
                    File f2 = new File("hw2_midterm_A_eval.txt");
                    try (Scanner scr2 = new Scanner(f2)) {
                        while (scr2.hasNextLine()) {
                            String str1 = scr2.nextLine();
                            String strar1[] = str1.split(" ");
                            x1 = Double.parseDouble(strar1[0]);
                            x2 = Double.parseDouble(strar1[1]);
                            y = Double.parseDouble(strar1[2]);
                            ua = computeUA(w, x1, x2);
                            ub = computeUB(w, x1, x2);
                            va = Math.max(ua, 0);
                            vb = Math.max(ub, 0);
                            uc = computeUC(w, va, vb);
                            vc = (1 / (1 + Math.exp(-uc)));
                            evalError += Math.pow((vc - y), 2);
                        }
                        System.out.println(String.format("%.5f", evalError / 2));
                    }
                    parDev_w.clear();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (flag == 700) {
            eta = Double.parseDouble(args[10]);
            int T = Integer.parseInt(args[11]);
            for (int j = 0; j < T; j++) {
                File f1 = new File("hw2_midterm_A_train.txt");
                try {
                    Scanner scr = new Scanner(f1);
                    while (scr.hasNextLine()) {
                        String str = scr.nextLine();
                        String strar[] = str.split(" ");
                        x1 = Double.parseDouble(strar[0]);
                        x2 = Double.parseDouble(strar[1]);
                        y = Double.parseDouble(strar[2]);
                        ua = computeUA(w, x1, x2);
                        ub = computeUB(w, x1, x2);
                        va = Math.max(ua, 0);
                        vb = Math.max(ub, 0);
                        uc = computeUC(w, va, vb);
                        vc = (1 / (1 + Math.exp(-uc)));

                        E =  Math.pow((vc - y), 2)/2;
                        parDev_vc = vc - y;
                        parDev_uc = vc * (1 - vc) * parDev_vc;

                        parDev_va = w.get(8) * parDev_uc;
                        parDev_vb = w.get(9) * parDev_uc;

                        if (ua >= 0) {
                            parDev_ua = parDev_va;
                        } else {
                            parDev_ua = 0.0;
                        }

                        if (ub >= 0) {
                            parDev_ub = parDev_vb;
                        } else{
                            parDev_ub = 0.0;
                        }
                        computePardevOfWeights(parDev_w, parDev_ua, parDev_ub, parDev_uc, va, vb, x1, x2);

                        updated_w.add(0.0);
                       // updated_w.se
                        for (int i = 1; i <= 9; i++) {
                            updated_w.add( w.get(i) - (eta * parDev_w.get(i)));
                        }

                        Collections.copy(w, updated_w);
                        updated_w.clear();
                        evalError = 0.0;
                        File f2 = new File("hw2_midterm_A_eval.txt");
                        try (Scanner scr2 = new Scanner(f2)) {
                            while (scr2.hasNextLine()) {
                                String str1 = scr2.nextLine();
                                String strar1[] = str1.split(" ");
                                x1 = Double.parseDouble(strar1[0]);
                                x2 = Double.parseDouble(strar1[1]);
                                y = Double.parseDouble(strar1[2]);
                                ua = computeUA(w, x1, x2);
                                ub = computeUB(w, x1, x2);
                                va = Math.max(ua, 0);
                                vb = Math.max(ub, 0);
                                uc = computeUC(w, va, vb);
                                vc = (1 / (1 + Math.exp(-uc)));
                                evalError += Math.pow((vc - y), 2);
                            }
                        }
                        parDev_w.clear();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                for (int i = 1; i <= 9; i++) {
                    System.out.print(String.format("%.5f ", w.get(i)));
                }

                System.out.println();
                System.out.println(String.format("%.5f", evalError / 2));
            }
        }
        if (flag == 800) {
            eta = Double.parseDouble(args[10]);
            int T = Integer.parseInt(args[11]);
            double epochEvalError = Double.MAX_VALUE;
            int j;
            for ( j = 0; j < T; j++) {
                File f1 = new File("hw2_midterm_A_train.txt");
                try {
                    Scanner scr = new Scanner(f1);
                    while (scr.hasNextLine()) {
                        String str = scr.nextLine();
                        String strar[] = str.split(" ");
                        x1 = Double.parseDouble(strar[0]);
                        x2 = Double.parseDouble(strar[1]);
                        y = Double.parseDouble(strar[2]);
                        ua = computeUA(w, x1, x2);
                        ub = computeUB(w, x1, x2);
                        va = Math.max(ua, 0);
                        vb = Math.max(ub, 0);
                        uc = computeUC(w, va, vb);
                        vc = (1 / (1 + Math.exp(-uc)));

                        E =  Math.pow((vc - y), 2)/2;
                        parDev_vc = vc - y;
                        parDev_uc = vc * (1 - vc) * parDev_vc;

                        parDev_va = w.get(8) * parDev_uc;
                        parDev_vb = w.get(9) * parDev_uc;

                        if (ua >= 0) {
                            parDev_ua = parDev_va;
                        } else {
                            parDev_ua = 0.0;
                        }

                        if (ub >= 0) {
                            parDev_ub = parDev_vb;
                        } else{
                            parDev_ub = 0.0;
                        }
                        computePardevOfWeights(parDev_w, parDev_ua, parDev_ub, parDev_uc, va, vb, x1, x2);

                        updated_w.add(0.0);
                        // updated_w.se
                        for (int i = 1; i <= 9; i++) {
                            updated_w.add( w.get(i) - (eta * parDev_w.get(i)));
                        }

                        Collections.copy(w, updated_w);
                        updated_w.clear();
                        evalError = 0.0;
                        File f2 = new File("hw2_midterm_A_eval.txt");
                        try (Scanner scr2 = new Scanner(f2)) {
                            while (scr2.hasNextLine()) {
                                String str1 = scr2.nextLine();
                                String strar1[] = str1.split(" ");
                                x1 = Double.parseDouble(strar1[0]);
                                x2 = Double.parseDouble(strar1[1]);
                                y = Double.parseDouble(strar1[2]);
                                ua = computeUA(w, x1, x2);
                                ub = computeUB(w, x1, x2);
                                va = Math.max(ua, 0);
                                vb = Math.max(ub, 0);
                                uc = computeUC(w, va, vb);
                                vc = (1 / (1 + Math.exp(-uc)));
                                evalError += Math.pow((vc - y), 2);
                            }
                        }
                        parDev_w.clear();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                evalError /= 2;

                if(j!=0 && evalError > epochEvalError){
                    break;
                }
                epochEvalError = evalError;
            }
            System.out.println(j+1);
            for (int i = 1; i <= 9; i++) {
                System.out.print(String.format("%.5f ", w.get(i)));
            }
            System.out.println();
            System.out.println(String.format("%.5f", evalError));

            File testfile = new File("hw2_midterm_A_test.txt");
            double testsetAccuracy = 0;
            int testsetCount = 0;
            int testsetCorrect = 0;
            try (Scanner scr3 = new Scanner(testfile)) {
                while (scr3.hasNextLine()) {
                    testsetCount++;
                    String str = scr3.nextLine();
                    String strar[] = str.split(" ");
                    x1 = Double.parseDouble(strar[0]);
                    x2 = Double.parseDouble(strar[1]);
                    y = Double.parseDouble(strar[2]);
                    ua = computeUA(w, x1, x2);
                    ub = computeUB(w, x1, x2);
                    va = Math.max(ua, 0);
                    vb = Math.max(ub, 0);
                    uc = computeUC(w, va, vb);
                    vc = (1 / (1 + Math.exp(-uc)));

                    if(Double.compare(vc,0.5)>=0){
                        if(Double.compare(y,1)==0)
                            testsetCorrect++;
                    }
                    else{
                        if(Double.compare(y,0)== 0){
                            testsetCorrect++;
                        }

                    }
                }
                testsetAccuracy = (double)testsetCorrect/testsetCount;
                System.out.println(String.format("%.5f", testsetAccuracy));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    }

