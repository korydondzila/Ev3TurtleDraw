/**
 * File:       Start.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 13, 2016, 2:24:04 PM
 * Purpose:    
 * @author     Kory Dondzila
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package draw;

import lejos.robotics.geometry.Point;


/**
 * 
 */
public class Start
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try ( Ev3TurtleDraw car = new Ev3TurtleDraw())
		{
			car.lineTo(20, 20);
			car.line( 0, 0, -10, -10 );
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
