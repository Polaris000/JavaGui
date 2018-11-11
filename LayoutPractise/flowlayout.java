import java.awt.*;
import java.awt.event.*;


class flowlayout extends Frame
{
	FlowLayout fl = new FlowLayout();

	CheckboxGroup group1 = new CheckboxGroup();
	Checkbox lr = new Checkbox("Left to right", group1, true);
	Checkbox rl = new Checkbox("Right to left", group1, false);
	Button applybutton = new Button("Apply orientation");


	boolean right = false;

	flowlayout() 
	{
		setLayout(fl);
		setSize(400, 100);

		// close window and end code on pressing X(to close window)
		addWindowListener(new WindowAdapter()
		{
        	public void windowClosing(WindowEvent we) 
        	{
            	dispose();
         	}
     	});

		add(new Button("Button1"));
		add(new Button("Button2"));
		add(new Button("Button3"));
		add(new Button("Long-Named Button4"));
		add(new Button("5"));

		lr.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{               
                 if (e.getStateChange() == 1)
                 {
                 	right = false;
                 }
            }  
        });

        rl.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{               
                 if (e.getStateChange() == 1)
                 {
                 	right = true;
                 }
            }  
        });


        applybutton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{  

        		if (right)
        		{
        			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        		}

        		else
        		{	
        			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				}

        		validate();
        		repaint();	
        		
			}  
        });

		add(lr);
		add(rl);
		add(applybutton);
		setVisible(true);
	}

	public static void main(String[] args) {
		flowlayout f = new flowlayout();
	}
}