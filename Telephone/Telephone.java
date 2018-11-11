import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Telephone 
{
	JFrame jframe = new JFrame("Telephone App");	
	JButton[][] numbers = new JButton[3][3];
	JTextField screen = new JTextField("");

	JButton clear = new JButton("Call");
	JPanel dialpad = new JPanel();


	Telephone()
	{
		jframe.setSize(300, 500);
		jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
		jframe.setBackground(Color.black);


		dialpad.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();  
		gbc.fill = GridBagConstraints.VERTICAL; 
         
        
		screen.setBounds(10, 20, 280, 100);
		screen.setBackground(Color.white);
		screen.setMaximumSize(new Dimension(280, 100));
		screen.setPreferredSize(new Dimension(280, 40));
		System.out.println(screen);


		jframe.setBackground(Color.black);


		for (int i = 0; i < 3; i ++)
		{

			for (int j = 0; j < 3; j ++)
			{
				numbers[i][j] = new JButton(String.valueOf(i * 3 + j));
				System.out.println(numbers[i][j].getAccessibleContext());
				System.out.println(i + " " + j);

				numbers[i][j].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText(screen.getText() + String.valueOf(e.getActionCommand()));
					} 
				}
				);

				gbc.gridx = j; 
				gbc.gridy = i;
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.fill = GridBagConstraints.BOTH; 


	 
		        dialpad.add(numbers[i][j],gbc);
			}
		}


			clear = new JButton("Clear");
				// clear.setBounds(150, 100, 80, 20);
				clear.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						screen.setText("");
					} 
				}
				);


			jframe.add(screen);
			jframe.add(dialpad);
			jframe.setResizable(false);
			jframe.add(clear);
			jframe.setVisible(true);
	}

	public static void main(String[] args) {
		new Telephone();
	}
}
