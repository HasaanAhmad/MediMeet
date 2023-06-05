//somehow import Storable class - its part of same package so automatically imported

// At the start of project, call Storable.fromDisk() on top of main method to load from disk
// At the end of program, call Storable.toDisk() on bottom of main method to save to disk
// Not call them in each class, just in the main method() once.

/*
WANT TO GET ALL USERS? : ArrayList<Storable> users = Storable.all(User.class);
WANT TO SAVE A NEWLY CREATED USER? : userInstance.save();
*/

import java.io.*;
import java.util.*;

public class User extends Storable {
  private static final long serialVersionUID = -132322942995336546L;

  private String name;
  private String email;
  private String password;
  private String phone;
  private String address;

  public User(
      String name,
      String email,
      String password,
      String phone,
      String address) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.address = address;
  }

  // Return user if present, else return null - Store this login user in some
  // global variable, and make it null when user logs out
  public static User login(String email, String password) {
    ArrayList<Storable> users = Storable.all(User.class); // filters all instances belonging to User calss
    for (Storable user : users) {
      User tmp = (User) user;
      if (tmp.getEmail().equals(email) && tmp.getPassword().equals(password)) {
        return tmp;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return ("User [name=" +
        name +
        ", email=" +
        email +
        ", password=" +
        password +
        ", phone=" +
        phone +
        ", address=" +
        address +
        "]");
  }
}
