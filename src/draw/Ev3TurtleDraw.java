/**
 * File:       Ev3TurtleDraw.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 17, 2016, 8:19:44 PM
 * Purpose:    Drawing Ev3 functions, works similarly to turtle graphics.
 * @author     Kory Dondzila, Garret Richardson, Theresa Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package draw;

import car.Ev3Car;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;

/**
 * Class of methods for a turtle drawing Ev3 car.
 */
public class Ev3TurtleDraw extends Ev3Car
{
	private float armLength;
	private RegulatedMotor pen;
	
	/**
	 * Base Constructor uses values based on original
	 * Turtle car setup.
	 */
	public Ev3TurtleDraw()
	{
		super( 5.6f, 16.5f, MotorPort.A, MotorPort.B );
		this.armLength = 27.0f;
		this.pen = new EV3MediumRegulatedMotor( MotorPort.C );
		this.pen.setSpeed( 360 );
	}
	
	/**
	 * Constructor initializes Turtle car to given specifications.
	 * @param armLength The distance from the center between the wheels to the pen tip.
	 * @param penPort The pen's port.
	 */
	public Ev3TurtleDraw(float armLength, Port pen)
	{
		this.armLength = armLength;
		this.pen = new EV3MediumRegulatedMotor( pen );
		this.pen.setSpeed( 360 );
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception
	{
		leftWheel.close();
		rightWheel.close();
		pen.close();
	}
	
	/**
	 * Lowers the pen.
	 */
	private void lowerPen()
	{
		pen.rotate( 15, true );
		pen.waitComplete();
	}
	
	/**
	 * Raises the pen.
	 */
	private void raisePen()
	{
		pen.rotate( -15, true );
		pen.waitComplete();
	}
	
	/**
	 * Draws a line from x1, y1 to x2, y2.
	 * @param x1 Starting x coordinate.
	 * @param y1 Starting y coordinate.
	 * @param x2 Ending x coordinate.
	 * @param y2 Ending y coordinate.
	 * @return The ending point.
	 */
	public Point line(float x1, float y1, float x2, float y2)
	{
		return line( new Point(x1, y1), new Point(x2, y2) );
	}
	
	/**
	 * Draws a line from starting point to x, y.
	 * @param startPoint The starting point.
	 * @param x Ending x coordinate.
	 * @param y Ending y coordinate
	 * @return The ending point.
	 */
	public Point line(Point startPoint, float x, float y)
	{
		return line( startPoint, new Point(x, y) );
	}
	
	/**
	 * Draws a line from startPoint to endPoint.
	 * @param startPoint The starting point.
	 * @param endPoint The ending point.
	 * @return The ending point.
	 */
	public Point line(Point startPoint, Point endPoint)
	{
		rotateTo( startPoint );
		moveForward( position.distance( startPoint ) );
		return lineTo( endPoint );
	}
	
	/**
	 * Draws a line from current position to x, y.
	 * @param x Ending x coordinate.
	 * @param y Ending y coordinate.
	 * @return The ending point.
	 */
	public Point lineTo(float x, float y)
	{
		return lineTo( new Point(x, y) );
	}
	
	/**
	 * Draws a line from current position to endPoint.
	 * @param endPoint The ending point.
	 * @return The ending point.
	 */
	public Point lineTo(Point endPoint)
	{
		Point initial = new Point( position.x, position.y );
		rotateTo( endPoint );
		moveBackward( armLength );
		lowerPen();
		moveForward( initial.distance( endPoint ) );
		raisePen();
		moveForward( armLength );
		return endPoint;
	}
}
