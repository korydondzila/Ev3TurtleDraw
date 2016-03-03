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
				case 'B':
					B(x, y);
					break;
				case 'C':
					C(x, y);
					break;
				case 'D':
					D(x, y);
					break;
				case 'E':
					E(x, y);
					break;
				case 'F':
					F(x, y);
					break;
				case 'G':
					G(x, y);
					break;
				case 'H':
					H(x, y);
					break;
				case 'I':
					I(x, y);
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
		car.line( x + 5.5f, y - 5, x + 2.5f, y - 5 );
	}
	
	private void B(float x, float y)
	{
		car.line( x + 1, y - 9, x + 1, y - 1 );
		car.lineTo( x + 4, y - 1 );
		car.lineTo( x + 5, y - 2 );
		car.lineTo( x + 5, y - 4 );
		car.lineTo( x + 4, y - 5 );
		car.line( x + 1, y - 5, x + 6, y - 5 );
		car.lineTo( x + 7, y - 6 );
		car.lineTo( x + 7, y - 8 );
		car.lineTo( x + 6, y - 9 );
		car.lineTo( x + 1, y - 9 );
	}
	
	private void C(float x, float y)
	{
		car.line( x + 7, y - 2, x + 6, y - 1 );
		car.lineTo( x + 2, y - 1 );
		car.lineTo( x + 1, y - 2 );
		car.lineTo( x + 1, y - 8 );
		car.lineTo( x + 2, y - 9 );
		car.lineTo( x + 6, y - 9 );
		car.lineTo( x + 7, y - 8 );
	}
	
	private void D(float x, float y)
	{
		car.line( x + 1, y - 9, x + 1, y - 1 );
		car.lineTo( x + 6, y - 1 );
		car.lineTo( x + 7, y - 2 );
		car.lineTo( x + 7, y - 8 );
		car.lineTo( x + 6, y - 9 );
		car.lineTo( x + 1, y - 9 );
	}
	
	private void E(float x, float y)
	{
		car.line( x + 7, y - 1, x + 1, y - 1 );
		car.lineTo( x + 1, y - 9 );
		car.lineTo( x + 7, y - 9 );
		car.line( x + 1, y - 5, x + 6, y - 5 );
	}
	
	private void F(float x, float y)
	{
		car.line( x + 7, y - 1, x + 1, y - 1 );
		car.lineTo( x + 1, y - 9 );
		car.line( x + 1, y - 5, x + 6, y - 5 );
	}
	
	private void G(float x, float y)
	{
		C( x, y );
		car.lineTo( x + 7, y - 6 );
		car.lineTo( x + 6, y - 6 );
	}
	
	private void H(float x, float y)
	{
		car.line( x + 1, y - 1, x + 1, y - 9 );
		car.line( x + 7, y - 9, x + 7, y - 1 );
		car.line( x + 7, y - 5, x + 1, y - 5 );
	}
	
	private void I(float x, float y)
	{
		car.line( x + 2, y - 1, x + 6, y - 1 );
		car.line( x + 5, y - 1, x + 5, y - 9 );
		car.line( x + 2, y - 9, x + 6, y - 9 );
	}
	
	private void J(float x, float y)
	{
		
	}
	
	private void K(float x, float y)
	{
		
	}
	
	private void L(float x, float y)
	{
		
	}
	
	private void M(float x, float y)
	{
		
	}
	
	private void N(float x, float y)
	{
		
	}
	
	private void O(float x, float y)
	{
		
	}
	
	private void P(float x, float y)
	{
		
	}
	
	private void Q(float x, float y)
	{
		
	}
	
	private void R(float x, float y)
	{
		
	}
	
	private void S(float x, float y)
	{
		
	}
	
	private void T(float x, float y)
	{
		
	}
	
	private void U(float x, float y)
	{
		
	}
	
	private void V(float x, float y)
	{
		
	}
	
	private void W(float x, float y)
	{
		
	}
	
	private void X(float x, float y)
	{
		
	}
	
	private void Y(float x, float y)
	{
		
	}
	
	private void Z(float x, float y)
	{
		
	}
}
