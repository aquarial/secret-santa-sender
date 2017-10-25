package solver;

class Node {
    public int groupID;
    public String name;
    public String email;


    @Override
    public String toString() {
        return "\nsolver.Node[ groupid="+groupID + ", name=" +name + ", email=" + email + "]";
    }
}
