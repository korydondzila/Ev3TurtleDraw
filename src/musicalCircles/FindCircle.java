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
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try ( Ev3MusicalCircles car = new Ev3MusicalCircles() )
		{
			car.setSpeedLeft( 180 );
			car.setSpeedRight( 180 );
			
			SampleProvider color = car.getColor();
			SampleProvider distance = car.getDistance();
			float[] colorSample = new float[color.sampleSize()];
			float[] distanceSample = new float[distance.sampleSize()];
			Random rand = new Random();
			boolean isDistSensor = true;
			
			while ( true )
			{
				color.fetchSample( colorSample, 0 );
				distance.fetchSample( distanceSample, 0 );
				
				if ( isDistSensor && distanceSample[0] <= 0.5 )
				{
					car.rotate( rand.nextInt( 135 ) + 45 );
				}
				
				LCD.drawString( "Color: " + colorSample[0], 0, 0 );
				
				if ( colorSample[0] == ColorId.BLACK.id )
				{
					isDistSensor = false;
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
