/**
 * File:       Letters.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 26, 2016, 11:05:24 AM
 * Purpose:    
 * @author     Kory Dondzila, Garret Richardson, Theresa
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package draw;

/**
 * 
 */
public class Letters
{
	private Ev3TurtleDraw car;
	
	public Letters(Ev3TurtleDraw car)
	{
		this.car = car;
	}
	
	public void drawString(float x, float y, String string)
	{
		char[] characters = string.toUpperCase().toCharArray();
		
		for ( char ch : characters )
		{
			switch ( ch )
			{
				case 'A':
					A(x, y);
					break;
				case ' ':
					break;
				default:
					x -= 8;
					break;
			}
			
			x += 8;
		}
	}
	
	private void A(float x, float y)
	{
		car.line( x + 1, y - 9, x + 4, y - 1 );
		car.lineTo( x + 7, y - 9 );
		car.line( x + 2.5f, y - 5, x + 5.5f, y - 5 );
	}
}
