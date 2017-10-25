public class Node {
    public int groupID;
    public String name;
    public String email;


    @Override
    public String toString() {
        return "\nNode[ groupid="+groupID + ", name=" +name + ", email=" + email + "]";
    }
}
