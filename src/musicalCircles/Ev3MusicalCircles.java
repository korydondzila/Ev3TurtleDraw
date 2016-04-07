/**
 * File:       Ev3MusicalCircles.java
 * Package:    musicalCircles
 * Project:    LegoEv3
 * Date:       Apr 4, 2016, 1:50:34 PM
 * Purpose:    Ev3 musical circles car functions.
 * @author     Kory Dondzila, Garret Richardson, Theresa Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package musicalCircles;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import car.Ev3Car;

/**
 * 
 */
public class Ev3MusicalCircles extends Ev3Car
{
	private EV3ColorSensor colorSensor;
	private SampleProvider color;
	private EV3UltrasonicSensor ultrasonicSensor;
	private SampleProvider distance;

	/**
	 * Base Constructor uses values based on original
	 * musical circles car setup.
	 */
	public Ev3MusicalCircles()
	{
		this( 5.6f, 16.5f, MotorPort.A, MotorPort.B, SensorPort.S1, SensorPort.S2 );
	}
	
	/**
	 * Constructor initializes Musical Circles car to given specifications.
	 * @param wheelDiameter The diameter of wheels used.
	 * @param wheelBase The distance between the centers of the two wheels.
	 * @param leftWheelPort The left wheel's port.
	 * @param rightWheelPort The right wheel's port.
	 * @param colorPort The color sensor port
	 * @param ultrasonicPort The ultrasonic sensor port.s
	 */
	public Ev3MusicalCircles( float wheelDiameter, float wheelBase,
							  Port leftWheelPort, Port rightWheelPort,
							  Port colorPort, Port ultrasonicPort )
	{
		super( wheelDiameter, wheelBase, leftWheelPort, rightWheelPort );
		this.colorSensor = new EV3ColorSensor( colorPort );
		this.ultrasonicSensor = new EV3UltrasonicSensor( ultrasonicPort );
		this.color = colorSensor.getColorIDMode();
		this.distance = ultrasonicSensor.getDistanceMode();
		this.colorSensor.setFloodlight( true );
		this.ultrasonicSensor.enable();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception
	{
		super.close();
		colorSensor.close();
		ultrasonicSensor.close();
	}

	/**
	 * @return the color
	 */
	public SampleProvider getColor()
	{
		return this.color;
	}

	/**
	 * @return the distance
	 */
	public SampleProvider getDistance()
	{
		return this.distance;
	}
	
	
}
