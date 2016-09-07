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

import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

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
	static final double WALL_DISTANCE = 0.1;

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try ( Ev3MusicalCircles car = new Ev3MusicalCircles() )
		{
			// Set wheel speed
			car.setSpeedLeft( 540 );
			car.setSpeedRight( 540 );
			
			// Get SampleProviders and initialize sample srrays.
			SampleProvider color = car.getColor();
			SampleProvider distance = car.getDistance();
			float[] colorSample = new float[color.sampleSize()];
			float[] distanceSample = new float[distance.sampleSize()];
			
			// Initialize flags and counter
			Random rand = new Random();
			boolean isFound = false;
			
			// Wait for button press to start
			Button.waitForAnyPress();
			
			while ( true )
			{
    			// Main while loop, RR
    			while ( true )
    			{
    				// Fetch samples
    				color.fetchSample( colorSample, 0 );
    				distance.fetchSample( distanceSample, 0 );
    				
    				// Check if on black area
    				if ( !isFound )
    				{
    					if ( colorSample[0] == ColorId.BLACK.id )
    					{
    						// Circle found stop car
        					car.LeftWheel().startSynchronization();
    						car.LeftWheel().stop();
    						car.RightWheel().stop();
    						car.LeftWheel().endSynchronization();
        					isFound = true;
    					}
    					else if ( distanceSample[0] < WALL_DISTANCE ) // near obstacle
    					{
    						//Drives forward until it encounters something, and then rotates until it is facing
    	    				//away and repeats. Drops out as soon as it detects black
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
    								isFound = true;
    							}
    						} while ( !isFound && distanceSample[0] < WALL_DISTANCE );
    					}
    					else
    					{
    						// Car not moving, start it moving
    						if ( !car.LeftWheel().isMoving() )
    						{
    							car.LeftWheel().startSynchronization();
    							car.LeftWheel().backward();
    							car.RightWheel().backward();
    							car.LeftWheel().endSynchronization();
    						}
    					}
    				}
    				
    				// Searches for right side of circle then rotates back
    				// to original direction
    				if ( isFound )
    				{
    					// Reset counts
						car.LeftWheel().resetTachoCount();
						car.RightWheel().resetTachoCount();
						int degreesStart = car.LeftWheel().getTachoCount();
						int degreesTurned = 0;
						car.setSpeedLeft( 360 );
						car.setSpeedRight( 360 );
						
						// Start rotation
						car.LeftWheel().startSynchronization();
						car.LeftWheel().backward();
						car.RightWheel().forward();
						car.LeftWheel().endSynchronization();
						
						// Look for first edge of circle
						do
						{
							color.fetchSample( colorSample, 0 );
							
							if ( colorSample[0] != ColorId.BLACK.id )
							{
								// Edge found get new start count
								degreesStart = Math.abs( car.LeftWheel().getTachoCount() );
								degreesTurned = 0;
							}
							else
							{
								// Edge not found, update turned count
								degreesTurned = Math.abs( car.LeftWheel().getTachoCount() ) - degreesStart;
								degreesTurned /= car.BaseToWheelRatio();
							}
						} while ( colorSample[0] == ColorId.BLACK.id && degreesTurned < 360 );
						
						if ( colorSample[0] != ColorId.BLACK.id ) // First edge found
						{
							Delay.msDelay( 50 ); // Slightly delay code because if sensor issues
							
							// Look for second edge of circle
							do
    						{
								color.fetchSample( colorSample, 0 );
								
    							if ( colorSample[0] == ColorId.BLACK.id )
    							{
    								// Edge found, stop car
    								car.LeftWheel().startSynchronization();
        							car.LeftWheel().stop();
        							car.RightWheel().stop();
        							car.LeftWheel().endSynchronization();
        							
        							// Get final turned count
    								degreesTurned = Math.abs( car.LeftWheel().getTachoCount() ) - degreesStart;
    								degreesTurned /= car.BaseToWheelRatio();
    								
    								// Rotate to center
    								car.rotate( -(360 - degreesTurned) / 2 );
    							}
    							else
    							{
    								// Edge not found, update turned count
    								degreesTurned = Math.abs( car.LeftWheel().getTachoCount() ) - degreesStart;
    								degreesTurned /= car.BaseToWheelRatio();
    							}
    						} while ( colorSample[0] != ColorId.BLACK.id && degreesTurned < 360 );
							
							if ( colorSample[0] == ColorId.BLACK.id ) // Second edge found
							{
	    						float distanceToCenter = 7.62f;
	        					
	        					// Amount to move is based on total degrees turned this
	        					// roughly estimates how close to the center the car already is.
	        					if ( 360 - degreesTurned > 180 )
	        					{
	        						distanceToCenter -= ((360 - degreesTurned - 180) / 180 * 2) * 2.54;
	        					}
	        					
	        					car.moveForward( distanceToCenter );
							}
						}
						else // No edge found
						{
							// Too far in black, stop car
							car.LeftWheel().startSynchronization();
							car.LeftWheel().stop();
							car.RightWheel().stop();
							car.LeftWheel().endSynchronization();
						}
						
    					break;
    				}
    			}
    			
    			// Incase car is pushed off circle.
    			while (true)
    			{
    				color.fetchSample( colorSample, 0 );
    				if ( colorSample[0] != ColorId.BLACK.id )
    				{
    					isFound = false;
    					car.setSpeedLeft( 540 );
    					car.setSpeedRight( 540 );
    					break;
    				}
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
