import java.util.*;
public class Hand{
    private final List<Card> cards = new ArrayList<>();

    void addCard(Card c){
        cards.add(c);
    }

    List<Card> getCards(){
        return Collections.unmodifiableList(cards);
    }

    int bestValue(){
        int sum = 0;
        int aces = 0;
        for (Card c: cards){
            sum += c.value();
            if(c.rank.equals("A")){
                aces ++;
            }
        }
        int best = sum;
        for(int i = 0; i < aces; i ++){
            if (best + 10 <= 21){
                best += 10;
            }
        }
        return best;
    }
    public String toString(){
        return cards.toString();
    }
}