public class Room {
    int id;
    String name;
    int max_count_players, count_players;
    private String password;
    Room(int id, String name, String password, int count, int max_count){
        this.id = id;
        this.name = name;
        this.count_players = count;
        this.max_count_players = max_count;
        this.password = password;
    }

    Boolean CheckPassword(String pass){
        if (pass.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
}
