/**
 * File:       FindCircle.java
 * Package:    musicalCircles
 * Project:    LegoEv3
 * Date:       Apr 6, 2016, 5:20:55 PM
 * Purpose:    Main program, car looks for black area and then
 * 			   attempts to move close to center.
 * @author     Kory Dondzila, Garret Richardson, Theresa Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package musicalCircles;

import java.util.Random;
import lejos.robotics.SampleProvider;

public class FindCircle
{
	// Defines the colors detected by ColorSensor as enum
	public enum ColorId
	{
	    RED(0), GREEN(1), BLUE(2), YELLOW(3), MAGENTA(4), ORANGE(5), WHITE(6), BLACK(7),
	    PINK(8), GRAY(9), LIGHT_GRAY(10), DARK_GRAY(11), CYAN(12), BROWN(13), NONE(-1);
	    
	    private int id;
	    
	    private ColorId( int id )
	    {
	    	this.id = id;
	    }

	    public static ColorId getInstance(int id)
	    {
	       for ( ColorId colorId : ColorId.values() )
	       {
	          if ( colorId.id == id )
	          {
	             return colorId;
	          }
	       }
	       return NONE;
	    }
	}
	
	//the car will back up this amount in cm when it detects an object
	static final double RETREAT_AMOUNT = 5.0;
	
	//the car will rotate until the sensor detects this distance in meters from an object
	static final double WALL_DISTANCE = 0.3;
	
	//the car will move forward each "step" this amount in cm
	static final double MOVE_FORWARD_AMOUNT = 5.0;

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try ( Ev3MusicalCircles car = new Ev3MusicalCircles() )
		{
			// Set wheel speed
			car.setSpeedLeft( 180 );
			car.setSpeedRight( 180 );
			
			// Get SampleProviders and initialize sample srrays.
			SampleProvider color = car.getColor();
			SampleProvider distance = car.getDistance();
			float[] colorSample = new float[color.sampleSize()];
			float[] distanceSample = new float[distance.sampleSize()];
			
			// Initialize flags and counter
			Random rand = new Random();
			boolean isDistSensor = true, isFound = false, rightSide = false, leftSide = false;
			int degreesTurned = 0;
			
			// Main while loop, RR
			while ( true )
			{
				// Fetch samples
				color.fetchSample( colorSample, 0 );
				distance.fetchSample( distanceSample, 0 );
				
				// Check if on black area
				if ( !isFound && colorSample[0] == ColorId.BLACK.id )
				{
					isDistSensor = false; // stop checking for obstacles.
					isFound = true;
				}
				
				//STATE 1: ROOMBA MODE
				//Drives forward until it encounters something, and then rotates until it is facing
				//away and repeats. Drops out as soon as it detects black
				if ( isDistSensor && !isFound )
				{
					if ( distanceSample[0] < 0.3 ) // near obstacle
					{
						car.moveBackward( RETREAT_AMOUNT );
						
						// Keep rotating if near obstacle
						do
						{
							int direction = rand.nextInt( 2 ); // Choose left or right
							car.rotate( (rand.nextInt( 90 ) + 45) * (direction == 0 ? -1 : 1) );
							
							// Get new sensor data
							color.fetchSample( colorSample, 0 );
							distance.fetchSample( distanceSample, 0 );
							
							// Check if on black area
							if ( colorSample[0] == ColorId.BLACK.id )
							{
								isDistSensor = false;
								isFound = true;
							}
						} while ( isDistSensor && !isFound && distanceSample[0] < WALL_DISTANCE );
					}
				}
				
				// Searches for right side of circle then rotates back
				// to original direction
				if ( isFound && !rightSide )
				{
					if ( colorSample[0] != ColorId.BLACK.id ) // Found right side
					{
						rightSide = true;
						car.rotate( degreesTurned );
						color.fetchSample( colorSample, 0 ); // Update sensor data
					}
					else // Rotate right and store degrees
					{
						car.rotate( -2 );
						degreesTurned += 2;
						
						// Too far in black and can't determine center location
						if ( degreesTurned >= 200 )
						{
							car.rotate( degreesTurned );
							break; // End program
						}
					}
				}
				
				// Searches for left side of circle then rotates to face center
				if ( isFound && rightSide && !leftSide )
				{
					if ( colorSample[0] != ColorId.BLACK.id ) // Found left side
					{
						leftSide = true;
						car.rotate( -degreesTurned / 2 ); // Rotate to center
						color.fetchSample( colorSample, 0 ); // Update sensor data
					}
					else // Rotate left and store degrees
					{
						car.rotate( 2 );
						degreesTurned += 2;
					}
				}
				
				// Move towards center
				if ( leftSide )
				{
					float distanceToCenter = 10.16f;
					
					// Amount to move is based on total degrees turned this
					// roughly estimates how close to the center the car already is.
					if ( degreesTurned > 180 )
					{
						distanceToCenter -= ((degreesTurned - 180) / 180 * 2) * 2.54;
					}
					
					car.moveForward( distanceToCenter );
					break; // End program
				}
				
				// Hasn't found circle, move forward
				if ( !isFound )
				{
					car.moveForward( MOVE_FORWARD_AMOUNT );
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
		}
	}
}
