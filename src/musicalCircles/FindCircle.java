/**
 * File:       FindCircle.java
 * Package:    musicalCircles
 * Project:    LegoEv3
 * Date:       Apr 6, 2016, 5:20:55 PM
 * Purpose:    Main program.
 * @author     Kory Dondzila, Garret Richardson, Theresa Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package musicalCircles;

import java.util.Random;

import lejos.robotics.SampleProvider;
import lejos.ev3.tools.LCDDisplay;
import lejos.hardware.lcd.*;
import lejos.robotics.geometry.Point;

/**
 * 
 */
public class FindCircle
{
	public enum ColorId
	{
	      RED(0), GREEN(1), BLUE(2), YELLOW(3), MAGENTA(4), ORANGE(5), WHITE(6), BLACK(7), PINK(8), GRAY(9),
	      LIGHT_GRAY(10), DARK_GRAY(11), CYAN(12), BROWN(13), NONE(-1);
	      
	      private int id;

	      private ColorId(int id) {
	         this.id = id;
	      }

	      public static ColorId getInstance(int id) {
	         for (ColorId colorId : ColorId.values()) {
	            if (colorId.id == id) {
	               return colorId;
	            }
	         }
	         return NONE;
	      }
	}
	
	//the car will back up this amount in cm when it detects an object
	static final double RETREAT_AMOUNT = 5.0;
	//the car will rotate until the sensor detects this distance in meters from an object
	static final double RETREAT_ROTATE_UNTIL = 0.25;
	//the car will move forward each "step" this amount in cm
	static final double MOVE_FORWARD_AMOUNT = 3.0;
	

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try ( Ev3MusicalCircles car = new Ev3MusicalCircles() )
		{
			SampleProvider color = car.getColor();
			SampleProvider distance = car.getDistance();
			float[] colorSample = new float[color.sampleSize()];
			float[] distanceSample = new float[distance.sampleSize()];
			Random rand = new Random();
			boolean isDistSensor = true, isFound = false, rightSide = false, leftSide = false;
			int degreesTurned = 0;
			
			while ( true )
			{
				color.fetchSample( colorSample, 0 );
				distance.fetchSample( distanceSample, 0 );
				car.setSpeedLeft( 180 );
				car.setSpeedRight( 180 );
			
				if ( !isFound && colorSample[0] == ColorId.BLACK.id )
				{
					isDistSensor = false;
					isFound = true;
				}
				
				if ( isFound && !rightSide )
				{
					if ( colorSample[0] != ColorId.BLACK.id )
					{
						rightSide = true;
						car.rotate( degreesTurned );
						color.fetchSample( colorSample, 0 );
					}
					else
					{
						car.rotate( -2 );
						degreesTurned += 2;
						
						if ( degreesTurned >= 360 )
						{
							break;
						}
					}
				}
				
				if ( isFound && rightSide && !leftSide )
				{
					if ( colorSample[0] != ColorId.BLACK.id )
					{
						leftSide = true;
						car.rotate( -degreesTurned / 2 );
						color.fetchSample( colorSample, 0 );
					}
					else
					{
						car.rotate( 2 );
						degreesTurned += 2;
					}
				}
				
				if ( !isFound )
				{
					car.moveForward( 5 );
				}
				
				if ( leftSide )
				{
					
					float distanceToCenter = 10.16f;
					
					if ( degreesTurned > 180 )
					{
						distanceToCenter -= ((degreesTurned - 180) / 180 * 2) * 2.54;
					}
					
					car.moveForward( distanceToCenter );
					break;
				}
				
				//STATE 1: ROOMBA MODE
				//Drives forward until it encounters something, and then rotates until it is facing
				//away and repeats. Drops out as soon as it detects black
				if ( isDistSensor && !isFound)
				{
					if (distanceSample[0] <= 0.1 )
					{	
						car.moveBackward(RETREAT_AMOUNT);
						distance.fetchSample( distanceSample, 0 );
						while (isDistSensor && !isFound && distanceSample[0] <= RETREAT_ROTATE_UNTIL)
						{
							car.rotate(rand.nextInt( 135 ) + 45);//rotate until distanceSample > 10
							
							color.fetchSample( colorSample, 0 );
							distance.fetchSample( distanceSample, 0 );
							if ( colorSample[0] == ColorId.BLACK.id )
							{
								isDistSensor = false;
								isFound = true;
							}
						}
						
					}
					car.moveForward(MOVE_FORWARD_AMOUNT);
				}
				LCD.drawString( "Color: " + colorSample[0], 0, 0 );
				
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			//explode
		}

	}

}
