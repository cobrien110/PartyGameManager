import java.util.Random;

/**
 * Main class used for the PARTY GAME MANAGER program. Stores information for a
 * game which will then be utilized by the FrontEnd program.
 * 
 * @author Cory O'Brien
 *
 */
public class Game implements Cloneable, Playable {
    protected String name;
    protected int players;
    protected double playtime;
    protected double activeTime = 0.0;

    /**
     * Default constructor.
     */
    public Game() {
        this.name = "Untitled";
        this.players = 1;
        this.playtime = 0;
    }

    /**
     * Standard constructor using all relevant instance variables.
     * 
     * @param n The name of the Game
     * @param p The number of players the game supports
     * @param t The total time the user has played the game in milliseconds
     */
    public Game(String n, int p, double t) {
        this.name = n;
        this.players = p;
        this.playtime = t;
    }

    /**
     * Clone constructor.
     * 
     * @param g The Game object to clone
     */
    public Game(Game g) {
        this.name = g.name;
        this.players = g.players;
        this.playtime = g.players;
    }

    /**
     * Returns a string in the following format: Game: [name] [max players]
     * [playtime].
     */
    @Override
    public String toString() {
        return "Game: " + this.getName() + "\tMax Players: "
                + this.getPlayers() + "\tPlaytime: "
                + Math.round(this.getPlaytime() / 60000) + " Minutes";
    }

    /**
     * Uses the clone constructor to clone the object.
     */
    @Override
    public Game clone() {
        return new Game(this);
    }

    /**
     * Returns The name of the object as a string.
     * 
     * @return The name of the object as a string.
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Returns The number of players.
     * 
     * @return The number of max players as an int.
     */
    protected int getPlayers() {
        return this.players;
    }

    /**
     * Returns the time spent playing the game.
     * 
     * @return The playtime of the game as a double.
     */
    protected double getPlaytime() {
        return this.playtime;
    }

    /**
     * This method randomly chooses between two game objects (this one and
     * another) and returns one of them.
     * 
     * @param g A second game object to choose between
     * @return The chosen game object
     */
    public Game chooseBetween(Game g) {
        Random rand = new Random();
        int r = rand.nextInt(1);
        if (r == 0) {
            return this;
        } else {
            return g;
        }
    }

    /**
     * Determines if this game is a good fit for the user based on whether or
     * not it can support the number of players at the party.
     * 
     * @param targetPlayers The number of players wishing to play
     * @return True if the game is a good fit
     */
    public boolean goodFit(int targetPlayers) {
        if (this.players >= targetPlayers) {
            return true;
        }
        return false;
    }

    /**
     * Starts a timer on the game to later add to the playtime.
     */
    @Override
    public void startPlaying() {
        this.activeTime = System.currentTimeMillis();
    }

    /**
     * Ends a timer on the game and adds to the playtime.
     */
    @Override
    public void stopPlaying() {
        if (this.activeTime != 0.0) {
            this.playtime = System.currentTimeMillis() - activeTime;
            this.activeTime = 0;
        }
    }

    /**
     * Resets a game's playtime to zero.
     */
    @Override
    public void resetTime() {
        this.playtime = 0;
    }
}
