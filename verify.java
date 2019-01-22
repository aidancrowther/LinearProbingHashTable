import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class verify{

    private static linearHash<String> hash = new linearHash<>();
    private static Random r = new Random();

    public static void main(String[] args){

        int numStrings = 1000;

        if(args.length > 0) numStrings = Integer.parseInt(args[0]);

        String[] strings = new String[numStrings];

        long firstTime = 0;
        long avgTime = 0;
        long worstTime = 0;
        long measuredTime = 0;
        long initTime = System.currentTimeMillis();

        long startTime = 0;
        int count = 0;
        int percentage = 0;

        startTime = System.nanoTime();

        while(count < numStrings){

            String toAdd = randomAlphaNumeric(18);
            if(!Arrays.asList(strings).contains(toAdd)) strings[count++] = toAdd;

        }

        System.out.println("Strings generated in: "+(System.nanoTime()-startTime)/1000000+"ms");

        for(int i = 0; i<numStrings; i++){

            if(i == 0){
                startTime = System.nanoTime();
                hash.insert(strings[i]);
                firstTime = System.nanoTime()-startTime;
                avgTime += firstTime;
            }
            else{
                startTime = System.nanoTime();
                hash.insert(strings[i]);
                measuredTime = System.nanoTime()-startTime;
                if(measuredTime > worstTime) worstTime = measuredTime;
                avgTime += measuredTime;
            }
            if(i%(numStrings/10) == 0) System.out.println((percentage++)*10+"%");

        }

        System.out.print("\n");
        System.out.println("First: "+firstTime+"ns");
        System.out.println("Average: "+(avgTime)/numStrings+"ns");
        System.out.println("Worst: "+worstTime+"ns");

        Boolean allThere = true;
        worstTime = 0;
        avgTime = 0;

        for(String s : strings){
            startTime = System.nanoTime();
            allThere &= hash.get(s) != -1;
            measuredTime = System.nanoTime()-startTime;
            if(measuredTime > worstTime) worstTime = measuredTime;
            avgTime += measuredTime;
        }

        System.out.print("\n");
        System.out.println("All entries found: "+allThere);
        System.out.println("Average: "+(avgTime)/numStrings+"ns");
        System.out.println("Worst: "+worstTime+"ns");
        System.out.println("\nTotal: "+(System.currentTimeMillis()-initTime)+"ms");

    }


    public static String randomAlphaNumeric(int count){

        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }

}