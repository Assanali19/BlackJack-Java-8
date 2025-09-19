import java.util.*;

public class Blackjack{
    private static final int initial_money = 1000;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        Deck deck = new Deck();
        deck.shuffle();

        int balance = initial_money;
        System.out.println("Welcome to Blackjack");
        System.out.println("");

        while (balance > 0) {
            printBet(balance);
            int bet = getBet(balance);
            if (bet == -1){
                break;
            }
            balance = round(deck,balance,bet);

            if (deck.size() < 15){
                System.out.println("New deck reshuffled!\n");
                deck = new Deck();
                deck.shuffle();
            }

            printNewRound();
        }
        if (balance <= 0){
                    System.out.println("You have no money. You lost");
        }
        System.out.println("Thank you for playing");
        sc.close();
    }

    private static int round(Deck deck, int balance, int bet){
        Hand player = new Hand();
        Hand dealer = new Hand();

        for (int i = 0; i <= 1; i++){
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }

        printDealerHand(dealer, false);

        boolean player_turn = playerTurn(deck,player);

        if (!player_turn){
            dealerTurn(deck,dealer);
        }
        
        int newBalance = determineResult(player,dealer,balance,bet);
        return newBalance;
    }

    private static void printBet(int balance){
        System.out.println("\n====================");
        System.out.println("Current balance: $" + balance);
        System.out.println("Place your bet (q = quit):");
        System.out.println("====================\n");
    }

    private static void printPlayerHand(Hand player){
        System.out.println("\n=== Your Hand ===");
        System.out.println("Total: " + player.bestValue());
        printHand(player.getCards());
        System.out.println("h = hit, s = stand\n");
    }

    private static void printDealerHand(Hand dealer, boolean showAll){
        System.out.println("\n=== Dealer's Hand ===");
        if (showAll) {
            System.out.println("Total: " + dealer.bestValue());
            printHand(dealer.getCards());
        } else {
            System.out.println("Visible card: " + dealer.getCards().get(0));
            printHand(Collections.singletonList(dealer.getCards().get(0)));
        }
        System.out.println();
    }

    private static void printRoundResult(int playerValue, int dealerValue, int balance, int bet){
        System.out.println("***** ROUND OVER *****");
        System.out.println("You: " + playerValue + " points");
        System.out.println("Dealer: " + dealerValue + " points");
        if (playerValue > dealerValue || dealerValue > 21){
            System.out.println("Result: You lost $" + bet);
        } else if (playerValue < dealerValue){
            System.out.println("Result: You won $" + bet);
        } else{
            System.out.println("Result: Draw. Money returned");
        }
        System.out.println("Balance remaining: $" + balance);
        System.out.println("**********************\n");
    }

    private static void printNewRound(){
        System.out.println("\n===========================");
        System.out.println("      NEW ROUND");
        System.out.println("===========================\n");
    }

    public static void printHand(List<Card> hand){
        List<String[]> asciiCards = new ArrayList<>();
        for (Card c: hand){
            asciiCards.add(c.asciiArt());
        }

        for(int line = 0; line < asciiCards.get(0).length; line++){
            for(String[] cardLines: asciiCards){
                System.out.print(cardLines[line] + " ");
            }
            System.out.println();
        }
    }
    private static int getBet(int balance){
        while (true){
            System.out.println("(q to leave) Make your bet, from 1 to - " + balance + ":");
            String input = sc.next().trim().toLowerCase();
            if(input.equals("q")){
                return -1;
            }
            try{
                int bet = Integer.parseInt(input);
                if (bet > 0 && bet <= balance){
                    return bet;
                }
                System.out.println("Error number");
            } catch (NumberFormatException e){
                System.out.println("Write number or q.");
            }
        }
    }

    private static boolean playerTurn(Deck deck, Hand player){
        while (true){
            printPlayerHand(player);
            if (player.bestValue() > 21){
                System.out.println("Busted!");
                return true;
            }
            System.out.println("Take card? (h = hit, s = stand): ");
            String cmd = sc.next().trim().toLowerCase();
            if (cmd.equals("h")){
                Card card = deck.draw();
                System.out.println("You took: " + card);
                player.addCard(card);
            } else if (cmd.equals("s")){
                return false;
            } else{
                System.out.println("Enter h or s: ");
            }
        }
    }

    private static void dealerTurn(Deck deck, Hand dealer){
        printDealerHand(dealer, true);
        while (dealer.bestValue() < 17){
            Card card = deck.draw();
            System.out.println("Dealer took: " + card);
            dealer.addCard(card);
        }
        System.out.println();
    }

    private static int determineResult(Hand player, Hand dealer, int balance, int bet){
        int playerValue = player.bestValue();
        int dealerValue = dealer.bestValue();
        printRoundResult(playerValue, dealerValue, balance, bet);

        if (playerValue > 21 || (playerValue < dealerValue && dealerValue <= 21)){
            return balance - bet;
        }else if(dealerValue > 21 || playerValue > dealerValue){
            return balance + bet;
        } else {
            return balance;
        }
    }
}