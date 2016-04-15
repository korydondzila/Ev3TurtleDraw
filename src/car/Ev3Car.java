/**
 * File:       Ev3Car.java
 * Package:    car
 * Project:    LegoEv3
 * Date:       Apr 4, 2016, 1:10:14 PM
 * Purpose:    Ev3 car functions.
 * @author     Kory Dondzila, Garret Richardson, Therese Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package car;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.geometry.Point;

/**
 * 
 */
public class Ev3Car implements AutoCloseable
{
	protected float wheelCircumference;
	protected float baseToWheelRatio;
	protected Point position;
	protected Point direction;
	protected RegulatedMotor leftWheel;
	protected RegulatedMotor rightWheel;
	
	/**
	 * Base Constructor uses values based on original car setup.
	 */
	public Ev3Car()
	{
		this( 5.6f, 16.5f, MotorPort.A, MotorPort.B );
	}
	
	/**
	 * Constructor initializes car to given specifications.
	 * @param wheelDiameter The diameter of wheels used.
	 * @param wheelBase The distance between the centers of the two wheels.
	 * @param leftWheelPort The left wheel's port.
	 * @param rightWheelPort The right wheel's port.
	 */
	public Ev3Car(float wheelDiameter, float wheelBase, Port leftWheelPort, Port rightWheelPort)
	{
		this.wheelCircumference = (float)(wheelDiameter * Math.PI);
		this.baseToWheelRatio = wheelBase / wheelDiameter;
		this.position = new Point( 0, 0 );
		this.direction = new Point( 1, 0 );
		this.leftWheel = new EV3LargeRegulatedMotor( leftWheelPort );
		this.rightWheel = new EV3LargeRegulatedMotor( rightWheelPort );
		this.leftWheel.setSpeed( 90 );
		this.rightWheel.setSpeed( 90 );
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
	}
	
	/**
	 * @return the direction
	 */
	public Point getDirection()
	{
		return this.direction;
	}
	
	/**
	 * @return the left wheel
	 */
	public RegulatedMotor LeftWheel()
	{
		return leftWheel;
	}
	
	/**
	 * @return the right wheel
	 */
	public RegulatedMotor RightWheel()
	{
		return rightWheel;
	}
	
	/**
	 * @return the base to wheel ratio
	 */
	public float BaseToWheelRatio()
	{
		return baseToWheelRatio;
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
	public void waiting()
	{
		leftWheel.waitComplete();
		rightWheel.waitComplete();
	}
}
