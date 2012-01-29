/*      */ package iphonebuyer;
/*      */ 
/*      */ import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import networking.http.HttpPost;
import networking.http.HttpResponseProcessor;
import networking.http.ProvideSuccess;

import com.heaton.threads.ThreadPool;
 
 public class MainJFrame extends JFrame
   implements Runnable, HttpResponseProcessor
 {
   public static boolean DEBUG_MODE = false;
 
   private ArrayList<User> users = new ArrayList(100);
   private DefaultTableModel model;
   public String store;
   public static MainJFrame instance;
   private ArrayList<Getor> getors = new ArrayList(100);
   private ImageIcon okIcon = new ImageIcon(getClass().getResource("success16.png"));
   private ImageIcon stopIcon = new ImageIcon(getClass().getResource("gg_ignored.png"));
   private ImageIcon runningIcon = new ImageIcon(getClass().getResource("greenled.png"));
   private ProvideSuccess provider = new ProvideSuccess();
   public String diskId = "";
   public int timoutcount = 0;
   private int timeout = 30;
   private MusicPlayer myMusicPlay = null;
   private ArrayList<CodeInputDlg> dlgs = new ArrayList(20);
   public static final int WHICH_USER = 0;
   public static ArrayList<String> phoneModels = new ArrayList(6);
   public static ArrayList<String> phoneTypes = new ArrayList(6);
   private String loginToken = null;
   private ButtonGroup group;
   private JButton jButton1;
   private JButton jButton3;
   private JCheckBox jCheckBox1;
   private JCheckBox jCheckBox2;
   private JCheckBox jCheckBox3;
   private JComboBox jComboBox1;
   private JComboBox jComboBox2;
   private JComboBox jComboBox3;
   private JLabel jLabel1;
   private JLabel jLabel2;
   private JLabel jLabel3;
   private JLabel jLabel5;
   private JLabel jLabel6;
   private JPanel jPanel1;
   private JPanel jPanel3;
   private JRadioButton jRadioButton1;
   private JRadioButton jRadioButton2;
   private JRadioButton jRadioButton3;
   private JRadioButton jRadioButton4;
   private JScrollPane jScrollPane1;
   private JScrollPane jScrollPane2;
   private JTable jTable1;
   private JTextArea jTextArea1;
   private JTextField jTextField1;
   private JTextField jTextField2;
 
   public MainJFrame()
   {
     initComponents();
     setTitle("鬼叫(iPad版) 5-22");
     this.model = ((DefaultTableModel)this.jTable1.getModel());
     instance = this;
     if ((LoginDialog.getInstance() == null) || (LoginDialog.getInstance().token == null)) {
       login();
     }
     readConfig();
   }
 
   public void readConfig() {
     try {
       File file = new File("settings.txt");
       if (!file.exists()) {
         file.createNewFile();
       } else {
         BufferedReader bi = new BufferedReader(new FileReader(file));
         String line = bi.readLine();
 
         while (line != null) {
           if (!line.trim().equals("")) {
             int index = line.indexOf("=");
             if (index > 0) {
               String key = line.substring(0, index);
               String value = line.substring(index + 1);
               if (key.equals("threadnum")) {
                 if ((value != null) && (!value.equals("")))
                   this.jTextField2.setText(value.trim());
               }
               else if (key.equals("timeout")) {
                 if ((value != null) && (!value.equals("")))
                   this.jTextField1.setText(value.trim());
               }
               else if (key.equals("store")) {
                 if ((value != null) && (!value.equals(""))) {
                   if (value.equals("1"))
                     this.jRadioButton1.setSelected(true);
                   else if (value.equals("2"))
                     this.jRadioButton2.setSelected(true);
                   else if (value.equals("3"))
                     this.jRadioButton3.setSelected(true);
                   else if (value.equals("4"))
                     this.jRadioButton4.setSelected(true);
                 }
               }
               else if (key.equals("cow")) {
                 if ((value != null) && (!value.equals(""))) {
                   int inx = 0;
                   try {
                     inx = Integer.parseInt(value);
                     if (inx < this.jComboBox3.getItemCount())
                       this.jComboBox3.setSelectedIndex(inx);
                   }
                   catch (Exception e) {
                     e.printStackTrace();
                   }
                 }
               } else if (key.equals("notify")) {
                 if ((value != null) && (value.equals("yes")))
                   this.jCheckBox1.setSelected(true);
                 else
                   this.jCheckBox1.setSelected(false);
               }
               else if (key.equals("five")) {
                 if ((value != null) && (value.equals("yes")))
                   this.jCheckBox2.setSelected(true);
                 else
                   this.jCheckBox2.setSelected(false);
               }
               else if (key.equals("repeat")) {
                 if ((value != null) && (value.equals("yes")))
                   this.jCheckBox3.setSelected(true);
                 else
                   this.jCheckBox3.setSelected(false);
               }
               else if ((key.equals("type")) && 
                 (value != null) && (!value.equals(""))) {
                 int inx = 0;
                 try {
                   inx = Integer.parseInt(value);
                   this.jComboBox1.setSelectedIndex(inx);
                 } catch (Exception e) {
                   e.printStackTrace();
                 }
 
               }
 
             }
 
           }
 
           line = bi.readLine();
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public void saveConfig() {
     try {
       FileWriter writer = new FileWriter(new File("settings.txt"));
       StringBuffer saveString = new StringBuffer(512);
       saveString.append("threadnum=" + this.jTextField2.getText().trim());
       saveString.append("\n");
       saveString.append("timeout=" + this.jTextField1.getText().trim());
       saveString.append("\n");
       saveString.append("store=" + (getSelectedStore() + 1));
       saveString.append("\n");
       saveString.append("cow=" + this.jComboBox3.getSelectedIndex());
       saveString.append("\n");
       saveString.append("notify=" + (this.jCheckBox1.isSelected() ? "yes" : "no"));
       saveString.append("\n");
       saveString.append("five=" + (this.jCheckBox2.isSelected() ? "yes" : "no"));
       saveString.append("\n");
       saveString.append("repeat=" + (this.jCheckBox3.isSelected() ? "yes" : "no"));
       saveString.append("\n");
       saveString.append("type=" + this.jComboBox1.getSelectedIndex());
       saveString.append("\n");
       writer.write(saveString.toString());
       writer.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public int getThreadNum() {
     int num = 0;
     try {
       num = Integer.parseInt(this.jTextField2.getText().trim());
     } catch (Exception e) {
       e.printStackTrace();
     }
     return num;
   }
   public int getSelectedCowIndex() {
     return this.jComboBox3.getSelectedIndex();
   }
   public int getSelectedStore() {
     if (this.jRadioButton1.isSelected()) {
       return 0;
     }
     if (this.jRadioButton2.isSelected()) {
       return 1;
     }
     if (this.jRadioButton3.isSelected())
       return 2;
     if (this.jRadioButton4.isSelected())
       return 3;
     return -1;
   }
 
   public void setPoneNum(Getor getor, int num) {
     for (int i = 0; i < this.users.size(); i++) {
       User usr = (User)this.users.get(i);
       if (usr.appleid.equals(getor.getUser().appleid)) {
         this.jTable1.setValueAt(Integer.valueOf(num), i, 5);
         break;
       }
     }
   }
 
   public JComboBox getModelComboBox() {
     return this.jComboBox1;
   }
   public int getSelectedPhone() {
     int index = this.jComboBox1.getSelectedIndex();
     if (index < 0) {
       index = 0;
     }
     return index;
   }
   public String getLoginToken() {
     return this.loginToken;
   }
   public void setLoginToken(String token) {
     this.loginToken = token;
   }
 
   public ProvideSuccess getProvider()
   {
     return this.provider;
   }
   public boolean isPlaySound() {
     return this.jCheckBox1.isSelected();
   }
   public void PlaySound() {
     this.myMusicPlay = new MusicPlayer(MusicPlayer.class.getResource("intwap-761700110.wav"));
     this.myMusicPlay.start();
   }
   public int getSelectedMode() {
     return this.jComboBox2.getSelectedIndex();
   }
   public boolean isToGetDouble() {
     return this.jCheckBox2.isSelected();
   }
   public boolean isRollback() {
     return this.jCheckBox3.isSelected();
   }
   public void addCodeInputDlg(CodeInputDlg dlg) {
     this.dlgs.add(dlg);
   }
   public CodeInputDlg getCodeInputDlg(Getor getor) {
     if (((CodeInputDlg)this.dlgs.get(0)).getor == getor) {
       CodeInputDlg dlg = (CodeInputDlg)this.dlgs.get(0);
       return dlg;
     }
     return null;
   }
   public void removeFirstDlg() {
     if (this.dlgs.size() > 0)
       this.dlgs.remove(0);
   }
 
   public void UpdateYelloCowList() {
     this.jComboBox3.removeAllItems();
     for (int i = 0; i < LoginDialog.getInstance().cows.size(); i++) {
       this.jComboBox3.addItem(((YellowCow)LoginDialog.getInstance().cows.get(i)).name);
     }
     if (this.jComboBox3.getItemCount() != 0)
       this.jComboBox3.setSelectedIndex(0);
   }
 
   public void setResult(Getor getor, String status) {
     for (int i = 0; i < this.users.size(); i++) {
       User usr = (User)this.users.get(i);
       if (usr.appleid.equals(getor.getUser().appleid)) {
         this.jTable1.setValueAt(status, i, 4);
         break;
       }
     }
   }
 
   public void setNetStatus(Getor getor, String status) {
     if (getor != null)
       for (int i = 0; i < this.users.size(); i++) {
         User usr = (User)this.users.get(i);
         if (usr.appleid.equals(getor.getUser().appleid)) {
           this.jTable1.setValueAt(status, i, 10);
           break;
         }
       }
   }
 
   public void setCurrentStatus(Getor getor, int currentPos, String status)
   {
     for (int i = 0; i < this.users.size(); i++) {
       User usr = (User)this.users.get(i);
       if (usr.appleid.equals(getor.getUser().appleid)) {
         this.jTable1.setValueAt(Integer.valueOf(currentPos), i, 4);
         break;
       }
     }
 
     int row = this.jTable1.getSelectedRow();
 
     if ((row >= 0) && (row < this.jTable1.getRowCount()))
     {
       if (getor.getUser().appleid.equals(((User)this.users.get(row)).appleid))
         this.jLabel1.setText(status);
     }
   }
 
   private void initComponents() {
     this.jScrollPane1 = new JScrollPane();
     this.jTable1 = new JTable();
     this.jPanel3 = new JPanel();
     this.jButton1 = new JButton();
     this.jButton3 = new JButton();
     this.jLabel1 = new JLabel();
     this.jScrollPane2 = new JScrollPane();
     this.jTextArea1 = new JTextArea();
     this.jPanel1 = new JPanel();
     this.jLabel5 = new JLabel();
     this.jTextField2 = new JTextField();
     this.jLabel6 = new JLabel();
     this.jRadioButton1 = new JRadioButton();
     this.jRadioButton2 = new JRadioButton();
     this.jRadioButton3 = new JRadioButton();
     this.jRadioButton4 = new JRadioButton();
     this.jLabel2 = new JLabel();
     this.jTextField1 = new JTextField();
     this.jCheckBox1 = new JCheckBox();
     this.jComboBox1 = new JComboBox();
     this.jCheckBox2 = new JCheckBox();
     this.jComboBox2 = new JComboBox();
     this.jCheckBox3 = new JCheckBox();
     this.jComboBox3 = new JComboBox();
     this.jLabel3 = new JLabel();
 
     setDefaultCloseOperation(3);
     addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent evt) {
         MainJFrame.this.formWindowClosing(evt);
       }
     });
     //this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[] { "姓名", "身份证", "ID", "密码", "是否可用", "购物车", "当前进度", "验证码输入", "结果", "操作", "网络", "序号" })
     this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[] { "姓名", "身份证", "ID",  "密码",  "当前进度",  "结果", "操作"}) {
         //Class[] types = { String.class, String.class, String.class, String.class, Boolean.class, String.class, JProgressBar.class, String.class, String.class, ImageIcon.class, String.class, String.class };
         Class[] types = {String.class, String.class, String.class, String.class, JProgressBar.class,String.class, ImageIcon.class,};
         //boolean[] canEdit = { false, false, false, false, false, false, false false, false, false, false, false };
         boolean[] canEdit = { false, false, false, false, false, false, false };
 
         public Class getColumnClass(int columnIndex) {
             return this.types[columnIndex];
         }
 
         public boolean isCellEditable(int rowIndex, int columnIndex) {
             return this.canEdit[columnIndex];
         }
     });
     this.jTable1.getColumn("当前进度").setCellRenderer(new MyTableCellRenderer());
     this.jTable1.setRowHeight(24);
     this.jTable1.setSelectionMode(0);
     this.jTable1.setShowVerticalLines(false);
     this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
     this.jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
 
     this.jTable1.getColumnModel().getColumn(1).setMinWidth(130);
     this.jTable1.getColumnModel().getColumn(1).setPreferredWidth(130);
 
     this.jTable1.getColumnModel().getColumn(2).setMinWidth(0);
     this.jTable1.getColumnModel().getColumn(2).setPreferredWidth(0);
 
     this.jTable1.getColumnModel().getColumn(3).setMinWidth(0);
     this.jTable1.getColumnModel().getColumn(3).setPreferredWidth(0);
 
     this.jTable1.getColumnModel().getColumn(4).setMinWidth(126);
     this.jTable1.getColumnModel().getColumn(4).setPreferredWidth(126);
 
     this.jTable1.getColumnModel().getColumn(5).setMinWidth(0);
     this.jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
 
     this.jTable1.getColumnModel().getColumn(6).setMinWidth(0);
     this.jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
 
//     this.jTable1.getColumnModel().getColumn(7).setMinWidth(80);
//     this.jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
//     this.jTable1.getColumnModel().getColumn(7).setMaxWidth(80);
//     this.jTable1.getColumnModel().getColumn(8).setMinWidth(95);
//     this.jTable1.getColumnModel().getColumn(8).setPreferredWidth(95);
//     this.jTable1.getColumnModel().getColumn(8).setMaxWidth(95);
// 
//     this.jTable1.getColumnModel().getColumn(9).setMinWidth(32);
//     this.jTable1.getColumnModel().getColumn(9).setPreferredWidth(32);
// 
//     this.jTable1.getColumnModel().getColumn(10).setMinWidth(196);
//     this.jTable1.getColumnModel().getColumn(10).setPreferredWidth(196);
//     this.jTable1.getColumnModel().getColumn(11).setMinWidth(32);
//     this.jTable1.getColumnModel().getColumn(11).setPreferredWidth(32);
     this.jTable1.addMouseListener(new MouseAdapter() {
       public void mouseClicked(MouseEvent evt) {
         MainJFrame.this.jTable1MouseClicked(evt);
       }
       public void mouseReleased(MouseEvent evt) {
         MainJFrame.this.jTable1MouseReleased(evt);
       }
     });
     this.jScrollPane1.setViewportView(this.jTable1);
     this.jTable1.getTableHeader().setReorderingAllowed(false);
 
     this.jButton1.setText("开始");
     this.jButton1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jButton1ActionPerformed(evt);
       }
     });
     this.jPanel3.add(this.jButton1);
 
     this.jButton3.setText("清空");
     this.jButton3.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jButton3ActionPerformed(evt);
       }
     });
     this.jPanel3.add(this.jButton3);
 
     this.jLabel1.setFont(new Font("宋体", 0, 18));
     this.jLabel1.setHorizontalAlignment(0);
     this.jLabel1.setBorder(BorderFactory.createTitledBorder("选中用户的当前进度"));
 
     this.jTextArea1.setColumns(10);
     this.jTextArea1.setRows(5);
     this.jTextArea1.setBorder(null);
     this.jScrollPane2.setViewportView(this.jTextArea1);
 
     this.jPanel1.setBorder(BorderFactory.createTitledBorder(""));
 
     this.jLabel5.setText("总线程数：");
 
     this.jTextField2.setText("18");
     this.jTextField2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jTextField2ActionPerformed(evt);
       }
     });
     this.jLabel6.setText("选择店面：");
 
     this.jRadioButton1.setText("浦西");
 
     this.jRadioButton2.setText("浦东");
 
     this.jRadioButton3.setText("三里屯");
 
     this.jRadioButton4.setText("西单");
 
     this.jLabel2.setText("超时(秒)：");
 
     this.jTextField1.setText("30");
     this.jTextField1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jTextField1ActionPerformed(evt);
       }
     });
     this.jCheckBox1.setSelected(true);
     this.jCheckBox1.setText("发货鬼叫");
 
     this.jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "白ipad-16G", "白ipad-32G", "白ipad-646G" }));
     this.jComboBox1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jComboBox1ActionPerformed(evt);
       }
     });
     this.jCheckBox2.setSelected(true);
     this.jCheckBox2.setText("5个！");
 
     this.jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "普通模式--熟手使用", "练习模式--新手使用", "自动模式--全天刷机" }));
     this.jComboBox2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent evt) {
         MainJFrame.this.jComboBox2ActionPerformed(evt);
       }
     });
     this.jCheckBox3.setSelected(true);
     this.jCheckBox3.setText("超时重来");
 
     this.jLabel3.setText("选择黄牛：");
 
     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
     this.jPanel1.setLayout(jPanel1Layout);
     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jCheckBox1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jCheckBox2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jCheckBox3)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField2, -2, 26, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2, -2, 60, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, 31, -2)).addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jRadioButton1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jRadioButton2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jRadioButton3, -2, 64, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jRadioButton4)).addComponent(this.jComboBox3, 0, 213, 32767))).addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup().addComponent(this.jComboBox2, -2, 149, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jComboBox1, 0, 118, 32767))).addGap(28, 28, 28))).addContainerGap()));
/*      */ 
/*  583 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField2, -2, -1, -2).addComponent(this.jLabel5).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jRadioButton1).addComponent(this.jLabel6).addComponent(this.jRadioButton2).addComponent(this.jRadioButton3).addComponent(this.jRadioButton4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3, -1, 21, 32767).addComponent(this.jComboBox3, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jCheckBox1).addComponent(this.jCheckBox2).addComponent(this.jCheckBox3)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBox2, -2, -1, -2).addComponent(this.jComboBox1, -2, -1, -2)).addContainerGap()));
 
     this.group = new ButtonGroup();
     this.group.add(this.jRadioButton1);
     this.group.add(this.jRadioButton2);
     this.group.add(this.jRadioButton3);
     this.group.add(this.jRadioButton4);
     this.jComboBox2.setSelectedIndex(0);
 
     GroupLayout layout = new GroupLayout(getContentPane());
     getContentPane().setLayout(layout);
     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel3, -1, 819, 32767).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, 489, 32767).addComponent(this.jScrollPane2, GroupLayout.Alignment.TRAILING, -1, 489, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel1, -2, 324, -2)).addComponent(this.jScrollPane1, -1, 819, 32767));
 
     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jScrollPane1, -1, 361, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jScrollPane2, -2, 94, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel1, -1, 46, 32767)).addComponent(this.jPanel1, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel3, -2, -1, -2)));
 
     pack();
   }
 
   private void jTable1MouseClicked(MouseEvent evt)
   {
     this.jLabel1.setText("");
     this.jTextArea1.setText("");
     int row = this.jTable1.rowAtPoint(evt.getPoint());
     int col = this.jTable1.getSelectedColumn();
     if (col != 9)
     {
       for (int i = 0; i < this.getors.size(); i++) {
         Getor getor = (Getor)this.getors.get(i);
         User user = getor.getUser();

         if (user.appleid.equals(this.jTable1.getValueAt(row, 2))) {
           this.jLabel1.setText(getor.getStatus());
           StringBuffer txt = new StringBuffer(200);
           txt.append("姓名：");
           txt.append(user.name);
           txt.append("\nemail：");
           txt.append(user.appleid);
           txt.append("\n拿货时间：");
           txt.append(getor.getFormattedTime());
           this.jTextArea1.setText(txt.toString());
           break;
         }
       }
     }
     else {
       ImageIcon icon = (ImageIcon)this.jTable1.getValueAt(row, col);
       String value = (String)this.jTable1.getValueAt(row, 8);
       if (icon == this.runningIcon) {
         ((Getor)this.getors.get(row)).stop();
         this.jTable1.setValueAt(this.stopIcon, row, col);
         this.jTable1.setValueAt("", row, 8);
         setResult((Getor)this.getors.get(row), "等待停止...");
       } else if ((icon == this.stopIcon) && (value != null) && (value.equals("已停止")))
       {
         Getor getor = (Getor)this.getors.get(row);
         this.jTable1.setValueAt(this.runningIcon, row, col);
         Getor gtr = new Getor(getor.getUser());
         this.getors.set(row, gtr);
         ThreadPool.getIntance().addThread(1);
         ThreadPool.getIntance().assign(gtr, 0);
         if (getor.isRunning())
         {
           getor.stop();
         }
         setResult(getor, "");
         setCurrentStatus(getor, 0, "");
       }
     }
   }
 
   public void run() {
     HashMap headers = new HashMap(15);
 
     headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
     headers.put("Accept-Language", "zh-cn,zh;q=0.5");
     headers.put("Accept-Encoding", "gzip, deflate, x-gzip, identity, *;q=0");
     headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
     headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;");
     headers.put("Content-Type", "application/x-www-form-urlencoded");
     headers.put("Connection", "Keep-Alive");
     headers.put("Keep-Alive", "300");
     headers.put("Pragma", "no-cache");
     headers.put("Cache-Control", "no-cache");
 
     String content = URLEncoder.encode(LoginDialog.getInstance().clientid + "&" + LoginDialog.getInstance().token + "&ipad");
 
     String url = "http://localhost:8080/QuerySun/GetUserList";
     HttpPost getor = new HttpPost(null, url, headers, content, this);
     //getor.doRequest(true);
 processRespose1();
     for (int i = 0; i < this.users.size(); i++) {
       User user = (User)this.users.get(i);
//User user = new User("01", "320323198101137359", "周海龙", "zhouhlcn", "102813","320323198101137359",true);
       Getor getr = new Getor(user);
       this.getors.add(getr);
       ThreadPool.getIntance().assign(getr);
     }
   }
 
   private void login() {
     LoginDialog loginDlg = new LoginDialog();
     loginDlg.showUI();
     setTitle(getTitle() + " 当前登录:" + loginDlg.straccount + " " + LoginDialog.getInstance().clientName);
   }
 
   private void jButton1ActionPerformed(ActionEvent evt)
   {
     MyTableCellRenderer render = (MyTableCellRenderer)this.jTable1.getColumn("当前进度").getCellRenderer();
     render.getBar().setMaximum(this.jCheckBox2.isSelected() ? 19 : 15);
 
     this.timeout = Integer.parseInt(this.jTextField1.getText().trim());
     if (this.timeout > 100) {
       JOptionPane.showMessageDialog(this, "超时时间太大了，一般30秒-60秒差不多了！", "警告", 2);
     }
     if (this.timeout < 30) {
       JOptionPane.showMessageDialog(this, "超时时间太小了，一般30秒-60秒差不多了！", "警告", 2);
     }
 
     if ((!this.jRadioButton1.isSelected()) && (!this.jRadioButton2.isSelected()) && (!this.jRadioButton3.isSelected()) && (!this.jRadioButton4.isSelected()))
     {
       JOptionPane.showMessageDialog(this.rootPane, "请选择店面", "店面", 0);
       return;
     }
 
     this.jRadioButton1.setEnabled(false);
     this.jRadioButton2.setEnabled(false);
     this.jRadioButton3.setEnabled(false);
     this.jRadioButton4.setEnabled(false);
     this.jComboBox1.setEnabled(false);
     this.jTextField2.setEnabled(false);
     this.jLabel5.setEnabled(false);
     this.jLabel6.setEnabled(false);
     if (this.jRadioButton4.isSelected()) {
       this.jCheckBox2.setSelected(true);
       this.jCheckBox2.setEnabled(false);
     }
     if (this.jTextField2.getText().trim().equals("")) {
       String num = this.jTextField2.getText();
       int i = 0;
       try {
         i = Integer.parseInt(num);
       } catch (Exception e) {
         JOptionPane.showMessageDialog(this.rootPane, "请输入数字", "输入错误", 0);
         e.printStackTrace();
       }
       return;
     }
     Thread thread = new Thread(this);
     thread.start();
   }
 
   public int getTimeout() {
     return this.timeout * 1000;
   }
   public void setRunningIcon(Getor getor, int b) {
     for (int i = 0; i < this.jTable1.getRowCount(); i++) {
       if (!getor.getUser().appleid.equals(this.jTable1.getValueAt(i, 2)))
         continue;
       if (b == 0) {
         this.jTable1.setValueAt(this.runningIcon, i, 6); break;
       }if (b == 1) {
         this.jTable1.setValueAt(this.stopIcon, i, 6); break;
       }if (b != 2) break;
       this.jTable1.setValueAt(this.okIcon, i, 6); break;
     }
   }
 
   private void jTable1MouseReleased(MouseEvent evt)
   {
   }
 
   private void formWindowClosing(WindowEvent evt)
   {
     saveConfig();
   }
 
   private void jTextField2ActionPerformed(ActionEvent evt)
   {
   }
 
   private void jTextField1ActionPerformed(ActionEvent evt)
   {
   }
 
   private void jButton3ActionPerformed(ActionEvent evt)
   {
     for (int i = 0; i < this.jTable1.getRowCount(); i++)
       this.jTable1.setValueAt("", i, 10);
   }
 
   private void jComboBox1ActionPerformed(ActionEvent evt)
   {
   }
 
   private void jComboBox2ActionPerformed(ActionEvent evt)
   {
     int index = this.jComboBox2.getSelectedIndex();
     if (index == 2);
   }
 
   public void processRespose(Object body){
   }

   public void processRespose1() {
     String path = "E:\\apple\\iphone\\a.txt";


     //String sb = "周海龙,11233445566777,iotkdcn,102813|";
     //if (sb != null) {
       //HttpResponse res = (HttpResponse)body;
       //StringBuffer sb = (StringBuffer)res.body;
       //String str = sb.toString();
      // Log.println(str);
       //if (str.startsWith("200"))
         try {
             File userFile = new File(path);
             FileInputStream in = new FileInputStream(userFile);
             BufferedReader reader = new BufferedReader( new FileReader(userFile));
             String sb = null;
           //int index = str.indexOf("&");
           //str = str.substring(index + 1);
           //StringTokenizer tokenizer = new StringTokenizer(str, "|");
           int rowNum = 0;
           while ((sb = reader.readLine()) != null) {

           //while (tokenizer.hasMoreTokens()) {
            String line = sb;
            // String line = tokenizer.nextToken();
             if (!line.trim().equals("")) {
               String name = null;
               String appleid = null;
               String password = null;
               String idcode = null;
               boolean used = false;
 
               StringTokenizer tokenizer1 = new StringTokenizer(line, ",");
               int count = 0;
               Object[] items = new Object[12];
               String id = null;
               String adminid = null;
               while (tokenizer1.hasMoreTokens()) {
                 String token = tokenizer1.nextToken();
 
                 switch (count) {
                 case 4:
                   id = token;
                   break;
                 case 0:
                   String tmp224_222 = token; name = tmp224_222; items[0] = tmp224_222;
                   System.out.println("items[0]:" + items[0]);
                   break;
                 case 2:
                   String tmp236_234 = token; appleid = tmp236_234; items[2] = tmp236_234;
                   System.out.println("items[2]:" + items[2]);
                   break;
                 case 3:
                   String tmp248_246 = token; password = tmp248_246; items[3] = tmp248_246;
                   System.out.println("items[3]:" + items[3]);
                   break;
                 case 1:
                   String tmp260_258 = token; idcode = tmp260_258; items[1] = tmp260_258;
                   System.out.println("items[1]:" + items[1]);
                   break;
                 case 5:
                   items[4] = Boolean.valueOf(!token.trim().equals("0") ? true : false);
                   items[4] = Boolean.valueOf(!((Boolean)items[4]).booleanValue() ? true : false);
                   break;
                 case 7:
                   int storeid = 0;
                   try {
                     storeid = Integer.parseInt(token.trim());
                   } catch (Exception e) {
                     e.printStackTrace();
                   }
                   String storeName = null;
                   switch (storeid) {
                   case 1:
                     storeName = "浦西";
                     break;
                   case 2:
                     storeName = "浦东";
                     break;
                   case 3:
                     storeName = "三里屯";
                     break;
                   case 4:
                     storeName = "西单";
                   }
 
                   items[5] = storeName;
                   case 6:
                 }
 
                 count++;
               }
               User user = new User(id, adminid, name, appleid, password, idcode, used);
               this.users.add(user);
               rowNum++;
                //tmp480_479 = (items[8] =  = items[9] =  = items[10] =  = null);
					//items[7] = tmp480_479;
					//items[6] = tmp480_479;
               items[4] = Integer.valueOf(rowNum);
               this.model.addRow(items);
             }
           }
         } catch (Exception e) {
           JOptionPane.showMessageDialog(this.rootPane, e.getMessage(), "读取用户列表出现异常", 0);
 
           e.printStackTrace();
         }
       //
         //JOptionPane.showMessageDialog(instance, str, "获取用户列表", 0);
     //}
   }
 
   public void processFileNotFoundException(FileNotFoundException e)
   {
   }
 
   public void processUnknownHostException(UnknownHostException e)
   {
   }
 
   public void processSocketTimeoutException(SocketTimeoutException e)
   {
   }
 
   public void processIOException(IOException e)
   {
   }
 
   public void processException(Exception e)
   {
   }
 
   public void processConnectionClosed()
   {
   }
 
   public static void main(String[] args)
   {

         MainJFrame mainfrm = new MainJFrame();
         Toolkit tk = Toolkit.getDefaultToolkit();
         Dimension d = tk.getScreenSize();
         d.height -= 30;
 
         mainfrm.setLocation(((int)d.getWidth() - mainfrm.getWidth()) / 2, ((int)d.getHeight() - mainfrm.getHeight()) / 2);
 
         mainfrm.setVisible(true);


   }
 
   class MyTableCellRenderer extends DefaultTableCellRenderer {
     private static final long serialVersionUID = 1L;
     private final JProgressBar b = new JProgressBar(0, 17);
 
     public MyTableCellRenderer() {
       setOpaque(true);
 
       this.b.setBackground(Color.WHITE);
     }
 
     public JProgressBar getBar() {
       return this.b;
     }
 
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	 try {
       String text = "";
       System.out.println("value:" + value);
       if (value != null) {
         Integer i = (Integer)value;
 
         text = "完成";
         if (i.intValue() < 0) {
           text = "取消完毕";
         } else if (i.intValue() < this.b.getMaximum()) {
           this.b.setValue(i.intValue());
           return this.b;
         }
       }
       super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
    	 } catch (Exception e) {
    		// e.printStackTrace();
    	 }
       return this;
     }
   }
 }

/* Location:           C:\Documents and Settings\Administrator\桌面\5-22\3\
 * Qualified Name:     iphonebuyer.MainJFrame
 * JD-Core Version:    0.6.0
 */