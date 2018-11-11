import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// for solving equations:
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;


public class Calculator
{
	JFrame jframe = new JFrame("Calculator");
	JButton[][] buttons = new JButton[4][4];
	JPanel buttonpad = new JPanel();
	JButton clearscreen = new JButton("Clear");
	JTextField screen = new JTextField();

	Calculator()
	{
		// JFrame
		jframe.setSize(300, 500);
		jframe.setLayout(new GridLayout(3, 0));
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//JButton clearscreen:
		clearscreen.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText("");
					}
				});

		//JButtons:
		// number keys (1 - 9)
		for (int row = 0; row < 3; row ++)
		{
			for (int col = 0; col < 3; col ++)
			{
				buttons[row][col] = new JButton(String.valueOf(3 * row + col + 1));
				buttons[row][col].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + String.valueOf(e.getActionCommand()));
					}
				});
			}
		}

		// number keys 0, =, .
		buttons[3][0] = new JButton("0");
		buttons[3][1] = new JButton("=");
		buttons[3][2] = new JButton(".");

		//action listeners for 0, = and .
		buttons[3][0].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + String.valueOf(e.getActionCommand()));
					}
				});
		buttons[3][1].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(validate(screen.getText()));

					}
				});
		buttons[3][2].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + String.valueOf(e.getActionCommand()));
					}
				});

		//operation keys: 
		buttons[0][3] = new JButton("+");
		buttons[1][3] = new JButton("-");
		buttons[2][3] = new JButton("*");
		buttons[3][3] = new JButton("/");

		//action listeners for operation keys:
		buttons[0][3].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + " " + String.valueOf(e.getActionCommand()) + " ");
					}
				});
		buttons[1][3].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + " " + String.valueOf(e.getActionCommand()) + " ");
					}
				});
		buttons[2][3].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + " " + String.valueOf(e.getActionCommand()) + " ");
					}
				});
		buttons[3][3].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + " " + String.valueOf(e.getActionCommand()) + " ");
					}
				});

		//JPanel
		buttonpad.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH; 

		//add buttons to jpanel
		for (int row = 0; row < 4; row ++)
		{
			for (int col = 0; col < 4; col ++)
			{

				gbc.gridx = col; 
				gbc.gridy = row;
				buttonpad.add(buttons[row][col], gbc); 
				System.out.println(buttons[row][col].getAccessibleContext());
			}
		}

		// add all components
		jframe.add(screen);
		jframe.add(buttonpad);
		jframe.add(clearscreen);
		jframe.setVisible(true);
	}

	public static void main(String[] args) 
	{
		new Calculator();
	}

	// calulator logic:
	//validate:
	public static String validate(String equation)
	{
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String infix = equation;

		try
		{
			return String.valueOf(engine.eval(infix));
		}

		catch(Exception e)
		{

			return "Invalid expression!";
		}
	}
}