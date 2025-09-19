public class Card{
    final String rank;
    final String suit;

    Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }

    int value() {
        if (rank.equals("A")){
            return 1;
        }
        if (rank.equals("K") || rank.equals("Q") || rank.equals("J")){
            return 10;
        }
        return Integer.parseInt(rank);
    }
    
    public String[] asciiArt(){
        String top = "┌─────────┐";
        String left = String.format("│ %-2s      │", rank);
        String line1 = "│         │";
        String line2 = String.format("│    %s    │", suit);
        String right = String.format("│      %-2s │", rank);
        String bottom = "└─────────┘";

        return new String[]{top, left, line1, line2, line1, right, bottom};
    }
    public String toString(){
        return rank + suit;
    }
}