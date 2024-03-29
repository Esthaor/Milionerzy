package converter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @since 2018-05-21, 02:46:04
 * @author Grzegorz Majchrzak
 */
public class Converter {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("../../files/questionsRaw/questions.txt"));
        String everything = null;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line.trim());
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        StringBuilder res = new StringBuilder();
        String[] split = everything.split("\n\n");
        String lastThreshold = null;
        for (String s : split)
            if (s.startsWith("<new>"))
                lastThreshold = s.substring(5);
            else {
                String[] q = s.split("\n");
                String questionText = q[0];
                String correctAnswer = q[1].startsWith("<true>") ? q[1].substring(6) : q[2].startsWith("<true>") ? q[2].substring(6) : q[3].startsWith("<true>") ? q[3].substring(6) : q[4].substring(6);
                List<String> temp = new ArrayList<>();
                if (!q[1].startsWith("<true>"))
                    temp.add(q[1]);
                if (!q[2].startsWith("<true>"))
                    temp.add(q[2]);
                if (!q[3].startsWith("<true>"))
                    temp.add(q[3]);
                if (!q[4].startsWith("<true>"))
                    temp.add(q[4]);
                String answer0 = temp.get(0);
                String answer1 = temp.get(1);
                String answer2 = temp.get(2);
                
                res.append("{\"")
                        .append(questionText)
                        .append("\", \"")
                        .append(lastThreshold)
                        .append("\", \"")
                        .append(correctAnswer)
                        .append("\", \"")
                        .append(answer0)
                        .append("\", \"")
                        .append(answer1)
                        .append("\", \"")
                        .append(answer2)
                        .append("\"},\n");
            }
        System.out.println(res.toString());
    }

}
