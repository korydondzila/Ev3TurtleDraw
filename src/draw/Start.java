/**
 * File:       Start.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 13, 2016, 2:24:04 PM
 * Purpose:    Main method
 * @author     Kory Dondzila, Garret Richardson, Theresa
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
			car.setSpeedLeft( 120 );
			car.setSpeedRight( 120 );
			Letters text = new Letters( car );
			text.drawString( 0, 0, "ABCDEF" );
			text.drawString( 0, -10, "GHIJKL" );
			text.drawString( 0, -20, "MNOPQR" );
			text.drawString( 0, -30, "STUVWX" );
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
