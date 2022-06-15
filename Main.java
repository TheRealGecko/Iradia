/**
 * This is the final draft of the Main class.
 * It creates an instance of and runs the game.
 * Version date: 06/14/2022
 *
 * @author Fatma Jadoon
 * @version 1.3.63
 * <p>
 * Final version submission of Iradia.
 * <p>
 * Task distribution:
 * <ul>
 *   <li> Alexandra Mitnik
 *      <ul>
 *          <li> Total time spent: ~20 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Coding/fixing stage2, stage3, and leaderboard
                    <li>Commenting all classes
                    <li>Implementing high score system
 *              </ul>
 *      </ul>
 *   <li> Bethany Lum
 *      <ul>
 *          <li> Total time spent: ~20 hours
 *          <li> Tasks
 *              <ul>
 *                  <li>Writing manual
 *                  <li>Coding leaderboard
 *                  <li>Debugging stage2 and stage3
 *              </ul>
 *      </ul>
 *   <li> Fatma Jadoon
 *      <ul>
 *          <li> Total time spent: ~20 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Fixing stage 2 and stage 3
 *                  <li>Making assets
 *              </ul>
 *      </ul>
 * </ul>
 */

public class Main {
    /**
     * Game - Stores the game
     */
    static Game game;

    /**
     * The main method.
     * Starts up the game.
     *
     * @param args
     */
    public static void main(String[] args) {
        game = new Game();
        game.run();
    }
}