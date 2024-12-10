/**
 * Interface implemented by the Game class and all children of Game class. This
 * forces uniform implementation of three methods related to managing the
 * playtime variable of each game object.
 * 
 * @author corym
 *
 */
public interface Playable {
    /**
     * Intended to start a timer to keep track of playtime.
     */
    public void startPlaying();

    /**
     * Intended to stop the playtime timer and then add the timer's value to the
     * game's total playtime.
     */
    public void stopPlaying();

    /**
     * Intended to reset a game's playtime to zero.
     */
    public void resetTime();
}
