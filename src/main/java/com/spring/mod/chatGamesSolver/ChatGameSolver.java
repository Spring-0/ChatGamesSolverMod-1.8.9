package com.spring.mod.chatGamesSolver;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class ChatGameSolver {
    public static final File answeredQuestions = new File("SpringAnsweredQuestions.txt");
    public static final File unansweredQuestions = new File("SpringUnansweredQuestions.txt");
    private String prefix = "";
    public Random rnd = new Random();

    public ChatGameSolver() {
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public static void createConfigFile() {

        try {
            if (answeredQuestions.createNewFile()) {
                System.out.println("Answered questions file created!");
            }
            if (unansweredQuestions.createNewFile()) {
                System.out.println("Unanswered questions file created");
            }
        } catch (IOException e) {
            System.out.println("An error occurred creating one of the files.");
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {

        String msg = event.message.getUnformattedText();
        if (msg.startsWith("EnchantedMC")) {
            if (!msg.contains("answered the question")) {
                if (msg.contains("Solve")) {

                    int mathResult = solveQuestion(msg);
                    if (mathResult != 0) {
                        Util.sendMCMessage(Integer.toString(mathResult), 3400, 4900);
                        return;
                    }

                } else if (msg.contains("Scramble")) {
                    String[] scrambledLetters = msg.split(" ")[3].split("");
                    Arrays.sort(scrambledLetters);
                    String scrambleResult = getScrambleAnswer(scrambledLetters);
                    if (!scrambleResult.equals("false")) {
                        Util.sendMCMessage(scrambleResult);
                        return;
                    }
                }
                String result = isInAnsweredQuestions(msg);
                if (!result.equals("false")) {
                    Util.sendMCMessage(result);
                } else {
                    System.out.println("Calling write new question");
                    if (!msg.contains("answered the question")) {
                        writeNewQuestion(msg);
                    }
                }
            }
        }
    }

    public static void writeNewQuestion(String q) {

        try {
            Writer output = new BufferedWriter(new FileWriter(unansweredQuestions, true));
            FileReader fileInvc = new FileReader(unansweredQuestions);
            BufferedReader readervc = new BufferedReader(fileInvc);
            String readvc = readervc.readLine();

            while (readvc != null) {
                if (readvc.contains(q)) {
                    System.out.println("Question is already here, just needs to be answered");
                    return;
                }
                readvc = readervc.readLine();

            }
            output.append(q + "\n");
            output.close();
            System.out.println("Wrote new question to file because it does  not already exist");


        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String isInAnsweredQuestions(String q) {
        try {
//            FileReader fileInvc = new FileReader(answeredQuestions);
//            BufferedReader readervc = new BufferedReader(fileInvc, UTF);
            BufferedReader readervc = new BufferedReader(new InputStreamReader(new FileInputStream(answeredQuestions), "UTF8"));
            String readvc = readervc.readLine();
            while (readvc != null) {
                System.out.println("READVC: --> " + readvc);
                System.out.println("Q: --> " + q);
                if (readvc.replace("»","").contains(q.replace("»","")) && readvc.replace("»","").contains(":")) {
                    return readvc.split(":")[1];
                }
                readvc = readervc.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    public static int solveQuestion(String q){
        // EnchantedMC >> Solve 130-10 to get a reward
        String mathQ = q.split(" ")[3];
        if (mathQ.contains("-")) {
            String[] mathqArr = mathQ.split("-");
            return Integer.parseInt(mathqArr[0]) - Integer.parseInt(mathqArr[1]);
        } else if (mathQ.contains("+")) {
            String[] mathqArr = mathQ.split("\\+");
            return Integer.parseInt(mathqArr[0]) + Integer.parseInt(mathqArr[1]);
        }
        return 0;
    }

    public static String getScrambleAnswer(String[] sortedLetters) {
        try {
            FileReader fileInvc = new FileReader(answeredQuestions);
            BufferedReader readervc = new BufferedReader(fileInvc);
            String readvc = readervc.readLine();
            while (readvc != null) {
                if (readvc.contains("Scramble")) {
                    String[] tempSortedLetters = readvc.split(" ")[3].split("");
                    Arrays.sort(tempSortedLetters);
                    if(Arrays.equals(tempSortedLetters, sortedLetters)) {
                        return readvc.split(":")[1];
                    }
                }
                readvc = readervc.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    // Overloading method

}
