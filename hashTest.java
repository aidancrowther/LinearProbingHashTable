import java.util.Scanner;

public class hashTest{

    private static linearHash<String> linear = new linearHash<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){

        Boolean running = true;

        while(running){

            System.out.print("\n\nA)dd string to table\nD)elete string from table\nF)ind string in table\nG)et load factor\nP)rint tables\nE)xit\n\n>");

            switch(scanner.nextLine().toLowerCase()){
                case("e"):
                    running = false;
                break;

                case("a"):
                    System.out.print("Input string to add: ");
                    System.out.println(linear.insert(scanner.nextLine()));
                break;

                case("t"):
                    System.out.print("Input: ");
                    System.out.println((scanner.nextLine().hashCode()%10));
                break;

                case("p"):
                    System.out.println(linear.toString());
                break;

                case("d"):
                    System.out.print("Input string to delete: ");
                    System.out.println(linear.delete(scanner.nextLine()));
                break;

                case("g"):
                    System.out.println("\n\nLoad is: "+linear.getLoad()+"\n\n");
                break;

                case("f"):
                    System.out.print("Input string to find: ");
                    System.out.println(linear.get(scanner.nextLine()));
                break;
            }
        }

    }

}