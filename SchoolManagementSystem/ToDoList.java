import java.util.InputMismatchException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

public class ToDoList {

    private static LinkedList linkedList = new LinkedList();

    public static void ToDo(String FileName) {

        Scanner scanner = new Scanner(System.in);

        boolean selection = true;
       
        while(selection) {

            try {
                    System.out.println();
                    System.out.println("Please select:");
                    System.out.println();
                    System.out.println("1. View to-do list");
                    System.out.println("2. Add to to-do list");
                    System.out.println("3. Remove from to-do list");
                    System.out.println("4. Edit to-do list");
                    System.out.println("5. Go back");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch(choice) {

                        case 1:
                                if(linkedList.ToDoListExists(FileName)) {

                                } else{linkedList.CreateToDoLIST();
                                
                                    }

                                linkedList.readFromFile(FileName);
                                linkedList.printList();
                                break;


                        case 2:
                                try{    
                                        if(linkedList.ToDoListExists(FileName)) {

                                            } else{linkedList.CreateToDoLIST();
                                
                                        }
                                        System.out.println("Please add the task:");
                                        System.out.println();
                                        String Task = scanner.nextLine();
                                        linkedList.insert(Task);
                                        linkedList.writeToFile(FileName);
                                        System.out.println();
                                        System.out.println("Thank you. It has been read. You may go and view it.");
                                        

                                } catch(InputMismatchException e){}
                                break;





                        case 3:
                                try{
                                        if(linkedList.ToDoListExists(FileName)) {

                                            } else{linkedList.CreateToDoLIST();
                                
                                        }

                                        System.out.println();
                                        System.out.println("Please enter the starting number of the task you would like to remove: ");
                                        int indexToRemove = scanner.nextInt();
                                        scanner.nextLine();
                                        linkedList.deleteAt(indexToRemove);
                                        linkedList.writeToFile(FileName);




                                }catch(NullPointerException e){System.out.println("The list is empty. Please add a task.");
                                }catch(InputMismatchException e){scanner.nextLine();}
                                break;
                                



                        case 4:
                                
                            try{
                                if(linkedList.ToDoListExists(FileName)) {

                                } else{linkedList.CreateToDoLIST();
                                
                                    }

                                boolean CheckIt = true;
                                while(CheckIt) {

                                if(linkedList.CheckIsEmpty(FileName)) {
                                System.out.println("The list is empty. Please add something to it.");
                                break;
                                    }
                                System.out.println();
                                System.out.println("Please enter the starting number of the task you would like to edit ");
                                int indexToRemove = scanner.nextInt();
                                scanner.nextLine();
                                
                                boolean isIndexValid = linkedList.checkIndex(indexToRemove);
                                if(isIndexValid){   
                                    
                                    System.out.println("Please input a suitable number");
                                }
                                
                                else{
                                System.out.println();
                                System.out.println("Please enter the updated task");
                                String newTask = scanner.nextLine();
                                linkedList.insertAt(indexToRemove, newTask);
                                linkedList.writeToFile(FileName);
                                break;

                                    }

                                }
                            } catch(InputMismatchException e){}
                            break;
                        
                        case 5:
                                System.out.println("Leaving now!");
                                selection = false;
                                break;
                    }


            } catch(InputMismatchException e) {scanner.nextLine();}


        }
        scanner.close();

    } 
}


class Node {

    String payload;
    Node next;

}

class LinkedList {

    Node head;


    public void insert(String payload) {

    if(taskExists(payload)) {

    Node node = new Node();
    node.payload = payload;
    node.next = null;


    if(head == null) {

        head = node;

    } else {

            Node cursor = head;
            while(cursor.next != null) {

                    cursor = cursor.next;

            }
            cursor.next = node;

            }
        }
    }

    public boolean CheckIsEmpty(String FileName) {

        File file = new File(FileName);
        return file.length() == 0;
    }

    public void CreateToDoLIST() {

        try{ 
                String FileName = "ToDoList.txt";
                File file = new File(FileName);
                file.createNewFile();

        } catch(IOException e) {}

    }

    public boolean ToDoListExists(String FileName) {

        File file = new File(FileName);
        return file.exists();
    }


    public void printList() {
    
        try{

        Node node = head;
        int index = 0;

            System.out.println("The to do list: ");
            System.out.println();

            while(node.next != null) {
                
                System.out.println(index + ". " + node.payload);
                System.out.println();
                node = node.next;
                index++;

                }
                
            System.out.println(index + ". " + node.payload);

        } catch(NullPointerException e) {
            
                System.out.println();
            
                System.out.println("Everything has been completed.");
        
        }

    }


    public void insertAtStart(String payload) {

        Node node = new Node();
        node.payload = payload;
        node.next = null;
        node.next = head;
        head = node;

    }

    public void insertAt(int index, String payload) {
        
            Node node = new Node();
            node.payload = payload;
            node.next = null;

            if (index < 0) {

                System.out.println("Please enter a suitable number above 0: ");
                return;

            }

            if(index == 0) {

                node.next = head.next;
                head = node;
                System.out.println();
                System.out.println("To-Do-List updated.");

            } else{

                    Node cursor = head;
                    for(int i = 0; i < index - 1; i++) {

                        if(cursor == null) {

                            System.out.println("Please enter a suitable number within the list");
                            return;
                        }

                        cursor = cursor.next;

                        }
                    
                        if(cursor != null && cursor.next != null) {

                    node.next = cursor.next.next;
                    cursor.next = node;

                        }
                }

    }

    public boolean checkIndex(int Index) {

        if (Index < 0) {

            return true;
        }

        Node cursor = head;
        for(int i = 0; i < Index && cursor !=null; i++) {
            cursor = cursor.next;
        }
        return cursor == null;
    }

    public void deleteAt(int index) {

        if(index < 0) {
            System.out.println("Please input a suitable number above 0");
            return;
        }

        if(index == 0 ) {
            
            head = head.next;
            System.out.println("Task successfully removed");

    } else {
            Node cursor = head;
            for(int i = 0; i < index - 1; i++) {

                if(cursor == null) {

                    System.out.println("Please enter a suitable number within the list.");
                    return;

                }
                cursor = cursor.next;
                
            }
            if(cursor != null && cursor.next != null) {

                cursor.next = cursor.next.next;
                System.out.println("Task successfully removed");

            } else {
                
                System.out.println("Please enter a suitable number within the list.");
            
            }

        }

    }

    public void writeToFile(String filename) {

        try(PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            Node cursor = head;
            while(cursor != null) {

                writer.println(cursor.payload);
                cursor = cursor.next;

            }

        } catch(IOException e){}

    }

    public void readFromFile(String filename) {


       try(Scanner scanner = new Scanner(new File(filename))) {

                while((scanner.hasNextLine())) {
                    String task = scanner.nextLine();
                    insert(task);

            }

        } catch(FileNotFoundException e) {}
    }


    private boolean taskExists(String task) {

        Node cursor = head;
        while(cursor!=null) {

            if(cursor.payload.equalsIgnoreCase(task)) {

                return false;
            }
            cursor = cursor.next;
        }
        return true;

    }
 
}
