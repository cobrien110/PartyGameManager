/**
 * Child class of Game used with PARTY GAME MANAGER. This specifically deals
 * with board games or tabletop games.
 * 
 * @author corym
 *
 */
public class BoardGame extends Game implements Cloneable, Playable {
    protected double complexity;
    protected boolean isFavoriteBG;

    /**
     * Default constructor.
     */
    public BoardGame() {
        this.name = "Untitled";
        this.players = 1;
        this.playtime = 0;
        this.complexity = 0.0;
    }

    /**
     * Standard constructor using all relevant instance variables.
     * 
     * @param n The name of the Game
     * @param p The number of players the game supports
     * @param t The total time the user has played the game in milliseconds
     * @param c The complexity of the game measured from 0.0-5.0
     */
    public BoardGame(String n, int p, double t, double c) {
        super(n, p, t);
        this.complexity = c;
    }

    /**
     * Clone constructor.
     * 
     * @param bg The BoardGame object to clone
     */
    public BoardGame(BoardGame bg) {
        this.name = bg.name;
        this.players = bg.players;
        this.playtime = bg.players;
        this.complexity = bg.complexity;
    }

    /**
     * Returns a string in the following format: Game: [name] [max players]
     * [complexity] [playtime].
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + "\tMax Players: "
                + this.getPlayers() + "\tComplexity: " + this.getComplexity()
                + "\tPlaytime: " + Math.round(this.getPlaytime() / 60000)
                + " Minutes";
    }

    /**
     * Uses the clone constructor to clone the object.
     */
    @Override
    public BoardGame clone() {
        return new BoardGame(this);
    }

    /**
     * Returns the complexity of the object.
     * 
     * @return The complexity of the object as a double
     */
    protected double getComplexity() {
        return this.complexity;
    }

    /**
     * Returns whether or not this game is set as a favorite.
     * 
     * @return True if the isFavorite variable is true
     */
    protected boolean getIsFavorite() {
        return this.isFavoriteBG;
    }

    /**
     * Sets the favorite variable.
     * 
     * @param f A boolean to copy to the favorite variable
     */
    public void setFavorite(boolean f) {
        if (f) {
            this.isFavoriteBG = true;
        } else {
            this.isFavoriteBG = false;
        }
    }

    /**
     * Determines if this game is a good fit for the user based on whether or
     * not it can support the number of players at the party and if it is within
     * a certain range of complexity.
     * 
     * @param targetPlayers    The number of players wishing to play
     * @param targetComplexity A set complexity the user wants to aim for
     * @return True if the game is a good fit
     */
    public boolean goodFit(int targetPlayers, double targetComplexity) {
        if (this.isFavoriteBG) {
            return true;
        }
        if (this.players >= targetPlayers
                && (this.complexity >= targetComplexity - 0.5
                        && this.complexity <= targetComplexity + 0.5)) {
            return true;
        }
        return false;
    }
}
