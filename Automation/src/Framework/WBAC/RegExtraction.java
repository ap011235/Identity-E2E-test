package Framework.WBAC;

import java.io.*;

import Framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.open;

public class RegExtraction extends Setup{

    //static WebDriver driver;
    //static String url;
    static ArrayList<String> regNumbers = new ArrayList<>();

    private static final String pFormat = "[A-Z]{2}\\d{2} [A-Z]{3}";
    private static final String pFormat2 = "[A-Z]{2}\\d{2}[A-Z]{3}";

    public static void extractPlate(String filePath) throws InterruptedException {

        String regex = "[A-Z]{2}\\d";
        BufferedReader car_input;
        {
            try {
                car_input = new BufferedReader(new FileReader(filePath));
                String row;
                try {
                    while ((row = car_input.readLine()) != null) {
                        for (int i = 0; i < row.length() - 1; i++) {
                            if (Character.isUpperCase(row.charAt(i)) && Character.isUpperCase(row.charAt(i + 1)) && Character.isUpperCase(row.charAt(i + 2))) {
                                regNumbers.add(row.substring(i-8, i));
                                System.out.println(row.substring(i, i + 8));
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<String> extractCarPlates(String filePath) {
        List<String> carPlates = new ArrayList<>();
        Pattern p1 = Pattern.compile("[A-Z]{2}\\d{2} [A-Z]{3}|[A-Z]{2}\\d{2}[A-Z]{3}");
        //Pattern p2 = Pattern.compile(pFormat2);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = p1.matcher(line);
                while (matcher.find()) {
                    carPlates.add(matcher.group());  // Add each matching plate to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return carPlates;
    }

    public static List<String> extractPlate2(String filePath) {
        List<String> regList = new ArrayList<>();
        String regformat = "[A-Z]{2}\\d{2} [A-Z]{3}";
        String regformat2 = "[A-Z]{2}\\d{2}[A-Z]{3}";
        Pattern regplate = Pattern.compile(regformat);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (regplate.matcher(line).matches()) {
                    regList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return regList;
    }

    public static String getFullDetails(String regNumber) throws Exception {

        String details;
        String currentURL;
        String currentURL2;

        Thread.sleep(3000);
        WBACNavigation.inputRegNumber(regNumber);
        WBACNavigation.inputMileage("15000");
        WBACNavigation.getValuation();
        Thread.sleep(3000);

        if (driver.getCurrentUrl().equals("https://www.webuyanycar.com/vehicle/check-registration/" + regNumber)){
            System.out.println("Register Number " + regNumber + " is not found in webuyanycar database");
            return "";
        } else {
            Thread.sleep(3000);
            String plateNS = WBACNavigation.getPlateNS();
            String make = WBACNavigation.getMake();
            String model = WBACNavigation.getModel();
            String year = WBACNavigation.getYear();
            details = regNumber + "," + make + "," + model + "," + year;
            return details;
        }


    }

    public static void compareWithOutput(String regNumber, String fullDetails) throws InterruptedException {
        BufferedReader car_output;
        {
            try {
                car_output = new BufferedReader(new FileReader("Resources/car_output.txt"));
                String rows;
                int contains = 0; int equals = 0; String rowValue = "";

                try {
                    while ((rows = car_output.readLine()) != null) {
                        if (rows.contains(regNumber)){
                            if (rows.equals(fullDetails)) {
                                equals = 1;
                            }
                            else {
                                contains = 1;
                                rowValue = rows;
                            }
                        }

                    }
                    if (equals == 1) {
                        System.out.println("Register Number " + regNumber + " was found in output file and ALL details of vehicle match." + "\n\n\n");
                    }
                    else if(contains == 1) {
                        System.out.println("Register Number " + regNumber + " was found in output file BUT details of vehicle don't match.");
                        System.out.println("Output file contains: " + rowValue);
                        System.out.println("Webuyanycar.com returned: " + fullDetails + "\n\n\n");
                    }
                    else {
                        System.out.println("Register Number " + regNumber + " was NOT found in output file" + "\n\n\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } catch(FileNotFoundException e){
                throw new RuntimeException(e);
            }

        }
    }


}
