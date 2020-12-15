import java.io.*;

public class JFuckTranspiler {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(args[0])));
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return;
        } catch (Exception e) {
            System.err.println("Usage: java -jar " + new File(JFuckTranspiler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() + " filename");
            return;
        }
        FileWriter writer = new FileWriter(args[0].substring(0, args[0].lastIndexOf('.')) + ".c");
        writer.write("#include <stdio.h>\nint main(){unsigned char tape[30000] = { 0 };unsigned char* ptr = tape;");
        int ch = 0;
        while ((ch = br.read()) != -1) {
            int sameCh = 0;
            do {
                sameCh++;
                br.mark(1);
            } while (ch == br.read() && (ch == '>' || ch == '<' || ch == '+' || ch == '-'));
            br.reset();
            switch (ch) {
                case '>':
                    writer.write("ptr+=" + sameCh + ";");
                    break;
                case '<':
                    writer.write("ptr-=" + sameCh + ";");
                    break;
                case '+':
                    writer.write("*ptr+=" + sameCh + ";");
                    break;
                case '-':
                    writer.write("*ptr-=" + sameCh + ";");
                    break;
                case '.':
                    writer.write("putchar(*ptr);");
                    break;
                case ',':
                    writer.write("*ptr=getchar();");
                    break;
                case '[':
                    writer.write("while(*ptr){");
                    break;
                case ']':
                    writer.write("}");
                    break;
            }
        }
        writer.write("}");
        writer.close();
    }
}
