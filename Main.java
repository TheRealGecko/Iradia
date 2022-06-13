/**
 * This is the third draft of the Main class.
 * It creates an instance of and runs the game.
 * Version date: 06/10/2022
 *
 * @author Fatma Jadoon
 * @version 1.3.63
 * <p>
 * Fourth version submission of Iradia.
 * <p>
 * Task distribution:
 * <ul>
 *   <li> Alexandra Mitnik
 *      <ul>
 *          <li> Total time spent: 20 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Coding, fixing, and optimizing stage 2 class
 *                  <li>Improving the name and score logic error, error trapping the name screen
 * <li>Adding the stage 2 tutorial case,
 * <li>Adding the sprite movement (stage 3)
 * <li>Improving the sprite hitboxes (stage 3)
 * <li>Coding the scenes for each stage 3 case file
 * <li> Debugging
 * <li> Writing the stage 3 script
 *              </ul>
 *      </ul>
 *   <li> Bethany Lum
 *      <ul>
 *          <li> Total time spent: 14 hours
 *          <li> Tasks
 *              <ul>
 *                  <li> Writing the leaderboard class
 *                  <li> Writing the script for stage 3
 * <li> Drawing graphics for stage 2 cases
 *                  <li> Writing a blog post for Iradia
 *              </ul>
 *      </ul>
 *   <li> Fatma Jadoon
 *      <ul>
 *          <li> Total time spent: 20 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Fixing/optimizing stage 2's logic
 *                  <li>Making graphics for the final screen, transition screens, and credits scenes
 * <li>Coding transition screens, hitboxes, credits screen and stage 3 dialogue in
 * <li> Debugging and making stage 3 work
 * <li>Adding key functionality to the leaderboard class
 *                  <li>Writing a blog post for Iradia
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
