package lk.ijse.entity;

public class user {

    private String U_ID;
    private String U_Name;
    private String U_Password;
    private String Role;
    private String U_Email;
    private String U_Address;
    private String U_Contact_Number;

    // Default constructor
    public user() {
    }

    // Parameterized constructor
    public user(String u_ID, String u_Name, String u_Password, String role, String u_Email, String u_Address, String u_Contact_Number) {
        U_ID = u_ID;
        U_Name = u_Name;
        U_Password = u_Password;
        Role = role;
        U_Email = u_Email;
        U_Address = u_Address;
        U_Contact_Number = u_Contact_Number;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getU_Name() {
        return U_Name;
    }

    public void setU_Name(String u_Name) {
        U_Name = u_Name;
    }

    public String getU_Password() {
        return U_Password;
    }

    public void setU_Password(String u_Password) {
        U_Password = u_Password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getU_Email() {
        return U_Email;
    }

    public void setU_Email(String u_Email) {
        U_Email = u_Email;
    }

    public String getU_Address() {
        return U_Address;
    }

    public void setU_Address(String u_Address) {
        U_Address = u_Address;
    }

    public String getU_Contact_Number() {
        return U_Contact_Number;
    }

    public void setU_Contact_Number(String u_Contact_Number) {
        U_Contact_Number = u_Contact_Number;
    }


    public String toString() {
        return "User{" +
                "U_ID='" + U_ID + '\'' +
                ", U_Name='" + U_Name + '\'' +
                ", U_Password='" + U_Password + '\'' +
                ", Role='" + Role + '\'' +
                ", U_Email='" + U_Email + '\'' +
                ", U_Address='" + U_Address + '\'' +
                ", U_Contact_Number='" + U_Contact_Number + '\'' +
                '}';
    }
}
