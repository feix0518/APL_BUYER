import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GetUnicode extends JFrame
{
	JTextField charact = new JTextField();
	JTextField out = new JTextField();

    public GetUnicode()
    {
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("GetUnicode");
        setSize(400, 300);
        setLocation(300,250);

		charact.setBounds(20,20,300,30);
		getContentPane().add(charact);

		JRadioButton rbJ = new JRadioButton("ì˙ñ{åÍ");
		rbJ.setSelected(true);
		JRadioButton rbC = new JRadioButton("íÜçëåÍ");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbJ);bg.add(rbC);

		rbJ.setBounds(20,55,100,30);
		rbC.setBounds(140,55,100,30);
		getContentPane().add(rbJ);
		getContentPane().add(rbC);

		rbJ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				charact.setFont(new Font("ÇlÇr ÉSÉVÉbÉN", Font.PLAIN, 15));
			}
		});

		rbC.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				charact.setFont(new Font("GB Mincho", Font.PLAIN, 12));
			}
		});

		JButton b = new JButton("DO");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String sb = "";
				char[] cs = charact.getText().toCharArray();
				for(int i=0;i<cs.length;i++)
				{
					sb = sb + Integer.toHexString((int)cs[i]) + "  ";
				}
				out.setText(sb);
			}
		});

		b.setBounds(50,90,100,30);
		getContentPane().add(b);

		out.setBounds(50,140,300,30);
		getContentPane().add(out);
		out.setEditable(false);
    }

    public static void main(String[] args)
    {
        new GetUnicode().setVisible(true);
    }
}
