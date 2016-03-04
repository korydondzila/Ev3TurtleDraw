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

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;

/**
 * Class of methods for a turtle drawing Ev3 car.
 */
public class Ev3TurtleDraw implements AutoCloseable
{
	//private float wheelDiameter;
	private float wheelCircumference;
	//private float wheelBase;
	//private float baseCircumference;
	private float baseToWheelRatio;
	private float armLength;
	private Point position;
	private Point direction;
	private RegulatedMotor leftWheel;
	private RegulatedMotor rightWheel;
	private RegulatedMotor pen;
	
	/**
	 * Base Constructor uses values based on original
	 * Turtle car setup.
	 */
	public Ev3TurtleDraw()
	{
		this( 5.6f, 16.5f, 27.0f, MotorPort.A, MotorPort.B, MotorPort.C );
	}
	
	/**
	 * Constructor initializes Turtle car to given specifications.
	 * @param wheelDiameter The diameter of wheels used.
	 * @param wheelBase The distance between the centers of the two wheels.
	 * @param armLength The distance from the center between the wheels to the pen tip.
	 * @param leftWheelPort The left wheel's port.
	 * @param rightWheelPort The right wheel's port.
	 * @param penPort The pen's port.
	 */
	public Ev3TurtleDraw(float wheelDiameter, float wheelBase, float armLength, Port leftWheelPort, Port rightWheelPort, Port pen)
	{
		//this.wheelDiameter = wheelDiameter;
		this.wheelCircumference = (float)(wheelDiameter * Math.PI);
		//this.wheelBase = wheelBase;
		//this.baseCircumference = (float)(wheelBase * Math.PI);
		this.baseToWheelRatio = wheelBase / wheelDiameter;
		this.armLength = armLength;
		this.position = new Point( 0, 0 );
		this.direction = new Point( 1, 0 );
		this.leftWheel = new EV3LargeRegulatedMotor( leftWheelPort );
		this.rightWheel = new EV3LargeRegulatedMotor( rightWheelPort );
		this.pen = new EV3MediumRegulatedMotor( pen );
		this.leftWheel.setSpeed( 90 );
		this.rightWheel.setSpeed( 90 );
		this.pen.setSpeed( 360 );
		leftWheel.synchronizeWith( new RegulatedMotor[] {rightWheel} );
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
	 * Sets the speed of the left wheel.
	 * @param speed The speed in degrees / s.
	 */
	public void setSpeedLeft(int speed)
	{
		leftWheel.setSpeed( speed );
	}
	
	/**
	 * Sets the speed of the right wheel
	 * @param speed The speed in degrees / s.
	 */
	public void setSpeedRight(int speed)
	{
		rightWheel.setSpeed( speed );
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
	 * Rotates the car by degrees.
	 * @param degrees Degrees to rotate by.
	 */
	public void rotate(double degrees)
	{
		setDirection( degrees );
		float wheelDegrees = Math.round( degrees * baseToWheelRatio );
		leftWheel.startSynchronization();
		leftWheel.rotate( (int)wheelDegrees, true );
		rightWheel.rotate( (int)-wheelDegrees, true );
		leftWheel.endSynchronization();
		waiting();
	}
	
	/**
	 * Rotates to given x and y coordinates.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public void rotateTo(float x, float y)
	{
		rotateTo( new Point( x, y ) );
	}
	
	/**
	 * Rotates to specified point.
	 * @param other The point to rotate towards.
	 */
	public void rotateTo(Point other)
	{
		other = other.subtract( position );
		other.normalize();
		double radians = Math.acos( direction.dotProduct( other ) );
		float degrees = (float)Math.toDegrees( radians );
		
		if ( direction.x * other.y - direction.y * other.x < 0)
		{
			degrees *= -1;
		}
		
		rotate( degrees );
	}
	
	/**
	 * Moves the car forward by the specified distance in cm.
	 * @param distance The distance in cm.
	 */
	public void moveForward(double distance)
	{
		setPosition( distance );
		int wheelDegrees = (int)Math.round( distance / wheelCircumference * 360.0f );
		leftWheel.startSynchronization();
		leftWheel.rotate( -wheelDegrees, true );
		rightWheel.rotate( -wheelDegrees, true );
		leftWheel.endSynchronization();
		waiting();
	}
	
	/**
	 * Moves the car backward by the specified distance in cm.
	 * @param distance The distance in cm.
	 */
	public void moveBackward(double distance)
	{
		moveForward( -distance );
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
	
	/**
	 * Sets the current direction the car is facing.
	 * @param degrees Degrees to rotate direction by.
	 */
	private void setDirection(double degrees)
	{
		float sinTheta = (float)Math.sin( Math.toRadians( degrees ) );
		float cosTheta = (float)Math.cos( Math.toRadians( degrees ) );
		
		if ( sinTheta < 1.0e-16 && sinTheta > 0 )
		{
			sinTheta = 0;
		}
		
		if ( cosTheta < 1.0e-16 && cosTheta > 0 )
		{
			cosTheta = 0;
		}
		
		float newX = direction.x * cosTheta - direction.y * sinTheta;
		float newY = direction.x * sinTheta + direction.y * cosTheta;
		
		direction.setLocation( newX, newY );
	}
	
	/**
	 * Translates the current position along the current
	 * direction by distance.
	 * @param distance The distance to translate by.
	 */
	private void setPosition(double distance)
	{
		float xDist = (float)(distance * direction.x);
		float yDist = (float)(distance * direction.y);
		
		if ( xDist < 1.0e-16 && xDist > 0 || xDist > -1.0e-16 && xDist < 0)
		{
			xDist = 0;
		}
		
		if ( yDist < 1.0e-16 && yDist > 0 || yDist > -1.0e-16 && yDist < 0 )
		{
			yDist = 0;
		}
		
		position.translate( xDist, yDist );
	}
	
	/**
	 * Waits until the motors stop moving.
	 */
	private void waiting()
	{
		leftWheel.waitComplete();
		rightWheel.waitComplete();
	}
}
