
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * É^ÉCÉgÉã:
 * ê‡ñæ:
 * íòçÏå†:   Copyright (c) 2002
 * âÔé–ñº:
 * @author
 * @version 1.0
 */

public class CodeConvert extends JFrame
{
    JTextField txtChar = new JTextField();
    JComboBox cmbFont = new JComboBox();
    JButton btnChar2Code = new JButton();
    JButton btnCode2Char = new JButton();
    JTextField txtUnicode = new JTextField();
    JTextField txtShiftJis = new JTextField();
    JTextField txtJIS = new JTextField();

    public CodeConvert() {
        try {
            jbInit();
			setSize(800,400);
			initComponent();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

	private void initComponent() throws Exception
	{
		//font
		String[] a = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i=0;i<a.length;i++) cmbFont.addItem(a[i]);
		cmbFont.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				txtChar.setFont(new Font(cmbFont.getSelectedItem().toString() , Font.PLAIN, 12));
			}
		});

		//char2code
		btnChar2Code.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					String sb = "";
					char[] cs = txtChar.getText().toCharArray();
					int code = (int) cs[0];
					for(int i=0;i<cs.length;i++)
					{
						sb = sb + Integer.toHexString((int)cs[i]) + " ";
					}
					txtUnicode.setText(sb);

					StringBuffer bbb = new StringBuffer("");
					for(int j=0;j<cs.length;j++)
					{
						byte[] b = String.valueOf(cs[j]).getBytes("SJIS");
						for(int i=0;i<b.length;i++)
						{
							String bb = Integer.toHexString(b[i]);
							if(bb.length() == 8)bb = bb.substring(6,8);
							bbb.append(bb);
						}
						bbb.append(" ");
					}
					txtShiftJis.setText(bbb.toString());

					StringBuffer bbb2 = new StringBuffer("");
					for(int j=0;j<cs.length;j++)
					{
						byte[] b = String.valueOf(cs[j]).getBytes("JIS");
						String tt = "";
						for(int i=0;i<b.length;i++)
						{
							tt = tt + Integer.toHexString(b[i]);
						}
						if(tt.length() == 16)tt = tt.substring(6,10);
						bbb2.append(tt).append(" ");
					}
					txtJIS.setText(bbb2.toString());
				}
				catch(Exception eee){eee.printStackTrace();}

			}
		});

		//code2char
		btnCode2Char.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					String s = txtUnicode.getText();
					if(s == null || "".equals(s.trim()))
					{
						s = txtShiftJis.getText();
						if(s == null || "".equals(s.trim()))
						{
							//s = txtJIS.getText();
							txtChar.setText("");
						}
						else
						{
							byte[] bs = new byte[s.length()/2];
							for(int i=0;i<bs.length;i+=1)
							{
								bs[i] = (byte)Integer.parseInt(s.substring(i*2,i*2+2),16);
							}

							txtChar.setText(new String(bs,"SJIS"));
						}
					}
					else
					{
							byte[] bs = new byte[s.length()/2+2];
							bs[0] = (byte)-1;
							bs[1] = (byte)-2;
							for(int i=0;i<bs.length-2;i+=1)
							{
								bs[bs.length-2-i+1] = (byte)Integer.parseInt(s.substring(i*2,i*2+2),16);
							}

							txtChar.setText(new String(bs,"UTF-16"));
					}
				}
				catch(Exception eee){eee.printStackTrace();}

			}
		});
	}

	private void jbInit() throws Exception {
	    JLabel jLabel1 = new JLabel();
	    JLabel jLabel2 = new JLabel();
	    JLabel jLabel3 = new JLabel();
        txtChar.setBounds(new Rectangle(27, 180, 188, 38));
        this.getContentPane().setLayout(null);
        cmbFont.setBounds(new Rectangle(28, 255, 193, 31));
        btnChar2Code.setText("Å‚");
        btnChar2Code.setBounds(new Rectangle(294, 167, 84, 55));
        btnCode2Char.setText("Å·");
        btnCode2Char.setBounds(new Rectangle(294, 249, 85, 54));
        jLabel1.setText("Unicode:");
        jLabel1.setBounds(new Rectangle(416, 133, 65, 31));
        txtUnicode.setBounds(new Rectangle(486, 125, 250, 43));
        txtShiftJis.setBounds(new Rectangle(486, 204, 250, 43));
        jLabel2.setBounds(new Rectangle(417, 212, 65, 31));
        jLabel2.setText("Shift JIS:");
        txtJIS.setBounds(new Rectangle(487, 291, 250, 43));
        jLabel3.setBounds(new Rectangle(417, 299, 65, 31));
        jLabel3.setText("JIS:");
        this.getContentPane().add(txtChar, null);
        this.getContentPane().add(cmbFont, null);
        this.getContentPane().add(btnChar2Code, null);
        this.getContentPane().add(btnCode2Char, null);
        this.getContentPane().add(txtUnicode, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(txtShiftJis, null);
        this.getContentPane().add(txtJIS, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jLabel2, null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public static final void main(String[] arg)
	{
		new CodeConvert().setVisible(true);
	}
}
