package hsuadddrop.model;

public class Staff {

    private String staffID;
    private String Name;
    private String Password;

    //Create an empty Staff object to transfer staffID, Name and Password;
    
    public Staff(String ID, String Name, String Password) {
        this.staffID = ID;
        this.Name = Name;
        this.Password = Password;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "Staff {" + "staffID=" + staffID + ", Name=" + Name + ", Password=" + Password + '}';
    }

}
