import java.util.*;

public class Deck{
    private final LinkedList<Card> cards = new LinkedList<>();
    private static final String[] SUITS = {"S","H","D","C"};
    private static final String[] RANKS =  {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    Deck(){
        for(String s: SUITS){
            for(String r: RANKS){
                cards.add(new Card(r,s));
            }
        }
    }

    void shuffle(){
        Collections.shuffle(cards, new Random());
    }

    Card draw(){
        if (cards.isEmpty()){
            throw new NoSuchElementException("Enpty");
        }
        return cards.removeFirst();
    }

    int size(){
        return cards.size();
    }
}