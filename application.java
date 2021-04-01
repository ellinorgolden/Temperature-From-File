import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.util.Timer;


public class application {

    //private static HttpURLConnection connection;

    public static void main(String[] args) throws Exception {

        List<Integer> tempArr = new ArrayList<>();
        File temperature = new File(("recruitment-tasks-master/temperature-sensor/temperature.txt"));
        Date appStart = new Date();


        Timer timer = new Timer();

        //read getTemperature() every 100ms.
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    int newTemp = getTemperature(temperature);
                    /* Convert to celsius.
                     If ADC signal is 3000 and (Tmax = 50, Tmin = -50)
                     12 bit adc equals values 0 - 4095 (2^12 -1).
                     TempC = ( (3000/4096)* (50 – (-50) ) – 50 = (0.7324 * 100) – 50 =  23,24 grader*/
                    double temp = (newTemp / (Math.pow(2, 12) - 1) * 100) - 50;
                    int tempC = (int) temp;
                    tempArr.add(tempC);


                    System.out.println(newTemp + " (rougly " + tempC + "C)");
                    // System.out.println(tempArr);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        //Print out max,min and average temperature over a period of 2 minutes.
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {

                Date appStopp = new Date();
                System.out.println("start: "+ appStart);
                System.out.println("end: "+ appStopp);

                //Find max
                Object max = Collections.max(tempArr);
                System.out.println("Max: " + max);
                //Fin min
                Object min = Collections.min(tempArr);
                System.out.println("Min: " + min);
                //Find average
                int total = 0;
                for (Integer temp : tempArr) {
                    total += temp;
                }
                int average = total / tempArr.size();
                System.out.println("Average: " + average);


            }
        };

        //executing task1 every 100ms
        timer.scheduleAtFixedRate(task1, 0, 100);
        //executing task12 every 2min
        timer.scheduleAtFixedRate(task2, 6000, 120000);

    }


        //Get a random line from file, convert it from String to int, trim and return.
        public static int getTemperature(File temperature) throws IOException {
        String result = null;
        Random rand = new Random();
        int count = 0;

        for(Scanner sc = new Scanner(temperature); sc.hasNext(); ){
            ++count;
            String line = sc.nextLine();
            if(rand.nextInt(count) == 0)
                result = line;

        }
            int temp = Integer.parseInt(result.trim());
            return temp;
        }




}





