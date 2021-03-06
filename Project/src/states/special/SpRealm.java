package states.special;

import base.Globals;
import components.underlying.Movable;
import org.newdawn.slick.Color;
import states.base.AutoState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Models spirit realm scenes
 *
 * @author David
 */
public class SpRealm extends AutoState {

    // Ship variables
    private Movable ship;
    private Image shipImage;

    // Control list
    private String[] contList;

    /**
     * Initialize state
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

        // Initialize ship image and position
        shipImage = new Image(Globals.getFP("alienship.png"));
        shipImage = shipImage.getScaledCopy(0.7f);
        shipImage.setRotation(90);

        // Initialize movable ship
        int shipX = Globals.screenW / 2 - (shipImage.getWidth() / 4 + 47);
        int shipY = (int) (Globals.screenH * 0.7);
        ship = new Movable(shipX, shipY, 69, 69, 0.69, 0.69f,
                true, "shipMove") {
            @Override
            public void procPosChange(char sign, char axis,
                    int trueSpeed) {

                // Get change
                int change = Integer.parseInt(sign + "" + trueSpeed);

                // Act based on axis
                if (axis == 'x') {

                    // If on X:
                    // Apply change to X
                    adjustX(change);

                    // Wrap around X axis
                    int curX = super.getX();
                    int limit = Globals.screenW;
                    super.setX(curX % limit);
                    if (curX < ship.getMovSpeed() * 10) {
                        super.setX(limit);
                    }
                } else {

                    // If on Y:
                    // Apply change to Y
                    adjustY(change);

                    // Wrap around Y axis
                    int curY = super.getY();
                    int limit = Globals.screenH;
                    super.setY(curY % limit);
                    if (curY < ship.getMovSpeed() * 10) {
                        super.setY(limit);
                    }
                }
            }
        };

        // Initialize control list
        contList = new String[]{"Up", "Down", "Left", "Right",
            "Back", "Screenshot"};
    }

    /**
     * Update state
     *
     * @param container
     * @param game
     * @param delta
     * @throws SlickException
     */
    @Override

    public void update(GameContainer container, StateBasedGame game,
            int delta) throws SlickException {

        // Update delta
        Globals.curDelta = delta;

        // Handle input
        Globals.conServer.handleInput(contList);
    }

    /**
     * Render state
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game,
            Graphics g) throws SlickException {

        // Draw particles on bottom
        Globals.partServer.drawRiftParticles();

        // Draw spaceship 
        g.drawImage(shipImage, ship.getX(), ship.getY());

        // Draw popup on top
        Globals.popStore.renderCurPopup(g);

        // Draw info
        Globals.drawRuntimeInfo(0, 0, Color.white);
    }

    /**
     * Access the ship
     *
     * @return
     */
    public Movable getShip() {
        return ship;
    }
}
