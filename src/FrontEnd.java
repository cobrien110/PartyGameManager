import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class runs all of the necessary operations of the PARTY GAME MANAGER
 * program. As the user, you will run this program and it will provide all
 * necessary information to you. This program is capable of importing a list of
 * games, adding to that list, suggesting games for you to play, printing that
 * list to a new file, and more!
 * 
 * @author Cory O'Brien
 *
 */
public class FrontEnd {
    // Game list to be used across the whole program
    private static ArrayList<Game> gameAr = new ArrayList<Game>();
    // Input from user
    private static Scanner in = new Scanner(System.in);
    // Visual star line to print between methods
    private static final String STARS = "***********************"
            + "******************";

    /**
     * Starts up the program and begins the main loop for interaction.
     * 
     * @param args Main args
     */
    public static void main(String[] args) {
        System.out.println("Hello! Welcome to the PARTY GAME MANAGER!");
        System.out.println(STARS);
        int choice = -1;
        while (choice != 0) { // If user enters 0, program will end
            printMenu();
            try {
                choice = in.nextInt();
                choiceMenu(choice);
            } catch (Exception e) { // Umbrella exception catcher
                System.out.println("An error has occured: ");
                e.printStackTrace();
            }
            if (choice != 0) { // Prompt the user to continue operation
                promptKey();
            }
        }
        // Closing
        System.out.println(STARS);
        System.out.println("Thank you for using PARTY GAME MANAGER!");
        System.out.println(STARS);
        in.close();
    }

    /**
     * This method runs a new method depending on a user input value.
     * 
     * @param choice An int that determines which follow-up method to run.
     */
    private static void choiceMenu(int choice) {
        if (choice == 1) {
            loadList();
        } else if (choice == 2) {
            printList();
        } else if (choice == 3) {
            addGame();
            // All methods below this point are hidden when
            // the list of games is empty
        } else if (choice == 4 && gameAr.size() > 0) {
            suggestRandom();
        } else if (choice == 5 && gameAr.size() > 0) {
            checkFit();
        } else if (choice == 6 && gameAr.size() > 0) {
            chooseBetween();
        } else if (choice == 7 && gameAr.size() > 0) {
            startPlaying();
        } else if (choice == 8 && gameAr.size() > 0) {
            stopPlaying();
        } else if (choice == 9 && gameAr.size() > 0) {
            try {
                saveList();
            } catch (IOException e) {
                System.out.println("IO EXCEPTION ERROR");
            }
        }
    }

    /**
     * This method prints all of the user's available options.
     */
    private static void printMenu() {
        System.out.println(
                "1. Load a list of games from a text file\n\t[Warning: Clears current list!]");
        System.out.println("2. Print all games in the list");
        System.out.println("3. Add a new game to the list");
        if (gameAr.size() > 0) {
            System.out.println("4. Suggest a random game to play");
            System.out.println("5. Ask if a game is a good fit");
            System.out.println("6. Choose between two games");
            System.out.println("7. Start playing a game");
            System.out.println("8. Stop playing a game");
            System.out.println(
                    "9. Save the current list of games to a text file");
        }
        System.out.println("0. End Program");
        System.out.println(
                "Please select an option from the ones available above: ");
    }

    /**
     * This method clears the current game list and loads a new one from an
     * input text file.
     */
    public static void loadList() {
        System.out.println("What is the name of the text file?");
        String txt = in.next();
        try {
            Scanner inFile = new Scanner(new File(txt));
            gameAr.clear();
            while (inFile.hasNext()) {
                // Depending on the class, we will add a new Game object to the
                // game array using the necessary constructors
                String nextType = inFile.next();
                if (nextType.equals("VideoGame")) {
                    gameAr.add(new VideoGame(inFile.next(), inFile.nextInt(),
                            inFile.nextDouble(), inFile.next()));
                } else if (nextType.equals("BoardGame")) {
                    gameAr.add(new BoardGame(inFile.next(), inFile.nextInt(),
                            inFile.nextDouble(), inFile.nextDouble()));
                } else if (nextType.equals("Game")) {
                    gameAr.add(new Game(inFile.next(), inFile.nextInt(),
                            inFile.nextDouble()));
                }
            }
            System.out.println(
                    "List Loaded! Loaded " + gameAr.size() + " items.");
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
    }

    /**
     * This method prints all of the games currently contained in the game
     * array.
     */
    public static void printList() {
        System.out.print(noGameWarning());
        for (int i = 0; i < gameAr.size(); i++) {
            System.out.println("[" + i + "] " + gameAr.get(i).toString());
        }
    }

    /**
     * This method prints the current game list to a file. The file can be an
     * existing file or one that doesn't exist, in which case it will create a
     * new file.
     * 
     * @throws IOException Necessary throw
     */
    public static void saveList() throws IOException {
        System.out.print(noGameWarning());
        System.out.println("What text file should the list be saved to?");
        // Grab the current directory and create a new file
        String currentPath = System.getProperty("user.dir");
        File f = new File(currentPath, in.next());
        f.createNewFile();
        try {
            PrintWriter w = new PrintWriter(f);
            for (int i = 0; i < gameAr.size(); i++) {
                // Write to file depending on class
                Game g = gameAr.get(i);
                if (g instanceof VideoGame) {
                    printGame(w, 1, g);
                } else if (g instanceof BoardGame) {
                    printGame(w, 2, g);
                } else {
                    w.println("Game " + g.getName() + " " + g.getPlayers() + " "
                            + g.getPlaytime());
                }
            }
            w.close();
            System.out.println("File saved!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    /**
     * This method is used to extend the previous saveList method to condense
     * line count. It deals with printing VideoGame and BoardGame objects.
     * 
     * @param w    The PrintWriter from saveList()
     * @param type The type of class we are printing. 1 == Video Game, 2 ==
     *             Board Game
     * @param g    The Game object to print
     */
    public static void printGame(PrintWriter w, int type, Game g) {
        if (type == 1) {
            VideoGame vg = (VideoGame) g;
            w.println("VideoGame " + vg.getName() + " " + vg.getPlayers() + " "
                    + vg.getPlaytime() + " " + vg.getConsole());
        } else if (type == 2) {
            BoardGame bg = (BoardGame) g;
            w.println("BoardGame " + bg.getName() + " " + bg.getPlayers() + " "
                    + bg.getPlaytime() + " " + bg.getComplexity());
        }
    }

    /**
     * This method begins the process of adding a new game to the list by
     * prompting the user which kind of game they want to add.
     */
    public static void addGame() {
        System.out.println("What kind of game are you adding?");
        System.out.println("1. Video Game");
        System.out.println("2. Board Game");
        System.out.println("3. Other Type of Game");
        int addChoice = in.nextInt();
        if (addChoice == 1) {
            addVideoGame();
        } else if (addChoice == 2) {
            addBoardGame();
        } else if (addChoice == 3) {
            addOtherGame();
        }
    }

    /**
     * This method adds a VideoGame object to the game list.
     */
    public static void addVideoGame() {
        try {
            System.out.println("Name of the Game? (no spaces)");
            String name = in.next();
            System.out.println("How many players?");
            int players = in.nextInt();
            System.out.println("What console is it played on?");
            String console = in.next();
            gameAr.add(new VideoGame(name, players, 0.0, console));
            System.out.println("Added at " + (gameAr.size() - 1) + "!");
        } catch (InputMismatchException e) {
            System.out.println("Add failed");
        }
    }

    /**
     * This method adds a BaordGame object to the game list.
     */
    public static void addBoardGame() {
        try {
            System.out.println("Name of the Game? (no spaces)");
            String name = in.next();
            System.out.println("How many players?");
            int players = in.nextInt();
            System.out.println("How complex is it (0.0 - 5.0)?");
            double complexity = in.nextDouble();
            gameAr.add(new BoardGame(name, players, 0.0, complexity));
            System.out.println("Added at " + (gameAr.size() - 1) + "!");
        } catch (InputMismatchException e) {
            System.out.println("Add failed");
        }
    }

    /**
     * This method adds a Game object to the game list.
     */
    public static void addOtherGame() {
        try {
            System.out.println("Name of the Game? (no spaces)");
            String name = in.next();
            System.out.println("How many players?");
            int players = in.nextInt();
            gameAr.add(new Game(name, players, 0.0));
            System.out.println("Added at " + (gameAr.size() - 1) + "!");
        } catch (InputMismatchException e) {
            System.out.println("Add failed");
        }
    }

    /**
     * This method checks if a specified game is a smart selection for the user
     * to play based on a few criteria depending on the class of the game
     * object. Each type of game object has its own version of a method to check
     * if it is a good fit for the situation. Check the classes themselves for
     * information.
     */
    public static void checkFit() {
        System.out.print(noGameWarning());
        System.out.println("How many players do you have?");
        int players = in.nextInt();
        System.out.println("What's the index of the game are you checking?");
        try {
            Game g = gameAr.get(in.nextInt());
            if (g instanceof VideoGame) {
                VideoGame vg = (VideoGame) g;
                System.out.println("What console are you playing?");
                String console = in.next();
                checkPrint(vg.goodFit(players, console), vg);
            } else if (g instanceof BoardGame) {
                BoardGame bg = (BoardGame) g;
                System.out.println("What is your target complexity (0.0-5.0)?");
                double complexity = in.nextDouble();
                checkPrint(bg.goodFit(players, complexity), bg);
            } else {
                checkPrint(g.goodFit(players), g);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such game has that index.");
        }
    }

    /**
     * Used with checkFit method. This method prints the appropriate message to
     * the user depending on whether or not the game is a good fit.
     * 
     * @param check Boolean obtained from the Game class's goodFit method
     * @param g     The Game object being checked
     */
    public static void checkPrint(boolean check, Game g) {
        if (check) {
            System.out.println(g.getName() + " is a great fit!");
        } else {
            System.out.println(g.getName() + " is not a good fit.");
        }
    }

    /**
     * This method chooses between two game objects randomly.
     */
    public static void chooseBetween() {
        System.out.print(noGameWarning());
        System.out.println(
                "Current games list has games stored in indexes 0 through "
                        + (gameAr.size() - 1));
        try {
            System.out.println("Choose one game's index from the list: ");
            Game g1 = gameAr.get(in.nextInt());
            System.out.println("Choose another game's index from the list: ");
            Game g2 = gameAr.get(in.nextInt());
            Game gc = g1.chooseBetween(g2);
            System.out.println(
                    "Of the two, you should play " + gc.getName() + "!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such game has that index.");
        }
    }

    /**
     * This method suggests a random game to the user from the whole list. If a
     * particular VideoGame or BoardGame is pulled by this method more than
     * once, an extra message is printed as if the software particularly enjoys
     * said game. This is purely a cosmetic effect to utilize the favorite
     * variables of the VideoGame and BoardGame classes.
     */
    public static void suggestRandom() {
        System.out.print(noGameWarning());
        int maxInt = gameAr.size();
        Random rand = new Random();
        int r = rand.nextInt(0, maxInt);
        System.out.print("You should play: ");
        System.out.println(gameAr.get(r).getName() + "!");
        if (gameAr.get(r) instanceof VideoGame) {
            VideoGame vg = (VideoGame) gameAr.get(r);
            if (vg.getIsFavorite()) {
                System.out.println("I really like this video game!");
            } else {
                vg.setFavorite(true);
            }
        } else if (gameAr.get(r) instanceof BoardGame) {
            BoardGame bg = (BoardGame) gameAr.get(r);
            if (bg.getIsFavorite()) {
                System.out.println("I really like this board game!");
            } else {
                bg.setFavorite(true);
            }
        }
    }

    /**
     * This method begins playing a particular game. This is used to update the
     * game's playtime variable.
     */
    public static void startPlaying() {
        System.out.print(noGameWarning());
        System.out.println(
                "Current games list has games stored in indexes 0 through "
                        + (gameAr.size() - 1));
        System.out
                .println("What's the index of the game you are going to play?");
        try {
            Game g = gameAr.get(in.nextInt());
            if (g.activeTime == 0.0) {
                g.startPlaying();
                System.out.println("Now playing " + g.getName() + "!");
            } else {
                System.out.println(
                        "You are already playing " + g.getName() + ".");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such game has that index.");
        }
    }

    /**
     * This method ends the playing of a particular game and updates its
     * playtime accordingly.
     */
    public static void stopPlaying() {
        System.out.print(noGameWarning());
        System.out.println(
                "Current games list has games stored in indexes 0 through "
                        + (gameAr.size() - 1));
        System.out.println(
                "What's the index of the game you are going to stop playing?");
        try {
            Game g = gameAr.get(in.nextInt());
            if (g.activeTime != 0.0) {
                g.stopPlaying();
                System.out.println("No longer playing " + g.getName() + ".");
            } else {
                System.out.println(
                        "You are not playing " + g.getName() + ".");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such game has that index.");
        }
    }

    /**
     * This method is used to prompt a simple input from the user after each
     * major operation. It helps make the program a bit more "digestible."
     */
    public static void promptKey() {
        System.out.println("\nEnter any character to continue.");
        in.next();
        System.out.println(STARS);
    }

    /**
     * This is a fail-safe method called in all other methods that access the
     * games list. In case the user accesses one of those methods and the games
     * list is empty, they will receive a warning that the program may not
     * function correctly.
     * 
     * @return A string with a warning if the games list is empty.
     */
    public static String noGameWarning() {
        if (gameAr.size() == 0) {
            return "WARNING: NO GAMES LOADED!\n"
                    + "PROGRAM WILL NOT FUNCTION CORRECTLY!\n";
        } else {
            return "";
        }
    }

}
