import java.io.*;
import java.util.Scanner;

public class JFuck {
    public static void interpret(String code) {
        Scanner read = new Scanner(System.in);
        int ptr = 0;
        byte[] tape = new byte[30000];
        for (int i = 0; i < code.length(); i++) {
            if (ptr < 0) {
                ptr = tape.length - 1;
            } else if (ptr > tape.length) {
                ptr = 0;
            }
            switch (code.charAt(i)) {
                case '>':
                    ptr++;
                    break;
                case '<':
                    ptr--;
                    break;
                case '+':
                    tape[ptr]++;
                    break;
                case '-':
                    tape[ptr]--;
                    break;
                case '.':
                    System.out.print((char) tape[ptr]);
                    break;
                case ',':
                    try {
                        tape[ptr] = (byte) System.in.read();
                        if (tape[ptr] == '\r') {
                            System.in.read();
                            tape[ptr] = '\n';
                        }
                    } catch (IOException ignored) {
                    }
                    break;
                case '[':
                    if (tape[ptr] == 0) {
                        int skipBrc = 1;
                        for (int j = i + 1; j < code.length(); j++) {
                            if (code.charAt(j) == '[') skipBrc++;
                            else if (code.charAt(j) == ']') skipBrc--;
                            if (skipBrc == 0) {
                                i = j;
                                break;
                            }
                        }
                    }
                    break;
                case ']':
                    int skipBrc = 1;
                    for (int j = i - 1; j > 0; j--) {
                        if (code.charAt(j) == ']') skipBrc++;
                        else if (code.charAt(j) == '[') skipBrc--;
                        if (skipBrc == 0) {
                            i = j - 1;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        String code;
        try {
            code = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return;
        } catch (Exception e) {
            System.err.println("Usage: java -jar " + new File(JFuck.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() + " filename");
            return;
        }
        interpret(code);
    }
}
