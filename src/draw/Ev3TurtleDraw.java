/**
 * File:       Ev3TurtleDraw.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 17, 2016, 8:19:44 PM
 * Purpose:    Drawing Ev3 functions, works similarly to turtle graphics.
 * @author     Kory Dondzila, Garret Richardson, Theresa
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package draw;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;
import lejos.utility.Delay;

/**
 * 
 */
public class Ev3TurtleDraw implements AutoCloseable
{
	private float wheelDiameter;
	private float wheelCircumference;
	private float wheelBase;
	private float baseCircumference;
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
		this( 5.6f, 16.5f, 26.0f, MotorPort.A, MotorPort.B, MotorPort.C );
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
		this.wheelDiameter = wheelDiameter;
		this.wheelCircumference = (float)(wheelDiameter * Math.PI);
		this.wheelBase = wheelBase;
		this.baseCircumference = (float)(wheelBase * Math.PI);
		this.baseToWheelRatio = wheelBase / wheelDiameter;
		this.armLength = armLength;
		this.position = new Point( 0, 0 );
		this.direction = new Point( 1, 0 );
		this.leftWheel = new EV3LargeRegulatedMotor( leftWheelPort );
		this.rightWheel = new EV3LargeRegulatedMotor( rightWheelPort );
		this.pen = new EV3LargeRegulatedMotor( pen );
		this.leftWheel.setSpeed( 90 );
		this.rightWheel.setSpeed( 90 );
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
		pen.rotate( 10, true );
	}
	
	/**
	 * Raises the pen.
	 */
	private void raisePen()
	{
		pen.rotate( -10, true );
	}
	
	/**
	 * Rotates the car by degrees.
	 * @param degrees Degrees to rotate by.
	 */
	public void rotate(float degrees)
	{
		float wheelDegrees = Math.round( degrees * baseToWheelRatio );
		leftWheel.rotate( (int)wheelDegrees, true );
		rightWheel.rotate( (int)-wheelDegrees, true );
		setDirection( (int)degrees );
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
		float radians = (float)Math.acos( direction.dotProduct( other ) );
		float degrees = (float)Math.toDegrees( radians );
		
		LCD.drawString( "degrees: " + degrees, 0, 0 );
		
		if ( direction.x * other.y - direction.y * other.x < 0)
		{
			degrees *= -1;
		}
		
		rotate( degrees );
	}
	
	/**
	 * Sets the current direction the car is facing.
	 * @param degrees Degrees to rotate direction by.
	 */
	private void setDirection(int degrees)
	{
		float sinTheta = (float)Math.sin( Math.toRadians( degrees ) );
		float cosTheta = (float)Math.cos( Math.toRadians( degrees ) );
		
		float newX = direction.x * cosTheta - direction.y * sinTheta;
		float newY = direction.x * sinTheta - direction.y * cosTheta;
		
		direction.setLocation( newX, newY );
	}
	
	private void waiting()
	{
		while ( leftWheel.isMoving() || rightWheel.isMoving() ){}
	}
}
