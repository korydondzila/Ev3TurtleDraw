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
    		car.rotate( 90 );
    		car.rotateTo( 5, 0 );
    		car.rotate( -45 );
    		car.rotateTo( -1, 4 );
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
