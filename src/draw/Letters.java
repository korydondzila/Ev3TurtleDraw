/**
 * File:       Letters.java
 * Package:    draw
 * Project:    LegoEv3
 * Date:       Feb 26, 2016, 11:05:24 AM
 * Purpose:    Class for letters using Ev3TurtleDraw
 * @author     Kory Dondzila, Garret Richardson, Theresa Horey
 * @version    "%I%, %G%"
 * Copyright:  2016
 */

package draw;

/**
 * Class that uses an Ev3 turtle to draw letters
 */
public class Letters
{
	private Ev3TurtleDraw car;
	
	/**
	 * Base constructor
	 * @param car The car to attach.
	 */
	public Letters(Ev3TurtleDraw car)
	{
		this.car = car;
	}
	
	/**
	 * Draws a string of letters starting at x, y
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param string The string to draw
	 */
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
				case 'J':
					J(x, y);
					break;
				case 'K':
					K(x, y);
					break;
				case 'L':
					L(x, y);
					break;
				case 'M':
					M(x, y);
					break;
				case 'N':
					N(x, y);
					break;
				case 'O':
					O(x, y);
					break;
				case 'P':
					P(x, y);
					break;
				case 'Q':
					Q(x, y);
					break;
				case 'R':
					R(x, y);
					break;
				case 'S':
					S(x, y);
					break;
				case 'T':
					T(x, y);
					break;
				case 'U':
					U(x, y);
					break;
				case 'V':
					V(x, y);
					break;
				case 'W':
					W(x, y);
					break;
				case 'X':
					X(x, y);
					break;
				case 'Y':
					Y(x, y);
					break;
				case 'Z':
					Z(x, y);
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
		car.line( x + 7, y - 1, x + 7, y - 9);
		car.lineTo( x + 3, y - 9);
		car.lineTo( x + 3, y - 6);
	}
	
	private void K(float x, float y)
	{
		car.line( x + 1, y - 1, x + 1, y - 9);
		car.line( x + 4, y - 1, x + 1, y - 4);
		car.lineTo( x + 4, y - 9);
	}
	
	private void L(float x, float y)
	{
		car.line( x + 1, y - 1, x + 1, y - 9);
		car.lineTo(x + 4, y - 9);
	}
	
	private void M(float x, float y)
	{
		car.line( x + 1, y - 9, x + 1, y - 1);
		car.lineTo( x + 4, y - 4);
		car.lineTo( x + 7, y - 1);
		car.lineTo( x + 7, y - 9);
	}
	
	private void N(float x, float y)
	{
		car.line( x + 1, y - 9, x + 1, y - 1);
		car.lineTo( x + 7, y - 9);
		car.lineTo(x + 7, y - 1);
	}
	
	private void O(float x, float y)
	{
		car.line( x + 1, y - 1, x + 7, y - 1);
		car.lineTo( x + 7, y - 9);
		car.lineTo( x + 1, y - 9);
		car.lineTo( x + 1, y - 1);
	}
	
	private void P(float x, float y)
	{
		car.line( x + 1, y - 9, x + 1, y - 1);
		car.lineTo( x + 4, y - 1);
		car.lineTo( x + 4, y - 4);
		car.lineTo( x + 1, y - 4);
	}
	
	private void Q(float x, float y)
	{
		car.line( x + 2, y - 2, x + 6, y - 2);
		car.lineTo( x + 6, y - 8);
		car.lineTo( x + 2, y - 8);
		car.lineTo( x + 2, y - 2);
		car.line( x + 5, y - 7, x + 7, y - 9);
	}
	
	private void R(float x, float y)
	{
		P(x, y);
		car.lineTo( x + 4, y - 9);
	}
	
	private void S(float x, float y)
	{
		car.line( x + 7, y - 3, x + 4, y - 1 );
		car.lineTo( x + 2, y - 3 );
		car.lineTo( x + 6, y - 7 );
		car.lineTo( x + 4, y - 9 );
		car.lineTo( x + 2, y - 7 );
	}
	
	private void T(float x, float y)
	{
		car.line( x + 2, y - 1, x + 6, y - 1 );
		car.line( x + 4, y - 1, x + 4, y - 9 );
	}
	
	private void U(float x, float y)
	{
		car.line( x + 1, y - 1, x + 1, y - 7 );
		car.lineTo( x + 4, y - 9 );
		car.lineTo( x + 7, y - 7 );
		car.lineTo( x + 7, y - 1 );
	}
	
	private void V(float x, float y)
	{
		car.line( x + 1, y - 1, x + 4, y - 9 );
		car.lineTo( x + 7, y - 1 );
	}
	
	private void W(float x, float y)
	{
		car.line( x + 1, y - 1, x + 2.5f, y - 9 );
		car.lineTo( x + 4, y - 1 );
		car.lineTo( x + 5.5f, y - 9 );
		car.lineTo( x + 7, y - 1 );
	}
	
	private void X(float x, float y)
	{
		car.line( x + 1, y - 1, x + 7, y - 9 );
		car.line( x + 1, y - 9, x + 7, y - 1 );
	}
	
	private void Y(float x, float y)
	{
		car.line( x + 1, y - 1, x + 4, y - 5 );
		car.lineTo( x + 4, y - 9);
		car.line( x + 4, y - 5, x + 7, y - 1 );
	}
	
	private void Z(float x, float y)
	{
		car.line( x + 1, y - 1, x + 7, y - 1 );
		car.lineTo( x + 1, y - 9 );
		car.lineTo( x + 7, y - 9 );
	}
}
