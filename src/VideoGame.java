/**
 * Child class of Game used with PARTY GAME MANAGER. This specifically deals
 * with video games.
 * 
 * @author Cory O'Brien
 *
 */
public class VideoGame extends Game implements Cloneable, Playable {
    protected String console;
    protected boolean isFavoriteVG;

    /**
     * Default Constructor.
     */
    public VideoGame() {
        this.name = "Untitled";
        this.players = 1;
        this.playtime = 0;
        this.console = "Undefined";
    }

    /**
     * Standard constructor using all relevant instance variables.
     * 
     * @param n The name of the Game
     * @param p The number of players the game supports
     * @param t The total time the user has played the game in milliseconds
     * @param c The console the game runs on
     */
    public VideoGame(String n, int p, double t, String c) {
        super(n, p, t);
        this.console = c;
    }

    /**
     * Clone constructor.
     * 
     * @param vg The game object to clone
     */
    public VideoGame(VideoGame vg) {
        this.name = vg.name;
        this.players = vg.players;
        this.playtime = vg.players;
        this.console = vg.console;
    }

    /**
     * Returns a string in the following format: Game: [name] [max players]
     * [console] [playtime].
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + "\tMax Players: "
                + this.getPlayers() + "\tConsole: " + this.getConsole()
                + "\tPlaytime: " + Math.round(this.getPlaytime() / 60000)
                + " Minutes";
    }

    @Override
    /**
     * Used with the clone constructor to clone the object.
     */
    public VideoGame clone() {
        return new VideoGame(this);
    }

    /**
     * Returns the console the game is on.
     * 
     * @return The console the game is on as a string
     */
    protected String getConsole() {
        return this.console;
    }

    /**
     * Returns whether or not this game is set as a favorite.
     * 
     * @return True if the isFavorite variable is true
     */
    protected boolean getIsFavorite() {
        return this.isFavoriteVG;
    }

    /**
     * Sets the favorite variable.
     * 
     * @param f A boolean to copy to the favorite variable
     */
    public void setFavorite(boolean f) {
        if (f) {
            this.isFavoriteVG = true;
        } else {
            this.isFavoriteVG = false;
        }
    }

    /**
     * Determines if this game is a good fit for the user based on whether or
     * not it can support the number of players at the party and if it is runs
     * on the desired console.
     * 
     * @param targetPlayers The number of players wishing to play
     * @param targetConsole The console the user wants to play the game on
     * @return True if the game is a good fit
     */
    public boolean goodFit(int targetPlayers, String targetConsole) {
        if (this.isFavoriteVG) {
            return true;
        }
        if (this.players >= targetPlayers
                && this.console.equals(targetConsole)) {
            return true;
        }
        return false;
    }
}
