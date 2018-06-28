/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication21;

/**
 *
 * @author ssngurjar
 */

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.table.*;

import java.io.*;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
//import com.sun.image.codec.jpeg.*;
import javax.swing.filechooser.FileFilter;



import java.util.Hashtable;
import java.util.Enumeration;
import java.nio.*;
import java.nio.channels.*;
//import java.util.Arrays;


/**************************** Main Class For This Image Processing Project ******************/////////////

class BilImage extends JFrame implements ActionListener
{

	JMenu zoom;
	JMenu sign;
	JMenu mnClass;
	JMenu mnHelp;
	JMenuItem miOpen;
	JMenuItem miSave;
	JMenuItem miExit;
	JMenuItem fuzzy;
	JMenuItem miAbout;
	JMenuItem miContrast;
	JMenuItem mifcc;
	static JMenuItem miRegion;
	JMenu mnEnhance;
	JMenuItem zin,zout;
	JMenuItem miFcm;
	JTextArea ta;
	JFrame Ofset;
	JLabel l1;
	JTextField t1;   // textfield for threshold
    JButton b;
    JLabel l2;
    JLabel l3;
    JLabel l4;
    JLabel l5;
    JLabel l6;
    JLabel l7;
    JLabel l8;
   static JRadioButton ra1;
    static JRadioButton ra2;
    static JCheckBox r3;
    static JCheckBox r4;
    static JCheckBox rE;
    static JCheckBox rM;
    static JCheckBox rCB;
    static JCheckBox rBC;
    static JCheckBox rC;
    static JCheckBox rMean;
    static JCheckBox rMedian;
    static JCheckBox rNE;
    static JCheckBox r7;
     static JCheckBox rSid;
    static JCheckBox rTan;
    static JCheckBox rSin;
    static JCheckBox rSca;
    JRadioButton r5;
    JRadioButton r6;
    static int wid=20;
	static int hei=20;
	static double thres=0.9;

    JTextField tf1;
    JTextField tf2;
JTextField tf3;
	JButton bok,bclose;

	AboutDialog dialog = null;
	Font f;
	Color co;
	public static String name;
	String fname;
	String ImageName;
	BufferedImage img;
	Container con = getContentPane();
	Container ContentPane = getContentPane();
	DemoPanel pane;
	static Fcm fcm;
	static float zoomFactor;

	static {
		try{
			File file=new File("C:\\BilImage");
			if(!file.exists())
				file.mkdir();
			System.out.println(file.getAbsolutePath());
			//PrintStream ps=new PrintStream(file.getAbsoluteFile()+"\\LogFile.txt");
		}catch (Exception e) {
			System.out.println("Error in creating log file."+e);
		}
	}

	public static void main(String s[])
	{

		BilImage frame = new BilImage();
		frame.setTitle("Similarity and Dissimilarity For Fuzzy Soft Classifier: SMIC Tool");
		frame.setVisible(true);
		frame.setLocation(100,100);
		frame.setSize(800, 550);
		frame.show();
		 ra1=new JRadioButton("ON");
	        ra2=new JRadioButton("OFF",true);
	        ra2.setSelected(true);
	        r3=new JCheckBox("PCC");
	        //r3.setSelected(true);
	        r4=new JCheckBox("CC");
                rE=new JCheckBox("E");
                rM=new JCheckBox("M");
                rCB=new JCheckBox("CB");
                rBC=new JCheckBox("BC");
                rC=new JCheckBox("C");
                rMean=new JCheckBox("MeD");
                rMedian=new JCheckBox("MiD");
                rNE=new JCheckBox("NE");
	        r7=new JCheckBox("Both");
                rSid=new JCheckBox("Sid");
                rTan=new JCheckBox("Tan");
                rSin=new JCheckBox("Sin");
                rSca=new JCheckBox("Sca");

	}

	BilImage()
	{

		JMenuBar mb = new JMenuBar();
		JMenu mnfile = new JMenu("File");
		mnClass=new JMenu("Classifier");
		sign =new JMenu("Signature files");
		sign.setEnabled(false);
		fuzzy=new JMenuItem("Table for fuzzy c mean");
		mnEnhance = new JMenu("Tools");
		mnHelp = new JMenu("Help");
		miOpen = new JMenuItem("Open");
		miSave = new JMenuItem("Save");
		miExit = new JMenuItem("Exit");
		miAbout = new JMenuItem("About");
		miContrast = new JMenuItem("Enhancement");
		mifcc = new JMenuItem("FCC");
		miRegion = new JMenuItem("Region Grow");

		zoom = new JMenu("Zoom");
		zin=new JMenuItem("Zoom In");
		zout=new JMenuItem("Zoom Out");
		miFcm=new JMenuItem("Fuzzy C-Means");

		mnEnhance.setEnabled(false);
		mnClass.setEnabled(false);

		mnEnhance.add(zoom);
		zoom.add(zin);
		zoom.add(zout);

		sign.add(fuzzy);

		mnfile.add(miOpen);
		mnfile.add(miSave);
		mnfile.addSeparator();
		mnfile.add(miExit);
		mnEnhance.add(miContrast);
		mnEnhance.add(mifcc);
		mnEnhance.add(miRegion);
		mnClass.add(miFcm);

		mnHelp.add(miAbout);
		mb.add(mnfile);
		mb.add(mnEnhance);
		mb.add(sign);
		mb.add(mnClass);
		mb.add(mnHelp);
		setJMenuBar(mb);

		con.setBackground(Color.PINK);
		f = new Font("Palatino Linotype", Font.ITALIC, 21);

		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miExit.addActionListener(this);
		sign.addActionListener(this);
		fuzzy.addActionListener(this);
		miAbout.addActionListener(this);
		miContrast.addActionListener(this);
		mifcc.addActionListener(this);
		miRegion.addActionListener(this);
		zoom.addActionListener(this);
		zin.addActionListener(this);
		zout.addActionListener(this);
		mnClass.addActionListener(this);
		miFcm.addActionListener(this);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});

	}

	JButton okBut;
	JTextField jtBand, jtRow, jtCol;
	JFrame cross;
	public static File imgFile;
	public static int p,q,r;
	public static int tab=0;

	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source==fuzzy)
		{
		    DemoPanel pane=new DemoPanel();
		    imagetable6 Table6;
			System.out.println("Table Opened");
			int Noclicks=pane.retclicks();
			Table6=new imagetable6();
			Table6=new imagetable6(Noclicks);
			Table6.setSize(570,350);
			Table6.setLocation(350,40);
			Table6.setVisible(true);
			System.out.println( " No of clicks is" + Noclicks);
			tab=1;

		}

		if (source == miOpen)
		{
			JFileChooser chooser = new JFileChooser();
			int r5 = chooser.showOpenDialog(this);
			if (r5 == chooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				imgFile=file;
				name = file.getName();
				ReadImageData Rid1 = new ReadImageData(file);
				this.ImageName = name;
				picture Picture1 = new picture();
				picture.setimage(name);
				p=q=r=1;
				img = Picture1.getpicdata(p, q, r);
				zoomFactor=1;
				DemoPanel pane = new DemoPanel(img);
				ContentPane.removeAll();
				ContentPane.add(new JScrollPane(pane));
				mnEnhance.setEnabled(true);
				mnClass.setEnabled(true);
				sign.setEnabled(true);
				System.gc();
				validate();
			}

		}

		if (source == miSave)
		{
			System.out.println("Clicked");

		}

		if (source == miExit)
		{
			System.exit(0);
		}

		if (source == miAbout)
		{
			JPanel panel=new JPanel();
			ta = new JTextArea();
			ta.setBounds(100, 100, 375, 100);
			ta.setFont(f);
			ta.setForeground(Color.RED);
			con.removeAll();
			panel.add(ta);
			con.add(panel,"Center");
			ta.setBackground(Color.PINK);
			panel.setBackground(Color.PINK);
			ta.setText("\n\n\tRemote Sensing is a technique of sensing the objects\n\twithout touching & seeing them.\n\n\tThis project helps us in gethering information from \n\tthese type of techniques.");

			System.out.println("Help Clicked");
		}

		if (source == miContrast)
		{

			       Enhancement  enhance1=new Enhancement();
                   enhance1.enhances(p,q,r);
	               img=enhance1.getpicdata();
			   pane=new DemoPanel(img);
			   pane.repaint();
                     ContentPane.removeAll();
		         ContentPane.add(new JScrollPane(pane));
			   validate();

		}

		if(source == miRegion)
        {
			ButtonGroup group1 = new ButtonGroup();
         Ofset= new JFrame("Settings");
         b= new JButton("Ok");
        l1=new JLabel("Length");
        l2= new JLabel("Height");
        l5= new JLabel("Correlation Threshold");
        l3=new JLabel("Px");
        l4= new JLabel("Px");
       // l6=new JLabel("%");
        l7=new JLabel("Region Grow");
        l8=new JLabel("Correlation Type");


         tf1= new JTextField("20");
         wid=20;
         tf2= new JTextField("20");
         hei=20;
         tf3= new JTextField("0.9");
         thres=90;

        l1.setLocation(55, 50);
        l1.setSize(75, 30);

        l2.setLocation(55, 100);
        l2.setSize(75, 30);

        l5.setLocation(25, 150);
        l5.setSize(120, 30);


        l3.setLocation(230, 50);
        l3.setSize(25, 30);

        l4.setLocation(230,100);
        l4.setSize(75, 30);

     //  l6.setLocation(230, 150);
       // l6.setSize(120, 30);

        l7.setLocation(55,195);
        l7.setSize(75, 30);

        l8.setLocation(55,230);
        l8.setSize(75, 30);

        tf1.setLocation(150,50);
        tf1.setSize(75, 30);

        tf2.setLocation(150,100);
        tf2.setSize(75, 30);

        tf3.setLocation(150,150);
        tf3.setSize(75, 30);

        ra1.setLocation(150,195);
        ra1.setSize(50,30);
        ra2.setLocation(200,195);
        ra2.setSize(50,30);
        r3.setLocation(150,230);
        r3.setSize(50,30);
        r4.setLocation(200,230);
        r4.setSize(50,30);
        rE.setLocation(150,260);
        rE.setSize(50,30);
        rM.setLocation(200,260);
        rM.setSize(50,30);
        rCB.setLocation(300,290);
        rCB.setSize(50,30);
        rBC.setLocation(300,320);
        rBC.setSize(50,30);
         rSid.setLocation(150,350);
        rSid.setSize(50,30);

        rTan.setLocation(150,380);
        rTan.setSize(50,30);

        rSin.setLocation(200,380);
        rSin.setSize(50,30);

        rSca.setLocation(250,380);
        rSca.setSize(50,30);

        rC.setLocation(200,290);
        rC.setSize(50,30);
        rMean.setLocation(250,290);
        rMean.setSize(50,30);
        rMedian.setLocation(250,320);
        rMedian.setSize(50,30);
        rNE.setLocation(200,320);
        rNE.setSize(50,30);
        r7.setLocation(250,195);
        r7.setSize(100,90);


        b.setBounds(100,300,100, 40);//x axis, y axis, width, height
        b.addActionListener(this);


        Ofset.add(b);//adding button in JFrame
        Ofset.add(l1);
        Ofset.add(l3);

        Ofset.add(l2);

        Ofset.add(l4);
        Ofset.add(l5);
      //  Ofset.add(l6);
        Ofset.add(l7);
        Ofset.add(l8);

        Ofset.add( ra1);
        Ofset.add( ra2);
        group1.add(ra1);
        group1.add(ra2);
        Ofset.add( r3);
        Ofset.add( r4);
        Ofset.add( rE);
        Ofset.add( rM);
        Ofset.add( rCB);
        Ofset.add( rBC);
        Ofset.add(rSid);
        Ofset.add(rTan);

        Ofset.add(rSin);
        Ofset.add(rSca);
        Ofset.add( rC);
        Ofset.add( rMean);
        Ofset.add( rMedian);
        Ofset.add( rNE);
        Ofset.add( r7);
      /*  group2.add(r3);
        group2.add(r4);
        group2.add(rE);
        group2.add(rM);
        group2.add(rCB);
        group2.add(rBC);
        group2.add(rC);
        group2.add(rMean);
        group2.add(rMedian);
        group2.add(rNE);
        group2.add(r7); */

        Ofset.add(tf1);
        Ofset.add(tf2);
        Ofset.add(tf3);

        Ofset.setSize(400,500);//400 width and 500 height
        Ofset.setLayout(null);//using no layout managers
        Ofset.setVisible(true);
        }
		if (source == b)
		{
			wid=Integer.parseInt(tf1.getText());
			hei=Integer.parseInt(tf2.getText());
			thres=Double.parseDouble(tf3.getText());
           System.out.println("Threshold value---: "+thres+"% Width:"+wid+" Height:"+hei);
		   Ofset.setVisible(false);
		}

		if (source == mifcc)
		{
			if (dialog == null)
				dialog = new AboutDialog(this);
			dialog.setSize(500, 150);
			dialog.setVisible(true);

			p = dialog.show_band1();
			q = dialog.show_band2();
			r = dialog.show_band3();
			picture Picture1 = new picture();
			img = Picture1.getpicdata(p, q, r);
			pane = new DemoPanel(img);
			pane.repaint();
			ContentPane.removeAll();
			ContentPane.add(new JScrollPane(pane));
			validate();
			System.out.println("p : "+p+"q : "+q+"r : "+r);

		}

		if(source==zin)
		{
	         System.out.println("zoom in");
	         String in=JOptionPane.showInputDialog(null,"Enter the Enlargement factor for zoom ");
	         int f= Integer.parseInt(in);
	         ZoomIn zoompane = new ZoomIn(img,f);
             img=zoompane.retchangepic();
			 pane=new DemoPanel(img);
			 pane.repaint();
             ContentPane.removeAll();
		     ContentPane.add(new JScrollPane(pane));
		     zoomFactor=zoomFactor*f;
			 validate();

	    }

	    if(source==zout)
	    {
	    	 System.out.println("zoom out");
	    	 String in=JOptionPane.showInputDialog(null,"Enter the shrinking factor for zoom out");
	         int f= Integer.parseInt(in);
	         ZoomOut zoompane=new ZoomOut(img,f);
             img=zoompane.retchangepic();
			 pane=new DemoPanel(img);
			 pane.repaint();
             ContentPane.removeAll();
		     ContentPane.add(new JScrollPane(pane));
		     zoomFactor=zoomFactor/f;
			 validate();


	    }

	    if(source==miFcm)
	    {

	    	System.out.println("FCM Frame Opened");
	    	fcm=new Fcm();
	    	fcm.setSize(1000,550);
	    	fcm.show();

	    }


	}

}


///////////*********************** Class Read Image Data To Read The Header File of Image. ***********///////////////



class ReadImageData
{
	public static File picfile;
	public static int Bands;
	private static int Rows;
	private static int Columns;

	public ReadImageData()
	{

	}

	public ReadImageData(File file)
	{
		String name = file.getName();
		this.picfile = file;
	}

	public void read(File Name)//read image file name
	{
		int index = 0;
		int b1 = 0;
		int row = 0;
		int col = 0;
		int x = 0;
		int c[];
		InputStream f1;
		char p[] = new char[42];
		String ban = new String();
		String row1 = new String();
		String col1 = new String();
		this.picfile = Name;
		String s[] = new String[30];
		try
		{
			f1 = new FileInputStream(Name + ".hdr");

			c = new int[11];
			for (int i = 0; i < 42; i = i + 1)
			{
				p[i] = (char)f1.read();
			}
			f1.close();
		}
		catch (Exception e)
		{
		}

		for (int i = 6; i < 13; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			ban = ban + p[i];
		}
		b1 = Integer.parseInt(ban);
		this.Bands = b1;
		System.out.println(b1);
		for (int i = 20; i < 27; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			row1 = row1 + p[i];
		}
		row = Integer.parseInt(row1);
		System.out.println(row);
		this.Rows = row;
		for (int i = 35; i < 41; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			col1 = col1 + p[i];
		}
		col = Integer.parseInt(col1);
		this.Columns = col;
		System.gc();
	}

	public int bands()//return bands
	{
		return Bands;
	}

	public int rows()//return rows
	{
		return Rows;
	}

	public int columns()//return columns
	{
		return Columns;
	}

	public File imagefile()//return imagefilename
	{
		return picfile;
	}
}




///////////////**************** Picture Class To Read & Display the Image ************////////////////////


class picture extends JPanel
{
	private BufferedImage img;
	private int Width;
	private int Height;
	private int Bands;
	private ReadImageData ImgData1;
	static private File ImageFile;
	static int[][][] a;
	int array_size;
	static String ImageName;
	int x_pass,y_pass,y_pass2,num_of_slice,origin_x=0,origin_y=0;
	public static int []maxBand;
	public static int []minBand;
	public static byte pix_rgb[][];
	public static int shiftFlag=0;

	public static void setimage(String nam)
	{
		ImageFile = new File(nam);
		ImageName = nam;
	}


	public BufferedImage getpicdata(int Color1, int Color2, int Color3)///return image
	{
		System.gc();
		ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();

		ImgData1.read(ImageFile);
		Bands = ImgData1.bands();
		Width = ImgData1.columns();
		Height = ImgData1.rows();
		int p = Color1;
		int q = Color2;
		int r = Color3;
		System.out.println("Bands : " + Bands);
		System.out.println("Width : " + Width);
		System.out.println("Height : " + Height);
		System.gc();
		int i=0, x, j=0, l, k;
		int index = 0;

		int pix[];
		int pix1[];
		int pix2[];
		int pix3[];

		int pix_2[];
		int pix1_2[];
		int pix2_2[];
		int pix3_2[];


		int p1, p2;
		int z,g;
		char temp[] = new char[10];
		char temp2[] = new char[100];
		int myFlag = 0;

		int maxRGB = 0, tempMax = 0;
		byte temp_max[];


		try
		{
			InputStream MyHdr = new FileInputStream(ImageFile + ".hdr");
			for (k = 0; k < 71; k++)
				temp2[k] = (char)MyHdr.read();
			for (k = 71; k < 78; k++)
			{
				temp[k - 71] = (char)MyHdr.read();
			}
		}
		catch (Exception e) { myFlag = 1; }
		String myTemp = (new String(temp)).trim();
		System.out.println("Read Data : " + myTemp);
		try
		{
			String s = myTemp.substring(1);
			shiftFlag = Integer.parseInt (s);
		}
		catch(Exception e){ myFlag = 1;}

		System.out.println("My Shift : " + shiftFlag);
		if(myFlag == 1)
			shiftFlag = 8;

		long fsize, fsize2;

		MappedByteBuffer mBuf;
		int tempWidth;

		try
		{

			FileInputStream f1 = new FileInputStream(ImageFile);            //reading pixel values for each band
			FileChannel fchan = f1.getChannel();
			FileOutputStream fout[] = new FileOutputStream[Bands];
			FileChannel fchanOut[] = new FileChannel[Bands];



			fsize = fchan.size();

			myFlag = 0;
			try
			{
				FileInputStream chk = new FileInputStream("Data" + ImageName +  "0");
			}
			catch(FileNotFoundException e)
			{
				myFlag = 1;
			}
			if(myFlag == 1)
			{

				for(k=0; k<Bands; k++)
				{

					fout[k] = new FileOutputStream("Data" + ImageName +  k);

					fchanOut[k] = fout[k].getChannel();

				}

				int pos=0;
				index=1;
				System.out.println("My Database Creation Started");



				tempWidth = Width;


				if (shiftFlag != 8)
					tempWidth = Width * 2;


				index=1;
				for(i=0;i<Height*Bands;i++)
				{
					mBuf=fchan.map(FileChannel.MapMode.READ_ONLY,pos,tempWidth);
					pos=pos+tempWidth;
					fchanOut[index-1].write(mBuf);

					index=index+1;
					if(index==(Bands+1))
						index=1;
				}

				f1.close();
				fchan.close();
				for(i=0;i<Bands;i++)
				{
					fout[i].close();
					fchanOut[i].close();
				}

			}
			System.gc();
		}
		catch (Exception e) { System.out.println("Before Database creation : " + e); }

		System.out.println("My Database Created");



		img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = img.getRaster();

		FileInputStream fin_band[]=new FileInputStream[Bands];
 		FileChannel fchan_in[]=new FileChannel[Bands];

 		index = 0;
 		double myTempCalc;

 		int slices, y_pass, y_origin;
 		int x_win=Width,y_win=600,y_win2,flag1=0;
 		int uneven_flag = 1;

 		if((Height % y_win) == 0)
 		{
 			slices = (int)(Height/y_win);
 			uneven_flag = 0;
 		}
 		else
 			slices = (int)(Height/y_win)+1;

 		fsize2=((Height-(slices-1)*y_win)*x_win);
 		y_win2 = (Height-(slices-1)*y_win);
		if(Height>y_win || Width>x_win)
		{
		 	fsize=(y_win*x_win);
		 	flag1=1;
		 	y_pass=y_win;

		}
	 	else
		{
		 	fsize=(Height*Width);
		 	System.out.println("Fsize : " + fsize);
		 	flag1=0;
		 	y_pass=Width;
		}


		int band_num[] = new int[Bands];
		String input;



		byte pix_byte[],pix_rgb2[][];



 		try
 		{
 			for(k=0; k<Bands; k++)
 			{
 				fin_band[k] = new FileInputStream("Data" + ImageName +  k);
 				fchan_in[k] = fin_band[k].getChannel();

 			}


 			if(shiftFlag == 8)
 			{
	 			pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize];
				pix_rgb2=new byte[Bands][(int)fsize2];
			}
			else
			{
				pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize*2];
				pix_rgb2=new byte[Bands][(int)fsize2*2];

			}


			byte pix_line[];
			byte pix_line2[];
			int abc=0;
			if(shiftFlag == 8)
			{
				pix_line = new byte[x_win];
				pix_line2 = new byte[x_win];
				abc = x_win;
			}
			else
			{
				pix_line = new byte[x_win * 2];
				pix_line2 = new byte[x_win * 2];
				abc = x_win * 2;
			}
			int line_start,line_start2=0;
			int myMax = 0;
			int myMin=0;
			long count;

			int tempCount;
			if(shiftFlag == 8)
				count = fsize;
			else
				count = fsize * 2;


			long counter=0;
			long start_pos = 0;
			maxBand=new int[Bands];
			minBand=new int[Bands];
			int maxBand2[]=new int[Bands];
		   	int minBand2[]=new int[Bands];
		   	int myMin2=0;
		   	int myMax2=0;
		   	line_start = 0;
		   	int first_time=1;
		   		System.out.println("Calculating Maximum and Minimum1");
		   	for(i=0; i<Bands; i++)
		   	{
		   		maxBand[i] = -1;
		   		minBand[i] = 65535;
		   	}
		   		System.out.println("Calculating Maximum and Minimum2");

		   	//Calculation of Maximum and Minimum
		   	FileInputStream f1 = new FileInputStream(ImageFile);
			FileChannel fchan = f1.getChannel();

		   	if(shiftFlag == 8)
				tempWidth = Width;
			else
				tempWidth = Width * 2;
			System.out.println("Calculating Maximum and Minimum");
		   	for(j=0; j<Height; j++)
		   	{

		   		for(i=0; i<Bands; i++)
		   		{
		   			mBuf = fchan.map(FileChannel.MapMode.READ_ONLY,line_start,tempWidth);
					mBuf.get(pix_line);
					for(k=0; k<tempWidth; k++)
						pix_rgb[i][k] = pix_line[k];
					line_start += tempWidth;

					for(k=0; k<Width; k++)
					{
						if(shiftFlag != 8)
						{
							p1 = pix_rgb[i][2*k] << 24;
							p2 = (pix_rgb[i][2*k+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
						}
						else
							tempCount = (pix_rgb[i][k] << 24) >>> 24;

						if(tempCount>maxBand[i])
							maxBand[i]=tempCount;

						if(tempCount<minBand[i])
						   minBand[i]=tempCount;
					}
		   		}
		   		if(j%500 == 0)
		   		{
		   			System.out.println("j : " + j);
		   			System.gc();
		   		}
		   	}



		myMax = maxBand[0];
		for(i=1; i<Bands; i++)
			if(myMax < maxBand[i])
				myMax = maxBand[i];

		for(i=0;i<Bands;i++)
			 System.out.println("the maximum value for band"+ (i+1 )+ " " + maxBand[i]);

		for(i=0;i<Bands;i++)
			System.out.println("the minimum value for band"+ (i+1) + " " + minBand[i]);


			// when size of image is smaller than window size set by x_win & y_win
			if(flag1==0)
			{

				for(i=0; i<Bands; i++)
				{
					if(shiftFlag == 8)
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					else
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb[i]);
				}
				System.gc();

 				//convert raw bytes into arrays.
 				if(shiftFlag == 8)
 				{
 					System.out.println("This is an 8 bit image");
					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							if(pix_rgb[p-1][i]>=0)
								pix1[i]= pix_rgb[p-1][i];
							else
							  	pix1[i]= 256+pix_rgb[p-1][i];

							if(pix_rgb[q-1][i]>=0)
							  	pix2[i]= pix_rgb[q-1][i];
							else
							  	pix2[i]= 256+pix_rgb[q-1][i];

							if(pix_rgb[r-1][i]>=0)
							  	pix3[i]= pix_rgb[r-1][i];
							else
							  	pix3[i]= 256+pix_rgb[r-1][i];
						}

						else
						{
							if(pix_rgb[0][i]>=0)
							{
								pix1[i]= pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
							else
							{
								pix1[i]= 256+pix_rgb[0][i];
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
						 }
	    	      	}//close for
				}
				else
				{

					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							//for pix1
							if(pix_rgb[p-1][2*i]<0)
								z=256+pix_rgb[p-1][2*i];
							else
								z=pix_rgb[p-1][2*i];

							if(pix_rgb[p-1][2*i+1]<0)
								g=256+pix_rgb[p-1][2*i+1];
							else
								g=pix_rgb[p-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix1[i] = p1 | p2;

							myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
							pix1[i] = (int)myTempCalc;

							// for pix2
							if(pix_rgb[q-1][2*i]<0)
								z=256+pix_rgb[q-1][2*i];
							else
								z=pix_rgb[q-1][2*i];

							if(pix_rgb[q-1][2*i+1]<0)
								g=256+pix_rgb[q-1][2*i+1];
							else
								g=pix_rgb[q-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix2[i] = p1 | p2;

							myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
							pix2[i] = (int)myTempCalc;

							// for pix3

							if(pix_rgb[r-1][2*i]<0)
								z=256+pix_rgb[r-1][2*i];
							else
								z=pix_rgb[r-1][2*i];

							if(pix_rgb[r-1][2*i+1]<0)
								g=256+pix_rgb[r-1][2*i+1];
							else
								g=pix_rgb[r-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix3[i] = p1 | p2;

							myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
							pix3[i] = (int)myTempCalc;

						}

						else
						{

						}

	    	      	}//close for
					System.out.println("Image Complete");
				}


				raster.setSamples(0,0,Width,Height,0,pix1);
				raster.setSamples(0,0,Width,Height,1,pix2);
				raster.setSamples(0,0,Width,Height,2,pix3);
				raster.setSamples(0,0,Width,Height,3,pix);


			}//close if
			else if(flag1==1) // when size of image is greater than window size set by x_win & y_win
			{

				System.out.println("the maximum value in bigger image is:"+ myMax);

				System.out.println("Maximum Value : " + myMax);

				if(shiftFlag==8)
				{
					line_start=0;
					for(int v=0;v<slices;v++)
					{
						System.out.println("enter v :"+v);

						if(v!=(slices-1))
						{

				  		  for( i=0;i<Bands;i++)
						  {
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width;
							System.out.println("line start i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;

								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
								// transfer one line data into pix_rgb[][]
								pix_rgb[i][k_index]=pix_line[k];

								}
							}//close for

							System.out.println("i :"+i);


				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);

						if(Bands!=1)
						{
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;
				    		    if(pix_rgb[p-1][i]>=0)
							    pix1[i]= pix_rgb[p-1][i];
								else
								pix1[i]= 256+pix_rgb[p-1][i];

								if(pix_rgb[q-1][i]>=0)
								pix2[i]= pix_rgb[q-1][i];
								else
								pix2[i]= 256+pix_rgb[q-1][i];

								if(pix_rgb[r-1][i]>=0)
								pix3[i]= pix_rgb[r-1][i];
								else
								pix3[i]= 256+pix_rgb[r-1][i];

							}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("slice displayed");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							  for( i=0;i<fsize;i++)
							  {
							      pix[i]=255;
							      if(pix_rgb[0][i]>=0)
							      pix1[i]= pix_rgb[0][i];
							      else
							  	  pix1[i]= 256+pix_rgb[0][i];

							  }
							  pix2=pix3=pix1;

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
						 	raster.setSamples(0,origin_y,x_win,y_win,3,pix);

							System.out.println("slice displayed");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("v origin_y line_start  :"+v+" "+origin_y+" "+line_start);
						for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}
							line_start=v*y_win*Width;
							System.out.println("line start2 i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window

						    for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;
								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
									// transfer one line data into pix_rgb[][]
									pix_rgb2[i][k_index]=pix_line[k];

								}

							}//close for
							System.out.println("i :"+i);
						}//close for

						System.out.println("enter ");
						System.out.println("fsize2 :"+fsize2);

						if(Bands!=1)
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

							    if(pix_rgb2[p-1][i]>=0)
							    pix1_2[i]= pix_rgb2[p-1][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[p-1][i];

							    if(pix_rgb2[q-1][i]>=0)
							    pix2_2[i]= pix_rgb2[q-1][i];
								else
								pix2_2[i]= 256+pix_rgb2[q-1][i];

								if(pix_rgb2[r-1][i]>=0)
								pix3_2[i]= pix_rgb2[r-1][i];
								else
								pix3_2[i]= 256+pix_rgb2[r-1][i];
							}
						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}
						else
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

								if(pix_rgb2[0][i]>=0)
							    pix1_2[i]= pix_rgb2[0][i];
							    else
							    pix1_2[i]= 256+pix_rgb2[0][i];
							}
							pix3_2=pix2_2=pix1_2;

						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}

						System.gc();

					}//close if(v==num_of_slice-1)


				}//close for "v"

			}// close else if
			else
			{

			  /* the code for 16 bit bigger image */
				line_start=0;
			  	System.out.println("Total number of slices : " + slices);
				for(int v=0;v<slices;v++)
				{
					System.out.println("\nSlice Number v :"+v);

					if(v!=(slices-1))
					{

				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of windowx
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;

				    		    //for pix1
								if(pix_rgb[p-1][2*i]<0)
									z=256+pix_rgb[p-1][2*i];
								else
									z=pix_rgb[p-1][2*i];

								if(pix_rgb[p-1][2*i+1]<0)
									g=256+pix_rgb[p-1][2*i+1];
								else
									g=pix_rgb[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1[i] = p1 | p2;

								myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
								pix1[i] = (int)myTempCalc;

							// for pix2
								if(pix_rgb[q-1][2*i]<0)
									z=256+pix_rgb[q-1][2*i];
								else
									z=pix_rgb[q-1][2*i];

								if(pix_rgb[q-1][2*i+1]<0)
									g=256+pix_rgb[q-1][2*i+1];
								else
									g=pix_rgb[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2[i] = p1 | p2;

								myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
								pix2[i] = (int)myTempCalc;

							// for pix3

								if(pix_rgb[r-1][2*i]<0)
									z=256+pix_rgb[r-1][2*i];
								else
									z=pix_rgb[r-1][2*i];

								if(pix_rgb[r-1][2*i+1]<0)
									g=256+pix_rgb[r-1][2*i+1];
								else
									g=pix_rgb[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3[i] = p1 | p2;

								myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
								pix3[i] = (int)myTempCalc;

				    		    line_start=line_start+Width*2;
				    		}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("Main Slice : " + v);
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("Last slice");
				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;

							for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb2[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize2;i++)
							{

				    		    pix_2[i]=255;

				    		    //for pix1
								if(pix_rgb2[p-1][2*i]<0)
									z=256+pix_rgb2[p-1][2*i];
								else
									z=pix_rgb2[p-1][2*i];

								if(pix_rgb2[p-1][2*i+1]<0)
									g=256+pix_rgb2[p-1][2*i+1];
								else
									g=pix_rgb2[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1_2[i] = p1 | p2;

								myTempCalc = ((double)pix1_2[i] / (double)myMax) * 255.0;
								pix1_2[i] = (int)myTempCalc;

								// for pix2
								if(pix_rgb2[q-1][2*i]<0)
									z=256+pix_rgb2[q-1][2*i];
								else
									z=pix_rgb2[q-1][2*i];

								if(pix_rgb2[q-1][2*i+1]<0)
									g=256+pix_rgb2[q-1][2*i+1];
								else
									g=pix_rgb2[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2_2[i] = p1 | p2;

								myTempCalc = ((double)pix2_2[i] / (double)myMax) * 255.0;
								pix2_2[i] = (int)myTempCalc;

								// for pix3

								if(pix_rgb2[r-1][2*i]<0)
									z=256+pix_rgb2[r-1][2*i];
								else
									z=pix_rgb2[r-1][2*i];

								if(pix_rgb2[r-1][2*i+1]<0)
									g=256+pix_rgb2[r-1][2*i+1];
								else
									g=pix_rgb2[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3_2[i] = p1 | p2;

								myTempCalc = ((double)pix3_2[i] / (double)myMax) * 255.0;
								pix3_2[i] = (int)myTempCalc;

					    		    line_start=line_start+Width*2;
				    		}

				    		System.out.println("Displayed Image");

							raster.setSamples(0,origin_y,x_win,y_win2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_win2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_win2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_win2,3,pix_2);
				 			System.out.println("slice displayed1");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{

							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();




					}//close if(v==num_of_slice-1)

			  }	//end of slice v==-1


			}


			for( i=0;i<Bands;i++)
			{
				fin_band[i].close();
				fchan_in[i].close();
			}
		}
 }

            catch(Exception e)
            {
            	System.out.println(e);
            }

 			System.out.println("Hi");

 		return img;
 		}

	public int[] minArray()
	{
		return minBand;
	}

	public int[] maxArray()
	{
		return maxBand;
	}


}


class DialogDemo
{

}


///////////*************  DemoPanel Class To take Clicks On The Displayed Image **********************//////////////////////



class DemoPanel extends JPanel implements MouseMotionListener
{
	static BufferedImage Imag;
	private Dimension ViewSize;
	private picture Picture1;
	int x;
	int y;
	int width;
	int height;
	int bands;
	int maxindex, minindex;
	private DialogDemo message;
	public static int Noofclicks;
	public static int flag1=1;
	int click_x[]=new int[10000];
	int click_y[]=new int[10000];
	private calculate c1;
	ReadImageData imgdata;
    public static int Bands1;
   	int Width1;
   	int Height1;
	MappedByteBuffer mBuf;
	static double val1[][];
	public static int clicks=0;
	public static double xratios[]= new double[ReadImageData.Bands];

	public static double  rowdata[]=new double[new ReadImageData().bands()];


	public DemoPanel()
	{
	}

	public BufferedImage getpixdata()
	{
		return Imag;
	}

	int return_x()
	{
		if (x >= 0)
			return x;
		else
			return -1;

	}

	int return_y()
	{
		if (y >= 0)
			return y;
		else
			return -1;
	}
	public int retclicks()
    {
    	return Noofclicks;
    }


	public DemoPanel (BufferedImage Img)
	{
	  System.gc();
	  this.Imag=Img;
	  int Width=Math.min(256,Imag.getWidth());
	  final int Height=Math.min(256,Imag.getHeight());
	  ViewSize=new Dimension(Width,Height);
	  setPreferredSize(new Dimension(Imag.getWidth(),Imag.getHeight()));

	  imgdata=new ReadImageData();
      Bands1=imgdata.bands();
  	  Width1=imgdata.columns();
  	  Height1=imgdata.rows();
  	  FileInputStream fin_band[]=new FileInputStream[Bands1];
      final FileChannel fchan_in[]=new FileChannel[Bands1];

	  try
	  {
	 	 for(int k=0; k<Bands1; k++)
 	  	 {
 			fin_band[k] = new FileInputStream("Data" + BilImage.name +  k);
 			fchan_in[k] = fin_band[k].getChannel();

 	     }
 	  }
 	  catch(Exception e)
 	  {
 		System.out.println("file Open Error in click : "+e);
 	  }
 	  val1=new double[Bands1][100];

	  addMouseListener(new MouseAdapter()
	   {
	       int val,i;
	       long start,len;
	       int arr[]=new int[Bands1];
	       double sum;

		   public void mouseClicked(MouseEvent evt)

		   {
			   if(BilImage.ra1.isSelected()==true)
			   {
				   System.gc();
				   flag1=0;
		           	if(flag1!=0)
		           		flag1=JOptionPane.showConfirmDialog(null,"Do you want to save the Signature File");

		           	if(flag1==0)
		           	{
		                       int width=Imag.getWidth();
								int height=Imag.getHeight();
								int x=evt.getX();
								int y=evt.getY();

								if(evt.getClickCount()>0)
								{
								if(y > (height) || x > (width) || y < 0 || x < 0)
								{
								  clicks=0;
								  Noofclicks=0;
								  JOptionPane.showMessageDialog(null,"You clicked outside the image","Warnning Message",JOptionPane.INFORMATION_MESSAGE);
								  flag1=1;
								}
								else                         ///getting all x,y coordinates clicked
								{
								 click_x[clicks]=x;
								 click_y[clicks]=y;

								 System.out.print("x : "+x);
								 System.out.println(" y : "+y);

								 x=(int)(x/BilImage.zoomFactor);
								 y=(int)(y/BilImage.zoomFactor);
								 System.out.print("After x : "+x);
								 System.out.println(" After y : "+y);
								 clicks=Noofclicks;
								 System.out.println("clicks : "+(Noofclicks+1));
								 try
								 {
									 byte b,b1;
									 	int temp,tempCount;
									 	int p1,p2;
									 	if(picture.shiftFlag==8)
									 	{
									 		for(i=0;i<Bands1;i++)
									 		{
									 			start=y*Width1+x;
									 			len=1;
									 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
									 			b=mBuf.get();
									 			tempCount = (b<< 24) >>> 24;
									 		    val1[i][clicks]=tempCount;
									 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
									 		}

									 	}
									 	else
									 	{
									 		for(i=0;i<Bands1;i++)
									 		{
									 			start=(y*Width1+x)*2;
									 			len=2;
									 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
									 			b=mBuf.get();
									 			b1=mBuf.get();
									 			p1 = b << 24;
												p2 = (b1 <<24 ) >>> 8;
												tempCount = p1 | p2;
												tempCount = tempCount >>> 16;
												val1[i][clicks]=tempCount;
									 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
									 		}

									 	}
								 		for(int z=0;z<Bands1;z++)
								 			xratios[z]=val1[z][clicks];

								 		RegionGrow.count=0;
										RegionGrow.count1=0;
							RegionGrow.windowmaker(x,y,Width1,Height1);
							RegionGrow. Bandratiocal();

								 	clicks++;
								 	Noofclicks++;

								}
								catch(Exception e)
								{
									System.out.println("Click error : "+e);
									e.printStackTrace();
								}
							   }
							  }
						  }
		           	flag1=0;
		             }

		   else
			   {
            	System.gc();
            	if(flag1!=0)
            		flag1=JOptionPane.showConfirmDialog(null,"Do you want to save the clicks");

            	if(flag1==0)
            	{
                        int width=Imag.getWidth();
						int height=Imag.getHeight();
						int x=evt.getX();
						int y=evt.getY();
						if(BilImage.tab==0)
						{
		    				DemoPanel pane=new DemoPanel();
		   					imagetable6 Table6;
							System.out.println("Table Opened");
							int Noclicks=pane.retclicks();
							Table6=new imagetable6();
							Table6=new imagetable6(Noclicks);
							Table6.setSize(550,300);
							Table6.setVisible(true);
							BilImage.tab=1;
						}
						if(evt.getClickCount()>0)
						{
						if(y > (height) || x > (width) || y < 0 || x < 0)
						{
						  clicks=0;
						  Noofclicks=0;
						  if(message==null)
						  message=new DialogDemo();
						  JOptionPane.showMessageDialog(null,"You clicked outside the image","Warnning Message",JOptionPane.INFORMATION_MESSAGE);
						  flag1=1;
						}
						else                         ///getting all x,y coordinates clicked
						{
						 click_x[clicks]=x;
						 click_y[clicks]=y;

						 System.out.print("x : "+x);
						 System.out.println(" y : "+y);

						 x=(int)(x/BilImage.zoomFactor);
						 y=(int)(y/BilImage.zoomFactor);

						 clicks=Noofclicks;
						 System.out.println("clicks : "+(Noofclicks+1));
						 try
						 {
						 	byte b,b1;
						 	int temp,tempCount;
						 	int p1,p2;
						 	if(picture.shiftFlag==8)
						 	{
						 		for(i=0;i<Bands1;i++)
						 		{
						 			start=y*Width1+x;
						 			len=1;
						 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
						 			b=mBuf.get();
						 			tempCount = (b<< 24) >>> 24;
						 		    val1[i][clicks]=tempCount;
						 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
						 		}
						 	}
						 	else
						 	{
						 		for(i=0;i<Bands1;i++)
						 		{
						 			start=(y*Width1+x)*2;
						 			len=2;
						 			mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,start,len);
						 			b=mBuf.get();
						 			b1=mBuf.get();
						 			p1 = b << 24;
									p2 = (b1 <<24 ) >>> 8;
									tempCount = p1 | p2;
									tempCount = tempCount >>> 16;
									val1[i][clicks]=tempCount;
						 			System.out.println("val on band : "+(i+1)+" = "+(double)tempCount);
						 		}
						 	}

						 	clicks++;
						 	Noofclicks++;

						}
						catch(Exception e)
						{
							System.out.println("Click error : "+e);
						}
					   }
					  }
				  }
              }
			   }

	     });

		addMouseMotionListener(this);
    }


          public void mouseDragged(MouseEvent evt){}
		  public void mouseMoved(MouseEvent evt)
          {
               // show coordinates
                 x = evt.getX();
                 y = evt.getY();

	           int width=Imag.getWidth();
			   int height=Imag.getHeight();
			   if(x>width)
			   		setToolTipText("");
			   else
			   		if(y>height)
			   			setToolTipText("");
			   		else
			   		{
	           			setToolTipText("R" + y + ",C" + x);
                 		repaint();
              		}
           }


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(Imag, 0, 0, this);
		g2.dispose();
	}


}

////////////////////**************** About Dialog Class **********************///////////////


class AboutDialog extends JDialog implements ActionListener
{
	static int Band1 = 4;
	static int Band2 = 4;
	static int Band3 = 4;
	private ReadImageData ImgData2;
	Container contentPane = getContentPane();
	JLabel pict, pict1, pict2;
	JTextField f1, f2, f3;
	JButton b1, b2;
	JComboBox list, list1, list2;
	int x, y, z;
	private boolean ok;

	public int show_band1()
	{
		return Band1;
	}

	public int show_band2()
	{
		return Band2;
	}

	public int show_band3()
	{
		return Band3;
	}

	public AboutDialog(JFrame parent)
	{
		super(parent, "Bands Chooser", true);
		ImgData2 = new ReadImageData();
		int i = ImgData2.bands();
		String str[] = new String[i];
		GridBagLayout g1;
		GridBagConstraints gbc;
		int k = i;

		for (int j = 0; j < i; j++)
		{
			str[j] = Integer.toString(k);
			k--;
		}

		b1 = new JButton("Ok");
		b2 = new JButton("Cancel");
		list = new JComboBox(str);
		list1 = new JComboBox(str);
		list2 = new JComboBox(str);
		pict = new JLabel("Red");
		pict1 = new JLabel("Green");
		pict2 = new JLabel("Blue");

		f1 = new JTextField(5);
		f1.setBackground(Color.red);
		f1.setEnabled(false);
		f2 = new JTextField(5);
		f2.setBackground(Color.green);
		f2.setEnabled(false);
		f3 = new JTextField(5);
		f3.setBackground(Color.blue);
		f3.setEnabled(false);


		g1 = new GridBagLayout();
		contentPane.setLayout(g1);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 0;
		gbc.gridy = 0;
		g1.setConstraints(pict, gbc);
		contentPane.add(pict);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 2;
		gbc.gridy = 0;

		g1.setConstraints(f1, gbc);
		contentPane.add(f1);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 4;
		gbc.gridy = 0;

		g1.setConstraints(list, gbc);
		contentPane.add(list);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;//4
		gbc.gridy = 0;
		g1.setConstraints(pict1, gbc);
		contentPane.add(pict1);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 8;//6
		gbc.gridy = 0;

		g1.setConstraints(f2, gbc);
		contentPane.add(f2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 10;
		gbc.gridy = 0;

		g1.setConstraints(list1, gbc);
		contentPane.add(list1);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 12;//8
		gbc.gridy = 0;
		g1.setConstraints(pict2, gbc);
		contentPane.add(pict2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 14;//10
		gbc.gridy = 0;


		g1.setConstraints(f3, gbc);
		contentPane.add(f3);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 16;//12
		gbc.gridy = 0;

		g1.setConstraints(list2, gbc);
		contentPane.add(list2);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 3;
		gbc.gridy = 2;
		g1.setConstraints(b1, gbc);
		contentPane.add(b1);
		b1.addActionListener(this);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.gridx = 6;
		gbc.gridy = 2;
		g1.setConstraints(b2, gbc);
		contentPane.add(b2);
		b2.addActionListener(this);
		dispose();
	}

	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		if (source == b1)//
		{
			String s = (String)list.getSelectedItem();
			String s1 = (String)list1.getSelectedItem();
			String s2 = (String)list2.getSelectedItem();
			int x = Integer.parseInt(s);
			this.Band1 = x;
			int y = Integer.parseInt(s1);
			this.Band2 = y;
			int z = Integer.parseInt(s2);
			this.Band3 = z;
			dispose();
		}
		else
		{
			dispose();
		}
	}
}


///////////////******************* Zoom In Class For Enlargement of The Image****************///////////////


class ZoomIn
 {
    private BufferedImage picture;
	private static int newsize=1;
	public ZoomIn ()
	{
	}
	public ZoomIn (BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()*Enlargefactor;
	  int h=Imag.getHeight()*Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  	for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x/Enlargefactor,y/Enlargefactor));
	  System.out.println(EnlargedImag.getWidth());
	  this.picture=EnlargedImag;
	  this.newsize=Enlargefactor;
    }

    public int retnewsize()
    {
     	return newsize;
    }
    public BufferedImage retchangepic()
	{
	    return picture;
    }
}

/***************** Shrinking The Image by Pixel Skipping***************************************/



 class ZoomOut
 {
    private BufferedImage picture;
	public ZoomOut(BufferedImage Imag,int Enlargefactor)
	{
	  System.gc();
	  int w=Imag.getWidth()/Enlargefactor;
	  int h=Imag.getHeight()/Enlargefactor;
	  BufferedImage EnlargedImag=new BufferedImage(w,h,Imag.getType());
	  for(int y=0;y < h;y++)
	  	for(int x=0;x < w;x++)
	     EnlargedImag.setRGB(x,y,Imag.getRGB(x*Enlargefactor,y*Enlargefactor));
	  this.picture=EnlargedImag;
     }
	 public BufferedImage retchangepic()
	 {
	    return picture;
     }

}


 //////////////////********** Calculate Class For Calculating Error Margin *************///////////////


class calculate
{

	static Vector pixelValue=new Vector();   				//vector for capture pixel value

	private DemoPanel demo;
	private BufferedImage image;
	private ReadImageData imgdat11;
    static int Bands;
    static int Width;
    static int Height;


    Double pixelArray[];
    static double pixel_Array[];		//arrary for geting vector values
    static int[]xy=new int[2];                  //for tranfering x,y coordinate for accuracy assesment


	int bands;
	private ZoomIn zoom1;
	private static int newsize=1;
	private ReadImageData imgdat;
	static double bandsdata[]=new double[Bands];
	static int[][][] pixvalue=new int[Bands][Width][Height];
	public calculate()
	{
	}
	public calculate(int a[][][])
	{
	  imgdat=new ReadImageData();
	  this.Bands=imgdat.bands();
      this.Width=imgdat.columns();
      this.Height=imgdat.rows();
      this.pixvalue=a;
	  double bandsdat[]=new double[Bands];
	  this.bandsdata=bandsdat;
	  for(int i=0;i<Bands;i++)
	    bandsdata[i]=0.00000000000;
	}

	public  calculate(int x,int y)
    {
	  imgdat11=new ReadImageData();
	  int bands=imgdat11.bands();
	  this.Bands=imgdat11.bands();
	  zoom1=new ZoomIn();
	  this.newsize=zoom1.retnewsize();
	  int newx=(int)(x/newsize);
	  int newy=(int)(y/newsize);
	  double banddata[]=new double[bands];
      xy[0]=newx;
      xy[1]=newy;

	  for(int i=0;i<Bands;i++)
	    banddata[i]=pixvalue[i][newy][newx];			//for tranfring value to acuracy class


	// code for storing pixel values

	try
	{


	   for(int i=0;i<Bands;i++)
	   {
	    	Double temp=new Double(banddata[i]);
	   		pixelValue.addElement(temp);                     			        //adding elements to vector array
	   }

       int l=pixelValue.size();
       pixelArray=new Double[l];
       pixel_Array=new double[l];
       pixelValue.copyInto(pixelArray);

       for(int m=0;m<l;m++)
       {

       	pixel_Array[m]=pixelArray[m].doubleValue();     //converting double object to primitive double**/
       	System.out.println(pixel_Array[m]);
       }

	}
	catch(Exception e)
	{
		System.out.println("UMA TYAGI"+  e);
	}
		for(int d=0;d<Bands;d++)
	   		this.bandsdata[d]=banddata[d]+this.bandsdata[d];
  }


  public double[] retbandsdata()// return the total of pixel values of coordinates clicked
  {
    return bandsdata;
  }


 public int[][][] retpix()      /** return the values of pixvalues matrix **/
  {
    return pixvalue;
  }

 public int[]retxy()            // to return x,y coordinate
  {
    return xy;
  }



  public double[] ret_pixelArray()			// function to return pixel values
  {
  	return pixel_Array;
  }

  public void remove()                          // function to remove vector elements
  {
    int i=pixelValue.size();

    for(int j=0;j<i;j++)
    {
    	Double t=new Double(pixel_Array[j]);

    	pixelValue.removeElement(t);

    }
  }


  /************* Function For Calculating Error Function *********************************************/

  public  double erf(double z)
  {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                                            t * ( 1.00002368 +
                                            t * ( 0.37409196 +
                                            t * ( 0.09678418 +
                                            t * (-0.18628806 +
                                            t * ( 0.27886807 +
                                            t * (-1.13520398 +
                                            t * ( 1.48851587 +
                                            t * (-0.82215223 +
                                            t * ( 0.17087277))))))))));
        if (z >= 0) return  ans;
        else        return -ans;
    }

}




//////////***************** Class For Enhancing The Apperance of The Image. ***************////////////////


class Enhancement
 {
  private ReadImageData imgdata;
  private calculate cal;
  public File fi;
  BufferedImage img;
  int [][][] pixeldata;
  double [][][] pixeldata1;
  int Bands;
  int Height;
  int Width;
  picture pict=new picture();

  public Enhancement()
   {}

  public Enhancement(int i,int j,int k)
   {
     pict=new picture();
     img=pict.getpicdata(i,j,k);
   }

 public void enhances(int col1,int col2,int col3)
  {int tRow=0;
  	try{
  		tRow=new ReadImageData().rows();
  	System.out.println("tRow="+tRow);
  }
  catch(Exception e)
  {
  	System.out.println("Ex:"+e);
  }
  if(true)
  {
   cal=new calculate();
   imgdata=new ReadImageData();
   double min=0;
   double max=0;
   int p=col1;
   int q=col2;
   int r=col3;
   int index=0;
   Bands=imgdata.bands();
   Width=imgdata.columns();
   Height=imgdata.rows();
   int pix[] = new int [(Width)*(Height)];
   int pix1[] = new int [(Width)*(Height)];
   int pix2[] = new int [(Width)*(Height)];
   int pix3[] = new int [(Width)*(Height)];
   if(Bands!=1)
   pixeldata1=new double[Bands][Height][Width];
   else
   pixeldata1=new double[3][Height][Width];
   pixeldata=new int[Bands][Height][Width];
   pixeldata=cal.retpix();
   MappedByteBuffer mBuf;
   long fsize=Height*Width;
   FileChannel fchan_in[]=new FileChannel[Bands];
   byte [][]pix_rgb=new byte[Bands][(int)fsize];
   byte [][]pix_rgb1=new byte[Bands][(int)fsize*2];
   FileInputStream fin_band[]=new FileInputStream[Bands];

 		try
 		{

 			for(int i=0;i<Bands;i++)
 			{
 				fin_band[i] = new FileInputStream("Data" + BilImage.name +  i);
 				fchan_in[i] = fin_band[i].getChannel();
 			}

 		}
 		catch(Exception e)
 		{
 			System.out.println("Ex:"+e);
 		}
		int temp,tempCount,p1,p2;;
		int i=0,j,k,x,l;
		int r1=(BilImage.p)-1;
		int g1=(BilImage.q)-1;
		int b1=(BilImage.r)-1;
 		int b[][]=new int[Bands*Height][Width];
 		int a[][][];
 		if(Bands!=1)
 		 a=new int [Bands][Height][Width*2];
 		else
 		 a=new int [3][Height][Width*2];
		try
		{
		     if(Bands!=1)
		     {
			 if(picture.shiftFlag==8)
			 {


				mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}


				mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[1][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[1][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}



				mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize);
				mBuf.get(pix_rgb[i]);
				for(j=0;j<Height;j++)
					for(k=0;k<Width;k++)
					{
						temp=(int)pix_rgb[i][j*(Width)+k];
						if(temp<0)
							a[2][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
						else
							a[2][j][k]=(int)pix_rgb[i][j*(Width)+k];
					}
				System.gc();

			}
			else
			{	System.out.println("begin");
				System.gc();
				mBuf=fchan_in[r1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[0][j][k]=tempCount;
					}

				mBuf=fchan_in[g1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[1][j][k]=tempCount;
					}

				mBuf=fchan_in[b1].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
				mBuf.get(pix_rgb1[0]);
				for(j=0;j<Height-1;j++)
					for(k=0;k<Width*2;k++)
					{
						p1 = pix_rgb1[0][2*(j*Width+k)] << 24;
						p2 = (pix_rgb1[0][2*(j*Width+k)+1] <<24 ) >>> 8;
						tempCount = p1 | p2;
						tempCount = tempCount >>> 16;
						a[2][j][k]=tempCount;
					}
				System.out.println("end");
			}
		}
		else
		{
		    FileInputStream fin=new FileInputStream("Data"+BilImage.name+"0");///change
		    FileChannel fchan_in1[]=new FileChannel[3];
            fchan_in1[0]=fin.getChannel();
            fchan_in1[1]=fin.getChannel();
            fchan_in1[2]=fin.getChannel();

            i=0;
            mBuf=null;
            try
            {
			mBuf=fchan_in1[0].map(FileChannel.MapMode.READ_ONLY,0,fsize);

			mBuf.get(pix_rgb[i]);

			for(j=0;j<Height;j++)
				for(k=0;k<Width;k++)
				{
					temp=(int)pix_rgb[i][j*(Width)+k];
					if(temp<0)
						a[0][j][k]=255+(int)pix_rgb[i][j*(Width)+k];
					else
						a[0][j][k]=(int)pix_rgb[i][j*(Width)+k];
				}
				max=pict.maxBand[0];
				min=pict.minBand[0];
				for(int i1=0;i1<3;i1++)
					for(j=0;j<Height;j++)
     	   				for( k=0;k<Width;k++)
        	   				pixeldata1[i1][j][k]=((a[0][j][k]-min)/(max-min))*255;
           	}
			catch(Exception e)
			{
				System.out.println("1 Band Enhance Error : "+e);
			}

			System.gc();

			}

	}
	catch(Exception e)
	{
		System.out.println("Exception1 : "+e);
	}

// Finding max & min of displaying Bands.

	if(Bands!=1)
	{
	 for(i=0;i<3;i++)
	 {
	 	if(i==0)
	 	{
	 		max=pict.maxBand[r1];
	 		min=pict.minBand[r1];
	 	}
	 	if(i==1)
	 	{
	 		max=pict.maxBand[g1];
	 		min=pict.minBand[g1];
	 	}
	 	if(i==2)
	 	{
	 		max=pict.maxBand[b1];
	 		min=pict.minBand[b1];
	 	}

	 	for(j=0;j<Height-1;j++)
     	   for( k=0;k<Width;k++)
        	   pixeldata1[i][j][k]=((a[i][j][k]-min)/(max-min))*255;
	 }
	}

	for(i=0;i<3;i++)
	{
		min=pixeldata1[i][0][0];
		max=pixeldata1[i][0][0];

		for(j=0;j<Height-1;j++)
		{
			for(k=0;k<Width;k++)
			{
				if(min>pixeldata1[i][j][k])
					min=pixeldata1[i][j][k];

				if(max<pixeldata1[i][j][k])
					max=pixeldata1[i][j][k];
			}
		}

	}

    for(i=0;i<Height;i++)
    {
	    for ( j=0;j<Width;j++)
	    {
			 pix[index]=255;
		     pix1[index]= (int)pixeldata1[0][i][j];
			 pix2[index]= (int)pixeldata1[1][i][j];
			 pix3[index]= (int)pixeldata1[2][i][j];
			 index++;
		}
    }

	img=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_ARGB);
	WritableRaster raster=img.getRaster();
	raster.setSamples(0,0,Width,Height,0,pix1);
	raster.setSamples(0,0,Width,Height,1,pix2);
	raster.setSamples(0,0,Width,Height,2,pix3);
	raster.setSamples(0,0,Width,Height,3,pix);
    }

   /* else
    {

         byte pix_rgb[][];
         int []minBand=new int[Bands];
		int []maxBand=new int[Bands];
		File ImageFile;
		ReadImageData ImgData1;

 		System.gc();
		ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();
		int origin_x=0;
		int origin_y=600;
		int y_pass2;
		ImgData1.read(ImageFile);
		Bands = ImgData1.bands();
		Width = ImgData1.columns();
		Height = ImgData1.rows();
		int p = col1;
		int q = col2;
		int r = col3;
		System.out.println("Bands : " + Bands);
		System.out.println("Width : " + Width);
		System.out.println("Height : " + Height);
		System.gc();
		int i=0, x, j=0, l, k;
		int index = 0;

		int pix[];
		int pix1[];
		int pix2[];
		int pix3[];

		int pix_2[];
		int pix1_2[];
		int pix2_2[];
		int pix3_2[];


		int p1, p2;
		int z,g;
		char temp[] = new char[10];
		char temp2[] = new char[100];
		int myFlag = 0;

		int maxRGB = 0, tempMax = 0;
		byte temp_max[];


		try
		{
			InputStream MyHdr = new FileInputStream(ImageFile + ".hdr");
			for (k = 0; k < 71; k++)
				temp2[k] = (char)MyHdr.read();
			for (k = 71; k < 78; k++)
			{
				temp[k - 71] = (char)MyHdr.read();
			}
		}
		catch (Exception e) { myFlag = 1; }
		String myTemp = (new String(temp)).trim();
		System.out.println("Read Data : " + myTemp);
		try
		{
			String s = myTemp.substring(1);
			picture.shiftFlag = Integer.parseInt (s);
		}
		catch(Exception e){ myFlag = 1;}

		System.out.println("My Shift : " + picture.shiftFlag);
		if(myFlag == 1)
			picture.shiftFlag = 8;

		long fsize, fsize2;

		MappedByteBuffer mBuf;
		int tempWidth;

		try
		{




			System.gc();
		}
		catch (Exception e) { System.out.println("Before Database creation : " + e); }





		img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);

		WritableRaster raster = img.getRaster();


		FileInputStream fin_band[]=new FileInputStream[Bands];
 		FileChannel fchan_in[]=new FileChannel[Bands];

 		index = 0;
 		double myTempCalc;

 		int slices, y_pass, y_origin;
 		int x_win=Width,y_win=600,y_win2,flag1=0;
 		int uneven_flag = 1;

 		if((Height % y_win) == 0)
 		{
 			slices = (int)(Height/y_win);
 			uneven_flag = 0;
 		}
 		else
 			slices = (int)(Height/y_win)+1;

 		fsize2=((Height-(slices-1)*y_win)*x_win);
 		y_win2 = (Height-(slices-1)*y_win);
		if(Height>y_win || Width>x_win)
		{
		 	fsize=(y_win*x_win);
		 	flag1=1;
		 	y_pass=y_win;

		}
	 	else
		{
		 	fsize=(Height*Width);
		 	System.out.println("Fsize : " + fsize);
		 	flag1=0;
		 	y_pass=Width;
		}


		int band_num[] = new int[Bands];
		String input;



		byte pix_byte[],pix_rgb2[][];



 		try
 		{
 			for(k=0; k<Bands; k++)
 			{
 				fin_band[k] = new FileInputStream("Data" + BilImage.name +  k);
 				fchan_in[k] = fin_band[k].getChannel();

 			}


 			if(picture.shiftFlag == 8)
 			{
	 			pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize];
				pix_rgb2=new byte[Bands][(int)fsize2];
			}
			else
			{
				pix = new int [(int)fsize];
				pix1 = new int [(int)fsize];
				pix2 = new int [(int)fsize];
				pix3 = new int [(int)fsize];

				pix_2 = new int [(int)fsize2];
			 	pix1_2 = new int [(int)fsize2];
				pix2_2 = new int [(int)fsize2];
				pix3_2 = new int [(int)fsize2];

	 			pix_rgb=new byte[Bands][(int)fsize*2];
				pix_rgb2=new byte[Bands][(int)fsize2*2];

			}


			byte pix_line[];
			byte pix_line2[];
			int abc=0;
			if(picture.shiftFlag == 8)
			{
				pix_line = new byte[x_win];
				pix_line2 = new byte[x_win];
				abc = x_win;
			}
			else
			{
				pix_line = new byte[x_win * 2];
				pix_line2 = new byte[x_win * 2];
				abc = x_win * 2;
			}
			int line_start,line_start2=0;
			int myMax = 0;
			int myMin=0;
			long count;

			int tempCount;
			if(picture.shiftFlag == 8)
				count = fsize;
			else
				count = fsize * 2;


			long counter=0;
			long start_pos = 0;
			maxBand=new int[Bands];
			minBand=new int[Bands];
			int maxBand2[]=new int[Bands];
		   	int minBand2[]=new int[Bands];
		   	int myMin2=0;
		   	int myMax2=0;
		   	line_start = 0;
		   	int first_time=1;
		   		System.out.println("Calculating Maximum and Minimum1");
		   	for(i=0; i<Bands; i++)
		   	{
		   		maxBand[i] = -1;
		   		minBand[i] = 65535;
		   	}
		   		System.out.println("Calculating Maximum and Minimum2");

		   	//Calculation of Maximum and Minimum
		   	FileInputStream f1 = new FileInputStream(ImageFile);
			FileChannel fchan = f1.getChannel();

		   	if(picture.shiftFlag == 8)
				tempWidth = Width;
			else
				tempWidth = Width * 2;
			System.out.println("Calculating Maximum and Minimum");
		   	for(j=0; j<Height; j++)
		   	{

		   		for(i=0; i<Bands; i++)
		   		{
		   			mBuf = fchan.map(FileChannel.MapMode.READ_ONLY,line_start,tempWidth);
					mBuf.get(pix_line);
					for(k=0; k<tempWidth; k++)
						pix_rgb[i][k] = pix_line[k];
					line_start += tempWidth;

					for(k=0; k<Width; k++)
					{
						if(picture.shiftFlag != 8)
						{
							p1 = pix_rgb[i][2*k] << 24;
							p2 = (pix_rgb[i][2*k+1] <<24 ) >>> 8;
							tempCount = p1 | p2;
							tempCount = tempCount >>> 16;
						}
						else
							tempCount = (pix_rgb[i][k] << 24) >>> 24;

						if(tempCount>maxBand[i])
							maxBand[i]=tempCount;

						if(tempCount<minBand[i])
						   minBand[i]=tempCount;
					}
		   		}
		   		if(j%500 == 0)
		   		{
		   			System.out.println("j : " + j);
		   			System.gc();
		   		}
		   	}



		myMax = maxBand[0];
		for(i=1; i<Bands; i++)
			if(myMax < maxBand[i])
				myMax = maxBand[i];

		for(i=0;i<Bands;i++)
			 System.out.println("the maximum value for band"+ (i+1 )+ " " + maxBand[i]);

		for(i=0;i<Bands;i++)
			System.out.println("the minimum value for band"+ (i+1) + " " + minBand[i]);


			// when size of image is smaller than window size set by x_win & y_win
			if(flag1==0)
			{

				for(i=0; i<Bands; i++)
				{
					if(picture.shiftFlag == 8)
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize);
					else
						mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,0,fsize*2);
					mBuf.get(pix_rgb[i]);
				}
				System.gc();

 				//convert raw bytes into arrays.
 				if(picture.shiftFlag == 8)
 				{
 					System.out.println("This is an 8 bit image");
					for(i=0;i<Height*Width;i++)
					{
						 pix[i]=255;
						if(Bands!=1)
						{

							if(pix_rgb[p-1][i]>0)
								pix1[i]= (int )((pix_rgb[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1]))*255;
							else
							  	pix1[i]= (int)((256+pix_rgb[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1]))*255;



							if(pix_rgb[q-1][i]>0)
							  	pix2[i]= (int)((pix_rgb[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1]))*255;
							else
							  	pix2[i]= (int)((256+pix_rgb[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1]))*255;


							if(pix_rgb[r-1][i]>0)
							  	pix3[i]= (int)((pix_rgb[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1]))*255;
							else
							  	pix3[i]= (int)((256+pix_rgb[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1]))*255;


						}

						else
						{

							if(pix_rgb[0][i]>0)
							{
								pix1[i]= ((pix_rgb[0][i]-minBand[0])/(maxBand[0]-minBand[0]))*255;
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
							else
							{
								pix1[i]= ((256+pix_rgb[0][i]-minBand[0])/(maxBand[0]-minBand[0]))*255;
								pix2[i]=pix1[i];
								pix3[i]=pix1[i];
							}
						 }
	    	      	}//close for
				}
				else
				{

					for(i=0;i<fsize;i++)
					{
						pix[i]=255;
						if(Bands!=1)
						{
							//for pix1
							if(pix_rgb[p-1][2*i]<0)
								z=256+pix_rgb[p-1][2*i];
							else
								z=pix_rgb[p-1][2*i];

							if(pix_rgb[p-1][2*i+1]<0)
								g=256+pix_rgb[p-1][2*i+1];
							else
								g=pix_rgb[p-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix1[i] = p1 | p2;

							myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
							pix1[i] = (int)(myTempCalc-minBand[p-1])/(maxBand[p-1]-minBand[p-1])*255;

							// for pix2
							if(pix_rgb[q-1][2*i]<0)
								z=256+pix_rgb[q-1][2*i];
							else
								z=pix_rgb[q-1][2*i];

							if(pix_rgb[q-1][2*i+1]<0)
								g=256+pix_rgb[q-1][2*i+1];
							else
								g=pix_rgb[q-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix2[i] = p1 | p2;

							myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
							pix2[i] = (int)(myTempCalc-minBand[q-1])/(maxBand[q-1]-minBand[q-1])*255;;

							// for pix3

							if(pix_rgb[r-1][2*i]<0)
								z=256+pix_rgb[r-1][2*i];
							else
								z=pix_rgb[r-1][2*i];

							if(pix_rgb[r-1][2*i+1]<0)
								g=256+pix_rgb[r-1][2*i+1];
							else
								g=pix_rgb[r-1][2*i+1];

							p1 = z << 8;
							p2 = g;
							pix3[i] = p1 | p2;

							myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
							pix3[i] = (int)(myTempCalc-minBand[r-1])/(maxBand[r-1]-minBand[r-1])*255;

						}

						else
						{

						}

	    	      	}//close for
					System.out.println("Image Complete");
				}

               	System.out.println("Image Complete hai bhai");
				raster.setSamples(0,0,Width,Height,0,pix1);
				raster.setSamples(0,0,Width,Height,1,pix2);
				raster.setSamples(0,0,Width,Height,2,pix3);
				raster.setSamples(0,0,Width,Height,3,pix);


			}//close if
			else if(flag1==1) // when size of image is greater than window size set by x_win & y_win
			{

				System.out.println("the maximum value in bigger image is:"+ myMax);

				System.out.println("Maximum Value : " + myMax);

				if(picture.shiftFlag==8)
				{
					line_start=0;
					for(int v=0;v<slices;v++)
					{
						System.out.println("enter v :"+v);

						if(v!=(slices-1))
						{

				  		  for( i=0;i<Bands;i++)
						  {
							if(Bands==1 && i!=0)
							{
								continue;
							}

							//line_start=line_start2;
							line_start=v*y_win*Width;
							System.out.println("line start i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;

								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
								// transfer one line data into pix_rgb[][]
								pix_rgb[i][k_index]=pix_line[k];

								}
							}//close for

							System.out.println("i :"+i);


				    	}//close for

						//line_start2=line_start;
							System.out.println("enter ");
							System.out.println("fsize :"+fsize);

						if(Bands!=1)
						{
							for( i=0;i<fsize;i++)
							{
                                pix[i]=255;
				    		    if(pix_rgb[p-1][i]>0)
							    pix1[i]= (pix_rgb[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1])*255;
								else
								pix1[i]= (256+pix_rgb[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1])*255;

								if(pix_rgb[q-1][i]>0)
								pix2[i]= (pix_rgb[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1])*255;
								else
								pix2[i]= (256+pix_rgb[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1])*255;

								if(pix_rgb[r-1][i]>0)
								pix3[i]= (pix_rgb[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1])*255;
								else
								pix3[i]= (256+pix_rgb[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1])*255;


							}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("slice displayed");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							  for( i=0;i<fsize;i++)
							  {
							      pix[i]=255;
							      if(pix_rgb[0][i]>0)
							      pix1[i]= (pix_rgb[0][i]-minBand[0])/(maxBand[0]-minBand[0])*255;
							      else
							  	  pix1[i]= (256+pix_rgb[0][i]-minBand[0])/(maxBand[0]-minBand[0])*255;

							  }
							  pix2=pix3=pix1;

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
						 	raster.setSamples(0,origin_y,x_win,y_win,3,pix);

							System.out.println("slice displayed");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("v origin_y line_start  :"+v+" "+origin_y+" "+line_start);
						for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}
							line_start=v*y_win*Width;
							System.out.println("line start2 i :"+line_start+" "+i);
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of window

						    for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win);
								mBuf.get(pix_line);// store each line in separate buffer
								// i.e pix_line[]
								line_start=line_start+Width;
								// increment line_start by Width to point to next line
								// and leave rest of the pixels
								for(k=0;k<x_win;k++,k_index++)
								{
									// transfer one line data into pix_rgb[][]
									pix_rgb2[i][k_index]=pix_line[k];

								}

							}//close for
							System.out.println("i :"+i);
						}//close for

						System.out.println("enter ");
						System.out.println("fsize2 :"+fsize2);

						if(Bands!=1)
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

							    if(pix_rgb2[p-1][i]>0)
							    pix1_2[i]= (pix_rgb2[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1])*255;
							    else
							    pix1_2[i]= (256+pix_rgb2[p-1][i]-minBand[p-1])/(maxBand[p-1]-minBand[p-1])*255;

							    if(pix_rgb2[q-1][i]>0)
							    pix2_2[i]= (pix_rgb2[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1])*255;
								else
								pix2_2[i]= (256+pix_rgb2[q-1][i]-minBand[q-1])/(maxBand[q-1]-minBand[q-1])*255;

								if(pix_rgb2[r-1][i]>0)
								pix3_2[i]= (pix_rgb2[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1])*255;
								else
								pix3_2[i]= (256+pix_rgb2[r-1][i]-minBand[r-1])/(maxBand[r-1]-minBand[r-1])*255;
							}
						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}
						else
						{
							for( i=0;i<fsize2;i++)
							{
								pix_2[i]=255;

								if(pix_rgb2[0][i]>0)
							    pix1_2[i]= (pix_rgb2[0][i]-minBand[0])/(maxBand[0]-minBand[0])*255;
							    else
							    pix1_2[i]= (256+pix_rgb2[0][i]-minBand[0])/(maxBand[0]-minBand[0])*255;
							}
							pix3_2=pix2_2=pix1_2;

						    y_pass2=(Height-origin_y);//(num_of_slice-1));
							raster.setSamples(0,origin_y,x_win,y_pass2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_pass2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_pass2,3,pix_2);

						}

						System.gc();

					}//close if(v==num_of_slice-1)


				}//close for "v"

			}// close else if
			else
			{

			  /* the code for 16 bit bigger image */
				/*line_start=0;
			  	System.out.println("Total number of slices : " + slices);
				for(int v=0;v<slices;v++)
				{
					System.out.println("\nSlice Number v :"+v);

					if(v!=(slices-1))
					{

				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;
							// read the files line by line i.e x_win bytes in one go
							// and repeat unpo y_win i.e. size of windowx
							for(j=0;j<y_win;j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize;i++)
							{
				    		    pix[i]=255;

				    		    //for pix1
								if(pix_rgb[p-1][2*i]<0)
									z=256+pix_rgb[p-1][2*i];
								else
									z=pix_rgb[p-1][2*i];

								if(pix_rgb[p-1][2*i+1]<0)
									g=256+pix_rgb[p-1][2*i+1];
								else
									g=pix_rgb[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1[i] = p1 | p2;

								myTempCalc = ((double)pix1[i] / (double)myMax) * 255.0;
								pix1[i] = (int)myTempCalc;

							// for pix2
								if(pix_rgb[q-1][2*i]<0)
									z=256+pix_rgb[q-1][2*i];
								else
									z=pix_rgb[q-1][2*i];

								if(pix_rgb[q-1][2*i+1]<0)
									g=256+pix_rgb[q-1][2*i+1];
								else
									g=pix_rgb[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2[i] = p1 | p2;

								myTempCalc = ((double)pix2[i] / (double)myMax) * 255.0;
								pix2[i] = (int)myTempCalc;

							// for pix3

								if(pix_rgb[r-1][2*i]<0)
									z=256+pix_rgb[r-1][2*i];
								else
									z=pix_rgb[r-1][2*i];

								if(pix_rgb[r-1][2*i+1]<0)
									g=256+pix_rgb[r-1][2*i+1];
								else
									g=pix_rgb[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3[i] = p1 | p2;

								myTempCalc = ((double)pix3[i] / (double)myMax) * 255.0;
								pix3[i] = (int)myTempCalc;

				    		    line_start=line_start+Width*2;
				    		}

							raster.setSamples(0,origin_y,x_win,y_win,0,pix1);
							raster.setSamples(0,origin_y,x_win,y_win,1,pix2);
							raster.setSamples(0,origin_y,x_win,y_win,2,pix3);
				 			raster.setSamples(0,origin_y,x_win,y_win,3,pix);
				 			System.out.println("Main Slice : " + v);
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{
							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();

					}//close if(v!=num_of_slice-1)

					else if(v==(slices-1))
					{
						System.out.println("Last slice");
				  		for( i=0;i<Bands;i++)
						{
							if(Bands==1 && i!=0)
							{
								continue;
							}

							line_start=v*y_win*Width*2;
							System.out.println("line start i :"+line_start+" "+i);
							System.out.println("\n");
							int k_index=0;

							for(j=0;j<(Height-(slices-1)*y_win);j++)
							{
								mBuf=fchan_in[i].map(FileChannel.MapMode.READ_ONLY,line_start,x_win*2);
								mBuf.get(pix_line);// store each line in separate buffer
								for(k=0;k<x_win*2;k++,k_index++)
								{
									pix_rgb2[i][k_index]=pix_line[k];

								}

								line_start=line_start+(Width*2);

							}//close for



				    	}//close for


							System.out.println("enter ");
							System.out.println("fsize :"+fsize);
							double TempCalc;
							int myChkVal;

						System.out.println("Check value : p : " + p + ", q : " + q + ", r : " + r);
						if(Bands!=1)
						{
							line_start = 0;
							for( i=0;i<fsize2;i++)
							{

				    		    pix_2[i]=255;

				    		    //for pix1
								if(pix_rgb2[p-1][2*i]<0)
									z=256+pix_rgb2[p-1][2*i];
								else
									z=pix_rgb2[p-1][2*i];

								if(pix_rgb2[p-1][2*i+1]<0)
									g=256+pix_rgb2[p-1][2*i+1];
								else
									g=pix_rgb2[p-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix1_2[i] = p1 | p2;

								myTempCalc = ((double)pix1_2[i] / (double)myMax) * 255.0;
								pix1_2[i] = (int)myTempCalc;

								// for pix2
								if(pix_rgb2[q-1][2*i]<0)
									z=256+pix_rgb2[q-1][2*i];
								else
									z=pix_rgb2[q-1][2*i];

								if(pix_rgb2[q-1][2*i+1]<0)
									g=256+pix_rgb2[q-1][2*i+1];
								else
									g=pix_rgb2[q-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix2_2[i] = p1 | p2;

								myTempCalc = ((double)pix2_2[i] / (double)myMax) * 255.0;
								pix2_2[i] = (int)myTempCalc;

								// for pix3

								if(pix_rgb2[r-1][2*i]<0)
									z=256+pix_rgb2[r-1][2*i];
								else
									z=pix_rgb2[r-1][2*i];

								if(pix_rgb2[r-1][2*i+1]<0)
									g=256+pix_rgb2[r-1][2*i+1];
								else
									g=pix_rgb2[r-1][2*i+1];

								p1 = z << 8;
								p2 = g;
								pix3_2[i] = p1 | p2;

								myTempCalc = ((double)pix3_2[i] / (double)myMax) * 255.0;
								pix3_2[i] = (int)myTempCalc;

					    		    line_start=line_start+Width*2;
				    		}

				    		System.out.println("Displayed Image");

							raster.setSamples(0,origin_y,x_win,y_win2,0,pix1_2);
							raster.setSamples(0,origin_y,x_win,y_win2,1,pix2_2);
							raster.setSamples(0,origin_y,x_win,y_win2,2,pix3_2);
				 			raster.setSamples(0,origin_y,x_win,y_win2,3,pix_2);
				 			System.out.println("slice displayed1");
							origin_x=0;
							origin_y=origin_y+y_win;
							System.gc();

					    }
						else
						{

							System.out.println("slice displayed2");
						 	origin_x=0;
						 	origin_y=origin_y+y_win;

						}
						System.gc();




					}//close if(v==num_of_slice-1)

			  }	//end of slice v==-1


			}


			for( i=0;i<Bands;i++)
			{
				fin_band[i].close();
				fchan_in[i].close();
			}
		}
 }

            catch(Exception e)
            {
            	System.out.println(e);
            }

 			System.out.println("Hi");








    	System.out.println("Else Executed.");
    }*/

	System.out.println("Image Enhanced.");

  }


  public BufferedImage getpicdata()
  {
	  return img;
  }
}


 /* ***********************   Class For Saving Image Clicks     **********************************/


class imagetable6 extends JFrame implements ActionListener
{
  static Vector pixels=new Vector();
  static public double c[][];
  Double[]pixelsarray;
  double[]pixels_array;
  int len;
  static int row=0;
  int preclicks=0,saveclicks;
  static double cw;
  int[][]pixelstore;
  private ReadImageData imgdata8=new ReadImageData();
  int bands=imgdata8.bands();
  static Vector varienceValue=new Vector();  //for storing variances
  Double varienceArray[];
  static double varience_Array[];
  static Vector avarageValue=new Vector();   //for storing avarage vlues
  Double avarageArray[];
  static double avarage_Array[];
  static double old_avg[][];
  double pixel[][];
  double[][] varm=new double[bands][bands];
  double[] av=new double[bands];                                               		/**array to store final values of co-varience matrix**/
  double p_array[];
  static int state=2;
  int cls=0;
  int row1=0;
  int flag; 			                            /**for storing the returned values from ret_matrixArray of calculate class**/
  static DefaultTableModel model;
  private JTable table;
  JMenuItem openitem;
  JMenuBar menubar;
  int click;
  static File tablefile;
  TableDialog  tablemessage;
  tabledata td=new tabledata();
  DemoPanel pane=new DemoPanel();
  static int Bands;
  String columnname[]=new String[14];
  static double rowdat[]=new double[Bands];
  static ArrayList a1=new ArrayList();
  static ArrayList a2=new ArrayList();
  ArrayList a3=new ArrayList();
  static ArrayList a4=new ArrayList();
  calculate c1=new calculate();
  private addfiledata afd;

  public imagetable6()
  {
  }

  static int rownum=0;
  static Object data[]=new Object[20];
  static Object rowdata[]=new Object[20];
  static Object cname[]=new Object[20];  ;
  int siz;
  static int tablerows;


  JButton b=new JButton("Add");
  JButton b1=new JButton("Cal.Stat.");
  JButton b2=new JButton("Save Sig.");

  JTextField t1=new JTextField(2);
  JRadioButton  box1=new JRadioButton("D Matrix",false);
  JRadioButton  box2=new JRadioButton("V-C Matrix",true);
  JRadioButton  box3=new JRadioButton("D.V-C Matrix",false);

  public imagetable6(int n)
  {

    setTitle("Table For Fuzzy C-Mean's Signature File");
	setSize(500,400);

	addWindowListener(new WindowAdapter()
	{
	  public void WindowClosing(WindowEvent e)
	  {
	    System.exit(0);
	  }
	});

    JMenu fmenu=new JMenu("File");
	fmenu.setMnemonic(KeyEvent.VK_F);
	openitem=new JMenuItem("Open Sig.File");
	openitem.setMnemonic(KeyEvent.VK_O);
	openitem.addActionListener(this);
	fmenu.add(openitem);
	menubar= new JMenuBar();
	menubar.add(fmenu);
	setJMenuBar(menubar);
	afd=new addfiledata();
	this.tablerows=afd.retnewsize();
	System.out.println(tablerows);
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	b.addActionListener(this);
	b1.addActionListener(this);
      b2.addActionListener(this);


    box1.addActionListener(this);
    box2.addActionListener(this);
    box3.addActionListener(this);

    ButtonGroup bg=new ButtonGroup();
    bg.add(box1);
    bg.add(box2);
    bg.add(box3);


	int bands;
	model=new DefaultTableModel(10000,0);
	data[0]= new String("Class");
	this.Bands=imgdata8.bands();
	bands=imgdata8.bands();
    model.addColumn(data[0]);

	for(int i=1;i<=Bands;i++)
	{
	 	data[i]=new String("Band")+ new Integer(i);
	 	model.addColumn(data[i]);
	}

	table=new JTable(model);
	Container contentpane=getContentPane();
	p1.setSize(100,150);
	p1.add(new JScrollPane(table),"Center");
	contentpane.add(p1,"Center");
	p2.add(b);
	p2.add(b2);
    p2.add(b1);
    p2.add(box1);
    p2.add(t1);
    p2.add(box2);
    p2.add(box3);
    t1.setText("2");
	contentpane.add(p2,"South");
  }



    public  Object[] retclassname()
     {

          return cname;
     }


   public double[][]retoldavarage()
    {
     return old_avg;
    }

    public double[] retvarianceArray()			// function to return varience values
    {
  	return varience_Array;
    }

    public double[] retavarageArray()			// function to return avarage values
    {
  	return avarage_Array;
    }

  public void remove()                          // function to remove vector elements
   {
    int i=varienceValue.size();
    for(int j=0;j<i;j++)
       {
        Double t=new Double(varience_Array[j]);

        varienceValue.removeElement(t);

        }
  }


  public void remove1()                          // function to remove vector elements
  {
    int i=avarageValue.size();
    for(int j=0;j<i;j++)
       {
        Double t=new Double(avarage_Array[j]);

        avarageValue.removeElement(t);

        }
  }
 double pixeldata[];

 public void helpaccuracy(double[]pix)          //Function For Calculating Stat Again For Subpixel Acc assesment
     {
                          int bands=imgdata8.bands();


                         int pixlength=pix.length;
                         pixeldata=new double[pixlength];
                         int l=pixlength/bands;
                         double[][] pixel=new double[l][bands];

                         this.pixeldata=pix;
//**********here is the logic to devlop the varience-covariance matrix**************

 			    double[][]varm=new double[bands][bands];
                      double[]av=new double[bands];
                      int k11=0;
                      for(int i=0;i<l;i++)
                          for(int j=0;j<bands;j++)
                               {
                               pixel[i][j]=pixeldata[k11];
                               k11=k11+1;
                               }




                        for(int i=0;i<pixlength;i++)
                             pixeldata[i]=0;


int p1=pixel.length;
int p2=p1-1;

for(int i=0;i<pixel[0].length;i++)
           av[i]=0;
      for(int i=0;i<pixel[0].length;i++)
          for(int j=0;i<pixel[0].length;i++)
                 varm[i][j]=0;

       for(int j=0;j<pixel[0].length;j++)
         for(int i=0;i<pixel.length;i++)
             av[j]=av[j]+pixel[i][j];

        for(int i=0;i<pixel[0].length;i++)
              av[i]=av[i]/p1;


       for(int k=0;k<pixel[0].length;k++)
         for(int j=0;j<pixel[0].length;j++)
           for(int i=0;i<pixel.length;i++)
               varm[k][j]=varm[k][j]+((pixel[i][k]-av[k])*(pixel[i][j]-av[j]));

           for(int i=0;i<pixel[0].length;i++)
                   for(int j=0;j<pixel[0].length;j++)
               varm[i][j]=varm[i][j]/(p2*p1);
           System.out.println("avarage is:");
       for(int i=0;i<pixel[0].length;i++)
           {
           System.out.print(av[i]);
           System.out.print("      ");
           }
       System.out.println("  ");



     if(state==2)
        {
         for(int i=0;i<bands;i++)
             for(int j=0;j<bands;j++)
               {
                if(i==j)
                   varm[i][j]=cw;
                 else
                   varm[i][j]=0;
               }
        }

      if(state==3)
        {
         for(int i=0;i<bands;i++)
             for(int j=0;j<bands;j++)
               {
                if(i!=j)
                 varm[i][j]=0;
               }
        }



       System.out.println("varience is:");
       for(int i=0;i<pixel[0].length;i++)
       {
        System.out.println("  ");

        System.out.println("  ");

         for(int j=0;j<pixel[0].length;j++)
            {
            System.out.print(varm[i][j]);
            System.out.print("  ");
            }
        }

         c1.remove();   // for removing the vector elements




     for(int i=0;i<pixel[0].length;i++)
          {
         for(int j=0;j<pixel[0].length;j++)
	      {
	      Double temp=new Double(varm[i][j]);
	      varienceValue.addElement(temp);
             			        //adding elements to vector array
	      }
           }


         for(int i=0;i<pixel[0].length;i++)
           {
             Double temp=new Double(av[i]);
             avarageValue.addElement(temp);
            }


          int l1=varienceValue.size();
          varienceArray=new Double[l1];
          varience_Array=new double[l1];
          varienceValue.copyInto(varienceArray);

         for(int m=0;m<l1;m++)
           {

       	varience_Array[m]=varienceArray[m].doubleValue();     //converting double object to primitive double**/

           }

          int l2=avarageValue.size();
          avarageArray=new Double[l2];
          avarage_Array=new double[l2];
          avarageValue.copyInto(avarageArray);

	     for(int m=0;m<l2;m++)
           {

       	avarage_Array[m]=avarageArray[m].doubleValue();     //converting double object to primitive double**/

           }

           int l3=l2/bands;

           old_avg=new double[l3][bands];
           int k12=0;
		   for(int i=0;i<l3;i++)
		       for(int j=0;j<bands;j++)
		            {
		            old_avg[i][j]=avarage_Array[k12];
		            k12=k12+1;
                        }

          c1.remove();

               for(int i=0;i<pixel[0].length;i++)
                   for(int j=0;j<pixel[0].length;j++)   //for removing previous values
                      varm[i][j]=0;

              for(int i=0;i<pixel.length;i++)
                   for(int j=0;j<pixel[0].length;j++)
                         pixel[i][j]=0;

  }

 public double[][] retmat()
 {
	 return c;

 }

  int empty1=0;// // for storing avarage in table

  public void actionPerformed(ActionEvent event)
  {
    Object source=event.getSource();

       if(source==box1)
           {
            state=1;
           }

       if(source==box2)
           {
            state=2;
           }



       if(source==box3)
           {
            state=3;
           }


      if (source==b)
	  {
	  	int i,j;
	  	int col=1;
	  	double sum;
	  	System.out.println("preclicks="+preclicks);
	  	System.out.println("Noofclicks="+DemoPanel.Noofclicks);
		for( j=preclicks;j<DemoPanel.Noofclicks;j++)
			for(i=0;i<new ReadImageData().bands();i++)
				System.out.println("Click="+j+" Band= "+i+" Value= "+DemoPanel.val1[i][j]);

	    for(i=0;i<new ReadImageData().bands();i++)
		{
			sum=0;
			for( j=preclicks;j<DemoPanel.Noofclicks;j++)
				sum+=DemoPanel.val1[i][j];
			System.out.println("Sum of clicks of Band "+(i+1)+" : "+sum);
			model.setValueAt((double)sum/(DemoPanel.Noofclicks-preclicks),row,col++);

		}
		saveclicks=preclicks;
		preclicks=DemoPanel.clicks;
		DemoPanel.clicks=0;
		row++;

      }

      else if (source==b2)
	  {
        /*************************** new image storing in files *************************/
          int temp,temp1,temp2;
          byte b1,b2;
          int tt;
          File file=null;
		  JFileChooser chooser=new JFileChooser();
          int r=chooser.showSaveDialog(this);
          if(r==JFileChooser.APPROVE_OPTION)
	      {
	       	file = chooser.getSelectedFile();

    		 try
     		 {
  				String name=file.toString();
   				System.out.println(name);
   				File ff=new File(name);
	            FileOutputStream fout=new FileOutputStream(name);
				for( int j2=saveclicks;j2<DemoPanel.Noofclicks;j2++)
	            {
	                  for(int k1=0;k1<new ReadImageData().bands();k1++)
		              {

                            temp= (int)DemoPanel.val1[k1][j2];
                            temp1=temp>>>8;
                            fout.write(temp1);
                            fout.write(temp);
                      }
                }
                DemoPanel.clicks=0;
                DemoPanel.Noofclicks=0;
                preclicks=0;
                saveclicks=0;

	      		fout.close();
     		 }
     		 catch(IOException e)
             {
                System.out.println("Exception is :"+ e);
             }
		  }

	  }


	  else if (source==b1)
      {

		     if(flag==0)
		     {
		  		len=len-1;
		  		flag=1;
		   	 }
	         int bands=imgdata8.bands();
	         int l=len/bands;
	         double[][] pixel=new double[bands][l];
		   	 c= new double[bands][bands];
			 double a[][]=new double[bands][l];
			 double b[]=new double[bands];

			 double sum;

			 System.out.println("Image Bands : "+bands);
			 System.out.println("No of Clicks : "+l);
			 for(int j=0;j<l;j++)
			 	for(int i=0;i<bands;i++)
			 		a[i][j]=pixels_array[j*bands+i];

			for(int i=0;i<bands;i++)
			{
				sum=0;
				for(int j=0;j<l;j++)
					sum+=a[i][j];
				b[i]=sum/l;
			}

			for(int i=0;i<bands;i++)
			{
				for(int j=0;j<bands;j++)
				{
					sum=0;
					for(int k=0;k<l;k++)
					{
						sum=sum+((a[j][k]-b[j])*(a[i][k]-b[i]));
					}
					c[i][j]=sum/(l-1);
				}
			}


			if(state==1)
			{
				System.out.println("The Unit Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						if(i==j)
							System.out.print("\t"+t1.getText());
						else
							System.out.print("\t"+0);
					}
				}
			}
			if(state==2)
			{
				System.out.println("Varience Co-Varience Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						System.out.print("\t"+c[i][j]);
					}
				}
			}
			if(state==3)
			{
				System.out.println("Diagonal Varience Co-Varience Matrix is =");
				for(int i=0;i<bands;i++)
				{
					System.out.println();
					for(int j=0;j<bands;j++)
					{
						if(i==j)
							System.out.print("\t"+c[i][j]);
						else
							System.out.print("\t"+0);
					}
				}
			}
			System.out.println();
			for(int i=0;i<bands;i++)
				 model.setValueAt(b[i],row,i+1);
				row++;

      }

	////////////////// To open a Signature File. ///////////////////////////

	 else if(source==openitem)
	 {

		int b,c1;
        JFileChooser chooser=new JFileChooser();
        int r=chooser.showOpenDialog(this);


        if(r==JFileChooser.APPROVE_OPTION)
		{
			 File file = chooser.getSelectedFile();
		     String name=file.getName();

			 System.gc();
			 validate();

             try
    		 {
                  FileInputStream f1=new FileInputStream(file);




                  try
                  {
                   		int c=1;
						while(c!=-1)
						{

					        b=f1.read();
					        if(b==-1)
					         break;
                            b=b<<8;
                            c1=f1.read();
                            Double temp=new Double(b|c1);
                            pixels.addElement(temp);


                        }

                   }
                   catch(Exception e)
                   {
                   		System.out.println("Hellow Uma"+e);
                   }
				   try
				   {
	                   f1.close();
					   System.gc();
				   }
				   catch(IOException e)
				   {
				   		System.out.println("Hellow Uma"+e);
				   }
             }
             catch(Exception e)
             {
             	System.out.println("Hellow Tyagi"+e);
             }

             len=pixels.size();
             pixelsarray=new Double[len];
             pixels.copyInto(pixelsarray);
             //len=len-1;
             pixels_array=new double[len];

	         for(int m=0;m<len;m++)
	         {

	       		pixels_array[m]=pixelsarray[m].doubleValue();     //converting double object to primitive double**/
	       		System.out.println(pixels_array[m]);
	         }


	      	for(int j=0;j<len;j++)
	        {
	       		 Double t=new Double(pixels_array[j]);               // for removing pixel values
	        	 pixels.removeElement(t);
	        }

	    }
	    flag=0;

     }

  }
}




/*********************************** Class AddFileData **********************************************/


 class addfiledata
{
       ArrayList a4=new ArrayList();
	   ArrayList a5=new ArrayList();
	   ArrayList a6=new ArrayList();
	   ArrayList a7=new ArrayList();
	   ArrayList a8=new ArrayList();
	   static File tablefile;
	   static int tablesize;
	   static ArrayList filedata=new ArrayList();
	   public addfiledata()
	   {
	   }
	   public addfiledata(File file)
	   {
	     this.tablefile=file;
         try
		  {
		    BufferedReader in=new BufferedReader(new FileReader(file));
			String s;
		    while((s=in.readLine())!=null)
		     {
    		    StringTokenizer t=new StringTokenizer(s,"\t");
			    a4.add(a4.size(),t.nextToken());
				a5.add(a5.size(),t.nextToken());
				a6.add(a6.size(),t.nextToken());
				a7.add(a7.size(),t.nextToken());
				a8.add(a8.size(),t.nextToken());
		    }
			in.close();
		} catch(IOException e)
			 {
				System.out.println("ERROR" +e);
				System.exit(1);
             }
			 this.tablesize=a4.size();
			 for(int i=1;i<a4.size();i++)
			 {
			   filedata.add(filedata.size(),a4.get(i));
			   filedata.add(filedata.size(),a5.get(i));
			   filedata.add(filedata.size(),a6.get(i));
			   filedata.add(filedata.size(),a7.get(i));
			   filedata.add(filedata.size(),a8.get(i));
              }
    }
	public int retnewsize()
	{
	  return tablesize;
    }
	public ArrayList retfiledata()
	{
	   return filedata;
    }
}

 /********************************** Table Message *********************************************/



class TableDialog extends JFrame
{
		 JFrame frame;
		 public TableDialog(){}
         public TableDialog(boolean val) {
         this.frame = frame;
         JOptionPane.showMessageDialog(frame,"You should enter the Name of place.");
		 }
}


/****************** This Class Contains The Data to be added to The Table**********************/


class  tabledata
{
    private calculate c1=new calculate();
    static int Bands;
	static double rowdata1[]=new double[Bands];
	private ReadImageData imgdata9=new ReadImageData();
    static double rowdata2[]=new double[Bands];
	static double temp[]=new double[20];

	public tabledata()
	{
	this.Bands=imgdata9.bands();
	double rowdata3[]=new double[Bands];
	this.rowdata2=rowdata3;
	this.rowdata1=rowdata2;
    }

	public double[] rettabledata()
	{
	   this.rowdata1=c1.retbandsdata();
	   for(int i=0;i<Bands;i++)
	   {
	    this.rowdata2[i]=this.rowdata1[i]-this.temp[i];
		this.temp[i]=this.rowdata1[i];
       }
       return rowdata2;
    }
}



///////////////************** Class for Classing The Pixels Of Image ****************///////////////////



class Fcm extends JFrame implements ActionListener
{
  static Vector pixels=new Vector();
  double x[];
  static double xnew[][];

  float m,del,lem,neo,beta,gama;
  double sub[][];
  double subT[][];
  public double c[][][];
  double sub1[][];
  double subT1[][];
  double sub2[][];
  double subT2[][];
  double prob[];

  double meu[][];
  double meun[][];

  double meunm[][];
  Double[]pixelsarray;
  double[]pixels_array;
  int len;
  int row=0;
  int count=0;
  int preclicks=0,saveclicks;
  static double cw;
  int[][]pixelstore;
  private ReadImageData imgdata8=new ReadImageData();
  int bands=imgdata8.bands();
  static Vector varienceValue=new Vector();  //for storing variances
  Double varienceArray[];
  static double varience_Array[];
  static Vector avarageValue=new Vector();   //for storing avarage vlues
  Double avarageArray[];
  static double avarage_Array[];
  static double old_avg[][];
  double pixel[][];
  double[][] varm=new double[bands][bands];
  double[] av=new double[bands];                                               		/**array to store final values of co-varience matrix**/
  double p_array[];
  static int state=1;
  static int typ=1;
  static int st=2;
  static int classi=1;
  static int advclassi=0;
  static int context=0;
  static int sa=0;
  int cls=0;
  int row1=0;
  int flag; 			                            /**for storing the returned values from ret_matrixArray of calculate class**/
  private DefaultTableModel model;
  private JTable table;
  JMenuItem openitem;
  JMenuItem openhashitem;
  JMenuItem openkitem[]=new JMenuItem[9];
  JMenuItem openk2item[]=new JMenuItem[9];
  JMenu sel_kernel=null;
  JMenu sel_2kernel=null;

  String kernels[] = {"Linear","Polynomial","Hypertangent","Gaussian","Radial","KMOD","Multiquadratic","Sigmoid","Spectral"};
  double mv_kernels[]={1.16, 2.0, 1.20, 1.01, 2.1, 1.1, 1.05};
  JMenuBar menubar;
  int click;
  static File tablefile;
  TableDialog  tablemessage;
  tabledata td=new tabledata();
  DemoPanel pane=new DemoPanel();
  static int Bands;
  String columnname[]=new String[14];
  static double rowdat[]=new double[Bands];
  static ArrayList a1=new ArrayList();
  static ArrayList a2=new ArrayList();
  ArrayList a3=new ArrayList();
  static ArrayList a4=new ArrayList();
  calculate c1=new calculate();
  private addfiledata afd;

  static int rownum=0;
  static Object data[]=new Object[20];
  static Object rowdata[]=new Object[20];
  static Object cname[]=new Object[20];
  double mean[][];
  double mul[][];
  double mul1[][];
  double mul2[][];

  int siz;
  static int tablerows;

  JButton btClasi=new JButton("Classify");
   JButton btsyn=new JButton("Synthetic Image");

  Font f=new Font("Arial",Font.BOLD,18);
  Font ff=new Font("Arial",Font.BOLD,12);
JRadioButton  box1=new JRadioButton("Eucledian  ",true);
//JRadioButton  box1=new JRadioButton("D Matrix  ",false);
//JRadioButton  box2=new JRadioButton("V-C Matrix",true);
//JRadioButton  box3=new JRadioButton("D.V-C Matrix",false);
  JRadioButton  box2=new JRadioButton("Type I  ",true);
  JRadioButton  box3=new JRadioButton("Type II",false);
  JRadioButton  box4=new JRadioButton("Hard Classifier",false);
  JRadioButton box5=new JRadioButton("Save Dist.",false);
  JRadioButton  fcm=new JRadioButton("Kernel Fuzzy C-Means",true);
  JRadioButton  fcms=new JRadioButton("Kernel Fuzzy C-Means S",false);
  JRadioButton  noiseOut=new JRadioButton("Noise Clustering Without Entropy",false);
  JRadioButton  noiseWith=new JRadioButton("Noise Clustering With Entropy",false);
  JRadioButton  entropy=new JRadioButton("KFCM With Entropy",false);
  JRadioButton  pcm=new JRadioButton("Kernel Possibilistic C-Means",false);
  JRadioButton  mpcm=new JRadioButton("Kernel Modified Possibilistic C-Means",false);
  JRadioButton  ipcm=new JRadioButton("Kernel Improved Possibilistic C-Means",false);
  JRadioButton  mlc=new JRadioButton("Kernel Maximum Likelihood Classifier",false);
  JRadioButton  pcms=new JRadioButton("Kernel Possibilistic C-Means S",false);


  JRadioButton  flicm=new JRadioButton("Kernel FLICM",false);
  JRadioButton  adflicm=new JRadioButton("Kernel ADFLICM",false);
   JRadioButton  plicm=new JRadioButton("Kernel PLICM",false);

  JRadioButton con=new JRadioButton("Smoothing Prior");
  JRadioButton dis=new JRadioButton("Discontinuity Adaptive Prior");
  JRadioButton h1=new JRadioButton("H1",false);
  JRadioButton h2=new JRadioButton("H2",true);
  JRadioButton h3=new JRadioButton("H3",false);
  JRadioButton h4=new JRadioButton("H4",false);

  JLabel l1=new JLabel("m >1   ");
  JLabel l2=new JLabel("Select A BASE Classifier --");
  JLabel ll2=new JLabel("Select the ADVANCED Classifier");
  JLabel l3=new JLabel("Select One Contextual --");
  JLabel sp=new JLabel("");
  JLabel l4=new JLabel((char)948+">0   ");
  JLabel l5=new JLabel("1>="+(char)955+">=0   ");
  JLabel l6=new JLabel((char)957+">0   ");
  JLabel l7=new JLabel((char)946+">0   ");
  JLabel l8=new JLabel("1>="+(char)947+">=0   ");
  JLabel l9=new JLabel("Distance used here is --");
  JLabel l12=new JLabel("Select A Type --");
  JLabel l10=new JLabel();
  JLabel l11=new JLabel();
  JLabel l13=new JLabel();
  JLabel lbl=new JLabel("Select in case of Adaptive prior --");
JLabel hfree=new JLabel();
JLabel hmeu=new JLabel("0< Threshhold meu <1");
  //JTextField tfd=new JTextField(2);
  JTextField tfm=new JTextField(2);
  JTextField tfdel=new JTextField(2);
  JTextField tflem=new JTextField(2);
  JTextField tfneo=new JTextField(2);
  JTextField tfbeta=new JTextField(2);
  JTextField tfgama=new JTextField(2);
   JTextField tfhard=new JTextField("0",2);
  int b;
  int h;
  int w;
  int flago=1;
  int j1=0;
  int val=0;
  double temp=0.0,energy=0.0,energy1=0.0,energy2=0.0;
  double temp1=0.0,temp2=0.0,temp3=0.0;
  double div=0.0;
  double dsq[][];
  double dsq_temp[][];
  double dsq1[][];
  double dsq2[][];
  double dsq3[][];


  double m1,m2;
  double mix_m;
  int flag_second=1;
  String txt;
  JCheckBox cb;
  JTextField meu_mix;
  JTextField poly_degree=new JTextField();
  ButtonGroup bg;

  public Fcm()
  {

    setTitle("Classifier For Kernel Fuzzy C-Means");
	setSize(650,500);
	addWindowListener(new WindowAdapter()
	{
	  public void WindowClosing(WindowEvent e)
	  {
	    System.exit(0);
	  }
	});

        JMenu fmenu=new JMenu("File");
	fmenu.setMnemonic(KeyEvent.VK_F);
	openitem=new JMenuItem("Open Sig.File");
	openitem.setMnemonic(KeyEvent.VK_O);
	openitem.addActionListener(this);
	fmenu.add(openitem);
	openhashitem=new JMenuItem("Open Hash File");
		openhashitem.setMnemonic(KeyEvent.VK_O);
		openhashitem.addActionListener(this);
	fmenu.add(openhashitem);

	/*kernel options*/
	        JMenu kernel1=new JMenu("Kernel-1");
	        sel_kernel=new JMenu("None");
	        JMenu kernel2=new JMenu("Kernel-2");
	        sel_2kernel=new JMenu("None");
	         cb= new JCheckBox();
	        JLabel meu_lb=new JLabel("      "+(char)955+ "  for mixture: ");
	        JLabel poly_degree_lb=new JLabel("p: ");
	        JLabel t1_lb=new JLabel("                      ");

	        JLabel t2_lb=new JLabel("    ");
	        JLabel t3_lb=new JLabel();
	         meu_mix=new JTextField("");

	        for(int i=0;i<9;i++)
	        {
	            openkitem[i]=new JMenuItem(kernels[i]);
	            kernel1.add(openkitem[i]);
	            openkitem[i].addActionListener(this);
	        }
	        for(int i=0;i<9;i++)
	        {
	            openk2item[i]=new JMenuItem(kernels[i]);
	            kernel2.add(openk2item[i]);
	            openk2item[i].addActionListener(this);
	        }

	menubar= new JMenuBar();
	menubar.add(fmenu);
	menubar.add(kernel1);
    menubar.add(sel_kernel);
    menubar.add(kernel2);
    menubar.add(sel_2kernel);

    menubar.add(t2_lb);
    menubar.add(cb);
    menubar.add(meu_lb);

    menubar.add(meu_mix);
    menubar.add(t1_lb);
    menubar.add(poly_degree_lb);
    menubar.add(poly_degree);

	setJMenuBar(menubar);
	afd=new addfiledata();
	this.tablerows=afd.retnewsize();

	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3=new JPanel();
	JPanel p4=new JPanel();
	JPanel p5=new JPanel();
	JPanel p6=new JPanel();
	JPanel p7=new JPanel();
	JPanel p8=new JPanel();
	JPanel p9=new JPanel();
	JPanel p10=new JPanel();

	JPanel p11=new JPanel();

     bg=new ButtonGroup();
    ButtonGroup bg1=new ButtonGroup();
	ButtonGroup bg2=new ButtonGroup();
	ButtonGroup bg3=new ButtonGroup();
	ButtonGroup bg4=new ButtonGroup();
        ButtonGroup bg5=new ButtonGroup();

          bg.add(box1);
         bg4.add(box2);
             bg4.add(box3);
          bg4.add(box4);
        box1.addActionListener(this);
	box2.addActionListener(this);
   	box3.addActionListener(this);
	box4.addActionListener(this);
	box5.addActionListener(this);
	bg1.add(fcm);
        bg1.add(fcms);
	bg1.add(noiseOut);
	bg1.add(noiseWith);
	bg1.add(entropy);
	bg1.add(pcm);
        bg1.add(mpcm);
        bg1.add(ipcm);
        bg1.add(mlc);
        bg1.add(pcms);

        bg5.add(flicm);
        bg5.add(adflicm);
        bg5.add(plicm);

	bg2.add(con);
	bg2.add(dis);

	bg3.add(h1);
	bg3.add(h2);
	bg3.add(h3);
	bg3.add(h4);

	box1.addActionListener(this);
   	box2.addActionListener(this);
   	box3.addActionListener(this);
   		box4.addActionListener(this);
   		box5.addActionListener(this);
    h1.addActionListener(this);
	h2.addActionListener(this);
	h3.addActionListener(this);
	h4.addActionListener(this);
	fcm.addActionListener(this);
    noiseOut.addActionListener(this);
    noiseWith.addActionListener(this);
    entropy.addActionListener(this);
    pcm.addActionListener(this);
    mpcm.addActionListener(this);
    ipcm.addActionListener(this);
    mlc.addActionListener(this);
    fcms.addActionListener(this);
    pcms.addActionListener(this);

    flicm.addActionListener(this);
    adflicm.addActionListener(this);
    plicm.addActionListener(this);
    btClasi.addActionListener(this);
 btsyn.addActionListener(this);

	con.addActionListener(this);
	dis.addActionListener(this);

	int bands;
	model=new DefaultTableModel(40,0);
	data[0]= new String("Class");
	this.Bands=imgdata8.bands();
	bands=imgdata8.bands();
    model.addColumn(data[0]);
	for(int i=1;i<=Bands;i++)
	{
	 data[i]=new String("Band")+ new Integer(i);
	 model.addColumn(data[i]);
	}
	table=new JTable(model);
	Container contentpane=getContentPane();
	p1.setSize(1000,1000);
	p1.add(new JScrollPane(table),"Center");
	contentpane.add(p1,"West");

    p2.add(tfm);
    p2.add(l1);
    p2.add(tfdel);
    p2.add(l4);
    p2.add(tflem);
    p2.add(l5);
    p2.add(tfneo);
    p2.add(l6);
    p2.add(tfbeta);
    p2.add(l7);
    p2.add(tfgama);
    p2.add(l8);
    p2.add(btClasi);
      p2.add(btsyn);

    p2.add(box5);

    p3.add(l2,"North");
    p3.add(sp);
    p3.add(fcm);

    p3.add(fcms);
    p3.add(noiseOut);
    p3.add(noiseWith);
    p3.add(entropy);
    p3.add(pcm);
    p3.add(mpcm);
    p3.add(ipcm);
    p3.add(mlc);
    p3.add(pcms);

    p3.add(sp);

    p3.add(ll2,"South");
     p3.add(sp);

    p3.add(flicm);
    p3.add(adflicm);
    p3.add(plicm);

    p4.add(l11);
    p4.add(l3);
    p4.add(con);
    p4.add(dis);

    p4.add(lbl);
    p4.add(h1,"East");
    p4.add(h2,"East");
    p4.add(h3,"East");
    p4.add(h4,"East");


	p7.add(l9);
    p7.add(box1);


    p8.add(l12);

    p8.add(box2,"East");
     p8.add(l13);

    p8.add(box3,"East");
     p8.add(l13);
    p8.add(box4,"East");
    p8.add(hfree);
    p8.add(hmeu);
    p8.add(tfhard);
    p8.add(l13);

    //p8.add(p9);

	//tfd.setText("2");
    tfm.setText("2.1");
    tfdel.setText("2.5");
    tflem.setText("0.7");
    tfneo.setText("2.0");
    tfbeta.setText("3.0");
    tfgama.setText("0.3");
    tfhard.setEnabled(false);
 p3.setSize(200,200);
	p2.setLayout(new FlowLayout());

	p3.setLayout(new GridLayout(10,1));

        p4.setLayout(new GridLayout(10,1));
	p5.setLayout(new GridLayout(3,1));
	p6.setLayout(new GridLayout(2,1));
	p7.setLayout(new GridLayout(2,1));
	p8.setLayout(new GridLayout(2,1));
	p9.setLayout(new GridLayout(1,3));
	p10.setLayout(new GridLayout(4,1));
        p11.setLayout(new GridLayout(10,50));

	p5.add(p3,"East");
	//p5.add(p11,"South");

        p5.add(p4,"West");
        p5.add(p6,"West");
	p6.add(p7);
	p6.add(p8);

	contentpane.add(p2,"South");
	contentpane.add(p5,"East");

	l1.setFont(f);
	l4.setFont(f);
	l5.setFont(f);
	l6.setFont(f);
	l7.setFont(f);
	l8.setFont(f);
hmeu.setFont(ff);

 	bands=new ReadImageData().bands();
    c= new double[10][bands][bands];

  }
  public double determinant(double a[][],int n)
{
	double det = 0;
	if(n==1)
	det=a[0][0];
	else if(n==2)
	det=a[0][0]*a[1][1]-a[1][0]*a[0][1];
	else
	{
		det=0;
		for(int j1=0;j1<n;j1++)
		{
			double[][] m = new double[n-1][];
			for(int k=0;k<(n-1);k++)
			{
				m[k]=new double[n-1];

			for(int i=1;i<n;i++)
			{
				int j2=0;
				for(int j=0;j<n;j++)
				{
					if(j==j1)
						continue;

					m[i-1][j2]=a[i][j];
					j2++;
				}
			}
			det +=Math.pow(-1.0,1.0+j1+1.0)*a[0][j1]*determinant(m,n-1);
		}
	}
       }
	return det;


}


public void kernel_mixture(Object source)
{
    double meu_mix_db=0;
       if(flag_second==0)
       meu_mix_db=Double.parseDouble(meu_mix.getText());
		/*if(source==box1)
           {
            state=1;
           }

         if(source==box2)
           {
            state=2;
           }

       if(source==box3)
           {
            state=3;
           }
           if(source==box5)
		              {
		               sa=1;
           }


       if(source==fcm)
           {
            classi=1;
           }

       if(source==noiseOut)
           {
            classi=2;
           }

       if(source==noiseWith)
           {
            classi=3;
           }

       if(source==entropy)
           {
            classi=4;
           }

       if(source==pcm)
           {
            classi=5;
           }

       if(source==con)
           {
            context=1;
           }

       if(source==dis)
           {
            context=2;
           }
       if(source==h1)
          {
          	st=1;
          }
       if(source==h2)
          {
          	st=2;
          }
       if(source==h3)
          {
          	st=3;
          }
       if(source==h4)
          {
          	st=4;
          }*/
	   if (source==btClasi || source==btsyn)
           {
       		b= new ReadImageData().bands();
    		h= new ReadImageData().rows();
    		w= new ReadImageData().columns();
			meu=new double[count+1][h*w];
                         meun=new double[b][h*w];

			meunm=new double[count+1][h*w];
			x=new double[b];
                         xnew=new double[b][h*w];


			dsq=new double[count][h*w];
 		    sub=new double[b][1];
 		    subT=new double[1][b];

			dsq1=new double[count][h*w];
			sub1=new double[b][1];
 		    subT1=new double[1][b];

 		    dsq2=new double[count][h*w];
			sub2=new double[b][1];
 		    subT2=new double[1][b];

 		    dsq3=new double[count][h*w];

  		    FileInputStream fin[]=new FileInputStream[b];
  		    FileChannel fchan[]=new FileChannel[b];
  		    mean=new double[count][b];

  		    mul=new double[1][b];
			mul1=new double[1][b];
  		    mul2=new double[1][b];

  		    MappedByteBuffer mBuf[]=new MappedByteBuffer[b];
  		    double c1[][][]=new double[count][b][b];

                    if(source== btClasi)
                    {
                        System.out.println("Selected Classifier is : "+classi);
  		    System.out.println("Selected Contextual is : "+context);
  		    System.out.println("Selected Matrix is : "+state);

                    }
                      else if(source==btsyn)
                   {
                       count=1;
                       classi=12;
                       txt="dead";
                   }

  		    if(sa==1)
			 System.out.println(" Only distance is saved ");
			 else
			  System.out.println("Distance is not saved");


  		    m=Float.parseFloat(tfm.getText());
  		    del=Float.parseFloat(tfdel.getText());
  		    lem=Float.parseFloat(tflem.getText());
  		    neo=Float.parseFloat(tfneo.getText());
  		    beta=Float.parseFloat(tfbeta.getText());
  		    gama=Float.parseFloat(tfgama.getText());

  		    try
  		    {
  		      for(int i=0;i<b;i++)
  		      {
  		      	 fin[i]=new FileInputStream("Data" + BilImage.name + i);
  		      	 fchan[i]= fin[i].getChannel();
  		      	 if(picture.shiftFlag!=8)
  		      	 mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w*2));
  		      	 else
  		      	 mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w));
  		      }

  		     // for(int i=0;i<b;i++)
  		     // System.out.println("mybuf:"+mBuf[i]);
  			}
  			catch(Exception e)
  			{
  				System.out.println("file Error" +e);
  			}




  		    // calculating mean vector Array.

  		   for(int j=0;j<count;j++)
              for(int i=0;i<b;i++)
              {
                 if(source==btClasi)
                    mean[j][i]=(Double)(model.getValueAt(j,i+1));

              }

			if(txt.compareTo("Linear")==0||txt.compareTo("Polynomial")==0||txt.compareTo("Hypertangent")==0||txt.compareTo("Gaussian")==0||
				txt.compareTo("Radial")==0||txt.compareTo("KMOD")==0||txt.compareTo("Multiquadratic")==0||txt.compareTo("Sigmoid")==0||txt.compareTo("Spectral")==0)
             {
             	for(int i=0;i<count;i++)
             	    for(int j=0;j<b;j++)
             	      for(int k=0;k<b;k++)
             	        if(j==k)
             	         c1[i][j][k]=1;
             	         else
             	         c1[i][j][k]=0;
             }


           //Calculating distance vector


double add=0;
           double max_xk[]=new double[b];
           double min_xk[]= new double[b];
           for (int i=0;i<b;i++)
           {
				 max_xk[i]=0;
				 min_xk[i]=10000000;//it can be at max 255 but still precautions have been taken
		}
           //Calculating distance vector.
				try{

			  for(int l=0;l<count;l++)
			  {


		    	for(int i=0;i<h*w;i++)
	  		    {
	  		    	for(int j=0;j<b;j++)
	  		    	{
	  		    		   if(picture.shiftFlag!=8)
	  		    		   {
	    				        byte b5=mBuf[j].get();
						 		byte b1=mBuf[j].get();
						 		int  p1 = b5 << 24;
								int  p2 = (b1 <<24 ) >>> 8;
								int	tempCount = p1 | p2;
								tempCount = tempCount >>> 16;
								x[j]=(double)tempCount;
							}
							else
							{
							  	x[j] = (mBuf[j].get() << 24) >>> 24;
							}
                                            xnew[j][i]=x[j];

	    			}



	    			for(int k=0;k<b;k++){
					if(x[k]<min_xk[k])
						min_xk[k]=x[k];

					if(x[k]>max_xk[k])
						max_xk[k]=x[k];


				}
			}
		}
	}
	catch(Exception e){
			System.out.println("AWT exception only");
		}
           try
			{
			  for(int l=0;l<count;l++)
			  {
			  	for(int i=0;i<b;i++)
			  		if(picture.shiftFlag!=8)
  		      	 		mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w*2));
  		      	    else
  		      	 		mBuf[i]=fchan[i].map(FileChannel.MapMode.READ_ONLY,0,(h*w));

		    	for(int i=0;i<h*w;i++)
	  		    {
	  		    	for(int j=0;j<b;j++)
	  		    	{
	  		    		   if(picture.shiftFlag!=8)
	  		    		   {
	    				        byte b5=mBuf[j].get();
						 		byte b1=mBuf[j].get();
						 		int  p1 = b5 << 24;
								int  p2 = (b1 <<24 ) >>> 8;
								int	tempCount = p1 | p2;
								tempCount = tempCount >>> 16;
								x[j]=(double)tempCount;
							}
							else
							{
							  	x[j] = (mBuf[j].get() << 24) >>> 24;
							}
	    			}


		//1.LINEAR KERNEL
						if(txt.compareTo("Linear")==0)
						{


						dsq[l][i]=0;
						for(int k=0;k<b;k++)
							dsq[l][i]+=(x[k]*x[k])+(mean[l][k]*mean[l][k])-2*(x[k]*mean[l][k]);

					/****Mixing second kernel*/

                                   if(flag_second==0)
                                    dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/
					}

			//2.POLYNOMIAL KERNEL
					else if(txt.compareTo("Polynomial")==0)
					{

					int p=3;
					 String s=poly_degree.getText();
					      if(s.compareTo("")==1)
					         p=Integer.parseInt(s);
						dsq1[l][i]=0;
										dsq2[l][i]=0;
										dsq3[l][i]=0;
										for(int k=0;k<b;k++){


											double xk=x[k];


											dsq1[l][i]+=xk*xk;

										}
											dsq1[l][i]=(dsq1[l][i]+1);





										for(int k=0;k<b;k++){

												double pk=mean[l][k];



											dsq2[l][i]+=pk*pk;


											}
						                     	dsq2[l][i]=(dsq2[l][i]+1);


										for(int k=0;k<b;k++){
											double pk=mean[l][k];
												double xk=x[k];



											dsq3[l][i]+= (pk*xk);
										}
							dsq3[l][i]=(dsq3[l][i]+1);

										dsq[l][i]=0;

											dsq[l][i]=Math.pow(dsq1[l][i],p)+Math.pow(dsq2[l][i],p)-2*Math.pow(dsq3[l][i],p);


					/****Mixing second kernel*/

                                    if(flag_second==0)
                                      dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/
				}


			//3.HYPERTANGENT KERNEL

				else if(txt.compareTo("Hypertangent")==0)
				{

						//calculating dsq1
								for(int k=0;k<b;k++)
										 sub1[k][0]= x[k]-x[k];

										for(int k=0;k<b;k++)
										 subT1[0][k]=sub1[k][0];

										for(int j=0;j<b;j++)
										{
							                mul1[0][j]=0;
											for(int k=0;k<b;k++)
											 mul1[0][j]+=(subT1[0][k]*c1[l][k][j]);
										}

										dsq1[l][i]=0;
										for(int k=0;k<b;k++)
					                 dsq1[l][i]+=mul1[0][k]*sub1[k][0];


						//calculating dsq2
					                 for(int k=0;k<b;k++)
										 sub2[k][0]= mean[l][k]-mean[l][k];

										for(int k=0;k<b;k++)
										 subT2[0][k]=sub2[k][0];

										for(int j=0;j<b;j++)
										{
							                mul2[0][j]=0;
											for(int k=0;k<b;k++)
											 mul2[0][j]+=(subT2[0][k]*c1[l][k][j]);
										}

										dsq2[l][i]=0;
										for(int k=0;k<b;k++)
					                  dsq2[l][i]+=mul2[0][k]*sub2[k][0];


						//calculating dsq3
					                 for(int k=0;k<b;k++)

										 sub[k][0]= ((x[k]-min_xk[k])-(mean[l][k]-min_xk[k]))/(max_xk[k]-min_xk[k]);

										for(int k=0;k<b;k++)
										 subT[0][k]=sub[k][0];

										for(int j=0;j<b;j++)
										{
							                mul[0][j]=0;
											for(int k=0;k<b;k++)
											 mul[0][j]+=(subT[0][k]*c1[l][k][j]);
										}

										dsq3[l][i]=0;
										for(int k=0;k<b;k++)
					                  		dsq3[l][i]+=mul[0][k]*sub[k][0];





					dsq[l][i]=0;

					dsq[l][i]=((1- Math.tanh(-dsq1[l][i]))+(1- Math.tanh(-dsq2[l][i]))-(2*(1- Math.tanh(-dsq3[l][i]))));
					                        if(dsq[l][i]<=0)

							                  {
												System.out.println(-dsq[l][i]);
												dsq[l][i] = -dsq[l][i];

											   }
											  else
											  {
											    System.out.println(dsq[l][i]);
						                      }
					/****Mixing second kernel*/

                                    if(flag_second==0)
                                     	dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/

			}

				//4.GAUSSIAN KERNEL
				else if(txt.compareTo("Gaussian")==0)
				{

					//calculating dsq1
								for(int k=0;k<b;k++)
										 sub1[k][0]= x[k]-x[k];

										for(int k=0;k<b;k++)
										 subT1[0][k]=sub1[k][0];

										for(int j=0;j<b;j++)
										{
							                mul1[0][j]=0;
											for(int k=0;k<b;k++)
											 mul1[0][j]+=(subT1[0][k]*c1[l][k][j]);
										}

										dsq1[l][i]=0;
										for(int k=0;k<b;k++)
					                 dsq1[l][i]+=mul1[0][k]*sub1[k][0];

				//calculating dsq2

					                 for(int k=0;k<b;k++)
										 sub2[k][0]= mean[l][k]-mean[l][k];

										for(int k=0;k<b;k++)
										 subT2[0][k]=sub2[k][0];

										for(int j=0;j<b;j++)
										{
							                mul2[0][j]=0;
											for(int k=0;k<b;k++)
											 mul2[0][j]+=(subT2[0][k]*c1[l][k][j]);
										}

										dsq2[l][i]=0;
										for(int k=0;k<b;k++)
					                  dsq2[l][i]+=mul2[0][k]*sub2[k][0];


				//calculating dsq3
					                 for(int k=0;k<b;k++)
										 sub[k][0]= ((x[k]-min_xk[k])-(mean[l][k]-min_xk[k]))/(max_xk[k]-min_xk[k]);

										for(int k=0;k<b;k++)
										 subT[0][k]=sub[k][0];

										for(int j=0;j<b;j++)
										{
							                mul[0][j]=0;
											for(int k=0;k<b;k++)
											 mul[0][j]+=(subT[0][k]*c1[l][k][j]);
										}

										dsq3[l][i]=0;
										for(int k=0;k<b;k++)
					                  		dsq3[l][i]+=mul[0][k]*sub[k][0];

					                  	//normalization done here
					                 //dsq3[l][i]=dsq3[l][i]*(Math.pow(10,-b));

				//Final dsq
						dsq[l][i]=0;

							dsq[l][i]=Math.pow(Math.E,-0.5*dsq1[l][i])+Math.pow(Math.E,-0.5*dsq2[l][i])-2*Math.pow(Math.E,-0.5*dsq3[l][i]);

					/****Mixing second kernel*/

                                    if(flag_second==0)
                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/
			}


			//5.RADIAL KERNEL
				else if(txt.compareTo("Radial")==0)
			{


						//calculating dsq1
										for(int k=0;k<b;k++)
										 sub1[k][0]= x[k]-x[k];

										for(int k=0;k<b;k++)
										 subT1[0][k]=sub1[k][0];

										for(int j=0;j<b;j++)
										{
							                mul1[0][j]=0;
											for(int k=0;k<b;k++)
											 mul1[0][j]+=(subT1[0][k]*c1[l][k][j]);
										}

										dsq1[l][i]=0;
										for(int k=0;k<b;k++)
					                 dsq1[l][i]+=mul1[0][k]*sub1[k][0];

				//calculating dsq2

					                 for(int k=0;k<b;k++)
										 sub2[k][0]= mean[l][k]-mean[l][k];

										for(int k=0;k<b;k++)
										 subT2[0][k]=sub2[k][0];

										for(int j=0;j<b;j++)
										{
							                mul2[0][j]=0;
											for(int k=0;k<b;k++)
											 mul2[0][j]+=(subT2[0][k]*c1[l][k][j]);
										}

										dsq2[l][i]=0;
										for(int k=0;k<b;k++)
					                  dsq2[l][i]+=mul2[0][k]*sub2[k][0];


				//calculating dsq3
					                 for(int k=0;k<b;k++)
										sub[k][0]= ((x[k]-min_xk[k])-(mean[l][k]-min_xk[k]))/(max_xk[k]-min_xk[k]);

										for(int k=0;k<b;k++)
										 subT[0][k]=sub[k][0];

										for(int j=0;j<b;j++)
										{
							                mul[0][j]=0;
											for(int k=0;k<b;k++)
											 mul[0][j]+=(subT[0][k]*c1[l][k][j]);
										}

										dsq3[l][i]=0;
										for(int k=0;k<b;k++)
					                  		dsq3[l][i]+=mul[0][k]*sub[k][0];

									//normalization done here
					                // dsq3[l][i]=dsq3[l][i]*(Math.pow(10,-b));

			//Final dsq
						dsq[l][i]=0;

							dsq[l][i]=Math.pow(Math.E,-1*dsq1[l][i])+Math.pow(Math.E,-1*dsq2[l][i])-2*Math.pow(Math.E,-1*dsq3[l][i]);

					/****Mixing second kernel*/

                                    if(flag_second==0)
                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/
		}


			//6.KMOD KERNEL
			else if(txt.compareTo("KMOD")==0)
			{


						//calculating dsq1
								for(int k=0;k<b;k++)
										 sub1[k][0]= x[k]-x[k];

										for(int k=0;k<b;k++)
										 subT1[0][k]=sub1[k][0];

										for(int j=0;j<b;j++)
										{
							                mul1[0][j]=0;
											for(int k=0;k<b;k++)
											 mul1[0][j]+=(subT1[0][k]*c1[l][k][j]);
										}

										dsq1[l][i]=0;
										for(int k=0;k<b;k++)
					                 dsq1[l][i]+=mul1[0][k]*sub1[k][0];

				//calculating dsq2

					                 for(int k=0;k<b;k++)
										 sub2[k][0]= mean[l][k]-mean[l][k];

										for(int k=0;k<b;k++)
										 subT2[0][k]=sub2[k][0];

										for(int j=0;j<b;j++)
										{
							                mul2[0][j]=0;
											for(int k=0;k<b;k++)
											 mul2[0][j]+=(subT2[0][k]*c1[l][k][j]);
										}

										dsq2[l][i]=0;
										for(int k=0;k<b;k++)
					                  dsq2[l][i]+=mul2[0][k]*sub2[k][0];


				//calculating dsq3
					                 for(int k=0;k<b;k++)
										 sub[k][0]= ((x[k]-min_xk[k])-(mean[l][k]-min_xk[k]))/(max_xk[k]-min_xk[k]);

										for(int k=0;k<b;k++)
										 subT[0][k]=sub[k][0];

										for(int j=0;j<b;j++)
										{
							                mul[0][j]=0;
											for(int k=0;k<b;k++)
											 mul[0][j]+=(subT[0][k]*c1[l][k][j]);
										}

										dsq3[l][i]=0;
										for(int k=0;k<b;k++)
					                  		dsq3[l][i]+=mul[0][k]*sub[k][0];

					                  	//normalization done here
										//dsq3[l][i]=dsq3[l][i]*(Math.pow(10,-b));

					//Fianl dsq
						dsq[l][i]=0;

							dsq[l][i]=((Math.pow(Math.E,1/(1+dsq1[l][i])))-1)+((Math.pow(Math.E,1/(1+dsq2[l][i])))-1)-2*((Math.pow(Math.E,1/(1+dsq3[l][i])))-1);

					/****Mixing second kernel*/

                                    if(flag_second==0)
                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/
		}


			//7.MULTIQUADRATRIC KERNEL
			else if(txt.compareTo("Multiquadratic")==0)
			{


						//calculating dsq1
								for(int k=0;k<b;k++)
										 sub1[k][0]= x[k]-x[k];

										for(int k=0;k<b;k++)
										 subT1[0][k]=sub1[k][0];

										for(int j=0;j<b;j++)
										{
							                mul1[0][j]=0;
											for(int k=0;k<b;k++)
											 mul1[0][j]+=(subT1[0][k]*c1[l][k][j]);
										}

										dsq1[l][i]=0;
										for(int k=0;k<b;k++)
					                 dsq1[l][i]+=mul1[0][k]*sub1[k][0];

				//calculating dsq2

					                 for(int k=0;k<b;k++)
										 sub2[k][0]= mean[l][k]-mean[l][k];

										for(int k=0;k<b;k++)
										 subT2[0][k]=sub2[k][0];

										for(int j=0;j<b;j++)
										{
							                mul2[0][j]=0;
											for(int k=0;k<b;k++)
											 mul2[0][j]+=(subT2[0][k]*c1[l][k][j]);
										}

										dsq2[l][i]=0;
										for(int k=0;k<b;k++)
					                  dsq2[l][i]+=mul2[0][k]*sub2[k][0];


				//calculating dsq3
					                 for(int k=0;k<b;k++)
										sub[k][0]= ((x[k]-min_xk[k])-(mean[l][k]-min_xk[k]))/(max_xk[k]-min_xk[k]);

										for(int k=0;k<b;k++)
										 subT[0][k]=sub[k][0];

										for(int j=0;j<b;j++)
										{
							                mul[0][j]=0;
											for(int k=0;k<b;k++)
											 mul[0][j]+=(subT[0][k]*c1[l][k][j]);
										}

										dsq3[l][i]=0;
										for(int k=0;k<b;k++)
					                  		dsq3[l][i]+=mul[0][k]*sub[k][0];

					                  	//normalization done here
										//dsq3[l][i]=dsq3[l][i]*(Math.pow(10,-b));

					//Final dsq
						dsq[l][i]=0;

							dsq[l][i]=(1/(Math.pow((dsq1[l][i]+1),(0.5))))+(1/(Math.pow((dsq2[l][i]+1),(0.5))))-2*(1/(Math.pow((dsq3[l][i]+1),(0.5))));

					/****Mixing second kernel*/

                                    if(flag_second==0)
                                     dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];

                     /*End Mixing*/

		}



			// 8.SIGMOID KERNEL
                       else if(txt.compareTo("Sigmoid")==0)
			{



				dsq1[l][i]=0;
				dsq2[l][i]=0;
				dsq3[l][i]=0;


					for(int k=0;k<b;k++){


					double xk=x[k];
					xk-=min_xk[k];
					double yk=max_xk[k]-min_xk[k];
					dsq1[l][i]+=( (xk*xk)/(yk*yk));

				}
					dsq1[l][i]=(dsq1[l][i]/2);





				for(int k=0;k<b;k++){

						double p=mean[l][k];
						p-=min_xk[k];
						double yk=max_xk[k]-min_xk[k];

					dsq2[l][i]+=( (p*p)/(yk*yk));


					}
                     	dsq2[l][i]=(dsq2[l][i]/2);


				for(int k=0;k<b;k++){
					double p=mean[l][k];
						p-=min_xk[k];
										double xk=x[k];
										xk-=min_xk[k];
										double yk=max_xk[k]-min_xk[k];


					dsq3[l][i]+=( (p*xk)/(yk*yk));
				}
	dsq3[l][i]=(dsq3[l][i]/2);

				dsq[l][i]=0;

					dsq[l][i]=Math.tanh(dsq1[l][i])+Math.tanh(dsq2[l][i])-2*Math.tanh(dsq3[l][i]);
					 if(dsq[l][i]<=0)

			          {
				        System.out.println(-dsq[l][i]);
						dsq[l][i] = -dsq[l][i];

					  }
					 else
					  {
						System.out.println(dsq[l][i]);
					  }


//dsq[l][i]=-1*dsq[l][i];




                                    if(flag_second==0)
										dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
                     }

 /********spectral angle********************/
                   else if(txt.compareTo("Spectral")==0)
			{
				//finding the mod values for the x vector and the mean vector
				dsq1[l][i]=0;
				dsq2[l][i]=0;
				dsq3[l][i]=0;
				double xmod=0,mean_mod=0;


				if(i==65920){
				for(int k=0;k<b;k++)
					System.out.println("value of x[i]for the "+k+ "band is"+x[k]);

				}
				for(int k=0;k<b;k++){
					xmod+=(x[k]*x[k]);
					mean_mod+=(mean[l][k] * mean[l][k]);

				}
				xmod=Math.sqrt(xmod);
				mean_mod=Math.sqrt(mean_mod);
				//if(xmod!=0)
					//System.out.println("xmod is "+" "+ xmod +" i is"+i+" "+x[0]+" "+x[1]+" "+x[2]+" "+x[3]);

				if(xmod==0)
					xmod=1;
				if(mean_mod==0)
					mean_mod=1;
				for(int k=0;k<b;k++){
					dsq1[l][i]+=((x[k]*x[k])/(xmod*xmod));

				}

				if(i==65920)
				System.out.println("dsq1:"+dsq1[l][i]);
				if(Double.compare(dsq1[l][i],1)>0){
							dsq1[l][i]=1.00000000;
							//System.out.println("val of acos   "+Math.acos(dsq2[l][i]));
						}



				for(int k=0;k<b;k++){

					dsq2[l][i]+=((mean[l][k]*mean[l][k])/(mean_mod*mean_mod));


					}

					if(i==65920)
				System.out.println("dsq2:"+dsq2[l][i]);
					if(Double.compare(dsq2[l][i],1)>0){
							dsq2[l][i]=1.00000000;
							//System.out.println("val of acos   "+Math.acos(dsq2[l][i]));
						}


				for(int k=0;k<b;k++){
					dsq3[l][i]+=((x[k]*mean[l][k])/(xmod*mean_mod));

					}
					if(i==65920)
				System.out.println("dsq3:"+dsq3[l][i]);
					if(Double.compare(dsq3[l][i],1)>0){
							dsq3[l][i]=1.00000000;
							//System.out.println("val of acos   "+Math.acos(dsq2[l][i]));
						}



				dsq[l][i]=0;

					dsq[l][i]=(Math.acos(dsq1[l][i]))+(Math.acos(dsq2[l][i]))-2*(Math.acos(dsq3[l][i]));
					//if(dsq[l][i]>=0)
					//System.out.println("xmod is "+" "+ dsq[l][i]+"for i="+i);



			if(i==65920)
				System.out.println("dsq:"+dsq[l][i]);
			if(Double.compare(dsq[l][i],1)>0){
							dsq[l][i]=1.00000000;
							//System.out.println("val of acos   "+Math.acos(dsq2[l][i]));
						}
			/*if(dsq[l][i]<add)
			{
				add=dsq[l][i];
			}*/

dsq[l][i]= -1* dsq[l][i];

                                    if(flag_second==0)
										dsq[l][i]=meu_mix_db*dsq_temp[l][i]+(1.0-meu_mix_db)*dsq[l][i];
                     }

                    }

	   			System.gc();
	   		  }






					/*System.out.println("add:"+add);//to shift from negative to positive scale
					add= -1*add;
						for(int l=0;l<count;l++)

					{

						for(int i=0;i<h*w;i++)
	  		    {
					dsq[l][i]+=add;

					}
				}

				add=0;*/

/*for(int l=0;l<count;l++)

					{

						for(int i=0;i<h*w;i++)
	  		    {
					System.out.println("DSQ1 for class "+ l + "for pixel "+ i+"="+ dsq1[l][i]);
					System.out.println("DSQ2 for class "+ l + "for pixel "+ i+"="+ dsq2[l][i]);
					System.out.println("DSQ3 for class "+ l + "for pixel "+ i+"="+ dsq3[l][i]);


					System.out.println(" Final DSQ for class "+ l + "for pixel "+ i+"="+ dsq1[0][65920]);
			}
				}*/


                    	System.out.println("DSQ1 for class 1"+" "+ dsq1[0][65920]);
			 			  System.out.println("DSQ1 for class 2"+" "+ dsq1[1][65920]);
			 			  System.out.println("DSQ2 for class 1"+" "+ dsq2[0][65920]);
			 			  System.out.println("DSQ2 for class 2"+" "+ dsq2[1][65920]);
			 			  System.out.println("DSQ3 for class 1"+" "+ dsq3[0][65920]);
			 	   		  System.out.println("DSQ3 for class 2"+" "+ dsq3[1][65920]);
			 	   		  System.out.println("Final DSQ for class1"+" " +dsq[0][65920]);
			 	   		  System.out.println("Final DSQ for class2"+" "+ dsq[1][65920]);

                                 if(source==btClasi)
                                 { System.out.println("DSquare i,j is Calculated");
	   		  System.out.println("status"+ st);
	   		}
                                 else if(source==btsyn)
                                 {
                                     System.out.println("\nSynthetic Image Formation");
                                 }
                        }
	  		catch(Exception e)
	  		{
	  			 System.out.println("dSquare i,j Error: "+ e);
	  		}

			if(flag_second==1&&cb.isSelected())
                 return;


	///********* Calculating Meu Array by Distance vector for Fuzzy C-Means *************////

			if(classi==1)
			{
		  		try
		  		{
		  			for(int j=0;j<count;j++)
		  			{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
		  					if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
		  					{
		  						for(int k=0;k<count;k++)
		  						{

									if(dsq[k][i]<0)
		  							div=div+Math.pow((1/(1-dsq[k][i])),(1/(m-1)));
		  							else
		  							div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));

		  						}
		  						if(dsq[j][i]<0)
		  					meu[j][i]=(Math.pow((1/(1-dsq[j][i])),(1/(m-1)))/div);
		  					else
		  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

							}
		  					else
		  					{
		  						for(int k=0;k<count;k++)
		  						{
									//System.out.println("val of 1/dsq[k][i] is"+(1/dsq[k][i]));
									div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));

								}
							//System.out.println(div);

							meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

						}


		  				}
		  				System.gc();
		  			}
		  			System.out.println("meu for class1 is" + meu[0][65920]);
		  		 System.out.println("meu for class2 is" + meu[1][65920]);
		  			System.out.println("Meu has been Computed for Kernel Fuzzy C-Means.");
		  		}
		  		catch(Exception e)
		  		{
		  			System.out.println("KFCM Meu Calculation Error : "+e);
		  		}
	   		}

	   		/******************Calculating meu Array for Noise Clustering without Entropy *************/



                        else if(classi==2)
	   			{
		   			try
		  		    {

		  			    flago=0;
		  			  	for(int j=0;j<count;j++)
		  				{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;




		  					for(int k=0;k<count;k++)
		  					{
		  						div=div+ Math.pow((dsq[j][i]/dsq[k][i]),(1/(m-1)));
		  					}

		  					meu[j][i]=1/(div+Math.pow((dsq[j][i]/del),(1/(m-1))));
		  				    }

		  					System.gc();
		  				}
		  				for(int i=0;i<h*w;i++)
		  			  	{
		  			  	 double div1=0.0;
		  			  	 for(int j=0;j<count;j++)
		  			  	  {
		  			  	  	div1=div1+Math.pow((del/dsq[j][i]),(1/(m-1)));
		  			  	  }
		  			  	meu[count][i]=1/(div1+1.00);
		  			  	}
		  				System.out.println("Meu Computed.");
		  			}
		  			catch(Exception e)
		  			{
		  				System.out.println("Meu Calculation Error : "+e);
		  			}


		   			System.out.println("Meu has been Computed for Noise Clustering without Entropy.");
	   		    }




	   				/////////*********Calculating meu Array for Noise Clustering with Entropy *************/

                        else if(classi==3)
	   				{
	   					try
  						{

  			            flago=0;
  						for(int j=0;j<count;j++)
						{
			  				for(int i=0;i<h*w;i++)
			  				{
			  					div=0.0;
			  					for(int k=0;k<count;k++)
			  					{
			  						div=div+ Math.exp(-(dsq[k][i]/neo));
			  					}

			  					meu[j][i]=Math.exp(-(dsq[j][i]/neo))/(div+Math.exp(-del/neo));
			  				}
			  				System.gc();
			  			}

			  			for(int i=0;i<h*w;i++)
			  			{
			  			   double div1=0.0;
			  			   for(int j=0;j<count;j++)
			  			   {
			  			  	  div1=div1+Math.exp(-(dsq[j][i]/neo));
			  			   }
			  			   meu[count][i]=Math.exp(-(del/neo))/(div1+Math.exp(-(del/neo)));
			  			}
			  			System.out.println("Meu Computed.");
				  		}
				  		catch(Exception e)
				  		{
				  			System.out.println("Meu Calculation Error : "+e);
				  		}


	   					System.out.println("Meu has been Computed for Noise Clustering with Entropy.");

	   				}

	   				/////////*********Calculating meu Array for KFCM with Entropy *************/


                        else if(classi==4)
	   					{
	   						try
					  		{

					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.exp(-(dsq[k][i])/neo);
					  					}

					  					meu[j][i]=(Math.exp(-(dsq[j][i])/neo)/div);

					  				}
					  				System.gc();
					  			}
					  			System.out.println("Meu Computed.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("Meu Calculation Error : "+e);
					  		}

		   					System.out.println("Meu has been Computed for KFCM with Entropy.");
	   					}

	   					/////////******** Calculating Meu Array For Possibilistic C-Means *********/////////

                        else if(classi==5)
	   					{



					  		try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;


					  	if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
								  					{
								  						for(int k=0;k<count;k++)
								  						{

															if (dsq[k][i]<0)
								  							div=div+Math.pow((1/(1-dsq[k][i])),(1/(m-1)));
								  						else
								  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));

								  						}
								  						if(dsq[j][i]<0)
								  					meu[j][i]=(Math.pow((1/(1-dsq[j][i])),(1/(m-1)))/div);
													else
													meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);
													}
								  					else
		  					{

					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

					  				}
								}
					  				System.gc();
					  			}

					  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];

					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
						  				val1=val1+Math.pow(meu[i][j],m);
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
						  				val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);
					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
					  					meu[i][j]=1/(1+(Math.pow((dsq[i][j]/arrayEta[i]),(1/(m-1)))));
					  				}
					  			}

                                                                System.out.println("Meu has been Computed for Kernel Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("KPCM Meu Calculation Error : "+e);
					  		}

	   		}
                                                  //Modified Possiblistic Cmeans
                        else if(classi==6)

                                                      {
                                                      try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
												  					{
												  						for(int k=0;k<count;k++)
												  						{

																			if(dsq[k][i]<0)
												  							div=div+Math.pow((1/(1-dsq[k][i])),(1/(m-1)));
												  						else
												  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
												  						}


												  						if(dsq[j][i]<0)
												  					meu[j][i]=(Math.pow((1/(1-dsq[j][i])),(1/(m-1)))/div);

																	else
																	meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

																	}
												  					else
		  					{
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);
								 }
					  				}
					  				System.gc();
					  			}


					  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
						  				val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									}
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
					  					meu[i][j]=Math.exp(-dsq[i][j]/arrayEta[i]);
					  				}
					  			}


					  			System.out.println("Meu has been Computed for Modified Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("MPCM Meu Calculation Error : "+e);
					  		}

                                                      }
     ///*********  Meu calculation for Possiblistic  C-Means Supervised(S) *************////
			if(classi==13){
				 double fac=0.0;
                                    double mx[]=new double[b];
                                    double mn[]=new double[b];
                                     double aa=0;int ws=0;
                                     int sec=0;int d=0,lim=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;
				while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }
                       xw=ws;
                       yw=ws;
                       System.out.println("\nWindow Size="+ws);
                        for(int j=0;j<count;j++)
					  			{

                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                     if(ws!=1)
                                                     {

		  			   if((x1-(xw/2))<0)
                                                                      xs=0;
                                                                       else
                                                                  xs=x1-(xw/2);

                                                              if((x1+(xw/2))>=w)
                                                                      xe=w-1;
                                                                   else
                                                                    xe=x1+(xw/2);

                                                                  if((y1-(yw/2))<0)
                                                                          ys=0;
                                                                             else
                                                                            ys=y1-(yw/2);

                                                                        if((y1+(yw/2))>=h)
                                                                          ye=h-1;
                                                                                 else
                                                                          ye=y1+(yw/2);
                                                     }
                                                      else
                                                     {
                                                             xe=0;xs=0;
                                                             ye=0;ys=0;
                                                             aa=0;
                                                             }




                                           	    for(int sd=ys;sd<=ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<=xe;gd++)
                                                              {
                                                                  if(sd!=x1 || gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d<(h*w))
                                                                      {     fac+=dsq[j][d];
                                                                      lim++;
                                                                      }

                                                                    }
                                                                  }

                                                                }
                                                   fac=fac*(aa/(lim));
                                               			


					  				}
                                                        }
					  			}

                                    try
		  		{
                                        double min=65535,max=-1;
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;


					  	if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
								  					{
								  						for(int k=0;k<count;k++)
								  						{

															if (dsq[k][i]<0)
								  							div=div+Math.pow((1/(1-(dsq[k][i]+fac))),(1/(m-1)));
								  						else
								  						div=div+Math.pow((1/(dsq[k][i]+fac)),(1/(m-1)));

								  						}
								  						if(dsq[j][i]<0)
								  					meu[j][i]=(Math.pow((1/(1-(dsq[j][i]+fac))),(1/(m-1)))/div);
													else
													meu[j][i]=(Math.pow((1/(dsq[j][i]+fac)),(1/(m-1)))/div);
													}
								  					else
		  					{

					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/(dsq[k][i]+fac)),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/(dsq[j][i]+fac)),(1/(m-1)))/div);

					  				}
								}
					  				System.gc();
					  			}

					  			double val1=0.0;
					  			double val2=0.0;
					  			double aaa;
					  			int wins;
					  			double []arrayEta=new double[count];
					  			//double []kDash=new double[count];
					  			//String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			                    //aaa = Double.parseDouble(input);
					  			// String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
					  			// wins = Integer.parseInt(inpt);
					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;

					  				
						  			for(int j=0;j<h*w;j++)
						  			{
						  				if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
						  				if((meu[i][j])>= 0.0)
										{
                                            val2=val2+(Math.pow(meu[i][j],m)*(dsq[i][j]+fac));
									        }
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);
					  			}
					  			 for(int j=0;j<count;j++)
					  			{

                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
					  			 int i=((y1)*w)+(x1);
                           	meu[j][i]=1/(1+(Math.pow(((dsq[j][i]+fac)/arrayEta[j]),(1/(m-1)))));
                                                                             //   System.out.println("\nMeu:"+meu[i][j]);
					  			
                         }
                     }
                 }
                            System.out.println("Meu has been Computed for Kernel Possibilistic C-Means Supervised.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("KPCMS Meu Calculation Error : "+e);
					  		}

	   		}
                       
                        //Improved possiblistic c means
                        else if(classi==7)
							{
                          	 try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;
					  					if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
												  					{
												  						for(int k=0;k<count;k++)
												  						{
																			if(dsq[k][i]<0)
												  							div=div+Math.pow((1/(1-dsq[k][i])),(1/(m-1)));
												  						else
												  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));

												  						}


												  						if(dsq[j][i]<0)
												  					meu[j][i]=(Math.pow((1/(1-dsq[j][i])),(1/(m-1)))/div);
												  					else
												  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

																	}
												  					else
		  					{
					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);

					  				}
								}
					  				System.gc();
					  			}


					  			double val1=0.0;
					  			double val2=0.0,sum=0.0;
					  			double []arrayEta=new double[count];


					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)        //To overcome from undefined meu in case of Cosine,Correlation and Normalized-Squared-euclidean distances
										{
						  				val1=val1+Math.pow(meu[i][j],m);
									}
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
										if((meu[i][j])>= 0.0)
										{
						  				val2=val2+(Math.pow(meu[i][j],m)*dsq[i][j]);
									}
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);

					  			}
                                                                int i,j,k;
                                                                for(i=0;i<count;i++)
                                                                {
                                                                	for(k=0;k<h*w;k++)
                                                                        {
                                                                                sum=0.0;
                                                                                for(j=0;j<count;j++)



                                                                                {

                                                                                if((dsq[i][k]<0)&&(dsq[j][k]<0))
sum+=Math.pow(((-1*arrayEta[i]*(1-Math.exp(-dsq[i][k]/arrayEta[i])))/(-1*arrayEta[j]*(1-Math.exp(-dsq[j][k]/arrayEta[j])))),(2/(m-1)));

else if(dsq[i][k]<0)

sum+=Math.pow(((-1*arrayEta[i]*(1-Math.exp(-dsq[i][k]/arrayEta[i])))/(arrayEta[j]*(1-Math.exp(-dsq[j][k]/arrayEta[j])))),(2/(m-1)));
else if(dsq[j][k]<0)

sum+=Math.pow(((arrayEta[i]*(1-Math.exp(-dsq[i][k]/arrayEta[i])))/(-1*arrayEta[j]*(1-Math.exp(-dsq[j][k]/arrayEta[j])))),(2/(m-1)));


                                                                                else
                                                                                sum+=Math.pow(((arrayEta[i]*(1-Math.exp(-dsq[i][k]/arrayEta[i])))/(arrayEta[j]*(1-Math.exp(-dsq[j][k]/arrayEta[j])))),(2/(m-1)));
                                                                                }
                                                                                meu[i][k]=1/sum;
                                                                        }
                                                                }


					  			System.out.println("Meu has been Computed for Improved Possibilistic C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("IPCM Meu Calculation Error : "+e);
					  		}

                             }
                        else if(classi==9)
			{
                                    double fac=0.0,fa=0.0;
                                     double aa=0;int ws=0;
                                     double sec=0.0;int d=0;
                                     int xs,xe,ys,ye,xw,yw;
                                     int lim=0;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }
                       xw=ws;
                       yw=ws;
                       System.out.println("\nWindow Size="+ws);
                                    try
		  		{
                                        double min=65535,max=-1;
		  			for(int j=0;j<count;j++)
		  			{
                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                           if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;


                                           	    for(int sd=ys;sd<ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {
                                                                  if(sd!=x1 && gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d>(h*w-1))
                                                                          System.out.println("\nd="+d+"\nxs="+xs+"\txe="+xe+"\tys="+ys+"\tye="+ye);
                                                                      fac+=dsq[j][d];
                                                                      lim++;

                                                                    }
                                                                  }

                                                                }
                                                   fac=fac*(aa/(lim));



						 int i=((y1)*w)+(x1);
                                     	              div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
                                                          fa=0.0;
                                                       lim=0;

                                                           if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;


                                           	    for(int sd=ys;sd<ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {
                                                                  if(sd!=x1 && gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d>(h*w-1))
                                                                          System.out.println("\nd="+d+"\nxs="+xs+"\txe="+xe+"\tys="+ys+"\tye="+ye);
                                                                      fa+=dsq[k][d];
                                                                      lim++;

                                                                    }
                                                                  }

                                                                }
                                                          fa=fa*(aa/(lim));

						  if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
		  					{
		  						for(int kh=0;kh<count;kh++)
		  						{

									if(dsq[k][i]<0)
		  							div=div+Math.pow((1/(1-(fa+dsq[kh][i]))),(1/(m-1)));
		  							else
		  							div=div+Math.pow((1/(fa+dsq[kh][i])),(1/(m-1)));

		  						}

							}
		  					else
		  					{
		  						for(int kh=0;kh<count;kh++)
		  						{
									//System.out.println("val of 1/dsq[k][i] is"+(1/dsq[k][i]));
									div=div+Math.pow((1/(fac+dsq[kh][i])),(1/(m-1)));

								}

						}
                                                        }

		  					  if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
		  					{

		  						if(dsq[j][i]<0)
		  					meu[j][i]=(Math.pow((1/(1-(fac+dsq[j][i]))),(1/(m-1)))/div);
		  					else
		  					meu[j][i]=(Math.pow((1/(fac+dsq[j][i])),(1/(m-1)))/div);

							}
		  					else
		  					{

							meu[j][i]=(Math.pow((1/(fac+dsq[j][i])),(1/(m-1)))/div);

						}

						   }

		  				}

				   }

	System.gc();


		  		}
		  		catch(Exception e)
		  		{
		  			System.out.println("KFCMS Meu Calculation Error : "+e);
		  		}

                       }
                        else  if(classi==12)
				              {



                                    {

                                    double fac=0.0;
                                    double mx[]=new double[b];
                                    double mn[]=new double[b];
                                     double aa=0;int ws=0;
                                     int sec=0;int d=0,lim=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       while(aa<=0.0)
                       {
                                   String input = JOptionPane.showInputDialog("Please Enter The Value Of a (1 < a < infinity) ");
			 aa = Double.parseDouble(input);
                      }
                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);
                                    try
		  		{
                                        double min=65535,max=-1;
		  			for(int j=0;j<b;j++)
		  			{
                                          	   for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {

                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {
                                                     fac=0.0;
                                                     lim=0;

                                                           if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;


                                           	    for(int sd=ys;sd<ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {
                                                                  if(sd!=x1 && gd!=y1)
                                                                  {
                                                                      d=((sd)*w)+(gd);
                                                                      if(d>(h*w-1))
                                                                          System.out.println("\nd="+d+"\nxs="+xs+"\txe="+xe+"\tys="+ys+"\tye="+ye);
                                                                      fac+=xnew[j][d];
                                                                      lim++;

                                                                    }
                                                                  }

                                                                }

                                                   fac=fac*(aa/(lim));



                                                      int i=((y1)*w)+(x1);

						      div=xnew[j][i]+fac;

                                                      meun[j][i]=div/(1+aa);

                                                      if(mn[j]>meun[j][i])
                                                          mn[j]=meun[j][i];


                                                      if(mx[j]<meun[j][i])
                                                          mx[j]=meun[j][i];
                                               //       System.out.println("\nmeu="+meu[j][i]+"\tdiv="+div+"\tx="+xnew[j][i]+"\tfac="+fac);


				   }

	System.gc();


		  		}
                                        }

                                }
		  		catch(Exception e)
		  		{

		  		  System.out.println("Synthetic Error  ");
                                      }

                                System.out.println("Synthetic Image Formation Has Been Completed.");
                                          }

                                                }
                        else
                        {
                            try
                            {
                          double[][] dis=new double[count][h*w];
			  int b = Bands;
		       	prob = new double[count];

                	double[] det = new double[count];
			double[] max = new double[count];
			double[] min = new double[count];


				for(int j=0;j<count;j++)
                                {
					max[j]=-1;
					min[j]=1000000000;
	         		}

			for(int j=0;j<count;j++)
				{
			for(int i=0;i<h*w;i++)
				{

                                    if((txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)&&(dsq[j][i]<0))
																	{

																		if((-1*dsq[j][i])>=max[j])
									          							{




																				max[j]=-1*dsq[j][i];
																		}


																	}


																	else
																	{

													                    if(dsq[j][i]>=max[j])
									          								{




																				max[j]=dsq[j][i];
																			}
																	}




													   	if((txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)&&(dsq[j][i]<0))

														{
															if((-1*dsq[j][i])<=min[j])

														    {




																		min[j]=-1*dsq[j][i];
															}
														}

															else
														{
													if(dsq[j][i]<=min[j])
												        	{
													min[j]=dsq[j][i];
													        }
												      	}
												        }
													}
									                        for(int j=0;j<count;j++)
												        {
										    	        for(int i=0;i<h*w;i++)
										    			{




															if((txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)&&(dsq[j][i]<0))

														{
										        		     dis[j][i]=8*(((-1*dsq[j][i])-min[j])/(max[j]-min[j]));
													}

													else

																	{
													        		     dis[j][i]=8*((dsq[j][i]-min[j])/(max[j]-min[j]));
																}





												}
                                }
                        //finding determinant of variance covariance matrix
		                System.out.println("Determinants are:-");
				for(int i=0;i<count;i++)
				{
		            		det[i]=determinant(c1[i],b);
					System.out.println("det "+det[i]);

				}
				System.out.println("Bands"+b);

				for(int i=0;i<h*w;i++)
				{

				double sum=0.0;
				for(int j=0;j<count;j++)
				{
					prob[j]=1/Math.pow(2*Math.PI,(b/2));
					if(det[j]<0)
					prob[j]=prob[j]*(1/Math.pow((-1*det[j]),0.5));
					else
					prob[j]=prob[j]*(1/Math.pow(det[j],0.5));
					prob[j]=prob[j]*Math.pow(Math.E,-0.5*dis[j][i]);
					sum+=prob[j];
					//System.out.println(dsq[j][i]);
				}

				for(int j=0;j<count;j++)
				{
					prob[j]=1/Math.pow(2*Math.PI,(b/2));
					if(det[j]<0)
										prob[j]=prob[j]*(1/Math.pow((-1*det[j]),0.5));
										else

					prob[j]=prob[j]*(1/Math.pow(det[j],0.5));
					prob[j]=prob[j]*Math.pow(Math.E,-0.5*dis[j][i]);
					meu[j][i]=prob[j]/sum;
			        }
				}


				System.out.println("Meu has been Computed for Maximum Likelihood Classifier.");
	  		}
			catch(Exception e)
			{
				System.out.println("MLC Meu Calculation Error : "+e);
			}


                        }


                           if(advclassi==1)
				              {
                                    {

                                    double fac=0.0;
                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                    double [][] dsw=new double[count][h*w];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double gval[][]=new double[count][h*w];
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size flicm="+ws);

                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {

                                                     fac=0.0;

                                                   // System.out.println("\nhere");
                                                        if(ws!=1)
                                                        {
                                                               if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;

                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                               //  System.out.println("\ni="+i);

                                     	            for(int sd=ys;sd<ye;sd++)
                                                          {
                                                 //             System.out.println("\nwindow");
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {
                                                                      d=((sd)*w)+(gd);
                                                                     if(sd!=y1 && gd!=x1)
                                                                        {
                                                                                                              for(int k=0;k<b;k++)
												     sub[k][0]= xnew[k][d]-xnew[k][i];

													 for(int k=0;k<b;k++)
													 subT[0][k]=sub[k][0];

													 for(int jh=0;jh<b;jh++)
													 {

                                                                                                         mul[0][jh]=0;
													 for(int k=0;k<b;k++)
													 mul[0][jh]+=subT[0][k]*c1[j][k][jh];

													 }

													 dsw[j][d]=0;
													 for(int k=0;k<b;k++)
													 dsw[j][d]+=mul[0][k]*sub[k][0];

                                                                     gval[j][i]+=((1/(1+dsw[j][d]))*(Math.pow((1-meu[j][d]),m))*(dsq[j][d]));
                                                                        }

                                                                  }

                                                                }



                                                }

	                                 System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("KFLICM Error  ");
                                      }

                                    try
                                    {
                                       for(int j=0;j<count;j++)
		  			{
		  				for(int i=0;i<h*w;i++)
		  				{
		  					div=0.0;
                                                        if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
		  					{
		  						for(int k=0;k<count;k++)
		  						{

									if(dsq[k][i]<0)
		  							div=div+Math.pow((1/(1-(dsq[k][i]+gval[k][i]))),(1/(m-1)));
		  							else
		  							div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));

		  						}
		  						if(dsq[j][i]<0)
		  					meunm[j][i]=(Math.pow((1/(1-(dsq[j][i]+gval[j][i]))),(1/(m-1)))/div);
		  					else
		  					meunm[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);

							}
		  					else
		  					{
		  						for(int k=0;k<count;k++)
		  						{
									//System.out.println("val of 1/dsq[k][i] is"+(1/dsq[k][i]));
									div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));

								}
							//System.out.println(div);

							meunm[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);



		  				}

				   }



                                    }
                                    }
                                    catch(Exception e)
                                    {

                                    }
                                          }
                                   System.out.println("KFLICM Classification has been Completed.");

                                                }

                                                            else  if(advclassi==2)
				              {


                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0) )
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double sval=0.0;
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size="+ws);
                       /*
                       try
                       {
                           for(int j=0;j<count;j++)
		  			{
                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                      for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                        {
                                                              int i=((y1)*w)+(x1);

                                                	div=0.0;
		  					for(int k=0;k<count;k++)
		  					{
								div=div+Math.pow((1/dsq[k][i]),(1/(m-1)));
							}

		  					meunm[j][i]=(Math.pow((1/dsq[j][i]),(1/(m-1)))/div);
                                               }
                                                   }

				   }
                       }
                       catch(Exception e)
                       {
                       }*/

                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)
                                            {
                                                for(int x1=0;x1<w;x1++)
                                                {



                                                        if(ws!=1)
                                                        {
                                                               if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;

                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                                      int dij=1,num=1;
                                                      double heat=0.0;
                                                      double fact[]=new double[count];



                                                      for(int jy=0;jy<count;jy++)
                                                                     {

                                                              heat=0.0;
                                                    for(int sd=ys;sd<ye;sd++)
                                                          {
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {num++;
                                                                          d=((sd)*w)+(gd);
                                                                     if(sd!=y1 && gd!=x1)
                                                                        {
                                                                            if((Math.abs(gd-x1))>(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(gd-x1));

                                                                            else if((Math.abs(gd-x1))<(Math.abs(sd-y1)))
                                                                                dij=(Math.abs(sd-y1));

                                                                            else
                                                                                  dij=(Math.abs(sd-y1));


                                                                           sval=(meu[jy][d]*meu[jy][i])/(dij*dij);
                                                                          heat+=(1-sval)*(dsq[jy][d]);
                                                                        }

                                                                     }
                                                                  }
                                                                  fact[jy]=heat;
                                                                }
                                                      num=num/count;

                                                    div=0.0;
                                                    if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
		  					{
		  						for(int k=0;k<count;k++)
		  						{

									if(dsq[k][i]<0)
		  							div=div+Math.pow((1/(1-(dsq[k][i]+(fact[k]/num)))),(1/(m-1)));
		  							else
		  							div=div+Math.pow((1/(dsq[k][i]+(fact[k]/num))),(1/(m-1)));

		  						}
		  						if(dsq[j][i]<0)
		  					meunm[j][i]=(Math.pow((1/(1-(dsq[j][i]+(fact[j]/num)))),(1/(m-1)))/div);
		  					else
		  					meunm[j][i]=(Math.pow((1/(dsq[j][i]+(fact[j]/num))),(1/(m-1)))/div);

							}
		  					else
		  					{
		  						for(int k=0;k<count;k++)
		  						{
									//System.out.println("val of 1/dsq[k][i] is"+(1/dsq[k][i]));
									div=div+Math.pow((1/(dsq[k][i]+(fact[k]/num))),(1/(m-1)));

								}
							//System.out.println(div);

							meunm[j][i]=(Math.pow((1/(dsq[j][i]+(fact[j]/num))),(1/(m-1)))/div);

						}
                                                   /* for(int k=0;k<count;k++)
		  					{
							div=div+Math.pow((1/(dsq[k][i]+(fact[k]/num))),(1/(m-1)));
					        	}
                            			meunm[j][i]=(Math.pow((1/(dsq[j][i]+(fact[j]/num))),(1/(m-1)))/div);*/
                                      }

	                                 System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("KADFLICM Error  ");
                                      }


                                   System.out.println("KADFLICM Classification has been Completed.");


                                                }
                                                else if(advclassi==3)
				              {
                                    {

                                    double fac=0.0;
                                    double maxa[]=new double[count];
                                    double mina[]=new double[count];
                                    double [][] dsw=new double[count][h*w];
                                     int ws=0;
                                     double lim=0.0;int d=0,sec=0;
                                     int xs,xe,ys,ye,xw,yw;
                                       if(h<w)
                                           sec=h;
                                       else
                                           sec=w;

                       div=0.0;

                      while(ws<=0 || (ws%2==0))
                       {
        String inpt = JOptionPane.showInputDialog("Please Enter The Value Of window size (1 < ws < "+sec+") \n(the value should be odd)");
			 ws = Integer.parseInt(inpt);
                      }

                      double gval[][]=new double[count][h*w];
                      xw=ws;
                      yw=ws;
                       System.out.println("\nWindow Size plicm="+ws);

                                    try
		  		{
                                       for(int j=0;j<count;j++)
		  			{
                                            mina[j]=65535;
                                        maxa[j]=-2;

                                            for(int y1=0;y1<h;y1++)//lim+1 to h-lim-1
                                            {
                                                for(int x1=0;x1<w;x1++)//lim+1 to w-lim-1
                                                {

                                                     fac=0.0;

                                                   // System.out.println("\nhere");
                                                        if(ws!=1)
                                                        {
                                                               if((x1-xw)<0)
                                                                      xs=1;
                                                                       else
                                                                  xs=x1-xw;

                                                              if(x1+xw>w)
                                                                      xe=w;
                                                                   else
                                                                    xe=x1+xw;

                                                                  if((y1-yw)<0)
                                                                          ys=1;
                                                                             else
                                                                            ys=y1-yw;

                                                                        if(y1+yw>h)
                                                                          ye=h;
                                                                                 else
                                                                          ye=y1+yw;

                                                        }
                                                        else
                                                        {
                                                            xs=0;ys=0;ye=0;xe=0;
                                                        }

                                                      int i=((y1)*w)+(x1);
                                               //  System.out.println("\ni="+i);

                                     	            for(int sd=ys;sd<ye;sd++)
                                                          {
                                                 //             System.out.println("\nwindow");
                                                     for(int gd=xs;gd<xe;gd++)
                                                              {
                                                                      d=((sd)*w)+(gd);
                                                                     if(sd!=y1 && gd!=x1)
                                                                        {
                                                                                                              for(int k=0;k<b;k++)
												     sub[k][0]= xnew[k][d]-xnew[k][i];

													 for(int k=0;k<b;k++)
													 subT[0][k]=sub[k][0];

													 for(int jh=0;jh<b;jh++)
													 {

                                                                                                         mul[0][jh]=0;
													 for(int k=0;k<b;k++)
													 mul[0][jh]+=subT[0][k]*c1[j][k][jh];

													 }

													 dsw[j][d]=0;
													 for(int k=0;k<b;k++)
													 dsw[j][d]+=mul[0][k]*sub[k][0];

                                                                     gval[j][i]+=((1/(1+dsw[j][d]))*(Math.pow((1-meu[j][d]),m))*(dsq[j][d]));
                                                                        }

                                                                  }

                                                                }



                                                }

	                                 System.gc();
                                 }
                               }
                                }
		  		catch(Exception e)
		  		{
                         	  System.out.println("PLICM Error  ");
                                      }

                                    try
					  		{
					  			for(int j=0;j<count;j++)
					  			{
					  				for(int i=0;i<h*w;i++)
					  				{
					  					div=0.0;


					  	if(txt.compareTo("Hypertangent")==0||txt.compareTo("Sigmoid")==0)
								  					{
								  						for(int k=0;k<count;k++)
								  						{

															if (dsq[k][i]<0)
								  							div=div+Math.pow((1/(1-(dsq[k][i]+gval[k][i]))),(1/(m-1)));
								  						else
								  						div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));

								  						}
								  						if(dsq[j][i]<0)
								  					meu[j][i]=(Math.pow((1/(1-(dsq[j][i]+gval[j][i]))),(1/(m-1)))/div);
													else
													meu[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);
													}
								  					else
		  					{

					  					for(int k=0;k<count;k++)
					  					{
					  						div=div+Math.pow((1/(dsq[k][i]+gval[k][i])),(1/(m-1)));
					  					}

					  					meu[j][i]=(Math.pow((1/(dsq[j][i]+gval[j][i])),(1/(m-1)))/div);

					  				}
								}
					  				System.gc();
					  			}

					  			double val1=0.0;
					  			double val2=0.0;
					  			double []arrayEta=new double[count];

					  			for(int i=0;i<count;i++)
					  			{
					  				val1=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
						  				val1=val1+Math.pow(meu[i][j],m);
						  			}

						  			val2=0.0;
						  			for(int j=0;j<h*w;j++)
						  			{
						  				val2=val2+(Math.pow(meu[i][j],m)*(dsq[i][j]+gval[i][j]));
						  			}

						  			arrayEta[i]=val2/val1;
					  			}

					  			for(int k=0;k<count;k++)
					  			{
					  				System.out.println("Eta value for class "+(k+1)+" : "+arrayEta[k]);
					  			}

					  			for(int i=0;i<count;i++)
					  			{
					  				for(int j=0;j<h*w;j++)
					  				{
					  					meu[i][j]=1/(1+(Math.pow(((dsq[i][j]+gval[i][j])/arrayEta[i]),(1/(m-1)))));
					  				}
					  			}

                             System.out.println("Meu has been Computed for Kernel Possibilistic Local information C-Means.");
					  		}
					  		catch(Exception e)
					  		{
					  			System.out.println("KPLICM Meu Calculation Error : "+e);
					  		}

                                          }
                                   System.out.println("KPLICM Classification has been Completed.");

                                                }



	 /////////////********** Minimization Of Energy ***********////////////////

	 	/*	if(context!=0)
		 	{
				int count2,counter=0;
				int iteration=0,k=0,N;
		 		double E1,E2,energyDiff;
		 		double t,tend,p;
		 		Fcm fcm1=new Fcm();
		 		count2=count;
				if(classi==2||classi==3)
					count2=count+1;
				double meu1[][]=new double[count2][h*w];
				double deviation[][]=new double[count2][h*w];

				String tempra=JOptionPane.showInputDialog(null,"Enter initial temprature");
				t=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Final Value Of Temprature(Small Float Value).");
				tend=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Total No. Of Iterations On Temprature(N Value).");
				N=Integer.parseInt(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Float Value Of Range 0.001 ( p Value).");
				p=Double.parseDouble(tempra);


				while(t>tend)		//while current temprature is greater than minimum temprature limit.
				{
					while((N>iteration)&&((h*w*p)>counter))
					{
						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								deviation[i][j]=(Math.sqrt(t)*meu[i][j])/count;
							}
						}

						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								meu1[i][j]=fcm1.normalRandom(meu[i][j],deviation[i][j]);
							}
						}

						E1=contextualEnergy(meu);
						E2=contextualEnergy(meu1);

						energyDiff=E2-E1;
						System.out.println("Diff= "+Math.exp(energyDiff/t));
						if((energyDiff>0)||(Math.exp(energyDiff/t)>=Math.random()))
						{
							for(int i=0;i<count2;i++)
							{
								for(int j=0;j<h*w;j++)
								{
									meu[i][j]=meu1[i][j];
								}
							}
							counter++;
							System.out.println("Meu i,j updated");
						}
						iteration++;
					}

					///////// Update Temprature //////////////

					k++;
					t=((Math.log(1+k)/Math.log(2+k))*t);
					iteration=0;
					counter=0;
				}
			}	*/


	 /////////////********** Classification Of Boundary Pixels ***********////////////////

	 		if(context!=0)
		 	{
				int count2,counter1,counter2,r1,c2;
				int iteration=0,k=0,N;
		 		double E1,E2,energyDiff;
		 		double t,tend;
		 		double sum=0.0;
		// 		Fcm fcm1=new Fcm();
		 		count2=count;
				if(classi==2||classi==3)


					count2=count+1;
				double meu1[][]=new double[count2][h*w];
				double deviation[][]=new double[count2][h*w];
				double ft[][]=new double[count2][h*w];
				double ft2[][]=new double[count2][h*w];
				int arr[]=new int[count2];

				String tempra=JOptionPane.showInputDialog(null,"Enter initial temprature");
				t=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Final Value Of Temprature(Small Float Value).");
				tend=Double.parseDouble(tempra);

				tempra=JOptionPane.showInputDialog(null,"Enter Total No. Of Iterations On Temprature(N Value).");
				N=Integer.parseInt(tempra);
				try
				{
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter row no. to check pixel value");
				r1=Integer.parseInt(br.readLine());
				System.out.println("Enter column no. to check pixel value");
				c2=Integer.parseInt(br.readLine());

				while(t>tend)		//while current temprature is greater than minimum temprature limit.
				{
					while(N>iteration)
					{
						for(int i1=0;i1<count2;i1++)
						System.out.println("Value of Meu(i,j) at pixel "+r1+","+c2+" class "+(i1+1)+" : "+meu[i1][r1*w+c2]);

						ft=BilImage.fcm.smoothing(meu,meu,count2,r1,c2);

					    double n1;
						int n2;
                        for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								boolean b=true;
							 	n1=Math.random();
								n1=n1*977;
								n2=(int)n1%count2;

								meu1[i][j]=meu[n2][j];

							}
						}
							ft2=BilImage.fcm.smoothing(meu1,meu,count2,r1,c2);

						energyDiff=0.0;
						counter1=0;
						counter2=0;
						for(int i=0;i<count2;i++)
						{
							for(int j=0;j<h*w;j++)
							{
								energyDiff=ft[i][j]-ft2[i][j];

								if(energyDiff>0)
								{
									meu[i][j]=meu1[i][j];
									counter1++;
								}
								else
								{
							/*		if(Math.exp(energyDiff/t)>=Math.random())
									{
									//	for(int j=0;j<count2;j++)
										{
											meu[i][j]=meu1[i][j];
										}
										counter2++;
									}	*/
								}
						  }
						}
						iteration++;
						System.out.println("Iteration "+iteration+" completed.");
						System.out.println("If updates "+counter1+" pixels");
						System.out.println("Else updates "+counter2+" pixels");
					}

					///////// Update Temprature //////////////

					k++;
					System.out.println("Current temprature value : "+t);
					t=((Math.log(1+k)/Math.log(2+k))*t);
					System.out.println("Temprature value after updation : "+t);
					iteration=0;
				}
				}
				catch(Exception e)
				{
					System.out.println("Temprature Exception : "+e);
				}
			}



      /******************** Saving the Meu matrix./****************************/
if((typ==1)||(typ==2))
				    {


            File file=null;
			JFileChooser chooser=new JFileChooser();
            int r=chooser.showSaveDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
			   file = chooser.getSelectedFile();
    		   try
     			{
     				int count1;
      				String name=file.toString();
					OutputStream fout=null;
					if(flago==0)
				    count1=count+1 ;
				    else
				    count1=count;



					 if(typ==2)


						{
							for(int j=0;j<count;j++)
							for(int i=0;i<h*w;i++)
							meu[j][i]= ((3*meu[j][i])-1)/2;
						  }

						  if(sa==1)//save dist is selected
						  {

				System.out.println("In save distance");


				        		double finalm[][]=new double[count][h*w];// array


								for(int i=0;i<h*w;i++)
									for(int j=0;j<count;j++)
									    finalm[j][i]=dsq[j][i];



								for( int j2=0;j2<count1;j2++)
						           {

						            	fout=new FileOutputStream(name + j2);


						                  for(int k1=0;k1<h*w;k1++)
				                              fout.write(((int)finalm[j2][k1]));

										System.out.println(name+j2);
										FileWriter out=new FileWriter(name+j2+".hdr");
										out.write("BANDS:      "+1+"\n");
										out.write("ROWS:    "+h+"\n");
										out.write("COLS:    "+w+"\n");
										out.close();

									}
									fout.close();




						  }



					else if(sa!=1)
					{
					int bt=0;

						if(advclassi==0 && classi!=12)
                                                {
                                                        System.out.println("\nBASE CLASSIFICATION");

                                                    for( int j2=0;j2<count1;j2++)
		            	{
		            		fout=new FileOutputStream(name + j2);

		            	      for(int k1=0;k1<h*w;k1++)
                    	          fout.write((int)((meu[j2][k1])*255.0));

							System.out.println(name+j2);
                                                    	FileWriter out=new FileWriter(name+j2+".hdr");
							out.write("BANDS:      "+1+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
							out.close();

						}

                                                }
                                                else if(advclassi==1 || advclassi==2||advclassi==3)
                                                {
                                                        System.out.println("\nADVANCED CLASSIFICATION");

                                                    for( int j2=0;j2<count1;j2++)
		            	{
		            		fout=new FileOutputStream(name + j2);

		            	      for(int k1=0;k1<h*w;k1++)
                    	          fout.write((int)((meunm[j2][k1])*255.0));

							System.out.println(name+j2);
                                                    	FileWriter out=new FileWriter(name+j2+".hdr");
							out.write("BANDS:      "+1+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
							out.close();

						}

                                                }

                                                else if(classi==12)
                                                {
                                                    if(b<=8)
                                                        bt=8;
                                                    else if(b>8 && b<=16)
                                                        bt=16;
                                                    else
                                                        bt=0;
                                                                   fout=new FileOutputStream(name+"syn");

                                                                            for(int y1=0;y1<h;y1++)
                                                                            {
                                                                                  for( int j2=0;j2<b;j2++)
		            	                                                  {

                                                                         for(int x1=0;x1<w;x1++)
                                                                               {
                                                                                     int ind=((y1)*w)+(x1);

                                                                                       fout.write((((int)(meun[j2][ind]))));
                                                                                   }

                                                                             }
                                                                         }
                                                      System.out.println(name+"syn");

                                                        System.out.println("\nSYNTHETIC IMAGE FORMATION");
							FileWriter out=new FileWriter(name+"syn.hdr");
                                                        out.write("BANDS:      "+b+"\n");
							out.write("ROWS:    "+h+"\n");
							out.write("COLS:    "+w+"\n");
                                                        out.write("INTERLEAVING:   BIL"+"\n");
                                                        out.write("DATATYPE: U"+bt+"\n");
							out.close();

                                                }
                                                fout.close();

					}
				}
catch(IOException e)
                {
                    System.out.println("Exception is :"+ e);
                }
			}

			con.setSelected(false);
			flago=1;
		    System.out.println("Fcm Completed");

	 }

				if(typ==3)

				{



			   File file=null;

			   double f1[]=new double[h*w];
            double m1[][]=new double[count][h*w];

			String min=tfhard.getText();
			double mini=Double.parseDouble(min);
			System.out.println("hard"+mini);

			JFileChooser chooser=new JFileChooser();
		 int r=chooser.showSaveDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				  file = chooser.getSelectedFile();
					 try
					 {
							int count1;
							String name=file.toString();
							OutputStream fout=null;
								if(flago==0)
										count1=count+1 ;
								else
						 				count1=count;
				System.out.println("count is"+count); //total number of classes

										 double max=0.0, temp=0.0;int c12,gz=1;

										 int c11=255/count; //label calculate

										  int l=1; int k2f=0,k2=0;


			 						System.out.println("label is"+c11);

										for(int k3=0;k3<h*w;k3++)
										{
												max=meu[0][k3];
													k2f=0;
												for(int k4=1;k4<count;k4++)
												{
													if (max<meu[k4][k3])
														{
															max=meu[k4][k3];
															k2f=k4;
														}
												}

												if(max<mini)
												f1[k3]=0;
												else
												f1[k3]=c11*(k2f+1);




											}
											if(sa==1)// if save distance selected
																	{

																			System.out.println("In sa");


															        		double finalm[][]=new double[count][h*w];// array


																			for(int i=0;i<h*w;i++)
																				for(int j=0;j<count;j++)
																				    finalm[j][i]=dsq[j][i];



																			for( int j2=0;j2<count1;j2++)
																	           {

																	            	fout=new FileOutputStream(name + j2);


																	                  for(int k1=0;k1<h*w;k1++)
															                              fout.write(((int)finalm[j2][k1]));

																					System.out.println(name+j2);
																					FileWriter out=new FileWriter(name+j2+".hdr");
																					out.write("BANDS:      "+1+"\n");
																					out.write("ROWS:    "+h+"\n");
																					out.write("COLS:    "+w+"\n");
																					out.close();

																				}
																				fout.close();


																			}

else if(sa!=1)
{
								fout=new FileOutputStream(name );
													for(int i=0;i<count;i++)
													{
														for(int k19=0;k19<h*w;k19++)
														{

												        	fout.write((((int)f1[k19])));// writing final array
																	}

													}

													System.out.println(name);
											FileWriter out=new FileWriter(name+".hdr");

													out.write("BANDS:      "+1+"\n");
														out.write("ROWS:    "+h+"\n");
														out.write("COLS:    "+w+"\n");
											out.close();




							fout.close();
						}

}





				catch(IOException e)
                {
                    System.out.println("Exception is :"+ e);
                }
			}

			con.setSelected(false);
			flago=1;
		    System.out.println("KFcm Completed");

		 }
	 }

    /****************** Code for Open the Signature File *****************************/

	   if(source==openitem)
	   {
            int by,cy;
            JFileChooser chooser=new JFileChooser();
            int r=chooser.showOpenDialog(this);
            if(r==JFileChooser.APPROVE_OPTION)
		    {
				File file = chooser.getSelectedFile();
				String name=file.getName();

				System.gc();
				validate();

                try
    		    {
                    InputStream f1=new FileInputStream(file);            //reading pixel values for each band
                    try
                  {
                   		int cd=1;
						while(cd!=-1)
						{

					        by=f1.read();
					        if(by==-1)
					         break;
                            by=by<<8;
                            cy=f1.read();
                            Double temp=new Double(by|cy);
                            pixels.addElement(temp);


                        }

                   }
                     catch(IOException e)
                     {
                        System.out.println("File Reading Error: "+e);
                     }
				     try
				     {
			              f1.close();
						  System.gc();
					 }
					 catch(IOException e)
					 {
					 	System.out.println("File Closing Error : "+e);
					 }

                }
                catch(FileNotFoundException e)
                {
                	System.out.println("File Error : "+e);
                }

				len=pixels.size();
				pixelsarray=new Double[len];
				pixels.copyInto(pixelsarray);
				pixels_array=new double[len];

		        for(int m=0;m<len;m++)
		        {

		       		pixels_array[m]=pixelsarray[m].doubleValue();     //converting double object to primitive double**/
		       		System.out.println(pixels_array[m]);
		        }


		        for(int j=0;j<len;j++)
		        {
		        	Double t=new Double(pixels_array[j]);               // for removing pixel values

		         	pixels.removeElement(t);

		        }

			 }


	        /////////////////// After Opening The File. //////////////////////////

	        len=len-1;
	        int bands=imgdata8.bands();
            int l=len/bands;
            double[][] pixel=new double[bands][l];

			double a[][]=new double[bands][l];
			double b[]=new double[bands];

			double sum;

			System.out.println("Image Bands : "+bands);
			System.out.println("No of Clicks : "+l);
			for(int j=0;j<l;j++)
			 	for(int i=0;i<bands;i++)
			 		a[i][j]=pixels_array[j*bands+i];

			for(int i=0;i<bands;i++)
			{
				sum=0;
				for(int j=0;j<l;j++)
					sum+=a[i][j];
				b[i]=sum/l;
			}

			for(int i=0;i<bands;i++)
			{
				for(int j=0;j<bands;j++)
				{
					sum=0;
					for(int k=0;k<l;k++)
					{
						sum=sum+((a[j][k]-b[j])*(a[i][k]-b[i]));
					}
					c[count][i][j]=sum/(l-1);
				}
			}

			/*System.out.println("Varience Co-Varience Matrix is =");
			for(int i=0;i<bands;i++)
			{
				System.out.println();
				for(int j=0;j<bands;j++)
				{
					System.out.print("\t"+c[count][i][j]);
				}
			}*/

			System.out.println();
			for(int i=0;i<bands;i++)
				 model.setValueAt(b[i],row,i+1);
			row++;
			count++;
			System.out.println("Number of Classes : "+count);

       }
       if(source==openhashitem)
	   	   	   {
	   	               int by,cy;String tempo;int index=0;

	   	   			String name=null;


	   	               JFileChooser chooser=new JFileChooser();
	   	               int r=chooser.showOpenDialog(this);
	   	               if(r==JFileChooser.APPROVE_OPTION)
	   	   		    {
	   	   				File file = chooser.getSelectedFile();
	   	   				name=file.getName();


	   	   			 				 				System.gc();
	   	   			 				 				validate();

	   	   			 				                 try
	   	   			 				     		    {
	   	   			 				                     InputStream f1=new FileInputStream(file);            //reading pixel values for each band
	   	   			 				                     DataInputStream dis=new DataInputStream(f1);
	   	   			 				                     try
	   	   			 				                   {    double val5;

	   	   			 				 						while(true)
	   	   			 				 						{

	   	   			 				 					       val5=dis.readDouble();
	   	   			 				 					       pixels.addElement(val5);
	   	   			 				 					       System.out.println("value read from file"+val5);


	   	   			 				                         }
	   	   			 				                         //System.out.println("variance and covariance are not calculated");

	   	   			 				                    }
	   	   			 				                      catch(IOException e)
	   	   			 				                      {
	   	   			 				                         //System.out.println("File Reading Error2: "+e);
	   	   			 				                      }
	   	   			 				 				     try
	   	   			 				 				     {
	   	   			 				 			              f1.close();
	   	   			 				 						  System.gc();
	   	   			 				 					 }
	   	   			 				 					 catch(IOException e)
	   	   			 				 					 {
	   	   			 				 					 	System.out.println("File Closing Error : "+e);
	   	   			 				 					 }

	   	   			 				                 }
	   	   			 				                 catch(FileNotFoundException e)
	   	   			 				                 {
	   	   			 				                 	System.out.println("File Error : "+e);
	   	   			 				                 }

	   	   			 				 				len=pixels.size();
	   	   			 				 				pixelsarray=new Double[len];
	   	   			 				 				pixels.copyInto(pixelsarray);
	   	   			 				 				pixels_array=new double[len];

	   	   			 				 		        for(int m=0;m<len;m++)
	   	   			 				 		        {

	   	   			 				 		       		pixels_array[m]=pixelsarray[m].doubleValue();     //converting double object to primitive double**/
	   	   			 				 		       		System.out.println(pixels_array[m]);
	   	   			 				 		        }


	   	   			 				 		        for(int j=0;j<len;j++)
	   	   			 				 		        {
	   	   			 				 		        	Double t=new Double(pixels_array[j]);               // for removing pixel values

	   	   			 				 		         	pixels.removeElement(t);

	   	   			 				 		        }



	   	   		 }


	   	   	        /////////////////// After Opening The File. //////////////////////////


	   	   	        int bands=imgdata8.bands();
	   	               int l=len/bands;
	   	               double[][] pixel=new double[bands][l];

	   	   			double a[][]=new double[bands][l];
	   	   			double b[]=new double[bands];

	   	   			double sum;

	   	   			System.out.println("Image Bands : "+bands);
	   	   			System.out.println("No of Clicks : "+l);

	   	   			for(int j=0;j<l;j++)
	   	   			 	for(int i=0;i<bands;i++)
	   	   			 		a[i][j]=pixels_array[j*bands+i];

	   	   			for(int i=0;i<bands;i++)
	   	   			{
	   	   				sum=0;
	   	   				for(int j=0;j<l;j++)
	   	   					sum+=a[i][j];
	   	   				b[i]=sum/l;
	   	   			}


	   	   			System.out.println();
	   	   			for(int i=0;i<bands;i++)
	   	   				 {   model.setValueAt(b[i],row,i+1);
	   	   				    System.out.println("mean at the table  "+model.getValueAt(row,i+1));
	   	   			     }
	   	   			row++;
	   	   			count++;
	   	   			System.out.println("Number of Classes : "+count);
	   	   			//clicks_fin=l;
       }
  }
  public void actionPerformed(ActionEvent event)
    {
    Object source=event.getSource();
    for(int i=0;i<9;i++)
         {

           if(source==openkitem[i])
           sel_kernel.setText(kernels[i]);

           if(source==openk2item[i])
            sel_2kernel.setText(kernels[i]);

         }

         if(source==box1)
             {
              typ=1;
             }

         if(source==box2)
             {
             //    System.out.println("State-2 click radio btn");
              typ=1;
              tfhard.setEnabled(false);

             }

         if(source==box3)
             {
              typ=2;
              tfhard.setEnabled(false);

             }
 if(source==box4)
             {
              typ=3;
              tfhard.setEnabled(true);

             }
             if(source==box5)
			 		              {
			 		               sa=1;
			            }



         if(source==fcm)
             {
              classi=1;
             }

         if(source==noiseOut)
             {
              classi=2;
             }

         if(source==noiseWith)
             {
              classi=3;
             }

         if(source==entropy)
             {
              classi=4;
             }

         if(source==pcm)
             {
              classi=5;
             }
         if(source==mpcm)
             {
              classi=6;
             }
         if(source==ipcm)
             {
              classi=7;
             }
         if(source==mlc)
             {
              classi=8;
             }
               if(source==fcms)
             {
              classi=9;
             }

                if(source==btsyn)
	    {
           classi=12;
       }
       if(source==pcms){
       	classi=13;
       }

         if(source==flicm)
             {
              advclassi=1;
             }
               if(source==adflicm)
             {
              advclassi=2;
             }

             if(source==plicm)
             {
              advclassi=3;
             }




         if(source==con)
             {
              context=1;
             }

         if(source==dis)
             {
              context=2;
             }
         if(source==h1)
            {
            	st=1;
            }
         if(source==h2)
            {
            	st=2;
            }
         if(source==h3)
            {
            	st=3;
            }
         if(source==h4)
            {
            	st=4;
            }

          txt=sel_kernel.getText();
        HashMap<String , Double> mymap=new HashMap<String , Double>();
        String tmpp=tfm.getText();
        if(tmpp.compareTo("")==0&&source==btClasi) {

        for(int i=0; i<9; i++)
            mymap.put(kernels[i], mv_kernels[i]);
        m1 = mymap.get(txt);
        m2=m1;
       String tmp=sel_2kernel.getText();
        if(tmp.compareTo("None")==0)
            m2 = m1;
        else if(cb.isSelected())
            m2 = mymap.get(sel_2kernel.getText());
        String st=meu_mix.getText();
        if(st.compareTo("")==0)
            meu_mix.setText("1");

        mix_m = Double.parseDouble(meu_mix.getText());
        mix_m = m1*mix_m + (1.0-mix_m)*m2;

        tfm.setText(mix_m+"");
        }
        kernel_mixture(source);

      /* Second Kernel Mixing starts--------------------------------------------------*/
        if(cb.isSelected()&&source==btClasi)
      {
          flag_second=0;
         txt=sel_2kernel.getText();
         dsq_temp =new double[count][h*w];
         for(int l1=0;l1<count;l1++)
             for(int i1=0;i1<h*w;i1++)
                 dsq_temp[l1][i1]=dsq[l1][i1];
         kernel_mixture(source);
      }
        flag_second=1;
        //tfm.setText("");

    }


	   ///////////***********Calculating Contextual for Classifier*************//////////////

  public double contextualEnergy(double [][]meu2)
  {

  			energy1=0.0;
	   		energy2=0.0;
	   		temp=0.0;
	   		temp1=0.0;
	   		temp2=0.0;
	   		temp3=0.0;

	   		if(context==1)
	   		{
	   			/////////////// Calculation Of Second Part Of Formula //////////////

	   			for(int i=0;i<h*w;i++)
	   			{
	   				for(int j=0;j<count;j++)
	   				{
	   					for(int k=0;k<8;k++)
	   					{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}
	   						try{

	   						temp=meu2[j][i]-meu2[j][j1];
	   					}catch(Exception e)
	   					{
	   						System.out.println("k= "+k);
	   						System.out.println(e);
	   					}
	   						temp=temp*temp*beta;
	   						energy1=energy1+temp;
	   					}
	   				}
	   			}

	   			energy1=energy1*lem;


				////////////////// FCM With Contextual ////////////////


				if(classi==1)
				{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					energy2=energy2+((Math.pow(meu2[j][i],m))*dsq[j][i]);
		   				}
		   			}

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification with Contextual is Completed for Fuzzy C-Means.");
	   			}


	   			////////// Noise clustering Without Entropy & With  Contextual ////////////////


		   		if(classi==2)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}
		   			energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification with Contextual is completed for Noise clustering Without Entropy.");
		   		}


	   			////////// Noise clustering With Entropy &  Contextual ////////////////


		   		if(classi==3)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count+1;j++)
		   				{
		   					temp3=temp3+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp3=temp3*neo;

	   				energy2=temp1+temp2+temp3;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification of Noise Clustering With Entropy is Completed.");
		   		}


		   		////////// Fuzzy C-Means With Entropy & Contextual ////////////////

		   		if(classi==4)
		   		{

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp1=temp1+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp2=temp2*neo;

	   				energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);
		   			System.out.println("Classification of FCM With Entropy is Completed.");
		   		}


	   		}


	////////////*************** Classifier With Discontinuity Adaptive Prior ***********/////////


	   		else
	   		{

		   		/////////// Computing Second Part Of Formula ///////////////////

		   		double eta=0.0;
		   		temp=0.0;temp1=0.0;temp2=0.0;temp3=0.0;

		   		for(int i=0;i<h*w;i++)
		   		{
		   			for(int j=0;j<count;j++)
		   			{
		   				for(int k=0;k<8;k++)
		   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						eta=Math.abs(meu2[j][i]-meu2[j][j1]);

	   						energy1=energy1+((gama*eta)-((gama*gama)*(Math.log(1+(eta/gama)))));


		   				}
		   			}
		   		}

		   		energy1=energy1*lem;


		   		////////// FCM With Discontinuity Adaptive Prior////////////////

		   		if(classi==1)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					energy2=energy2+((Math.pow(meu2[j][i],m))*dsq[j][i]);
		   				}
		   			}

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification with Discontinuity Adaptive Prior is Completed for Fuzzy C-Means.");
		   		}



		   		////////// Noise clustering Without Entropy & With Discontinuity Adaptive Prior////////////////

		   		if(classi==2)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}
		   			energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Contextual of Noise clustering Without Entropy & With Discontinuity Adaptive Prior is completed.");
		   		}



		   		////////// Noise clustering With Entropy & Discontinuity Adaptive Prior////////////////

		   		if(classi==3)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				temp1=temp1+(Math.pow(meu2[count][i],m));
		   			}
		   			temp1=temp1*del;

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count+1;j++)
		   				{
		   					temp3=temp3+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp3=temp3*neo;

	   				energy2=temp1+temp2+temp3;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification of Noise Clustering With Entropy & Discontinuity Adaptive Prior is Completed.");
		   		}



		   		////////// Fuzzy C-Means With Entropy & Discontinuity Adaptive Prior////////////////

		   		if(classi==4)
		   		{
		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp1=temp1+(meu2[j][i]*dsq[j][i]);
		   				}
		   			}

		   			for(int i=0;i<h*w;i++)
		   			{
		   				for(int j=0;j<count;j++)
		   				{
		   					temp2=temp2+(meu2[j][i]*(Math.log10(meu2[j][i])));
		   				}
		   			}
	   				temp2=temp2*neo;

	   				energy2=temp1+temp2;

		   			energy2=(1-lem)*energy2;
		   			energy=energy1+energy2;

		   			System.out.println("Energy = "+energy);

		   			System.out.println("Classification of FCM With Entropy & Discontinuity Adaptive Prior is Completed.");
		   		}


	    	}
	    	return energy;
  }


  ///////////************* Normal Random Function ***************//////////////////////

  public double normalRandom(double mean,double stdDev)
  {

  	double val;

  	Random rand=new Random();
  	val=stdDev*rand.nextGaussian()+mean;
  	return val;

  }


  ////////////////************************* Smoothing Of Boundry Pixels *******************////////////////

  public double[][] smoothing(double [][]meu3,double [][]meu4,int count1,int r1,int c1)
  {

  	double temp4=0.0,temp5=0.0;
  	double eta=0.0;
  	double sum=0.0;
  	ReadImageData obj=new ReadImageData();
  	h=obj.rows();
  	w=obj.columns();
  	beta=Float.parseFloat(tfbeta.getText());
  	gama=Float.parseFloat(tfgama.getText());
  	lem=Float.parseFloat(tflem.getText());

  	double fl[][]=new double[count1][h*w];
  	double fp[][]=new double[count1][h*w];
  	double ft1[][]=new double[count1][h*w];

  	for(int i=0;i<count1;i++)
  	{
  		for(int j=0;j<h*w;j++)
  		{
  			fl[i][j]=meu3[i][j];

  		}
  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FL at pixel "+r1+","+c1+" class "+(i1+1)+" : gama : "+gama+" : "+fl[i1][r1*w+c1]);




  	/////////////////// For Smoothing Prior /////////////////////////

  	if(context==1)
  	{

		for(int j=0;j<count1;j++)
   		{
   			for(int i=0;i<h*w;i++)
   			{
   				temp5=0.0;

   				for(int k=0;k<8;k++)
   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

					temp4=Math.abs((meu3[j][i]-meu4[j][j1]));
					temp5=temp5+(temp4);
   				}

   				fp[j][i]=beta*temp5/8;
   			}
   		}
   		System.out.println("beta"+ beta);

  	}

  	///////////////// For Discontinuity Adaptive Prior /////////////////

  	else
  	{	System.out.println("Dis. adaptive prior"+beta+" gama "+gama);
  	    System.out.println("status"+st);
  		for(int j=0;j<count1;j++)
   		{
   			for(int i=0;i<h*w;i++)
   			{
   				temp4=0.0;

   				for(int k=0;k<8;k++)
   				{

	   						if(k==0)
	   						{
	   							j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

	   						if(k==1)
	   						{
	   								j1=(i-w)+1;
	   							if((i+1)%w==0)
	   								j1=i-w;
	   							if(j1<0);
	   								j1=i;
	   						}

	   						if(k==2)
	   						{
	   							j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==3)
	   						{
	   							j1=i+w+1;
	   							if(j1>=h*w)
	   								j1=i+1;
	   							if((i+1)%w==0)
	   								j1=i;
	   						}

	   						if(k==4)
	   						{
	   							j1=i+w;
	   							if(j1>=h*w)
	   								j1=i;
	   						}

	   						if(k==5)
	   						{
	   							j1=(i+w)-1;
	   							if(j1>=h*w)
	   								j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==6)
	   						{
	   							j1=i-1;
	   							if(j1<0)
	   								j1=i;
	   							if(i%w==0)
	   								j1=i;
	   						}

	   						if(k==7)
	   						{
	   							j1=(i-w)-1;
	   							if(i%w==0)
	   								j1=i-w;
	   							if(j1<0)
	   								j1=i;
	   						}

					        eta=Math.abs(meu3[j][i]-meu4[j][j1]);
					        if(st==4)
	   						{

	   						temp4=temp4+((gama*eta)-((gama*gama)*(Math.log(1+(eta/gama)))));
	   					    }
	   						if(st==3)
	   						{

	   						temp4=temp4+(gama*(Math.log(1+((eta*eta)/gama))));
	   					    }
	   						if(st==2)
	   						{

	   						temp4=temp4+(-(gama/(1+(eta*eta)/gama)));
	   				    	}
	   						if(st==1)
	   						{

	   						temp4=temp4+(Math.pow((- gama*2.7182818284),(-(eta*eta)/gama)));
	   					    }

   				}

   				fp[j][i]=temp4;
   			}
   		}

  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FP at pixel "+r1+","+c1+" class "+(i1+1)+" : "+fp[i1][r1*w+c1]);


  	for(int j=0;j<count1;j++)
  	{
  		for(int i=0;i<h*w;i++)
  		{

  			ft1[j][i]=((1-lem)*fl[j][i])+(lem*fp[j][i]);
  		}
  	}

	for(int i1=0;i1<count1;i1++)
	System.out.println("Value of FT at pixel "+r1+","+c1+" class "+(i1+1)+" : "+ft1[i1][r1*w+c1]);

  	return ft1;

  }
}

///// CLASS TO CALCULATE THE CORRELATION COEFFICIENT//////

class Corelation
{
public static double Correlation(double[] xs, double[] ys) {

    double sx = 0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sx += x;
      sy += y;
      sxx += x * x;
      syy += y * y;
      sxy += x * y;
    }


// covariation
    double cov = sxy / n - sx * sy / n / n;
    // standard error of x
    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
    // standard error of y
    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

    // correlation is just a normalized covariation
    return cov / sigmax / sigmay;
  }
}

class CosCorelation
{


public static double CosCorrelation(double[] xs, double[] ys) {

    double sx = 0.0;
    double sy = 0.0;
    double sxx = 0.0;
    double syy = 0.0;
    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sx += x;
      sy += y;
      sxx += x * x;
      syy += y * y;
      sxy += x * y;
    }


// covariation
    double cov = sxy ;
    // standard error of x
    double sigmax = Math.sqrt(sxx);
    // standard error of y
    double sigmay = Math.sqrt(syy);

    // correlation is just a normalized covariation
    return cov / sigmax / sigmay;

  }
}
class TanD
{


public static double TanDistance(double[] xs, double[] ys) {

    int n = xs.length;
    int b=n;
   double sub[][]=new double[b][1];
  double subT[][]=new double[1][b];
   double mul[][]=new double[1][b];
   double angle=0.0,dsq=0.0,value=0.0;



    for(int i = 0; i < n; ++i) {


                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (xs[k]*ys[k]);  //filling where sub matrix is added with the value of the sid
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(xs[k]*xs[k]);//mul square to find its magnitude
                                    subT[0][j]+=(ys[k]*ys[k]);//mean is pre defined squaring of sub is stored in subT
                             /*      if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }


                            dsq=0.0;//the main matrix in which the values are supposed to be filled
                            for(int k=0;k<b;k++)
                            {

                                if(mul[0][k]==0)
                                {
                                    dsq=0.0;//if value is passed as 0.0 then the dsq element is supposed to be fileed with 0 to avoid NaN excception
                                }
                                else //the values are filled in the dsq matrix
                                dsq=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq;
                                angle = (Math.acos(value)*180)/Math.PI;	//it refers to tetha where theta is the cos inverse of the value,the values are supppoes to be radian
                              double tan_value=Math.tan(angle*Math.PI/180);//this is the intergral part the sin for the value is stored in the sin_value

                             dsq=tan_value;//the final value is stored in the dsq
                            //  dsq=angle;

		            }

    }


    return dsq;

  }
}



class SinD
{


public static double SinDistance(double[] xs, double[] ys) {

    int n = xs.length;
    int b=n;
   double sub[][]=new double[b][1];
  double subT[][]=new double[1][b];
   double mul[][]=new double[1][b];
   double angle=0.0,dsq=0.0,value=0.0;



    for(int i = 0; i < n; ++i) {


                            for(int j=0;j<b;j++)//b is no of bands
			    {
                                sub[j][0]=0;//sub is 2d array
                                    for(int k=0;k<b;k++)
                                        sub[j][0]+= (xs[k]*ys[k]);  //filling where sub matrix is added with the value of the sid
                            }

                            for(int j=0;j<b;j++)
                            {
                                mul[0][j]=0;
                                subT[0][j]=0;
                                for(int k=0;k<b;k++)
                                {
                                    mul[0][j]+=(xs[k]*xs[k]);//mul square to find its magnitude
                                    subT[0][j]+=(ys[k]*ys[k]);//mean is pre defined squaring of sub is stored in subT
                                  /* if(k==b-1)
                                    {
                                        mul[0][j]=Math.sqrt(mul[0][j]);//to find magnitude
                                        subT[0][j]=Math.sqrt(subT[0][j]);//to find magnitude
                                    }*/
                                }
                            }


                            dsq=0.0;//the main matrix in which the values are supposed to be filled
                            for(int k=0;k<b;k++)
                            {

                                if(mul[0][k]==0)
                                {
                                    dsq=0.0;//if value is passed as 0.0 then the dsq element is supposed to be fileed with 0 to avoid NaN excception
                                }
                                else //the values are filled in the dsq matrix
                                dsq=(sub[k][0])/((Math.sqrt(mul[0][k])*Math.sqrt(subT[0][k])));

                                value= dsq;
                                angle = (Math.acos(value)*180)/Math.PI;	//it refers to tetha where theta is the cos inverse of the value,the values are supppoes to be radian
                              double sin_value=Math.sin(angle*Math.PI/180);//this is the intergral part the sin for the value is stored in the sin_value

                              dsq=sin_value;//the final value is stored in the dsq [][]
//dsq=angle;
		            }

    }


    return dsq;

  }
}



class Sid
{


public static double SidDistance(double[] xs, double[] ys) {


    int n = xs.length;
    int b=n;
    double sid=0.0;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];


              double p=0.0;
                   double [] ps=new double[b];  //   System.out.println("\nBands:"+Bands);

			 for(int s=0;s<b;s++)
                         {   p =p+xs[s];
                            }
                         for(int s=0;s<b;s++)
                              ps[s]=xs[s]/p;


                        double q=0.0;
                      for(int s=0;s<b;s++)
                       q =q+ys[s];

                      double [] qs=new double[b];
                      for(int s=0;s<b;s++)
                      {
                          qs[s]=ys[s]/q;
                      }



        if(q==0.0)
        {
           for(int s=0;s<b;s++)
            qs[s]=0.0;
        }

                 double a_b[]=new double[b];
                 double b_a[]=new double[b];
                   double fac[]=new double[b];
                   double fact[]=new double[b];

                    for(int s=0;s<b;s++)
                    {
                        fac[s]=ps[s]/qs[s];
                        fact[s]=qs[s]/ps[s];

                    }


                       for(int s=0;s<b;s++)
                       {
                           if(qs[s]==0.0)
                           {
                               a_b[s]=0.0;
                               b_a[s]=0.0;
                           }
                           else
                           {
                               a_b[s]=ps[s]*Math.log10(fac[s]);
                                b_a[s]=qs[s]*Math.log10(fact[s]);
                           }
                       }


                      double sida_b=0.0,sidb_a=0.0;
                      for(int s=0;s<b;s++)
                      {
                          sida_b=sida_b+a_b[s];
                          sidb_a=sidb_a+b_a[s];

                      }

                       sid=sida_b+sidb_a;

                      }
 // System.out.println("\nSid:"+sid);
   return sid;

  }

}
class ScaD
{
    public static double ScaDistance(double []xs,double []ys)
    {


    double si=0.0;
    double sj=0.0;
    double sij=0.0;
    double nsij=0.0;
    double r_num=0.0;
    double si2=0.0;
    double sj2=0.0;
    double s_i2=0.0;
    double s_j2=0.0;
    double nsi2=0.0;
     double  nsj2=0.0;
     double res=0.0;
     double r_dem=0.0;
     double sca1=0.0;
     double sca2=0.0;
    int n = xs.length;


    for(int i = 0; i < n; ++i)//n is no of bands
    {
      double x = xs[i];
      double y = ys[i];
      si+=x;
      sj+=y;
      sij+=x*y;
      si2+=x*x;
      sj2+=y*y;



    }

    nsij=n*sij;
    r_num=nsij-(si*sj);

    s_i2=si*si;
    s_j2=sj*sj;
    nsi2=n*s_i2;
    nsj2=n*s_j2;
    System.out.println("r_num="+r_num);
   r_dem=Math.sqrt((nsi2-s_i2)*(nsj2-s_j2));
   System.out.println("r_dem="+r_dem);
   if(r_dem==0.0)
   {
       r_dem=1.0;
   }
   if(r_num==0.0)
   {
       r_num=1.0;

   }
   res=r_num/r_dem;

   sca1=(res+1)/2;System.out.println("sca1="+sca1);
   sca2=(Math.acos(sca1));System.out.println("sca2="+sca2);
  // System.out.println("\nvalue="+sca2);
    return sca2;
    }
}


class Euclidean
{


public static double EuclideanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sxy += (x-y)*(x-y);
    }

    double ans=Math.sqrt(sxy);

    return ans;

  }
}


class Manhattan
{


public static double ManhattanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      sxy +=(x-y);
    else
      sxy+=(y-x);
    }

    return sxy;

  }
}

class Chessboard
{


public static double ChessboardDistance(double[] xs, double[] ys) {

    double max=0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(i==0)
    {if(x>=y)
      max=(x-y);
    else
      max=(y-x);

    }

    else{
    double temp;
    if(x>=y)
      temp=(x-y);
    else
      temp=(y-x);
    if(temp>max)
        max=temp;
    }
    }
    return max;

  }
}

class Braycurtis
{


public static double BraycurtisDistance(double[] xs, double[] ys) {

    double N = 0.0;
    double D = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      N +=(x-y);
    else
      N+=(y-x);
    D+=(x+y);
    }

    return N/D;

  }
}

class Canberra
{


public static double CanberraDistance(double[] xs, double[] ys) {

    double N = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    N+=Math.abs(x-y)/(Math.abs(x)+Math.abs(y));
    }

    return N;

  }
}

class Mean
{


public static double MeanDistance(double[] xs, double[] ys) {

    double sxy = 0.0;

    int n = xs.length;

    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

    if(x>=y)
      sxy +=(x-y);
    else
      sxy+=(y-x);
    }

    return sxy/n;

  }
}

class Median
{


public static double MedianDistance(double[] xs, double[] ys) {



    int n = xs.length;

    double sxy[]=new double[n];
    for(int i = 0; i < n; ++i) {
      double x = xs[i];
      double y = ys[i];

      sxy[i]=Math.abs(x-y);
    }

     Arrays.sort(sxy);
   if(n%2!=0)
       return sxy[n/2];
   else
   {
       return (sxy[n/2]+sxy[n/2-1])/2;
   }
  }
}
class NormalisedEuclidean{

    public static double mean(double xs[])
    {
        int len=xs.length;
        double mean=0;
        for(int i=0;i<len;i++)
        {
            mean+=xs[i];
        }
        return mean/len;
    }

    public static double NormalisedEuclideanDistance(double xs[],double ys[]){
        int bands=xs.length;
        double seedmean=mean(xs);
        double samplemean=mean(ys),meandiff;
        meandiff=seedmean-samplemean;
        double numerator=0,denominator=0;
        for(int i=0;i<bands;i++)
        {
            numerator+=Math.pow(meandiff+ys[i]-xs[i],2);
            denominator+=Math.pow(xs[i]-seedmean, 2)+Math.pow(ys[i]-samplemean,2);
        }
        return numerator/(bands*denominator);
    }
}

class RegionGrow
{
  public static int ycod;
public static int xcod;
static int xw=BilImage.wid;     //half the Width of region grow window
  static int yw=BilImage.hei;     //half the Height of region grow window
  static double threshold=(BilImage.thres); // Threshold value for region grow samples


 public static  double yratio []=new double[DemoPanel.xratios.length];

static  int xs; //Cordinates For Starting and ending Points of Region Grow WIndow
static  int ys;
static  int xe;
static  int ye;

static int width1;
static int height1;
static int myflag;
static int shiftflag;

private static ReadImageData Data1;
static int band1;
static int samplecount;
static private File ImageFile;
static int count=0;
static int count1=0;
static int turn=1;
static double maxS,minS;
static double maxSn,minSn;
static double maxT,minT;
static double maxSc,minSc;

static double maxE,minE;
static double maxM,minM;
static double maxCB,minCB;
static double maxBC,minBC;
static double maxC,minC;
static double maxMean,minMean;
static double maxMedian,minMedian;
static double maxNE,minNE;

public static void windowmaker(int xclickcor, int yclickcor, int width,int height)   //Function to calculate Window size
  {
    if((xclickcor-xw)<0)
        xs=1;
      else
        xs=xclickcor-xw;
    System.out.println("xstart :" +xs);

      if(xclickcor+xw>width)
          xe=width;
      else
          xe=xclickcor+xw;
      System.out.println("xend :" +xe);

      if((yclickcor-yw)<0)
        ys=1;
      else
        ys=yclickcor-yw;
      System.out.println("ystart :" +ys);

      if(yclickcor+yw>height)
          ye=height;
      else
          ye=yclickcor+yw;
      System.out.println("yend :" +ye);

}


  public static void Bandratiocal() throws FileNotFoundException
  {
	  ReadImageData ImgData1 = new ReadImageData();
		ImageFile = ImgData1.imagefile();

		ImgData1.read(ImageFile);
		//threshold=threshold/100;
		//threshold=threshold*Math.pow(100,DemoPanel.clicks);
         File file=null;
		  JFileChooser chooser=new JFileChooser();
         int r=chooser.showSaveDialog(chooser);
         if(r==JFileChooser.APPROVE_OPTION)
	      {
	       	file = chooser.getSelectedFile();
		try
		{
			String name=file.toString();
				System.out.println(name);
            FileOutputStream fout=new FileOutputStream(name);
		DemoPanel pane=new DemoPanel();
			imagetable6 Table6;
		System.out.println("Table Opened");
		int Noclicks=pane.retclicks();
		Table6=new imagetable6();
		Table6=new imagetable6(Noclicks);
		Table6.setSize(550,300);
		Table6.setVisible(true);
		xw=BilImage.wid;      //half the Width of region grow window
		yw=BilImage.hei;     //half the Height of region grow window
        threshold=(BilImage.thres); // Threshold value for region grow samples
		System.out.println("Threshold: "+threshold);

       for(xcod=xs;xcod<=xe;xcod++)
      {
                for(ycod=ys;ycod<=ye;ycod++)
             {

                    if(xcod==xs&&ycod==ys)
                        turn=1;
                            try
                            {

                            	FileInputStream fin_band=new FileInputStream(ImageFile);
                            	 FileOutputStream fout_band[]=new FileOutputStream[band1];
                            	 FileChannel fchan_in = fin_band.getChannel();
                                 try
                           	  {
                           	 	 for(int k=0; k<band1; k++)
                            	  	 {
                            			fin_band = new FileInputStream("Data" + BilImage.name +  k);


                            	     }
                            	  }
                            	  catch(Exception e)
                            	  {
                            		System.out.println("file Open Error in click : "+e);
                            		e.printStackTrace();
                            	  }
					Data1 = new ReadImageData();
					width1 = Data1.columns();
					height1 = Data1.rows();
					band1= Data1.bands();


                                        	//System.out.println("Hell");
					MappedByteBuffer mBuf;
					int bvalues[] = new int[band1];
					long lstart;
					byte b4,b5;
					int p1,p2;
					int temp,tempCount;
					int z,nob=1;
                                     System.gc();
                                     if(picture.shiftFlag==8)
                                     {
                     for(int ii=0; ii<band1; ii++)
					{

						lstart = (ycod * width1*band1) + (ii * width1)+ xcod;
						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

							b4 = mBuf.get();

							bvalues[ii] = (b4<<24) >>> 24;
					}
                                     }
                                     else
                                     {
                                    	 for(int ii=0; ii<band1; ii++)
                     					{
                                         	nob=2;
                     						lstart = ((ycod * width1*band1) + (ii * width1)+ xcod)*2;
                     						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

                     							b4=b5 = mBuf.get();
                     							p1 = b4<< 24;
            									p2 = (b5 <<24 ) >>> 8;
            									tempCount = p1 | p2;
            									tempCount = tempCount >>> 16;
                     							bvalues[ii] = tempCount;
                     					}
                                     }
                        for( z=0;z<band1;z++)
						yratio[z]=bvalues[z];
 				}
				catch(Exception e)
				{
					System.out.println(e);
					e.printStackTrace();
				}
                   if(BilImage.rE.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        minE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)>maxE)
                           maxE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                        if(Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)<minE&&Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)!=0)
                          minE=Euclidean.EuclideanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
                     if(BilImage.rTan.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        minT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(TanD.TanDistance(DemoPanel.xratios,yratio)>maxT)
                           maxT=TanD.TanDistance(DemoPanel.xratios,yratio);
                        if(TanD.TanDistance(DemoPanel.xratios,yratio)<minT)
                          minT=TanD.TanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(BilImage.rSca.isSelected()==true)
                   {
                        if(turn==1)
                    {
                        maxSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        minSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(ScaD.ScaDistance(DemoPanel.xratios,yratio)>maxSc)
                           maxSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                        if(ScaD.ScaDistance(DemoPanel.xratios,yratio)<minSc)
                          minSc=ScaD.ScaDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
              //   System.out.println("the maxsc is="+maxSc);
                // System.out.println("the min Sc i s="+minSc);

                   }

                   if(BilImage.rSin.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        minSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(SinD.SinDistance(DemoPanel.xratios,yratio)>maxSn)
                           maxSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                        if(SinD.SinDistance(DemoPanel.xratios,yratio)<minSn)
                          minSn=SinD.SinDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }



                    if(BilImage.rSid.isSelected()==true)
                {
                    if(turn==1)
                    {
                        maxS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        minS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Sid.SidDistance(DemoPanel.xratios,yratio)>maxS)
                           maxS=Sid.SidDistance(DemoPanel.xratios,yratio);
                        if(Sid.SidDistance(DemoPanel.xratios,yratio)<minS)
                          minS=Sid.SidDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }



                 if(BilImage.rM.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        minM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)>maxM)
                           maxM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                        if(Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)<minM&&Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)!=0)
                          minM=Manhattan.ManhattanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(BilImage.rBC.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        minBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)>maxBC)
                           maxBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                        if(Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)<minBC && Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)!=0)
                          minBC=Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                 if(BilImage.rCB.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        minCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)>maxCB)
                           maxCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                        if(Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)<minCB && Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)!=0)
                          minCB=Chessboard.ChessboardDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                if(BilImage.rC.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                        minC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Canberra.CanberraDistance(DemoPanel.xratios,yratio)>maxC)
                           maxC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                        if(Canberra.CanberraDistance(DemoPanel.xratios,yratio)<minC&&Canberra.CanberraDistance(DemoPanel.xratios,yratio)!=0)
                          minC=Canberra.CanberraDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                 if(BilImage.rMean.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                        minMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Mean.MeanDistance(DemoPanel.xratios,yratio)>maxMean)
                           maxMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                        if(Mean.MeanDistance(DemoPanel.xratios,yratio)<minMean&&Mean.MeanDistance(DemoPanel.xratios,yratio)!=0)
                          minMean=Mean.MeanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }

                  if(BilImage.rMedian.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        minMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(Median.MedianDistance(DemoPanel.xratios,yratio)>maxMedian)
                           maxMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                        if(Median.MedianDistance(DemoPanel.xratios,yratio)<minMedian&&Median.MedianDistance(DemoPanel.xratios,yratio)!=0)
                          minMedian=Median.MedianDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
                  if(BilImage.rNE.isSelected()==true)
                {   //System.out.println("bhak");

                    if(turn==1)
                    {
                        maxNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        minNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        //System.out.println
                    }

                    else
                    {
                       if(NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)>maxNE)
                           maxNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                        if(NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)<minNE&&NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)!=0)
                          minNE=NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio);
                    }
                 turn++;
                }
             }
      }
       /*
	  System.out.println("----");
      System.out.println(maxE+" "+minE);
	  System.out.println(maxM+" "+minM);
	  System.out.println(maxCB+" "+minCB);
	  System.out.println(maxBC+" "+minBC);
	  System.out.println(maxC+" "+minC);
	  System.out.println(maxMean+" "+minMean);
	  System.out.println(maxMedian+" "+minMedian);
	  System.out.println(maxNE+" "+minNE);
	  System.out.println("----");*/
      for(xcod=xs;xcod<=xe;xcod++)
      {
                for(ycod=ys;ycod<=ye;ycod++)
             {
                    count1++;

                            try
                            {

                            	FileInputStream fin_band=new FileInputStream(ImageFile);
                            	 FileOutputStream fout_band[]=new FileOutputStream[band1];
                            	 FileChannel fchan_in = fin_band.getChannel();
                                 try
                           	  {
                           	 	 for(int k=0; k<band1; k++)
                            	  	 {
                            			fin_band = new FileInputStream("Data" + BilImage.name +  k);


                            	     }
                            	  }
                            	  catch(Exception e)
                            	  {
                            		System.out.println("file Open Error in click : "+e);
                            		e.printStackTrace();
                            	  }
					Data1 = new ReadImageData();
					width1 = Data1.columns();
					height1 = Data1.rows();
					band1= Data1.bands();


                                        	//System.out.println("Hell");
					MappedByteBuffer mBuf;
					int bvalues[] = new int[band1];
					long lstart;
					byte b4,b5;
					int p1,p2;
					int temp,tempCount;
					int z,nob=1;
                                     System.gc();
                                     if(picture.shiftFlag==8)
                                     {
                     for(int ii=0; ii<band1; ii++)
					{

						lstart = (ycod * width1*band1) + (ii * width1)+ xcod;
						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

							b4 = mBuf.get();

							bvalues[ii] = (b4<<24) >>> 24;
					}
                                     }
                                     else
                                     {
                                    	 for(int ii=0; ii<band1; ii++)
                     					{
                                         	nob=2;
                     						lstart = ((ycod * width1*band1) + (ii * width1)+ xcod)*2;
                     						mBuf=fchan_in.map(FileChannel.MapMode.READ_ONLY,lstart,nob);

                     							b4=b5 = mBuf.get();
                     							p1 = b4<< 24;
            									p2 = (b5 <<24 ) >>> 8;
            									tempCount = p1 | p2;
            									tempCount = tempCount >>> 16;
                     							bvalues[ii] = tempCount;
                     					}
                                     }
                        for( z=0;z<band1;z++)
						yratio[z]=bvalues[z];
 				}
				catch(Exception e)
				{
					System.out.println(e);
					e.printStackTrace();
				}

                            if(BilImage.r3.isSelected()==true)
                            {
                            if(Corelation.Correlation(DemoPanel.xratios,yratio)>=threshold)

                            {
                            	count++;
                            	{
                            	  	int i,j;
                            	  	int col=1;
                            	    for(i=0;i<new ReadImageData().bands();i++)
                            		{
                            			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                            			j=(int)yratio[i]>>>8;
                           	 	        fout.write(j);
                            			fout.write((int)yratio[i]);
                            		}
                            		imagetable6.row++;

                                  }

                           }
             }
                else if(BilImage.r4.isSelected()==true)
                {
                	if(CosCorelation.CosCorrelation(DemoPanel.xratios,yratio)>=threshold)

                    {
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                else if(BilImage.rSid.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }

                   else if(BilImage.rTan.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }
                   else if(BilImage.rSin.isSelected()==true)
                {      /* if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))<min)
                           min=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);

                              if(((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS))>max )
                                    max=(Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS);*/



                	if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                        if(yratio[i]==0.0)
                                        System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                     //   System.out.println("\nMaxSid="+max+"\nMinSid="+min);
                       // System.out.println("\nMAXS:"+maxS+"\nMINS="+minS);

                }
                     else if(BilImage.rSca.isSelected()==true)
                {
                	if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {//System.out.println("\nDis::"+((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc)));
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                else if( (BilImage.rE.isSelected()==true) && (BilImage.rM.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rE.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rE.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rE.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rE.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

				else if( (BilImage.rE.isSelected()==true) && (BilImage.rCB.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rE.isSelected()==true) && (BilImage.rBC.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rE.isSelected()==true) && (BilImage.rC.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rE.isSelected()==true) && (BilImage.rMean.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rE.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rE.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rCB.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                                else if( (BilImage.rM.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rM.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rM.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rM.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rBC.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rC.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rMean.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rM.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rCB.isSelected()==true) && (BilImage.rBC.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                               else if( (BilImage.rCB.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rCB.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rCB.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rCB.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
				else if( (BilImage.rCB.isSelected()==true) && (BilImage.rC.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rCB.isSelected()==true) && (BilImage.rMean.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rCB.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rCB.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rBC.isSelected()==true) && (BilImage.rC.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                 else if( (BilImage.rBC.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rBC.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rBC.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rBC.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
				else if( (BilImage.rBC.isSelected()==true) && (BilImage.rMean.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rBC.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rBC.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rC.isSelected()==true) && (BilImage.rMean.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (BilImage.rC.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rC.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rC.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rC.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
				else if( (BilImage.rC.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rC.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rMean.isSelected()==true) && (BilImage.rMedian.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (BilImage.rMean.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rMean.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rMean.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rMean.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

				else if( (BilImage.rMean.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
				else if( (BilImage.rMedian.isSelected()==true) && (BilImage.rNE.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                                else if( (BilImage.rMedian.isSelected()==true) && (BilImage.rSid.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
						{
							count++;
							{
								int i,j;
								int col=1;
								for(i=0;i<new ReadImageData().bands();i++)
								{
									imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
									j=(int)yratio[i]>>>8;
									fout.write(j);
									fout.write((int)yratio[i]);
								}
								imagetable6.row++;
							}
						}
					}
				}
                else if( (BilImage.rMedian.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                 else if( (BilImage.rMedian.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                  else if( (BilImage.rMedian.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
                  else if( (BilImage.rSid.isSelected()==true) && (BilImage.rSin.isSelected()==true) )
				{
					if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
					{
						if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}


                 else if( (BilImage.rSid.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}
				else if( (BilImage.rSid.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((Sid.SidDistance(DemoPanel.xratios,yratio)-minS)/(maxS-minS)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)

                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

		else if( (BilImage.rSin.isSelected()==true) && (BilImage.rSca.isSelected()==true) )
				{
					if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)
					{
						if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)


                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                else if( (BilImage.rSin.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if((SinD.SinDistance(DemoPanel.xratios,yratio)-minSn)/(maxSn-minSn)<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)


                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}

                else if( (BilImage.rSca.isSelected()==true) && (BilImage.rTan.isSelected()==true) )
				{
					if(((ScaD.ScaDistance(DemoPanel.xratios,yratio)-minSc)/(maxSc-minSc))<=threshold)
					{
						if((TanD.TanDistance(DemoPanel.xratios,yratio)-minT)/(maxT-minT)<=threshold)


                    {	count++;
                       {
                          	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                                       // if(yratio[i]==0.0)
                                        //System.out.println("\nRatio::"+yratio[i]+"\ni:"+i+"\nCount:"+count);
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }}
				}


                 else if(BilImage.rE.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Euclidean.EuclideanDistance(DemoPanel.xratios,yratio)-minE)/(maxE-minE)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(BilImage.rM.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Manhattan.ManhattanDistance(DemoPanel.xratios,yratio)-minM)/(maxM-minM)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(BilImage.rCB.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Chessboard.ChessboardDistance(DemoPanel.xratios,yratio)-minCB)/(maxCB-minCB)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(BilImage.rBC.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Braycurtis.BraycurtisDistance(DemoPanel.xratios,yratio)-minBC)/(maxBC-minBC)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(BilImage.rC.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Canberra.CanberraDistance(DemoPanel.xratios,yratio)-minC)/(maxC-minC)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                 else if(BilImage.rMean.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Mean.MeanDistance(DemoPanel.xratios,yratio)-minMean)/(maxMean-minMean)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                 else if(BilImage.rMedian.isSelected()==true)
                {   //System.out.println("bhak");


                	if((Median.MedianDistance(DemoPanel.xratios,yratio)-minMedian)/(maxMedian-minMedian)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }

                 else if(BilImage.rNE.isSelected()==true)
                {   //System.out.println("bhak");


                	if((NormalisedEuclidean.NormalisedEuclideanDistance(DemoPanel.xratios,yratio)-minNE)/(maxNE-minNE)<=threshold)

                    {
                    	count++;

                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                else
                {
                	if(Corelation.Correlation(DemoPanel.xratios,yratio)>=threshold)
                	{
                  if(CosCorelation.CosCorrelation(DemoPanel.xratios,yratio)>=threshold)

                    {
                    	count++;
                    	{
                    	  	int i,j;
                    	  	int col=1;
                    	    for(i=0;i<new ReadImageData().bands();i++)
                    		{
                    			imagetable6.model.setValueAt(yratio[i],imagetable6.row,col++);
                    			j=(int)yratio[i]>>>8;
                   	 	        fout.write(j);
                    			fout.write((int)yratio[i]);
                    		}
                    		imagetable6.row++;

                          }

                   }
                }
                }
      }
      }

      System.out.println("Total number of pixels investigated:"+count1);
      System.out.println("Number of similar pixels:"+count);
      System.out.print("Training Samples calculated Succesfully..");
		}
		catch(Exception e)
		{
			 System.out.print(e);
			 e.printStackTrace();
		}
		}
  }
}
