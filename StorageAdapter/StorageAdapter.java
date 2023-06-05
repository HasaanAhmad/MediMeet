
// ! DO NOT REMOVE COMMENTS

// it aint much, but its honest work - dont credit chatgpt or copilot please
// i drink malt and feel like im drinking beer

// Author: Mujtaba SP22-BSE-036 muhammadmujtaba150@gmail.com
// Date: May 26, 2023

// Description: Storable module API for the MediMeet application.

// Tests:
// 1. Create Test instance and save to file : PASSED
// 2. Display Test saved instances from file : PASSED
// 3. Separate filter for each sub-class of Storable: PASSED
// 4. Successful deletion of instances: NOT ATTEMPTED
// 5. Filter based on multiple attributes: NOT ATTEMPTED

// Notes: Currently Storable does not include filters other than all().
// However, they will be added sooner.

// ! DO NOT REMOVE COMMENTS


import java.util.*;
import java.io.*;

// Test class. Real module is simply Storable. Just inherit from it and do shit
public class StorageAdapter {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Storable.fromDisk();

        boolean exit = false;
        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Create Test instance and save to file");
            System.out.println("2. Display Test saved instances from file");

            System.out.println("3. Create AnotherTest instance and save to file");
            System.out.println("4. Display AnotherTest saved instances from file");

            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    createAndSaveTestInstance();
                    break;
                case 2:
                    displaySavedTestInstances();
                    break;
                case 3:
                    createAndSaveAnotherTestInstance();
                    break;
                case 4:
                    displaySavedAnotherTestInstances();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        Storable.toDisk();
    }

    private static void createAndSaveTestInstance() {
        System.out.print("Enter the name of the Test instance: ");
        String name = scanner.nextLine();
        Test test = new Test(name);
        test.save();
        System.out.println("Test instance saved.");
    }

    private static void displaySavedTestInstances() {
        ArrayList<Storable> instances = Storable.all(Test.class);
        if (instances.isEmpty()) {
            System.out.println("No instances found.");
        } else {
            System.out.println("Saved instances:");
            for (Storable instance : instances) {
                System.out.println(instance);
            }
        }
    }


    private static void createAndSaveAnotherTestInstance() {
        System.out.print("Enter the int of the AnotherTest instance: ");
        int i = scanner.nextInt();
        AnotherTest test = new AnotherTest(i);
        test.save();
        System.out.println("Test instance saved.");
    }

    private static void displaySavedAnotherTestInstances() {
        ArrayList<Storable> instances = Storable.all(AnotherTest.class);
        if (instances.isEmpty()) {
            System.out.println("No instances found.");
        } else {
            System.out.println("Saved instances:");
            for (Storable instance : instances) {
                System.out.println(instance);
            }
        }
    }
}

class Test extends Storable {
    String name;

    public Test(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test: " + name;
    }
}


class AnotherTest extends Storable {
    int i;

    public AnotherTest(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "AnotherTest: " + i;
    }
}


// Above is testing
// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
// Below is the real API


class Storable implements Serializable {

    private static ArrayList<Storable> instances = new ArrayList<Storable>();

    public static void toDisk() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("storage.ser");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(instances);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void fromDisk() {
        try {
            FileInputStream fileInputStream = new FileInputStream("storage.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            instances = (ArrayList<Storable>) objectInputStream.readObject();
            objectInputStream.close();

            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Storable> all(Class<? extends Storable> type) {
        ArrayList<Storable> instancesOfType = new ArrayList<Storable>();

        for(Storable instance : instances) {
            if(type.isInstance(instance)) {
                instancesOfType.add(instance);
            }
        }
        return instancesOfType;
    }


    public void save() {
        instances.add(this);
        Storable.toDisk();
    }

    public void delete() {
        instances.remove(this);
        Storable.toDisk();
    }

}