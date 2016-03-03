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
			car.setSpeedLeft( 180 );
			car.setSpeedRight( 180 );
			Letters text = new Letters( car );
			text.drawString( 0, 0, "A 1BCDEF" );
			text.drawString( 0, -10, "2GHI" );
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
