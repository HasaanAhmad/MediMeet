
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

class Storable implements Serializable {
    public static final long serialVersionUID = 1L;
    public static final String storagePath = "storage.ser";

    private static ArrayList<Storable> instances = new ArrayList<Storable>();

    public static void toDisk() {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(storagePath);

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
            FileInputStream fileInputStream = new FileInputStream(storagePath);
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

        for (Storable instance : instances) {
            if (type.isInstance(instance)) {
                instancesOfType.add(instance);
            }
        }
        return instancesOfType;
    }

    // Returns object of class with ID: null if not found ID is data member of
    // class.
    public static Storable get(Class<? extends Storable> type, int id) {
        ArrayList<Storable> instancesOfType = all(type);

        for (Storable instance : instancesOfType) {
            if (instance.getId() == id) {
                return instance;
            }
        }
        return null;
    }

    private int getId() {
        return 0;
    }

    public void save() {
        instances.add(this);
        Storable.toDisk();
    }

    public void delete() {
        instances.remove(this);
        Storable.toDisk(); // immediately reflect deleted stuff to file
    }

}