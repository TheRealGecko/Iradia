/**
 * This is the third draft of the Main class.
 * It creates an instance of and runs the game.
 * Version date: 06/03/2022
 * @author Fatma Jadoon
 * @version 1.3.63
 * <p>
 * Third version submission of Iradia.
 * <p>
 * Task distribution:
 * <ul>
 *   <li> Alexandra Mitnik
 *      <ul>
 *          <li> Total time spent: 11 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Coding stage 2's logic
 *                  <li>Enhancing efficiency of stage 2
 *                  <li>Writing case files for stage 2
 *                  <li>Writing a blog post for Iradia
 *                  <li>Creating stage 2 character references
 *              </ul>
 *      </ul>
 *   <li> Bethany Lum
 *      <ul>
 *          <li> Total time spent: 7 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Writing case files for stage 2
 *                  <li>Illustrating characters for stage 2
 *                  <li>Writing a blog post for Iradia
 *              </ul>
 *      </ul>
 *   <li> Fatma Jadoon
 *      <ul>
 *          <li> Total time spent: 9 hours
 *          <li> Tasks:
 *              <ul>
 *                  <li>Coding stage 2's logic
 *                  <li>Writing case files for stage 2
 *                  <li>Writing a blog post for Iradia
 *                  <li>Creating stage 2 character references
 *                  <li>Illustrating sprite sheet for stage 3, and characters for stage 2
 *              </ul>
 *      </ul>
 * </ul>
 */

public class Main {
    /**
     * The main method
     * @param args
     */
    static Game game;

    public static void main(String[] args) {
        game = new Game();
        game.run();
    }
}
