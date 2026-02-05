package gradeautomator;

import java.util.Scanner;

/**
 * @authors
 * OGWANG GIFT GIDEON   VU-BCS-2503-0706-EVE
 * SUUBI DEBORAH 	VU-DIT-2503-1213-EVE
 * NYEBA OSCAR MATHEW	VU-BCS-2503-1204-EVE 
 * NALWOGA MADRINE      VU-BIT-2503-2460-EVE 
 * NAMAGAMBE PRECIOUS   VU-BSC-2503-0355-EVE
 * KINTU BRIAN          VU-DIT-2503-0306-EVE
 * 
 */
public class GradeAutomator {
    
    // Constant: Defined once and can run everywhere
    private static final int NUM_STUDENTS = 5;
    private static final int MIN_MARK = 0;
    private static final int MAX_MARK = 100;
    
    // Grade boundaries using parallel arrays that share an index (index = gradeValue - 1)
    private static final int[] GRADE_BOUNDARIES = {80, 75, 66, 60, 50, 45, 35, 30, 0};
//    private static final String[] GRADE_REMARKS = {"D1", "D2", "C3", "C4", "C5", "C6", "P7", "P8", "F"};
    
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        message("=== Welcome to GradeAutomator ===");
        
        int[] marks = getMarks(scanner, NUM_STUDENTS);
        
        int[] gradeCounts = calculateGradeDistribution(marks);
        
        showResults(marks, gradeCounts);
        
        scanner.close();

    }
    
    // Prints message to the screen
    private static void message(String message) {
        System.out.println(message);
    }
    
    private static int[] getMarks(Scanner scanner, int studentsNum){
        int[] marks = new int[studentsNum];
        
        for (int i=0; i<studentsNum; i++) {
            marks[i] = getValidMark(scanner, i+1);
        }
        
        return marks;
    }
    
    private static int getValidMark(Scanner scanner, int studentNum){
        System.out.printf("Enter marks for student %d: ", studentNum);
        
        while (true) {
            // Ensures user enters the correct type int and not any other type
            if (!scanner.hasNextInt()){
                message("Please enter a valid number...");
                scanner.next(); // This clears the garbage value stored if a wrong value was entered
                continue;
            }
            
            int mark = scanner.nextInt();
            
            if (mark >= MIN_MARK && mark <= MAX_MARK) {
                return mark;
            } else {
                // printf: Prints a formated string
                // %d is a place holder for an integer value
                System.out.printf("Marks must be between %d and %d: ",MIN_MARK,MAX_MARK);
            }
        }
    }

    private static int[] calculateGradeDistribution(int[] marks){
        // Counts how many students got a particular grade
        int[] gradeCounts = new int[GRADE_BOUNDARIES.length];
        
        for (int mark: marks) {
            int gradeIndex = getGradeIndex(mark);
            gradeCounts[gradeIndex]++;
        }
        
        return gradeCounts;
    }
    
    private static int getGradeIndex(int mark){
        // Checks and returns the grade index i.e grade1 is 0 index
        for (int i=0;i<GRADE_BOUNDARIES.length;i++){
            if (mark >= GRADE_BOUNDARIES[i]){
                return i;
            }
        }
        
        return GRADE_BOUNDARIES.length - 1; // If all fails,, return the last index for the last grade
    }

    private static void showResults(int[] marks, int[] gradeCounts) {
        message("===Grades and number of students who attained them===");        
        message("Grade===Students");
        
        for (int i=0;i<gradeCounts.length;i++){
            System.out.printf("%d===%d\n", i+1, gradeCounts[i]);
        }
    }
    
}